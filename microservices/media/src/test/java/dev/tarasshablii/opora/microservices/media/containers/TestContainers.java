package dev.tarasshablii.opora.microservices.media.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Extend this class to bootstrap tests with testcontainers.
 */
public class TestContainers {

	public static final MinIOContainer mediaContainer;
	public static final PostgreSQLContainer<?> mediaMetadataContainer;

	static {
		mediaContainer = new MinIOContainer("minio/minio")
				.withUserName("media_user")
				.withPassword("media_pass");

		mediaMetadataContainer = new PostgreSQLContainer<>("postgres:latest")
				.withDatabaseName("media_metadata")
				.withUsername("media_user")
				.withPassword("media_pass");

		mediaContainer.start();
		mediaMetadataContainer.start();
	}

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("media.database.port", mediaContainer::getFirstMappedPort);

		registry.add("spring.datasource.url", mediaMetadataContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mediaMetadataContainer::getUsername);
		registry.add("spring.datasource.password", mediaMetadataContainer::getPassword);
	}
}