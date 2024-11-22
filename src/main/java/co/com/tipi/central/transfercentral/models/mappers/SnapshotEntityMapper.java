package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.entities.SnapshotEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SnapshotEntityMapper {

    Snapshot snapshotEntityToSnapshot(SnapshotEntity snapshotEntity);
}
