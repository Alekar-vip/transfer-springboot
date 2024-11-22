package co.com.tipi.central.transfercentral.models.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serial;
import java.time.ZonedDateTime;

@Entity
@Table(name = "archivo")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
public class FileTagedEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 9120315942191449893L;

    @NonNull
    @Column(name = "ruta", nullable = false)
    String path;

    @Column(name = "snapshot_route")
    String snapshotPath;

    @Column(name = "transferred_at")
    ZonedDateTime transferredAt;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "etiqueta_id", nullable = false)
    TagEntity tag;

    @ManyToOne
    @JoinColumn(name = "particion_id")
    PartitionEntity partition;
}
