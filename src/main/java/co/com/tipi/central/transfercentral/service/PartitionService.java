package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;

import java.util.List;


public interface PartitionService {
    Partition getPartitionData(Partition partition);
    List<Partition> findAll();
    void transferFile(Partition partition, FileTaged fileTaged);
    void transferAnnex(Partition partition, Annex annex);
    void transferSnapshot(Partition partition, Snapshot snapshot);
    void transferMark(Partition partitionInit, Partition partitionEnd, Mark mark);

}
