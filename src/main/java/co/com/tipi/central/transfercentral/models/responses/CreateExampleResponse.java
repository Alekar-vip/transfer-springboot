package co.com.tipi.central.transfercentral.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contains basic info when a example is created")
public record CreateExampleResponse(@Schema(description = "example") String example) {
}
