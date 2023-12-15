package dev.tarasshablii.opora.microservices.initiatives.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

/**
 * Extend this class to bootstrap tests with testcontainers.
 */
public class TestContainers {

    public static final MongoDBContainer initiativesContainer;

    static {
        initiativesContainer = new MongoDBContainer("mongo:latest");

        initiativesContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", initiativesContainer::getConnectionString);
    }
}
