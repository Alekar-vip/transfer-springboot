package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.PartitionConfig;
import co.com.tipi.central.transfercentral.models.mappers.PartitionEntityMapper;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PartitionActionServiceImplLocal extends PartitionActionServiceRedLocal {

    public PartitionActionServiceImplLocal(PartitionRepository partitionRepository, PartitionEntityMapper partitionEntityMapper) {
        super(partitionRepository, partitionEntityMapper);
    }

    @Override
    public boolean validateConnection(Partition partition, PartitionConfig config) {
        return new File(config.getUrl()).exists();
    }
}
