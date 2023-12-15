package dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.containers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import static org.testcontainers.containers.BindMode.READ_ONLY;

@Slf4j
public class TestContainers {

    public static final GenericContainer<?> WIREMOCK;
    private static final String STUBS_PATH = "/src/test/resources/stubs/";

    static {
        WIREMOCK = new GenericContainer<>("wiremock/wiremock:latest")
                .waitingFor(Wait.forHttp("/__admin/"))
                .withExposedPorts(8080)
                .withFileSystemBind(getStubsPath(), "/home/wiremock", READ_ONLY)
                .withLogConsumer(new Slf4jLogConsumer(log));
        WIREMOCK.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("initiatives.service.port", WIREMOCK::getFirstMappedPort);
        registry.add("media.service.port", WIREMOCK::getFirstMappedPort);
        registry.add("sponsors.service.port", WIREMOCK::getFirstMappedPort);
    }

    private static String getStubsPath() {
        return System.getProperty("user.dir") + STUBS_PATH;
    }
}
