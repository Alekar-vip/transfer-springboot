package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Annex;
import co.com.tipi.central.transfercentral.models.entities.AnnexEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnnexEntityMapper {
    Annex annexEntityToAnnex(AnnexEntity annexEntity);
}
