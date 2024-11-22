package co.com.tipi.central.transfercentral.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "Request object to create a new example")
public record CreateExampleRequest(
        @NotBlank @Schema(description = "example field") String example) {
}
