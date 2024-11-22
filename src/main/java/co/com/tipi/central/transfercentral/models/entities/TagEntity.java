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
@Table(name = "etiqueta")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class TagEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 3307302222677235243L;

    @Column(name = "activo", nullable = false)
    boolean active;
    @NonNull
    @Column(name = "nombre", nullable = false)
    String name;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    CategoryEntity category;
}
