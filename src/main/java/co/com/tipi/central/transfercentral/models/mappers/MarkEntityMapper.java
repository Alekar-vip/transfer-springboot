package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Mark;
import co.com.tipi.central.transfercentral.models.entities.MarkEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarkEntityMapper {
    Mark markEntityToMark(MarkEntity markEntity);
}
