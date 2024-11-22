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
@NoArgsConstructor
public class Tag extends Domain{

    @Serial
    private static final long serialVersionUID = -7637753754709553791L;
    boolean active;
    @NonNull
    String name;
    @NonNull
    Category category;
}
