package co.com.tipi.central.transfercentral.models.domains;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PartitionConfig {

    @NonNull
    String type;
    @NonNull
    String user;
    @NonNull
    String pass;
    @NonNull
    String url;
}
