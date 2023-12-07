package dev.tarasshablii.opora.monolith;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "dev.tarasshablii.opora.monolith",
		importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

	@ArchTest
	static final ArchRule isModularHexagonalArchitectureRespected =
			Architectures.layeredArchitecture()
							 .consideringOnlyDependenciesInLayers()
							 .withOptionalLayers(true)
							 .layer("initiatives.domain").definedBy("..initiatives.domain..")
							 .layer("media.domain").definedBy("..media.domain..")
							 .layer("sponsors.domain").definedBy("..sponsors.domain..")
							 .whereLayer("initiatives.domain").mayNotAccessAnyLayer()
							 .whereLayer("media.domain").mayNotAccessAnyLayer()
							 .whereLayer("sponsors.domain").mayNotAccessAnyLayer();

}
