package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Partition;
import co.com.tipi.central.transfercentral.models.domains.PartitionConfig;
import co.com.tipi.central.transfercentral.models.domains.Route;
import co.com.tipi.central.transfercentral.models.mappers.PartitionEntityMapper;
import co.com.tipi.central.transfercentral.repositories.PartitionRepository;
import com.google.gson.Gson;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PartitionActionServiceImplRed extends PartitionActionServiceRedLocal {

    public PartitionActionServiceImplRed(PartitionRepository partitionRepository, PartitionEntityMapper partitionEntityMapper) {
        super(partitionRepository, partitionEntityMapper);
    }

    @Override
    public boolean validateConnection(Partition partition, PartitionConfig config) {
        SMBClient client = new SMBClient();
        Route processURL = new Route(config.getUrl());
        try (Connection connection = client.connect(processURL.getIp())){
            AuthenticationContext ac = new AuthenticationContext(config.getUser(), config.getPass().toCharArray(), "");
            connection.authenticate(ac);
            Gson gson = new Gson();
            PartitionConfig partitionConfig = gson.fromJson(partition.getConfigJson(), PartitionConfig.class);
            partitionConfig.setUrl(config.getUrl());
            partition.setConfigJson(gson.toJson(partitionConfig));
            return true;
        } catch (IOException e) {
            log.error("Error en la conexion a la unidad compartida por red");
        }
        return false;
    }
}
