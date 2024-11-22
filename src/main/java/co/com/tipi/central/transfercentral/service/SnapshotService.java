package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Snapshot;

import java.util.List;

public interface SnapshotService {

    List<Snapshot> findAllByTransferredAtIsNull();

    void save(Snapshot snapshot);
}
