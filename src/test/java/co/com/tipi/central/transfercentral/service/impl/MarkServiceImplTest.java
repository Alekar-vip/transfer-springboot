package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.entities.FileTagedEntity;
import co.com.tipi.central.transfercentral.models.entities.MarkEntity;
import co.com.tipi.central.transfercentral.models.mappers.MarkEntityMapper;
import co.com.tipi.central.transfercentral.repositories.MarkRepository;
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

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class MarkServiceImplTest {
    @InjectMocks
    private MarkServiceImpl markService;

    @Mock
    private MarkRepository markRepository;
    @Mock
    private MarkEntityMapper markEntityMapper;

    private Mark mark;
    private MarkEntity markEntity;


    @BeforeEach
    void setUp() {
        mark = new Mark();
        mark.setId(1);
        mark.setPathInitial("setPathInitial");
        mark.setPathEnd("setPathEnd");
        mark.setTransferredAt(ZonedDateTime.now());
        mark.setFileTagedInit(new FileTaged());
        mark.setFileTagedEnd(new FileTaged());

        markEntity = new MarkEntity();
        markEntity.setId(mark.getId());
        markEntity.setPathInitial(mark.getPathInitial());
        markEntity.setPathEnd(mark.getPathEnd());
        markEntity.setTransferredAt(mark.getTransferredAt());
        markEntity.setFileTagedInit(new FileTagedEntity());
        markEntity.setFileTagedEnd(new FileTagedEntity());

    }

    @Test
    void findAllByTransferredAtNotNull() {
        List<MarkEntity> list = new ArrayList<>();
        list.add(markEntity);
        when(markRepository.fetchAllByTransferredAtIsNull()).thenReturn(list);
        when(markEntityMapper.markEntityToMark(markEntity)).thenReturn(mark);
        var result = markService.findAllByTransferredAtIsNull();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(), result.size());
    }
    @Test
    void findAllByTransferredAtIsNull() {
        List<Mark> markList = new ArrayList<>();
        when(markRepository.fetchAllByTransferredAtIsNull()).thenReturn(Collections.emptyList());
        var result = markService.findAllByTransferredAtIsNull();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(markList, result);
    }
    @Test
    void save() {
        when(markRepository.fetchById(mark.getId())).thenReturn(markEntity);
        markService.save(mark);
        Assertions.assertNotNull(mark);
        Assertions.assertNotNull(markEntity);
    }
}