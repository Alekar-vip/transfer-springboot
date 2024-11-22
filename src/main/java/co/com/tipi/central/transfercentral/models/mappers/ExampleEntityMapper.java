package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Example;
import co.com.tipi.central.transfercentral.models.entities.ExampleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExampleEntityMapper {

    Example exampleEntityToExample(ExampleEntity exampleEntity);

    ExampleEntity exampleToExampleEntity(Example carrier);

}
