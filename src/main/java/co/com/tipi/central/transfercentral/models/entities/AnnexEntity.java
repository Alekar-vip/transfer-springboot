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
@Table(name = "anexo")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
public class AnnexEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -4811764975399910257L;

    @NonNull
    @Column(name = "ruta", nullable = false)
    String path;

    @Column(name = "transferred_at")
    ZonedDateTime transferredAt;

    @JoinColumn(name = "archivo_id", nullable = false)
    @ManyToOne
    FileTagedEntity fileTaged;
}
