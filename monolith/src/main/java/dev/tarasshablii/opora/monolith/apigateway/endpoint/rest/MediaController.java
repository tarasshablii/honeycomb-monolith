package dev.tarasshablii.opora.monolith.apigateway.endpoint.rest;

import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.api.MediaApi;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.MediaResponseDto;
import dev.tarasshablii.opora.monolith.apigateway.provider.MediaProvider;
import dev.tarasshablii.opora.monolith.apigateway.provider.dto.MediaDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MediaController implements MediaApi {

    private final HttpServletRequest request;
    private final MediaProvider mediaProvider;

    @Override
    public ResponseEntity<MediaResponseDto> createMedia(Resource body) {

        log.debug("Creating new media");
        MediaDto mediaDto = MediaDto.builder()
                .media(body)
                .contentType(request.getHeader(HttpHeaders.CONTENT_TYPE))
                .build();
        UUID id = mediaProvider.create(mediaDto);

        return ResponseEntity.status(CREATED)
                .body(MediaResponseDto.builder()
                        .id(id)
                        .build());
    }

    @Override
    public ResponseEntity<Void> deleteMedia(UUID id) {

        log.debug("Deleting media with id [{}]", id);
        mediaProvider.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Resource> getMedia(UUID id) {

        log.debug("Fetching media for id [{}]", id);
        MediaDto mediaDto = mediaProvider.getById(id);
        return ResponseEntity.status(OK)
                .contentType(MediaType.parseMediaType(mediaDto.getContentType()))
                .body(mediaDto.getMedia());
    }

    @Override
    public ResponseEntity<Void> updateMedia(UUID id, Resource body) {

        log.debug("Updating media for id [{}]", id);
        MediaDto mediaDto = MediaDto.builder()
                .id(id)
                .media(body)
                .contentType(request.getHeader(HttpHeaders.CONTENT_TYPE))
                .build();
        mediaProvider.update(mediaDto);

        return ResponseEntity.ok().build();
    }
}
