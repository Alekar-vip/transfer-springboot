package co.com.tipi.central.transfercentral.models.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serial;
import java.time.ZonedDateTime;

@Entity
@Table(name = "marca")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
public class MarkEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 7692151878076824783L;

    @NonNull
    @Column(name = "snapshot_inicial")
    String pathInitial;

    @NonNull
    @Column(name = "snapshot_final")
    String pathEnd;

    @Column(name = "transferred_at")
    ZonedDateTime transferredAt;

    @JoinColumn(name = "archivo_inicial_id", nullable = false)
    @ManyToOne
    FileTagedEntity fileTagedInit;

    @JoinColumn(name = "archivo_final_id", nullable = false)
    @ManyToOne
    FileTagedEntity fileTagedEnd;
}
