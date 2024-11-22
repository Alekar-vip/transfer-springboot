package co.com.tipi.central.transfercentral;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TransferCentralApplicationTests {

    @Test
    void contextLoads(ApplicationContext context) {
        Assertions.assertNotNull(context);
    }

}
