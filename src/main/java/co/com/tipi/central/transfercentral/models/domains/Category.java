package co.com.tipi.central.transfercentral.models.domains;

import lombok.Data;
import lombok.NonNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.Serial;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Category extends Domain{
    @Serial
    private static final long serialVersionUID = -476068072107540709L;
    boolean active;
    @NonNull
    String name;
    Integer monthsOfVideoPermanence;
    Partition partition;
    Boolean permanenceDocking;
}
