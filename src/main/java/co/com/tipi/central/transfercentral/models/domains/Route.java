package co.com.tipi.central.transfercentral.models.domains;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route {
    String ip;
    String path;

    public Route(String path) {
        String[] partes = path.split("\\\\", 4);
        this.ip = partes[2];
        this.path = partes[3];
    }
}
