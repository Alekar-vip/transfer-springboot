package co.com.tipi.central.transfercentral.models.domains;

import lombok.Data;
import lombok.NonNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.time.ZonedDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class FileTaged extends Domain {

    @Serial
    private static final long serialVersionUID = -1828350757572952989L;

    @NonNull
    String path;
    String snapshotPath;
    ZonedDateTime transferredAt;
    @NonNull
    Tag tag;
    Partition partition;
}
