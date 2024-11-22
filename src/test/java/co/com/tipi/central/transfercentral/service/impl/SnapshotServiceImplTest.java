package co.com.tipi.central.transfercentral.service.impl;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.entities.SnapshotEntity;
import co.com.tipi.central.transfercentral.models.mappers.SnapshotEntityMapper;
import co.com.tipi.central.transfercentral.repositories.SnapshotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SnapshotServiceImplTest {

    @InjectMocks
    private SnapshotServiceImpl snapshotService;
    @Mock
    private SnapshotRepository snapshotRepository;
    @Mock
    private SnapshotEntityMapper snapshotEntityMapper;
    private Snapshot snapshot;
    private SnapshotEntity snapshotEntity;

    @BeforeEach
    void setUp() {
        snapshot = new Snapshot();
        snapshot.setId(1);
        snapshot.setPath("setPath");
        snapshot.setTransferredAt(ZonedDateTime.now());
        snapshot.setFileTaged(new FileTaged());

        snapshotEntity = new SnapshotEntity();
        snapshotEntity.setId(snapshot.getId());
        snapshotEntity.setPath(snapshot.getPath());
        snapshotEntity.setTransferredAt(snapshot.getTransferredAt());
        snapshotEntity.setCreatedAt(ZonedDateTime.now());
        snapshotEntity.setUpdatedAt(ZonedDateTime.now());
    }

    @Test
    void when_TransferredAt_IsEmpty() {
        List<Snapshot> list = new ArrayList<>();
        when(snapshotRepository.fetchAllByTransferredAtIsNull()).thenReturn(Collections.emptyList());
        var result = snapshotService.findAllByTransferredAtIsNull();
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(0, result.size());
    }
    @Test
    void when_TransferredAt_IsNull() {
        snapshotEntity = null;
        List<Snapshot> list = new ArrayList<>();
        list.add(snapshotEntityMapper.snapshotEntityToSnapshot(snapshotEntity));
        when(snapshotRepository.fetchAllByTransferredAtIsNull()).thenReturn(new ArrayList<>());
        var result = snapshotService.findAllByTransferredAtIsNull();
        Assertions.assertNull(snapshotEntity);
        Assertions.assertNull(snapshotEntity,"All elements are null");
    }
    @Test
    void when_TransferredAt_IsNotNull() {
        List<SnapshotEntity> list = Stream.of(snapshotEntity, snapshotEntity).collect(Collectors.toList());
        when(snapshotRepository.fetchAllByTransferredAtIsNull()).thenReturn(list);
        when(snapshotEntityMapper.snapshotEntityToSnapshot(snapshotEntity)).thenReturn(snapshot);
        var result = snapshotService.findAllByTransferredAtIsNull();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(list.size(), result.size());
    }

    @Test
    void save() {
        when(snapshotRepository.fetchById(snapshot.getId())).thenReturn(snapshotEntity);
        snapshotService.save(snapshot);
        Assertions.assertNotNull(snapshot);
        Assertions.assertNotNull(snapshotEntity);
    }
}