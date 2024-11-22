package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.Tag;
import co.com.tipi.central.transfercentral.models.entities.FileTagedEntity;
import co.com.tipi.central.transfercentral.models.entities.PartitionEntity;
import co.com.tipi.central.transfercentral.models.mappers.FileTagedEntityMapper;
import co.com.tipi.central.transfercentral.repositories.FileTagedRepository;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileTagedServiceImplTest {
    @InjectMocks
    private FileTagedServiceImpl fileService;
    @Mock
    private FileTagedRepository fileTagedRepository;
    @Mock
    private FileTagedEntityMapper fileTagedEntityMapper;
    @Mock
    private PartitionRepository partitionRepository;

    private FileTaged fileTaged;

    @BeforeEach
    void setUp(){
        fileTaged = new FileTaged();
        fileTaged.setId(1);
        fileTaged.setPath("pathprueba");
        fileTaged.setSnapshotPath("snapshotPath");
        fileTaged.setTransferredAt(ZonedDateTime.now());
        fileTaged.setTag(new Tag());
        fileTaged.setPartition(new Partition());
        fileTaged.setUpdatedAt(ZonedDateTime.now());
    }
    @Test
    void when_findAllByTransferredAtIsNull_if_null() {
        FileTagedEntity fileTagedEntity = null;
        List<FileTagedEntity> fileTagedEntityList = new ArrayList<>();
        fileTagedEntityList.add(fileTagedEntity);
        when(fileTagedRepository.fetchAllByTransferredAtIsNull()).thenReturn(fileTagedEntityList);
        fileService.findAllByTransferredAtIsNull();
        Assertions.assertNull(fileTagedEntity);
        Assertions.assertNull(fileTagedEntity,"All elements are null");
    }
    @Test
    void when_findAllByTransferredAtIsNull_isEmpty() {
        List<FileTagedEntity> fileTaggedEntityList =  new ArrayList<>();
        when(fileTagedRepository.fetchAllByTransferredAtIsNull()).thenReturn(fileTaggedEntityList);
        fileService.findAllByTransferredAtIsNull();
        Assertions.assertTrue(fileTaggedEntityList.isEmpty());
    }
    @Test
    void when_findAllByTransferredAtIsNull_isElse() {
        FileTagedEntity fileTagedEntity = new FileTagedEntity();
        fileTagedEntity.setId(1);
        fileTagedEntity.setPath("pathname");
        List<FileTagedEntity> fileTaggedEntityList = new ArrayList<>();
        fileTaggedEntityList.add(fileTagedEntity);
        when(fileTagedRepository.fetchAllByTransferredAtIsNull()).thenReturn(fileTaggedEntityList);
        fileService.findAllByTransferredAtIsNull();
        Assertions.assertNotNull(fileTaggedEntityList);
        Assertions.assertNotNull(fileTagedEntity);
    }
    @Test
    void save() {
        FileTagedEntity fileTagedEntity = new FileTagedEntity();
        fileTagedEntity.setId(fileTaged.getId());
        fileTagedEntity.setSnapshotPath(fileTaged.getSnapshotPath());
        fileTagedEntity.setTransferredAt(fileTaged.getTransferredAt());
        fileTagedEntity.setUpdatedAt(fileTaged.getTransferredAt());
        fileTagedEntity.setPartition(partitionRepository.fetchById(fileTaged.getPartition().getId()));
        when(fileTagedRepository.fetchById(fileTaged.getId())).thenReturn(fileTagedEntity);
        when(partitionRepository.fetchById(fileTaged.getPartition().getId())).thenReturn(new PartitionEntity());
        fileService.save(fileTaged);
        Assertions.assertNotNull(fileTaged);
        Assertions.assertNotNull(fileTagedEntity);
    }
}