package dev.tarasshablii.opora.microservices.media.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MediaProperties.class)
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
}