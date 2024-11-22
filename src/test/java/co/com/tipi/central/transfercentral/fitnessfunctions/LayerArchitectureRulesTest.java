package co.com.tipi.central.transfercentral.fitnessfunctions;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN, importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerArchitectureRulesTest {
  public static final String CONTROLLER = "Controller";
  public static final String SERVICE = "Service";
  public static final String REPOSITORY = "Repository";
  public static final String SCHEDULE = "Schedule";
  @ArchTest
  public static final ArchRule project_should_follow_layer_architecture =
          layeredArchitecture()
                 .layer(CONTROLLER).definedBy("..controller..")
                 .layer(SCHEDULE).definedBy("..scheduler..")
                 .layer(SERVICE).definedBy("..service..")
                 .layer(REPOSITORY).definedBy("..repositories..")

                 .whereLayer(CONTROLLER).mayNotBeAccessedByAnyLayer()
                 .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(CONTROLLER, SCHEDULE)
                 .whereLayer(REPOSITORY).mayOnlyBeAccessedByLayers(SERVICE);
}
