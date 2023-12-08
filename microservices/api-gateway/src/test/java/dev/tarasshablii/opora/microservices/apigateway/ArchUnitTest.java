package dev.tarasshablii.opora.microservices.apigateway;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "dev.tarasshablii.opora.microservices.apigateway",
		importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

	@ArchTest
	static final ArchRule isLayerSeparationRespected =
			Architectures.layeredArchitecture()
							 .consideringOnlyDependenciesInLayers()
							 .withOptionalLayers(true)
							 .layer("endpoint").definedBy("..endpoint..")
							 .layer("provider").definedBy("..provider..")
							 .whereLayer("endpoint").mayOnlyAccessLayers("provider")
							 .whereLayer("provider").mayNotAccessAnyLayer()
							 .because("""
									           
									 1. Endpoint should only depend on Provider.
									 2. Provider should be self-sufficient and not depend on any other layers.
									 """);

}
