package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.entities.SnapshotEntity;
import co.com.tipi.central.transfercentral.models.mappers.SnapshotEntityMapper;
import co.com.tipi.central.transfercentral.repositories.SnapshotRepository;
import co.com.tipi.central.transfercentral.service.SnapshotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class SnapshotServiceImpl implements SnapshotService {

    final SnapshotRepository snapshotRepository;
    final SnapshotEntityMapper snapshotEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Snapshot> findAllByTransferredAtIsNull() {
        List<SnapshotEntity> listSnapshot = snapshotRepository.fetchAllByTransferredAtIsNull();
        if (listSnapshot == null || listSnapshot.isEmpty()) {
            return Collections.emptyList();
        }
        return listSnapshot.stream().map(snapshotEntityMapper::snapshotEntityToSnapshot).toList();
    }

    @Override
    @Transactional
    public void save(Snapshot snapshot) {
        SnapshotEntity entity = snapshotRepository.fetchById(snapshot.getId());
        entity.setPath(snapshot.getPath());
        entity.setTransferredAt(snapshot.getTransferredAt());
        snapshotRepository.save(entity);
    }
}
