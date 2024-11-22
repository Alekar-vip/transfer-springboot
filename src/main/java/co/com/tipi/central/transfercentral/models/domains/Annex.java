package co.com.tipi.central.transfercentral.models.domains;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.time.ZonedDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Annex extends Domain{
    @Serial
    private static final long serialVersionUID = -5231387143939140899L;

    @NonNull
    String path;

    ZonedDateTime transferredAt;

    @NonNull
    FileTaged fileTaged;
}
