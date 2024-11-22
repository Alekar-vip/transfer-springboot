package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.entities.MarkEntity;
import co.com.tipi.central.transfercentral.models.mappers.MarkEntityMapper;
import co.com.tipi.central.transfercentral.repositories.MarkRepository;
import co.com.tipi.central.transfercentral.service.MarkService;
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
public class MarkServiceImpl implements MarkService {

    final MarkRepository markRepository;
    final MarkEntityMapper markEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Mark> findAllByTransferredAtIsNull() {
        List<MarkEntity> listMark = markRepository.fetchAllByTransferredAtIsNull();
        if (listMark == null || listMark.isEmpty()) {
            return Collections.emptyList();
        }
        return listMark.stream().map(markEntityMapper::markEntityToMark).toList();
    }

    @Override
    @Transactional
    public void save(Mark mark) {
        MarkEntity entity = markRepository.fetchById(mark.getId());
        entity.setTransferredAt(mark.getTransferredAt());
        entity.setPathInitial(mark.getPathInitial());
        entity.setPathEnd(mark.getPathEnd());
        markRepository.save(entity);
    }
}
