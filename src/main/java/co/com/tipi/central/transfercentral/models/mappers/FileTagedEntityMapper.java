package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.FileTaged;
import co.com.tipi.central.transfercentral.models.entities.FileTagedEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileTagedEntityMapper {
    FileTaged fileEntityToFile(FileTagedEntity fileTagedEntity);
    List<FileTaged> fileEntityToFileList(List<FileTagedEntity> fileTagedEntity);
}
