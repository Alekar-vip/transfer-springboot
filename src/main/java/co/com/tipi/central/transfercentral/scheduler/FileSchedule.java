package co.com.tipi.central.transfercentral.scheduler;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.Snapshot;
import co.com.tipi.central.transfercentral.models.exceptions.CategoryException;
import co.com.tipi.central.transfercentral.models.exceptions.NotFileException;
import co.com.tipi.central.transfercentral.models.exceptions.NotPartitionException;
import co.com.tipi.central.transfercentral.service.AnnexService;
import co.com.tipi.central.transfercentral.service.FileTagedService;
import co.com.tipi.central.transfercentral.service.MarkService;
import co.com.tipi.central.transfercentral.service.PartitionService;
import co.com.tipi.central.transfercentral.service.SnapshotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableScheduling
@Slf4j
public class FileSchedule {


    final FileTagedService fileTagedService;
    final AnnexService annexService;
    final SnapshotService snapshotService;
    final MarkService markService;

    final PartitionService partitionService;

    @Value("${cron.expression}")
    private String cronExpression;

    @Scheduled(cron = "${cron.expression}", zone = "America/Bogota")
    public void transferFilesSchedule(){
        log.info("Inicia proceso de transferencia");
        transferFiles();
        transferAnnex();
        transferSnapshots();
        transferSnapshotsMark();
        log.info("Finaliza proceso de transferencia");
    }

    private void transferFiles() {
        log.info("Inicia proceso de transferencia de archivos");
        List<FileTaged> noTransferredFiles = fileTagedService.findAllByTransferredAtIsNull();
        log.info("Se encontraron " + noTransferredFiles.size() + " archivos para transferir");
        if (ObjectUtils.isEmpty(noTransferredFiles)) {
            log.info("No hay archivos para transferir");
            return;
        }

        noTransferredFiles.forEach(file -> {
            try {
                Partition selectPartition = partitionService.getPartitionData(file.getTag().getCategory().getPartition());
                partitionService.transferFile(selectPartition, file);
            } catch (NotPartitionException | CategoryException | NotFileException ex) {
                log.error("Error al transferir el archivo # " + file.getId(), ex);
            }
        });
    }
    private void transferAnnex() {
        log.info("Inicia proceso de transferencia de anexos");
        List<Annex> noTransferredAnnex = annexService.findAllByTransferredAtIsNull();
        if (noTransferredAnnex.isEmpty()) {
            log.info("No hay anexos para transferir");
            return;
        }
        noTransferredAnnex.forEach(annex -> {
            try {
                Partition selectPartition;
                if (annex.getFileTaged().getPartition() != null) {
                    selectPartition = partitionService.getPartitionData(annex.getFileTaged().getPartition());
                } else {
                    selectPartition = partitionService.getPartitionData(annex.getFileTaged().getTag().getCategory().getPartition());
                }
                partitionService.transferAnnex(selectPartition, annex);
            } catch (NotPartitionException | CategoryException | NotFileException ex) {
                log.error("Error al transferir el anexo # " + annex.getId(), ex);
            }
        });
    }
    private void transferSnapshots() {
        log.info("Inicia proceso de transferencia de snapshots");
        List<Snapshot> noTransferredSnapshot = snapshotService.findAllByTransferredAtIsNull();
        if (noTransferredSnapshot.isEmpty()) {
            log.info("No hay snapshots para transferir");
            return;
        }
        noTransferredSnapshot.forEach(snapshot -> {
            try {
                Partition selectPartition;
                if (snapshot.getFileTaged().getPartition() != null) {
                    selectPartition = partitionService.getPartitionData(snapshot.getFileTaged().getPartition());
                } else {
                    selectPartition = partitionService.getPartitionData(snapshot.getFileTaged().getTag().getCategory().getPartition());
                }
                partitionService.transferSnapshot(selectPartition, snapshot);
            } catch (NotPartitionException | CategoryException | NotFileException ex) {
                log.error("Error al transferir el snapshot # " + snapshot.getId(), ex);
            }
        });
    }
    private void transferSnapshotsMark() {
        log.info("Inicia proceso de transferencia de marcas");
        List<Mark> noTransferredMarks = markService.findAllByTransferredAtIsNull();
        if (noTransferredMarks.isEmpty()) {
            log.info("No hay marcas para transferir");
            return;
        }
        noTransferredMarks.forEach(mark -> {
            try {
                Partition selectPartitionInit;
                Partition selectPartitionEnd;
                if (mark.getFileTagedInit().getPartition() != null) {
                    selectPartitionInit = partitionService.getPartitionData(mark.getFileTagedInit().getPartition());
                } else {
                    selectPartitionInit = partitionService.getPartitionData(mark.getFileTagedInit().getTag().getCategory().getPartition());
                }
                if (mark.getFileTagedEnd().getPartition() != null) {
                    selectPartitionEnd = partitionService.getPartitionData(mark.getFileTagedEnd().getPartition());
                } else {
                    selectPartitionEnd = partitionService.getPartitionData(mark.getFileTagedEnd().getTag().getCategory().getPartition());
                }
                partitionService.transferMark(selectPartitionInit, selectPartitionEnd, mark);
            } catch (NotPartitionException | CategoryException | NotFileException ex) {
                log.error("Error al transferir el Marca # " + mark.getId(), ex);
            }
        });
    }
}
