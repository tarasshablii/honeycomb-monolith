package dev.tarasshablii.opora.microservices.sponsors.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Extend this class to bootstrap tests with testcontainers.
 */
public class TestContainers {

	public static final PostgreSQLContainer<?> sponsorsContainer;

	static {
		sponsorsContainer = new PostgreSQLContainer<>("postgres:latest")
				.withDatabaseName("sponsors")
				.withUsername("sponsors_user")
				.withPassword("sponsors_pass")
				.withUrlParam("stringtype", "unspecified");

		sponsorsContainer.start();
	}

	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", sponsorsContainer::getJdbcUrl);
		registry.add("spring.datasource.username", sponsorsContainer::getUsername);
		registry.add("spring.datasource.password", sponsorsContainer::getPassword);
	}
}