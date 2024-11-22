package co.com.tipi.central.transfercentral.controller;

import co.com.tipi.central.transfercentral.models.domains.Example;
import co.com.tipi.central.transfercentral.models.mappers.ExampleRequestResponseMapper;
import co.com.tipi.central.transfercentral.models.requests.CreateExampleRequest;
import co.com.tipi.central.transfercentral.models.responses.CreateExampleResponse;
import co.com.tipi.central.transfercentral.models.responses.ErrorResponse;
import co.com.tipi.central.transfercentral.service.ExampleCRUDUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Tag(name = "Example", description = "Example API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Credentials are required to access this resource", content =
        @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)))
})
@SecurityRequirement(name = "JWT")
@RequestMapping("/v1/examples")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExampleController {

    final ExampleCRUDUseCase exampleCRUDUseCase;

    final ExampleRequestResponseMapper exampleRequestResponseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new example into the system")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "When a user has been created",
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreateExampleResponse.class))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "When a user with the same username already exists on the system",
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(
                            responseCode = "412",
                            description = "When mandatory fields are not populated correctly",
                            content =
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<CreateExampleResponse> createExample(@RequestBody @Valid CreateExampleRequest createExampleRequest) {
        var example = new Example(createExampleRequest.example());
        var response = exampleCRUDUseCase.createExample(example);
        return ResponseEntity.status(HttpStatus.CREATED).body(exampleRequestResponseMapper.exampleToExampleCreateResponse(response));
    }
}
