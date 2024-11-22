package co.com.tipi.central.transfercentral.models.mappers;

import co.com.tipi.central.transfercentral.models.domains.Example;
import co.com.tipi.central.transfercentral.models.responses.CreateExampleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExampleRequestResponseMapper {
    CreateExampleResponse exampleToExampleCreateResponse(Example example);
}
