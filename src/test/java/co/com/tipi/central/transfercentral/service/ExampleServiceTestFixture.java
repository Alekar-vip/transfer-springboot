package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Example;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExampleServiceTestFixture {

    private static final Faker FAKER = new Faker();

    public static Example createExample() {
        return new Example(FAKER.company().name());
    }

}
