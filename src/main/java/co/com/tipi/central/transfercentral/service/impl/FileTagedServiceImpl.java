package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.entities.FileTagedEntity;
import co.com.tipi.central.transfercentral.models.mappers.FileTagedEntityMapper;
import co.com.tipi.central.transfercentral.repositories.FileTagedRepository;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import co.com.tipi.central.transfercentral.service.FileTagedService;

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
public class FileTagedServiceImpl implements FileTagedService {

    final FileTagedRepository fileTagedRepository;
    final FileTagedEntityMapper fileTagedEntityMapper;
    final PartitionRepository partitionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FileTaged> findAllByTransferredAtIsNull() {
        List<FileTagedEntity> listFiles = fileTagedRepository.fetchAllByTransferredAtIsNull();
        if (listFiles == null || listFiles.isEmpty()) {
            return Collections.emptyList();
        }
        return fileTagedEntityMapper.fileEntityToFileList(listFiles);
    }

    @Override
    @Transactional
    public void save(FileTaged fileTaged) {
        FileTagedEntity entity = fileTagedRepository.fetchById(fileTaged.getId());
//        entity.setPath(fileTaged.getPath());
//        entity.setSnapshotPath(fileTaged.getSnapshotPath());
        entity.setTransferredAt(fileTaged.getTransferredAt());
        entity.setUpdatedAt(fileTaged.getTransferredAt());
        entity.setPartition(partitionRepository.fetchById(fileTaged.getPartition().getId()));
        fileTagedRepository.save(entity);
    }
}
