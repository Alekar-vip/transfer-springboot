package co.com.tipi.central.transfercentral.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Contains basic error response")
public class ErrorResponse {
  @Schema(description = "errorMessage")
  String errorMessage;
  @Schema(description = "description")
  Integer statusCode;
}
