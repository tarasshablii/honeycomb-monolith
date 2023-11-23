package dev.tarasshablii.monolith.opora.sponsors.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "dev.tarasshablii.monolith.opora.sponsors.provider.persistence",
		entityManagerFactoryRef = "sponsorsEntityManagerFactory",
		transactionManagerRef = "sponsorsTransactionManager"
)
public class SponsorsConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource sponsorsDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean sponsorsEntityManagerFactory(
			EntityManagerFactoryBuilder builder, @Qualifier("sponsorsDataSource") DataSource sponsorsDataSource) {
		return builder
				.dataSource(sponsorsDataSource)
				.packages("dev.tarasshablii.monolith.opora.sponsors.provider.persistence.entity")
				.persistenceUnit("sponsors")
				.build();
	}

	@Bean
	@Primary
	public PlatformTransactionManager sponsorsTransactionManager(
			@Qualifier("sponsorsEntityManagerFactory") EntityManagerFactory sponsorsEntityManagerFactory) {
		return new JpaTransactionManager(sponsorsEntityManagerFactory);
	}
}