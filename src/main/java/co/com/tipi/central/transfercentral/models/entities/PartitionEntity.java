package co.com.tipi.central.transfercentral.models.entities;

import co.com.tipi.central.transfercentral.models.enums.PartitionType;
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
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.io.Serial;

@Entity
@Table(name = "particiones")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class PartitionEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8950040545450837821L;

    @Column(name = "activo", nullable = false)
    boolean active;
    @NonNull
    @Column(name = "nombre", nullable = false)
    String name;
    @Column(name = "prioridad_llenado", nullable = false)
    int fillingPriority;
    @NonNull
    @Column(name = "configjson", nullable = false, columnDefinition = "TEXT")
    String configJson;
    @Column(name = "tamano_disponible", nullable = false)
    int availableSize;
    @Column(name = "tamano_total", nullable = false)
    int totalSize;
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_particion", nullable = false)
    PartitionType partitionType;
}
