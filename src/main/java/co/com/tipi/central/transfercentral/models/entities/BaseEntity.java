package co.com.tipi.central.transfercentral.models.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {
  @Serial
  private static final long serialVersionUID = 288943108022469977L;
  @Id
  @Column(name = "id", nullable = false, updatable = false, columnDefinition="INT")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;
  @Column(name = "fechaCreacion", nullable = false)
  ZonedDateTime createdAt;

  @Column(name = "fechaActualizacion")
  ZonedDateTime updatedAt;

  @PreUpdate
  public void preUpdate() {
    setUpdatedAt(ZonedDateTime.now());
  }
}