package co.com.tipi.central.transfercentral.controller;

import co.com.tipi.central.transfercentral.models.requests.CreateExampleRequest;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExampleControllerTestFixture {

    private static final Faker FAKER = new Faker();

    public static CreateExampleRequest createExampleRequest() {
        return new CreateExampleRequest(FAKER.company().name());
    }

}
