package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Example;
import co.com.tipi.central.transfercentral.models.mappers.ExampleEntityMapper;
import co.com.tipi.central.transfercentral.repositories.ExampleMySQLJPARepository;
import co.com.tipi.central.transfercentral.service.ExampleCRUDUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class ExampleService implements ExampleCRUDUseCase {

    final ExampleMySQLJPARepository exampleMySQLJPARepository;

    final ExampleEntityMapper exampleEntityMapper;

    @Override
    public Example createExample(Example example) {
        var entity = exampleMySQLJPARepository.save(exampleEntityMapper.exampleToExampleEntity(example));
        return exampleEntityMapper.exampleEntityToExample(entity);
    }
}