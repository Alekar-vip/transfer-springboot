package co.com.tipi.central.transfercentral.models.domains;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Domain implements Serializable {
  @Serial
  private static final long serialVersionUID = -7328890446544690254L;
  int id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
}