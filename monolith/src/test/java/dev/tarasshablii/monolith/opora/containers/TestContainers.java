package dev.tarasshablii.monolith.opora.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Extend this class to bootstrap tests with testcontainers.
 */
public class TestContainers {

	public static final PostgreSQLContainer<?> postgreSQLContainer;

	static {
		postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
				.withDatabaseName("sponsors")
				.withUsername("sa")
				.withPassword("sa")
				.withUrlParam("stringtype", "unspecified");
		postgreSQLContainer.start();
	}

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}
}