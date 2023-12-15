package dev.tarasshablii.opora.monolith;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "dev.tarasshablii.opora.monolith",
        importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {

    private static final String APIGATEWAY_ENDPOINT = "apigateway.endpoint";
    private static final String APIGATEWAY_PROVIDER = "apigateway.provider";
    private static final String INITIATIVES_ENDPOINT = "initiatives.endpoint";
    private static final String INITIATIVES_DOMAIN = "initiatives.domain";
    private static final String INITIATIVES_PROVIDER = "initiatives.provider";
    private static final String MEDIA_ENDPOINT = "media.endpoint";
    private static final String MEDIA_DOMAIN = "media.domain";
    private static final String MEDIA_PROVIDER = "media.provider";
    private static final String SPONSORS_ENDPOINT = "sponsors.endpoint";
    private static final String SPONSORS_DOMAIN = "sponsors.domain";
    private static final String SPONSORS_PROVIDER = "sponsors.provider";
    @ArchTest
    static final ArchRule isModularHexagonalArchitectureRespected =
            Architectures.layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()
                    .withOptionalLayers(true)
                    .layer(APIGATEWAY_ENDPOINT).definedBy("..apigateway.endpoint..")
                    .layer(APIGATEWAY_PROVIDER).definedBy("..apigateway.provider..")
                    .layer(INITIATIVES_ENDPOINT).definedBy("..initiatives.endpoint..")
                    .layer(INITIATIVES_DOMAIN).definedBy("..initiatives.domain..")
                    .layer(INITIATIVES_PROVIDER).definedBy("..initiatives.provider..")
                    .layer(MEDIA_ENDPOINT).definedBy("..media.endpoint..")
                    .layer(MEDIA_DOMAIN).definedBy("..media.domain..")
                    .layer(MEDIA_PROVIDER).definedBy("..media.provider..")
                    .layer(SPONSORS_ENDPOINT).definedBy("..sponsors.endpoint..")
                    .layer(SPONSORS_DOMAIN).definedBy("..sponsors.domain..")
                    .layer(SPONSORS_PROVIDER).definedBy("..sponsors.provider..")
                    // API Gateway Hexagon (domainless)
                    .whereLayer(APIGATEWAY_ENDPOINT).mayOnlyAccessLayers(APIGATEWAY_PROVIDER)
                    .whereLayer(APIGATEWAY_PROVIDER).mayOnlyAccessLayers(
                            INITIATIVES_ENDPOINT, MEDIA_ENDPOINT, SPONSORS_ENDPOINT)
                    // Initiatives Hexagon
                    .whereLayer(INITIATIVES_ENDPOINT).mayOnlyAccessLayers(INITIATIVES_DOMAIN)
                    .whereLayer(INITIATIVES_DOMAIN).mayNotAccessAnyLayer()
                    .whereLayer(INITIATIVES_PROVIDER).mayOnlyAccessLayers(INITIATIVES_DOMAIN)
                    // Media Hexagon
                    .whereLayer(MEDIA_ENDPOINT).mayOnlyAccessLayers(MEDIA_DOMAIN)
                    .whereLayer(MEDIA_DOMAIN).mayNotAccessAnyLayer()
                    .whereLayer(MEDIA_PROVIDER).mayOnlyAccessLayers(MEDIA_DOMAIN)
                    // Sponsors Hexagon
                    .whereLayer(SPONSORS_ENDPOINT).mayOnlyAccessLayers(SPONSORS_DOMAIN)
                    .whereLayer(SPONSORS_DOMAIN).mayNotAccessAnyLayer()
                    .whereLayer(SPONSORS_PROVIDER).mayOnlyAccessLayers(SPONSORS_DOMAIN)
                    .because("""
                                      
                            1. API Gateway Endpoint should only depend on API Gateway Provider.
                            2. API Gateway Provider should only depend on Initiatives, Media, and Sponsors Endpoints.
                            3. Initiatives Endpoint should only depend on Initiatives Domain.
                            4. Initiatives Domain should be self-sufficient and not depend on any other layers.
                            5. Initiatives Provider should only depend on Initiatives Domain.
                            6. Initiatives Endpoint and Provider should not access each other directly.
                            7. Media Endpoint should only depend on Media Domain.
                            8. Media Domain should be self-sufficient and not depend on any other layers.
                            9. Media Provider should only depend on Media Domain.
                            10. Media Endpoint and Provider should not access each other directly.
                            11. Sponsors Endpoint should only depend on Sponsors Domain.
                            12. Sponsors Domain should be self-sufficient and not depend on any other layers.
                            13. Sponsors Provider should only depend on Sponsors Domain.
                            14. Sponsors Endpoint and Provider should not access each other directly.
                            """);

}
