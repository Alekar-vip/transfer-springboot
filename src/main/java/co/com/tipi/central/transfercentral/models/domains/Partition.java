package co.com.tipi.central.transfercentral.models.domains;

import co.com.tipi.central.transfercentral.models.enums.PartitionType;
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
public class Partition extends Domain{

    @Serial
    private static final long serialVersionUID = 8115139774530432528L;
    boolean active;
    @NonNull
    String name;
    int fillingPriority;
    @NonNull
    String configJson;
    int availableSize;
    int totalSize;
    @NonNull
    PartitionType partitionType;
}
