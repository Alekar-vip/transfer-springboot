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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;

@Entity
@Table(name = "categoria")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(staticName = "of")
public class CategoryEntity extends BaseEntity{
    @Serial
    private static final long serialVersionUID = 5077891449568516836L;

    @Column(name = "activo", nullable = false)
    boolean active;
    @NonNull
    @Column(name = "nombre", nullable = false)
    String name;
    @Column(name = "meses_permanencia_videos")
    Integer monthsOfVideoPermanence;
    @Column(name = "permanencia_docking")
    Boolean permanenceDocking;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "particion_id", referencedColumnName = "id", nullable = false)
    PartitionEntity partition;
}
