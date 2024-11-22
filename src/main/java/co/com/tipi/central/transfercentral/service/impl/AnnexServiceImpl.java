package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.entities.AnnexEntity;
import co.com.tipi.central.transfercentral.models.mappers.AnnexEntityMapper;
import co.com.tipi.central.transfercentral.repositories.AnnexRepository;
import co.com.tipi.central.transfercentral.service.AnnexService;
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
public class AnnexServiceImpl implements AnnexService {

    final AnnexRepository annexRepository;
    final AnnexEntityMapper annexEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Annex> findAllByTransferredAtIsNull() {
        List<AnnexEntity> listAnnex = annexRepository.fetchAllByTransferredAtIsNull();
        if (listAnnex == null || listAnnex.isEmpty()) {
            return Collections.emptyList();
        }
        return listAnnex.stream().map(annexEntityMapper::annexEntityToAnnex).toList();
    }

    @Override
    @Transactional
    public void save(Annex annex) {
        AnnexEntity entity = annexRepository.fetchById(annex.getId());
        entity.setPath(annex.getPath());
        entity.setTransferredAt(annex.getTransferredAt());
        annexRepository.save(entity);
    }
}
