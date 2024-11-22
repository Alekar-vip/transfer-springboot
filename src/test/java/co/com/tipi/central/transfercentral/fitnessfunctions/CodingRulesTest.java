package co.com.tipi.central.transfercentral.fitnessfunctions;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

@AnalyzeClasses(packages = FitnessFunctionsConstants.PACKAGE_TO_SCAN, importOptions = ImportOption.DoNotIncludeTests.class)
public class CodingRulesTest {
    @ArchTest
    public static final ArchRule
            NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS_OR_THROW_GENERIC_EXCEPTIONS =
            CompositeArchRule.of(NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS)
                    .and(NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS);

    @ArchTest
    public static final ArchRule NO_ACCESS_TO_STANDARD_STREAMS = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    public static final ArchRule NO_GENERIC_EXCEPTIONS =
            NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.because("Throw TransferException or any child of this instead");

    @ArchTest
    public static final ArchRule NO_JAVA_UTIL_LOGGING =
            NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.because("Use org.slf4j.Logger instead");

    @ArchTest
    public static final ArchRule NO_JODA_TIME_LIBRARY =
            NO_CLASSES_SHOULD_USE_JODATIME.because("Use java.time objects instead");

    @ArchTest
    public static final ArchRule NO_FIELD_INJECTION = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    @ArchTest
    public static final ArchRule LOGGERS_SHOULD_BE_PRIVATE_STATIC_FINAL =
            fields()
                    .that()
                    .haveRawType(Logger.class)
                    .should()
                    .bePrivate()
                    .andShould()
                    .beStatic()
                    .andShould()
                    .beFinal()
                    .allowEmptyShould(true)
                    .because("Logger variables should be private, static and final, and it should be named as LOGGER");

    @ArchTest
    static final ArchRule beanMethodsShouldBePublic = methods().that().areAnnotatedWith(Bean.class).should().bePublic().allowEmptyShould(true)
            .because("@Bean annotation does not work in non public methods");

    @ArchTest
    public static void NO_ACCESS_TO_STANDARD_STREAMS_AS_METHOD(JavaClasses classes) {
        noClasses().should(ACCESS_STANDARD_STREAMS).check(classes);
    }
}
