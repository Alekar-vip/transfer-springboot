package co.com.tipi.central.transfercentral.models.domains.file;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class FilePath {
    @NonNull
    String oldPath;

    @NonNull
    String newPath;
}
