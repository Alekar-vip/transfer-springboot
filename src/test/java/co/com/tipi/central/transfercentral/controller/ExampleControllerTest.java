package co.com.tipi.central.transfercentral.controller;

import co.com.tipi.central.transfercentral.config.TransferCentralControllerAdvice;
import co.com.tipi.central.transfercentral.models.domains.Example;
import co.com.tipi.central.transfercentral.models.mappers.ExampleRequestResponseMapper;
import co.com.tipi.central.transfercentral.models.mappers.ExampleRequestResponseMapperImpl;
import co.com.tipi.central.transfercentral.service.ExampleCRUDUseCase;
import co.com.tipi.central.transfercentral.service.ExampleServiceTestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ExampleController.class, ExampleRequestResponseMapperImpl.class})
public class ExampleControllerTest {

    private static final String BASE_URL = "/v1/examples";

    private static final String EMPTY_STRING = "";
    private static final String CARRIER = "CARRIER";
    private static final String TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
    private static final String USER_TYPE = "userType";
    private static final String LEGAL_TYPE = "legalType";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ExampleRequestResponseMapper exampleRequestResponseMapper;

    @MockBean
    private ExampleCRUDUseCase exampleCRUDUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        var controller = new ExampleController(exampleCRUDUseCase, exampleRequestResponseMapper);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new TransferCentralControllerAdvice())
                .setMessageConverters(converter)
                .build();
    }

    @Test
    void when_create_example_with_correct_data_then_created() throws Exception {
        var request = ExampleControllerTestFixture.createExampleRequest();
        var exampleCreated = ExampleServiceTestFixture.createExample();
        when(exampleCRUDUseCase.createExample(any(Example.class))).thenReturn(exampleCreated);
        var expectedResponse = exampleRequestResponseMapper.exampleToExampleCreateResponse(exampleCreated);
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(expectedResponse)));
    }

}
