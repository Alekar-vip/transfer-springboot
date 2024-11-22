package co.com.tipi.central.transfercentral.fitnessfunctions;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static java.text.MessageFormat.format;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN, importOptions = ImportOption.DoNotIncludeTests.class)
public class ApplicationLayerRulesTest {

  static final String CONTROLLERS_PACKAGE = "..controller..";
  static final String MAPPERS_PACKAGE = "..models.mappers..";
  static final String REQUESTS_PACKAGE = "..models.requests..";
  static final String RESPONSE_PACKAGE = "..models.responses..";

  @ArchTest
  static final ArchRule requests_should_be_suffixed =
          classes()
                  .that()
                  .resideInAPackage(REQUESTS_PACKAGE)
                  .should()
                  .haveSimpleNameEndingWith("Request")
                  .because("Request object must have Request suffix");

  @ArchTest
  static final ArchRule responses_should_be_suffixed =
          classes()
                  .that()
                  .resideInAPackage(RESPONSE_PACKAGE)
                  .should()
                  .haveSimpleNameEndingWith("Response")
                  .because("Response object must have Response suffix");

  @ArchTest
  static final ArchRule requests_and_responses_should_be_annotated_with_schema =
          classes()
                  .that()
                  .resideInAPackage(RESPONSE_PACKAGE)
                  .or()
                  .resideInAPackage(REQUESTS_PACKAGE)
                  .should()
                  .beAnnotatedWith(Schema.class)
                  .because("Request and response object must be annotated with @Schema annotation for Open API 3");

  @ArchTest
  static final ArchRule requests_and_responses_fields_should_be_annotated_with_schema =
          fields()
                  .that()
                  .areDeclaredInClassesThat()
                  .resideInAPackage(RESPONSE_PACKAGE)
                  .or()
                  .areDeclaredInClassesThat()
                  .resideInAPackage(REQUESTS_PACKAGE)
                  .should()
                  .beAnnotatedWith(Schema.class)
                  .because("Request and response fields must be annotated with @Schema annotation explaining each field");

  @ArchTest
  static final ArchRule requests_fields_should_be_annotated_with_javax_validations =
          fields()
                  .that()
                  .areDeclaredInClassesThat()
                  .resideInAPackage(REQUESTS_PACKAGE)
                  .should()
                  .beAnnotatedWith(NotBlank.class)
                  .orShould().
                  beAnnotatedWith(Email.class)
                  .orShould().
                  beAnnotatedWith(NotNull.class)
                  .orShould().
                  beAnnotatedWith(Size.class)
                  .orShould().
                  beAnnotatedWith(Digits.class)
                  .orShould().
                  beAnnotatedWith(Max.class)
                  .orShould().
                  beAnnotatedWith(Min.class)
                  .orShould().
                  beAnnotatedWith(NotEmpty.class)
                  .orShould().
                  beAnnotatedWith(NotNull.class)
                  .orShould().
                  beAnnotatedWith(Past.class)
                  .orShould().
                  beAnnotatedWith(Future.class)
                  .orShould().
                  beAnnotatedWith(Pattern.class)
                  .orShould().
                  beAnnotatedWith(Positive.class)
                  .orShould().
                  beAnnotatedWith(PositiveOrZero.class)
                  .allowEmptyShould(true)
                  .because("Request fields must be annotated with Javax Validations annotations to validate each field");

  @ArchTest
  static final ArchRule mappers_should_be_suffixed =
          classes()
                  .that()
                  .resideInAPackage(MAPPERS_PACKAGE)
                  .should()
                  .haveSimpleNameEndingWith("Mapper")
                  .orShould()
                  .haveSimpleNameEndingWith("MapperImpl")
                  .because("Mappers object must have Mapper suffix");

  @ArchTest
  static final ArchRule controller_should_be_annotated = classes()
          .that().resideInAPackage(CONTROLLERS_PACKAGE)
          .should().beAnnotatedWith(RestController.class)
          .andShould().notBeAnnotatedWith(Controller.class)
          .because("Controllers should be annotated with @RestController, and not with @Controller");

  @ArchTest
  static final ArchRule all_controller_should_reside_in_controller_package =
          classes()
                  .that()
                  .areAnnotatedWith(RestController.class)
                  .should()
                  .resideInAPackage(CONTROLLERS_PACKAGE)
                  .because(format("Controllers should reside only in the package {0}", CONTROLLERS_PACKAGE));

  @ArchTest
  static final ArchRule controllers_should_be_suffixed =
          classes()
                  .that()
                  .resideInAPackage(CONTROLLERS_PACKAGE)
                  .should()
                  .haveSimpleNameEndingWith("Controller")
                  .because("Controllers must be named with Controller suffix");

  @ArchTest
  static final ArchRule all_controllers_methods_should_return_response_entity =
          methods()
                  .that()
                  .areAnnotatedWith(GetMapping.class)
                  .and()
                  .areAnnotatedWith(PostMapping.class)
                  .and()
                  .areAnnotatedWith(PutMapping.class)
                  .and()
                  .areAnnotatedWith(DeleteMapping.class)
                  .and()
                  .areAnnotatedWith(PatchMapping.class)
                  .should()
                  .haveRawReturnType(ResponseEntity.class)
                  .allowEmptyShould(true)
                  .because("All Rest API must return an instance of ResponseEntity class");

  @ArchTest
  static final ArchRule all_controllers_should_have_swagger_annotations =
          methods()
                  .that()
                  .areAnnotatedWith(GetMapping.class)
                  .and()
                  .areAnnotatedWith(PostMapping.class)
                  .and()
                  .areAnnotatedWith(PutMapping.class)
                  .and()
                  .areAnnotatedWith(DeleteMapping.class)
                  .and()
                  .areAnnotatedWith(PatchMapping.class)
                  .should()
                  .beAnnotatedWith(Operation.class)
                  .andShould()
                  .beAnnotatedWith(ApiResponses.class)
                  .andShould()
                  .beAnnotatedWith(ResponseStatus.class)
                  .allowEmptyShould(true)
                  .because("All Rest API must have Swagger annotations to be able to build Open API 3 documentation");
}
