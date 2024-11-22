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
public class Mark extends Domain {
    @Serial
    private static final long serialVersionUID = 4254355922315008105L;

    @NonNull
    String pathInitial;

    @NonNull
    String pathEnd;

    ZonedDateTime transferredAt;

    @NonNull
    FileTaged fileTagedInit;

    @NonNull
    FileTaged fileTagedEnd;
}
