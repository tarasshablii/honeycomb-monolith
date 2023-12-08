package dev.tarasshablii.opora.microservices.media;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "dev.tarasshablii.opora.microservices.media",
		importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

	@ArchTest
	static final ArchRule isHexagonalArchitectureRespected =
			Architectures.layeredArchitecture()
							 .consideringOnlyDependenciesInLayers()
							 .withOptionalLayers(true)
							 .layer("endpoint").definedBy("..endpoint..")
							 .layer("domain").definedBy("..domain..")
							 .layer("provider").definedBy("..provider..")
							 .whereLayer("endpoint").mayOnlyAccessLayers("domain")
							 .whereLayer("domain").mayNotAccessAnyLayer()
							 .whereLayer("provider").mayOnlyAccessLayers("domain")
							 .because("""
									           
									 1. Endpoint should only depend on Domain.
									 2. Domain should be self-sufficient and not depend on any other layers.
									 3. Provider should only depend on Domain.
									 4. Endpoint and Provider should not access each other directly.
									 """);

}
