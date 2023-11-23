package dev.tarasshablii.monolith.opora.media.provider.persistence.media;

import dev.tarasshablii.monolith.opora.media.config.MediaProperties;
import dev.tarasshablii.monolith.opora.media.domain.model.Media;
import dev.tarasshablii.monolith.opora.media.domain.model.Metadata;
import dev.tarasshablii.monolith.opora.media.domain.port.MediaProvider;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediaProviderImpl implements MediaProvider {

	private final MediaProperties properties;
	private final MinioClient minioClient;

	@Override
	public void save(Media media) {
		try {
			String id = Optional.of(media)
									  .map(Media::getMetadata)
									  .map(Metadata::getId)
									  .map(UUID::toString)
									  .orElseThrow();
			String contentType = Optional.of(media)
												  .map(Media::getMetadata)
												  .map(Metadata::getContentType)
												  .orElse("");
			Resource resource = Optional.of(media)
												 .map(Media::getResource)
												 .orElseThrow();
			minioClient.putObject(
					PutObjectArgs.builder()
									 .bucket(properties.bucket())
									 .object(id)
									 .contentType(contentType)
									 .stream(resource.getInputStream(), resource.contentLength(), -1)
									 .build()
			);
		} catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
				InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteById(UUID mediaId) {
		try {
			minioClient.removeObject(
					RemoveObjectArgs.builder()
										 .bucket(properties.bucket())
										 .object(mediaId.toString())
										 .build()
			);
		} catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
				InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Media> findById(UUID mediaId) {
		InputStreamResource inputStreamResource = null;
		try {
			GetObjectResponse getObjectResponse = minioClient.getObject(
					GetObjectArgs.builder()
									 .bucket(properties.bucket())
									 .object(mediaId.toString())
									 .build()
			);
			inputStreamResource = new InputStreamResource(getObjectResponse);

		} catch (ErrorResponseException e) {
			return Optional.empty();
		} catch (InsufficientDataException | InternalException | InvalidKeyException |
				InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
			log.error(e.getMessage(), e);
		}

		return Optional.ofNullable(inputStreamResource)
							.map(resource -> Media.builder()
														 .resource(resource)
														 .build());
	}
}