package dev.tarasshablii.monolith.opora.media.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableConfigurationProperties(MediaProperties.class)
@EnableJpaRepositories(
		basePackages = "dev.tarasshablii.monolith.opora.media.provider.persistence.metadata",
		entityManagerFactoryRef = "mediaEntityManagerFactory",
		transactionManagerRef = "mediaTransactionManager"
)
public class MediaConfig {

	private final MediaProperties properties;

	@Bean
	public MinioClient minioClient() throws Exception {
		MinioClient minioClient = MinioClient.builder()
														 .endpoint(properties.url())
														 .credentials(properties.username(), properties.password())
														 .build();

		boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.bucket()).build());
		if (!isExist) {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.bucket()).build());
		}

		return minioClient;
	}

	@Bean
	@ConfigurationProperties(prefix = "media.metadata.datasource")
	public DataSource mediaDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean mediaEntityManagerFactory(
			EntityManagerFactoryBuilder builder, @Qualifier("mediaDataSource") DataSource mediaDataSource) {
		return builder
				.dataSource(mediaDataSource)
				.packages("dev.tarasshablii.monolith.opora.media.provider.persistence.metadata.entity")
				.persistenceUnit("media")
				.build();
	}

	@Bean
	public PlatformTransactionManager mediaTransactionManager(
			@Qualifier("mediaEntityManagerFactory") EntityManagerFactory mediaEntityManagerFactory) {
		return new JpaTransactionManager(mediaEntityManagerFactory);
	}

}