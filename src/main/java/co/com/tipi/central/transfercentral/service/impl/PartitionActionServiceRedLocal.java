package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.PartitionConfig;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.domains.file.FilePath;
import co.com.tipi.central.transfercentral.models.exceptions.NotFileException;
import co.com.tipi.central.transfercentral.models.mappers.PartitionEntityMapper;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import co.com.tipi.central.transfercentral.service.PartitionActionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public abstract class PartitionActionServiceRedLocal implements PartitionActionService {

    final PartitionRepository partitionRepository;
    final PartitionEntityMapper partitionEntityMapper;

    private String basePathFile;

    @Override
    @Transactional
    public boolean validateAvailableSize(Partition partition, PartitionConfig config) {
        try {
            final String splitValue = "\\.";
            Path path = Path.of(config.getUrl());
            FileStore fileStore = Files.getFileStore(path);
            var freeSpaceMB = String.valueOf(fileStore.getUsableSpace() / 1048576);
            var totalSpaceMB = String.valueOf(fileStore.getTotalSpace() / 1048576);

            partition.setAvailableSize(Integer.parseInt(freeSpaceMB.split(splitValue)[0]));
            partition.setTotalSize(Integer.parseInt(totalSpaceMB.split(splitValue)[0]));
            partitionRepository.save(partitionEntityMapper.partitionToPartitionEntity(partition));
        } catch (IOException e) {
            log.error("Error al consultar el espacio de la unidad: " + partition.toString(), e);
        }
        return partition.getAvailableSize() > MIN_PARTITION_AVAILABLE_SIZE_MB;
    }

    @Override
    @Transactional
    public FileTaged transferFile(Partition partition, PartitionConfig partitionConfig, FileTaged fileTaged) {
        getBasePathFile();
        FilePath file = getFilePath(fileTaged.getPath(), partitionConfig);
        FilePath snapshot = getFilePath(fileTaged.getSnapshotPath(), partitionConfig);
        boolean isValidFile = transferFileUnit(fileTaged, file, "Archivo");
        boolean isValidSnapshot = true;
        if (fileTaged.getSnapshotPath() != null) {
             isValidSnapshot = transferFileUnit(fileTaged, snapshot, "Snapshot");
        }
        fileTaged.setPartition(partition);
        if(isValidFile && isValidSnapshot)
            fileTaged.setTransferredAt(ZonedDateTime.now());
        return fileTaged;
    }

    @Override
    @Transactional
    public Annex transferAnnex(PartitionConfig partitionConfig, Annex annex) {
        getBasePathFile();
        FilePath path = getFilePath(annex.getPath(), partitionConfig);
        log.info(" Pat converido a file java: {}", path);
        if (isValidPathFiles(path)) {
            moveFileAction(path);
            log.info("ruta del nuevo path: {}", getFilePath(replacePath(annex.getPath()), partitionConfig));
            annex.setPath(getNewFilePath(replacePath(annex.getPath()), partitionConfig));
            annex.setTransferredAt(ZonedDateTime.now());
        } else {
            log.warn(getExceptionMessage("Anexo", annex.getId(), annex.getPath()));
        }
        return annex;
    }

    @Override
    @Transactional
    public Mark transferMark(PartitionConfig partitionConfigInit, PartitionConfig partitionConfigEnd, Mark mark) {
        getBasePathFile();
        FilePath newPath = getFilePath(mark.getPathInitial(), partitionConfigInit);
        FilePath oldPad = getFilePath(mark.getPathEnd(), partitionConfigEnd);
        if (isValidPathFiles(newPath) && isValidPathFiles(oldPad)) {
            moveFileAction(newPath);
            moveFileAction(oldPad);
        } else {
            log.warn(getExceptionMessage("Marca", mark.getId(), mark.getPathInitial()));
        }
        mark.setTransferredAt(ZonedDateTime.now());
        mark.setPathInitial(getNewFilePath(replacePath(mark.getPathInitial()), partitionConfigInit));
        mark.setPathEnd(getNewFilePath(replacePath(mark.getPathEnd()), partitionConfigEnd));
        return mark;
    }

    @Override
    @Transactional
    public Snapshot transferSnapshot(PartitionConfig partitionConfig, Snapshot snapshot) {
        getBasePathFile();
        FilePath path = getFilePath(snapshot.getPath(), partitionConfig);
        if (isValidPathFiles(path)) {
            moveFileAction(path);
        } else {
            log.warn(getExceptionMessage("Snapshot", snapshot.getId(), snapshot.getPath()));
        }
        snapshot.setTransferredAt(ZonedDateTime.now());
        snapshot.setPath(getNewFilePath(replacePath(snapshot.getPath()), partitionConfig));
        return snapshot;
    }

    public boolean transferFileUnit(FileTaged fileTaged, FilePath filePath, String fileName){
        if (isValidPathFiles(filePath)) {
            moveFileAction(filePath);
        } else {
            log.warn(getExceptionMessage(fileName, fileTaged.getId(), filePath.getOldPath()));
            String var = System.getenv("TRANSFER_IS_ALLOW_WITH_EXCEPTION");
            if(var.equals("true")){
                log.info("el video ya no existe, se modifica transferredAt");
                fileTaged.setTransferredAt(ZonedDateTime.now());
                return false;
            }
        }
        return true;
    }


    private void moveFileAction(FilePath filePath) {
        try {
            Path sourceFile = Paths.get(filePath.getOldPath());
            Path destinationFile = Paths.get(filePath.getNewPath().replace("000000000", ""));
            File folder = new File(filePath.getNewPath().replace("000000000", "").substring(0, filePath.getNewPath().replace("000000000", "").lastIndexOf('/')));
            if (!folder.exists()) {
                folder.mkdirs();
            }
            log.info("rutas:{},{}", sourceFile, destinationFile);
            Files.move(sourceFile, destinationFile);
        } catch (Exception e) {
            log.error("Error al mover el archivo", e);
            throw new NotFileException("Error al mover el archivo");
        }
    }

    private boolean isValidPathFiles(FilePath filePath) {
        return new File(filePath.getOldPath()).exists();
    }

    private String getActualFilePath(String path) {
        return validateLastChartBasePath(basePathFile) + path;
    }

    private String getNewFilePath(String path, PartitionConfig config) {
        return validateLastChartBasePath(config.getUrl()) + path;
    }

    private String validateLastChartBasePath(String path) {
        String lastChar = String.valueOf(path.charAt(path.length() - 1));
        if (lastChar.equals("/")) {
            return path;
        } else {
            return path + "/";
        }
    }

    private void getBasePathFile() {
        basePathFile = System.getenv("FILE_ROUTE");
    }

    private FilePath getFilePath(String path, PartitionConfig config) {
        String newFilePath = replacePath(path);
        return new FilePath(getActualFilePath(newFilePath), getNewFilePath(newFilePath, config));
    }

    private String replacePath(String path) {
        if (path == null) {
            return "";
        }
        return path.replace(basePathFile, "");
    }

    private String getExceptionMessage(String fileType, int id, String route) {
        return "La evidencia del " + fileType + " # [" + id + "] no fue encontrada en la ruta: " + route;
    }

}
