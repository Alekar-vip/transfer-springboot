package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.PartitionConfig;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.domains.Mark;

public interface PartitionActionService {
    Integer MIN_PARTITION_AVAILABLE_SIZE_MB = 300;

    boolean validateConnection(Partition partition, PartitionConfig config);
    boolean validateAvailableSize(Partition partition, PartitionConfig config);
    FileTaged transferFile(Partition partition, PartitionConfig partitionConfig, FileTaged fileTaged);
    Annex transferAnnex(PartitionConfig partitionConfig, Annex annex);
    Mark transferMark(PartitionConfig partitionConfigInit, PartitionConfig partitionConfigEnd, Mark mark);
    Snapshot transferSnapshot(PartitionConfig partitionConfig, Snapshot snapshot);
}
