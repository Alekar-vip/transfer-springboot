package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.entities.PartitionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartitionEntityMapper {
    Partition partitionEntityToPartition(PartitionEntity partitionEntity);
    PartitionEntity partitionToPartitionEntity(Partition partition);
}
