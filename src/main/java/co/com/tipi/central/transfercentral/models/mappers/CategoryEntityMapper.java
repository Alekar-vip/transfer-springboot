package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Category;
import co.com.tipi.central.transfercentral.models.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    Category categoryEntityToCategory(CategoryEntity categoryEntity);
}
