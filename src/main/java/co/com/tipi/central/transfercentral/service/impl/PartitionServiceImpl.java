package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.PartitionConfig;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.entities.PartitionEntity;
import co.com.tipi.central.transfercentral.models.enums.PartitionType;
import co.com.tipi.central.transfercentral.models.exceptions.NotPartitionException;
import co.com.tipi.central.transfercentral.models.mappers.PartitionEntityMapper;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import co.com.tipi.central.transfercentral.service.AnnexService;
import co.com.tipi.central.transfercentral.service.FileTagedService;
import co.com.tipi.central.transfercentral.service.MarkService;
import co.com.tipi.central.transfercentral.service.PartitionActionService;
import co.com.tipi.central.transfercentral.service.PartitionService;
import co.com.tipi.central.transfercentral.service.SnapshotService;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class PartitionServiceImpl implements PartitionService {
    private static final String ERROR_MESSAGE = "No hay particiones disponibles";
    final Gson gson;
    final PartitionRepository partitionRepository;
    final PartitionEntityMapper partitionEntityMapper;
    final FileTagedService fileTagedService;
    final AnnexService annexService;
    final SnapshotService snapshotService;
    final MarkService markService;
    private List<Partition> listPartitions;
    private boolean endPartitions = false;


    @Override
    public Partition getPartitionData(Partition partition) {
        endPartitions = true;
        do {
            if (partition == null) {
                partition = findMaxPriorityPartition();
            }
            if (validatePartition(partition)){
                endPartitions = false;
            } else {
                partition = null;
            }
        }while (endPartitions);

        if (partition == null) {
            throw new NotPartitionException(ERROR_MESSAGE);
        }

        listPartitions = null;
        return partition;
    }

    private boolean validatePartition(Partition partition) {
        PartitionConfig config = gson.fromJson(partition.getConfigJson(), PartitionConfig.class);
        return validatePartitionAction(partition, getConfig(partition), getPartitionInstance(partition, config));
    }


    private PartitionActionService getPartitionInstance(Partition partition, PartitionConfig config) {
        if (partition.getPartitionType().equals(PartitionType.LOCAL)) {
            if (config.getType().equalsIgnoreCase("DOCKING") || config.getType().equalsIgnoreCase("RED")) {
                return new PartitionActionServiceImplRed(partitionRepository, partitionEntityMapper);
            } else if (config.getType().equalsIgnoreCase("LOCAL")) {
                return new PartitionActionServiceImplLocal(partitionRepository, partitionEntityMapper);
            }
        } else if (partition.getPartitionType().equals(PartitionType.NUBE)) {
            if (config.getType().equalsIgnoreCase("AWS")) {
                return new PartitionActionServiceImplAWS(partitionRepository, partitionEntityMapper);
            } else if (config.getType().equalsIgnoreCase("AZURE")) {
                return new PartitionActionServiceImplAzure(partitionRepository, partitionEntityMapper);
            } else if (config.getType().equalsIgnoreCase("GOOGLE CLOUD")) {
                return new PartitionActionServiceImplCloud(partitionRepository, partitionEntityMapper);
            }
        }
        return null;
    }


    private boolean validatePartitionAction(Partition partition, PartitionConfig config, PartitionActionService partitionActionService) {
        if (partitionActionService == null) {
            return false;
        }
        return partitionActionService.validateConnection(partition, config) &&
                partitionActionService.validateAvailableSize(partition, config);
    }


    private Partition findMaxPriorityPartition() {
        if (listPartitions == null) {
            listPartitions = findAll();
        }
        Partition selectPartition = listPartitions.get(0);
        listPartitions = listPartitions.stream().filter(partition -> !Objects.equals(partition.getId(), selectPartition.getId())).toList();
        if (listPartitions.isEmpty()){
            endPartitions = false;
            listPartitions = null;
        }
        return selectPartition;
    }

    @Override
    public List<Partition> findAll() {
        List<PartitionEntity> partitionEntities = partitionRepository.fetchAllOrderByPriority();
        if (partitionEntities == null || partitionEntities.isEmpty()) {
            throw new NotPartitionException(ERROR_MESSAGE);
        }
        return partitionEntities.stream().map(partitionEntityMapper::partitionEntityToPartition).toList();
    }

    private PartitionConfig getConfig(Partition partition) {
        PartitionConfig config = gson.fromJson(partition.getConfigJson(), PartitionConfig.class);
        if (config == null) {
            throw new NotPartitionException("No se puede extraer la configuracion.");
        }
        return config;
    }

    @Override
    public void transferFile(Partition partition, FileTaged fileTaged) {
        PartitionActionService partitionActionService = getPartitionInstance(partition, getConfig(partition));
        if (partitionActionService != null) {
            var resp = partitionActionService.transferFile(partition, getConfig(partition), fileTaged);
            if(resp.getTransferredAt()!=null)
                fileTagedService.save(resp);
        }
    }

    @Override
    public void transferAnnex(Partition partition, Annex annex) {
        PartitionActionService partitionActionService = getPartitionInstance(partition, getConfig(partition));
        if (partitionActionService != null) {
            var resp = partitionActionService.transferAnnex(getConfig(partition), annex);
            log.info("repuesta anexo a guardar {}", resp);
            annexService.save(resp);
        }
    }

    @Override
    public void transferSnapshot(Partition partition, Snapshot snapshot) {
        PartitionActionService partitionActionService = getPartitionInstance(partition, getConfig(partition));
        if (partitionActionService != null) {
            var resp = partitionActionService.transferSnapshot(getConfig(partition), snapshot);
            snapshotService.save(resp);
        }
    }

    @Override
    public void transferMark(Partition partitionInit, Partition partitionEnd, Mark mark) {
        PartitionActionService partitionActionService = getPartitionInstance(partitionInit, getConfig(partitionInit));
        if (partitionActionService != null) {
            var resp = partitionActionService.transferMark(getConfig(partitionInit), getConfig(partitionEnd), mark);
            markService.save(resp);
        }
    }
}
