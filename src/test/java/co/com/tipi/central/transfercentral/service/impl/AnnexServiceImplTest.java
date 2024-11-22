package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.entities.AnnexEntity;
import co.com.tipi.central.transfercentral.models.mappers.AnnexEntityMapper;
import co.com.tipi.central.transfercentral.repositories.AnnexRepository;
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
class AnnexServiceImplTest {
    @InjectMocks
    private AnnexServiceImpl annexService;

    @Mock
    private AnnexRepository annexRepository;
    @Mock
    private AnnexEntityMapper annexEntityMapper;

    private Annex annex;
    private AnnexEntity entity;

    @BeforeEach
    void setUp() {
        annex = new Annex();
        annex.setId(1);
        annex.setPath("prue");
        annex.setTransferredAt(ZonedDateTime.now());
        annex.setFileTaged(new FileTaged());

        entity = new AnnexEntity();
        entity.setId(annex.getId());
        entity.setPath(annex.getPath());
        entity.setTransferredAt(annex.getTransferredAt());
    }


    @Test
    void findAllByTransferredAtIsNotNull() {
        List<AnnexEntity> annexEntityList = Stream.of(new AnnexEntity(), new AnnexEntity()).collect(Collectors.toList());
        when(annexRepository.fetchAllByTransferredAtIsNull()).thenReturn(annexEntityList);
        when(annexEntityMapper.annexEntityToAnnex(new AnnexEntity())).thenReturn(new Annex());
        var result = annexService.findAllByTransferredAtIsNull();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(annexEntityList.size(), result.size());
    }

    @Test
    void findAllByTransferredAtIsNull() {
        List<Annex> list = new ArrayList<>();
        when(annexRepository.fetchAllByTransferredAtIsNull()).thenReturn(Collections.emptyList());
        var result = annexService.findAllByTransferredAtIsNull();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals(list, result);

    }

    @Test
    void save() {
        when(annexRepository.fetchById(annex.getId())).thenReturn(entity);
        annexService.save(annex);
        Assertions.assertNotNull(annex);
        Assertions.assertNotNull(entity);
    }
}
