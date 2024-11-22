package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.entities.PartitionEntity;
import co.com.tipi.central.transfercentral.models.enums.PartitionType;
import co.com.tipi.central.transfercentral.models.exceptions.NotPartitionException;
import co.com.tipi.central.transfercentral.models.mappers.PartitionEntityMapper;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import co.com.tipi.central.transfercentral.service.AnnexService;
import co.com.tipi.central.transfercentral.service.FileTagedService;
import co.com.tipi.central.transfercentral.service.MarkService;
import co.com.tipi.central.transfercentral.service.SnapshotService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartitionServiceImplTest {

    @InjectMocks
    private PartitionServiceImpl partitionService;

    @Mock
    private PartitionRepository partitionRepository;

    @Mock
    private PartitionEntityMapper partitionEntityMapper;

    @Mock
    private FileTagedService fileTagedService;

    @Mock
    private AnnexService annexService;

    @Mock
    private SnapshotService snapshotService;

    @Mock
    private MarkService markService;

    private List<Partition> listPartitions;
    private boolean endPartitions = false;

    private Partition partition;

    private PartitionEntity partitionEntity;

    private final String ERROR_MESSAGE = "No hay particiones disponibles";

    @BeforeEach
    void setUp() {
        partition = new Partition();
        partition.setId(1);
        partition.setActive(true);
        partition.setName("name");
        partition.setFillingPriority(1);
        partition.setConfigJson("{type: \"type\", user: \"user\", pass: \"pass\", url: \"url\"}");
        partition.setTotalSize(100);
        partition.setPartitionType(PartitionType.LOCAL);
        partition.setCreatedAt(ZonedDateTime.now());
        partition.setUpdatedAt(ZonedDateTime.now());

        partitionEntity = new PartitionEntity();
        partitionEntity.setId(partition.getId());
        partitionEntity.setActive(partition.isActive());
        partitionEntity.setFillingPriority(partition.getFillingPriority());
        partitionEntity.setConfigJson(partition.getConfigJson());
        partitionEntity.setTotalSize(partition.getTotalSize());
        partitionEntity.setPartitionType(partition.getPartitionType());
        partitionEntity.setCreatedAt(partition.getCreatedAt());
        partitionEntity.setUpdatedAt(partition.getUpdatedAt());

        ReflectionTestUtils.setField(partitionService, "gson", new Gson());
    }

    @Test
    void when_getPartitionData_is_null() {
        //Given
        Partition partition1 = null;
        endPartitions = true;
        listPartitions = null;
        List<PartitionEntity> partitionEntities = new ArrayList<>();
        partitionEntities.add(partitionEntity);
        //When findAll is not empty
        when(partitionRepository.fetchAllOrderByPriority()).thenReturn(partitionEntities);
        when(partitionEntityMapper.partitionEntityToPartition(partitionEntity)).thenReturn(partition);
        //Then
        Exception exception = assertThrows(NotPartitionException.class, () -> {
            partitionService.getPartitionData(partition1);
        });
        Assertions.assertEquals(ERROR_MESSAGE, exception.getMessage());
        verify(partitionRepository).fetchAllOrderByPriority();
        verify(partitionEntityMapper).partitionEntityToPartition(partitionEntity);
    }

    @Test
    void when_getPartitionData_is_null_2() {
        //Given
        Partition partition1 = null;
        endPartitions = true;
        listPartitions = null;
        List<PartitionEntity> partitionEntities = new ArrayList<>();
        partitionEntities.add(partitionEntity);
        //When findAll is emtyList
        when(partitionRepository.fetchAllOrderByPriority()).thenReturn(Collections.emptyList());
        //Then
        Exception exception = assertThrows(NotPartitionException.class, () -> {
            partitionService.getPartitionData(partition1);
        });
        Assertions.assertEquals(ERROR_MESSAGE, exception.getMessage());
        verify(partitionRepository).fetchAllOrderByPriority();
    }

    @Test
    void when_getPartitionData_is_null_3() {
        //Given
        Partition partition1 = null;
        endPartitions = true;
        listPartitions = null;
        List<PartitionEntity> partitionEntities = new ArrayList<>();
        partitionEntities.add(partitionEntity);
        //When findAll is null
        when(partitionRepository.fetchAllOrderByPriority()).thenReturn(null);
        //Then
        Exception exception = assertThrows(NotPartitionException.class, () -> {
            partitionService.getPartitionData(partition1);
        });
        Assertions.assertEquals(ERROR_MESSAGE, exception.getMessage());
        verify(partitionRepository).fetchAllOrderByPriority();
    }


    @Test
    void when_getPartitionData_is_nul_4(){
        //Given
        Partition partition1 = null;
        endPartitions = true;
        listPartitions = null;
        List<PartitionEntity> partitionEntities = new ArrayList<>();
        partitionEntities.add(partitionEntity);
        //When config type is LOCAL
        partition.setConfigJson(
                "{type: \"LOCAL\", user: \"user\", pass: \"pass\", url: \"url\"}"
        );
        when(partitionRepository.fetchAllOrderByPriority()).thenReturn(partitionEntities);
        when(partitionEntityMapper.partitionEntityToPartition(partitionEntity)).thenReturn(partition);
        //Then
        Exception exception = assertThrows(NotPartitionException.class, () -> {
            partitionService.getPartitionData(partition1);
        });
    }
}