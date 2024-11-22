package co.com.tipi.central.transfercentral.fitnessfunctions;

import co.com.tipi.central.transfercentral.models.entities.BaseEntity;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.text.MessageFormat.format;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN, importOptions = ImportOption.DoNotIncludeTests.class)
public class InfrastructureLayerRulesTest {

  static final String REPOSITORIES_PACKAGE = "..repositories";
  static final String ENTITIES_PACKAGE = "..models.entities..";

  @ArchTest
  static final ArchRule repositories_must_have_repository_annotation =
          classes()
                  .that()
                  .resideInAPackage(REPOSITORIES_PACKAGE)
                  .should()
                  .beAnnotatedWith(Repository.class)
                  .because(format("Spring JPA Repositories should reside on {0}", REPOSITORIES_PACKAGE));

  @ArchTest
  static final ArchRule repositories_should_be_suffixed =
          classes()
                  .that()
                  .resideInAPackage(REPOSITORIES_PACKAGE)
                  .should()
                  .haveSimpleNameEndingWith("Repository")
                  .because("All Spring JPA Repositories should be suffixed with Repository");

  @ArchTest
  static final ArchRule repositories_should_be_interfaces =
          classes().that()
                  .resideInAPackage(REPOSITORIES_PACKAGE)
                  .should().beInterfaces()
                  .because(format("Repositories should be interfaces in {0}", REPOSITORIES_PACKAGE));

  @ArchTest
  static final ArchRule entities_must_reside_in_a_domain_package =
          classes()
                  .that()
                  .resideInAPackage(ENTITIES_PACKAGE)
                  .should().beAssignableTo(BaseEntity.class)
                  .because(format("All entities should reside in a package {0}", ENTITIES_PACKAGE));

  @ArchTest
  static final ArchRule entities_should_be_suffixed =
          classes()
                  .that()
                  .resideInAPackage(ENTITIES_PACKAGE)
                  .should()
                  .haveSimpleNameEndingWith("Entity")
                  .because("All entities should be suffixed with Entity");


}
