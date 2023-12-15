package dev.tarasshablii.opora.monolith.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Extend this class to bootstrap tests with testcontainers.
 */
public class TestContainers {

    public static final PostgreSQLContainer<?> sponsorsContainer;
    public static final MongoDBContainer initiativesContainer;
    public static final MinIOContainer mediaContainer;
    public static final PostgreSQLContainer<?> mediaMetadataContainer;

    static {
        sponsorsContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("sponsors")
                .withUsername("sponsors_user")
                .withPassword("sponsors_pass")
                .withUrlParam("stringtype", "unspecified");

        initiativesContainer = new MongoDBContainer("mongo:latest");

        mediaContainer = new MinIOContainer("minio/minio")
                .withUserName("media_user")
                .withPassword("media_pass");

        mediaMetadataContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("media_metadata")
                .withUsername("media_user")
                .withPassword("media_pass");

        sponsorsContainer.start();
        initiativesContainer.start();
        mediaContainer.start();
        mediaMetadataContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.jdbc-url", sponsorsContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sponsorsContainer::getUsername);
        registry.add("spring.datasource.password", sponsorsContainer::getPassword);

        registry.add("spring.data.mongodb.uri", initiativesContainer::getConnectionString);

        registry.add("media.database.port", mediaContainer::getFirstMappedPort);

        registry.add("media.metadata.datasource.jdbc-url", mediaMetadataContainer::getJdbcUrl);
        registry.add("media.metadata.datasource.username", mediaMetadataContainer::getUsername);
        registry.add("media.metadata.datasource.password", mediaMetadataContainer::getPassword);
    }
}
