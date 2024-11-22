package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.PartitionConfig;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.exceptions.NotImplementException;
import co.com.tipi.central.transfercentral.models.mappers.PartitionEntityMapper;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import co.com.tipi.central.transfercentral.service.PartitionActionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PartitionActionServiceImplAzure implements PartitionActionService {
    final PartitionRepository partitionRepository;
    final PartitionEntityMapper partitionEntityMapper;

    @Override
    public boolean validateConnection(Partition partition, PartitionConfig config) {
        throw new NotImplementException();
    }

    @Override
    public boolean validateAvailableSize(Partition partition, PartitionConfig config) {
        throw new NotImplementException();
    }

    @Override
    public FileTaged transferFile(Partition partition, PartitionConfig partitionConfig, FileTaged fileTaged) {
        throw new NotImplementException();
    }

    @Override
    public Annex transferAnnex(PartitionConfig partitionConfig, Annex annex) {
        throw new NotImplementException();
    }

    @Override
    public Mark transferMark(PartitionConfig partitionConfigInit, PartitionConfig partitionConfigEnd, Mark mark) {
        throw new NotImplementException();
    }

    @Override
    public Snapshot transferSnapshot(PartitionConfig partitionConfig, Snapshot snapshot) {
        throw new NotImplementException();
    }
}
