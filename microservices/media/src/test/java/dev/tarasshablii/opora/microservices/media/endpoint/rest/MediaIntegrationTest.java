package dev.tarasshablii.opora.microservices.media.endpoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarasshablii.opora.microservices.media.containers.TestContainers;
import dev.tarasshablii.opora.microservices.media.domain.model.Media;
import dev.tarasshablii.opora.microservices.media.domain.port.MediaProvider;
import dev.tarasshablii.opora.microservices.media.endpoint.rest.dto.MediaDto;
import dev.tarasshablii.opora.microservices.media.provider.persistence.metadata.MetadataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static dev.tarasshablii.opora.microservices.media.MediaTestUtils.MEDIA_ID;
import static dev.tarasshablii.opora.microservices.media.MediaTestUtils.defaultMedia;
import static dev.tarasshablii.opora.microservices.media.MediaTestUtils.defaultMetadataEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MediaIntegrationTest extends TestContainers {

    private static final String MEDIA_URL = "/v1/media";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MediaProvider mediaProvider;
    @Autowired
    private MetadataRepository metadataRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        mediaProvider.deleteById(MEDIA_ID);
        metadataRepository.deleteAll();
    }

    @Nested
    class CreateMediaTest {

        @Value("classpath:request/media/200_create_media.jpeg")
        private Resource validRequestResource;
        private MockHttpServletRequestBuilder post;

        @BeforeEach
        void setUp() {
            post = post(MEDIA_URL);
        }

        @Test
        void createMedia_shouldCreateNewMedia() throws Exception {
            String mediaResponse = mvc.perform(post.content(validRequestResource.getContentAsByteArray())
                            .contentType(IMAGE_JPEG)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andReturn().getResponse().getContentAsString();

            MediaDto dto = objectMapper.readValue(mediaResponse, MediaDto.class);

            Optional<Media> savedMedia = mediaProvider.findById(dto.getId());
            assertThat(savedMedia).isPresent();
            assertThat(savedMedia.get().getResource().getContentAsByteArray())
                    .isEqualTo(validRequestResource.getContentAsByteArray());
            assertThat(metadataRepository.existsById(dto.getId())).isTrue();
        }

        @Test
        void createMedia_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(post.contentType(IMAGE_JPEG)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));

            assertThat(mediaProvider.findById(MEDIA_ID)).isEmpty();
            assertThat(metadataRepository.findAll()).isEmpty();
        }

        @Test
        void createMedia_shouldReturnBadRequest_givenNoContentType() throws Exception {
            mvc.perform(post.content(validRequestResource.getContentAsByteArray())
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));

            assertThat(mediaProvider.findById(MEDIA_ID)).isEmpty();
            assertThat(metadataRepository.findAll()).isEmpty();
        }

        @Test
        void createMedia_shouldReturnBadRequest_givenWrongRequestType() throws Exception {
            mvc.perform(post.content(validRequestResource.getContentAsByteArray())
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));

            assertThat(mediaProvider.findById(MEDIA_ID)).isEmpty();
            assertThat(metadataRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class GetMediaTest {

        @Value("classpath:response/media/200_get_media.jpeg")
        private Resource responseResource;
        private MockHttpServletRequestBuilder get;

        @BeforeEach
        void setUp() {
            get = get("%s/%s".formatted(MEDIA_URL, MEDIA_ID));
        }

        @Test
        void getMedia_shouldReturnExistingMedia() throws Exception {
            saveDefaultMedia(responseResource);

            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(IMAGE_JPEG))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(IMAGE_JPEG))
                    .andExpect(content().bytes(responseResource.getContentAsByteArray()));
        }

        @Test
        void getMedia_shouldReturnNotFound_givenNoMediaExists() throws Exception {
            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(IMAGE_JPEG))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }
    }

    @Nested
    class UpdateMediaTest {

        @Value("classpath:request/media/200_create_media.jpeg")
        private Resource existingResource;
        @Value("classpath:request/media/200_update_media.jpeg")
        private Resource updateResource;
        private MockHttpServletRequestBuilder put;

        @BeforeEach
        void setUp() {
            put = put("%s/%s".formatted(MEDIA_URL, MEDIA_ID));
        }

        @Test
        void updateMedia_shouldUpdateExistingMedia() throws Exception {
            saveDefaultMedia(existingResource);

            mvc.perform(put.content(updateResource.getContentAsByteArray())
                            .contentType(IMAGE_JPEG))
                    .andExpect(status().isOk());

            Optional<Media> updated = mediaProvider.findById(MEDIA_ID);
            assertThat(updated).isPresent();
            assertThat(updated.get().getResource().getContentAsByteArray())
                    .isEqualTo(updateResource.getContentAsByteArray());
        }

        @Test
        void updateMedia_shouldReturnNotFound_givenNoMediaExists() throws Exception {
            mvc.perform(put.content(updateResource.getContentAsByteArray())
                            .contentType(IMAGE_JPEG))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void updateMedia_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(put.contentType(IMAGE_JPEG))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));
        }

        @Test
        void updateMedia_shouldReturnBadRequest_givenNoContentType() throws Exception {
            mvc.perform(put.content(updateResource.getContentAsByteArray()))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }

        @Test
        void updateMedia_shouldReturnBadRequest_givenWrongContentType() throws Exception {
            mvc.perform(put.content(updateResource.getContentAsByteArray())
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }
    }

    @Nested
    class DeleteMediaTest {

        @Value("classpath:request/media/200_create_media.jpeg")
        private Resource existingResource;

        private MockHttpServletRequestBuilder delete;

        @BeforeEach
        void setUp() {
            delete = delete("%s/%s".formatted(MEDIA_URL, MEDIA_ID));
        }

        @Test
        void deleteMedia_shouldDeleteExistingMedia() throws Exception {
            saveDefaultMedia(existingResource);

            mvc.perform(delete.contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            assertThat(mediaProvider.findById(MEDIA_ID)).isEmpty();
            assertThat(metadataRepository.existsById(MEDIA_ID)).isFalse();
        }

        @Test
        void deleteMedia_shouldReturnNotFound_givenNoMediaExists() throws Exception {
            mvc.perform(delete.contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }
    }

    private void saveDefaultMedia(Resource resource) {
        Media media = defaultMedia();
        media.setResource(resource);
        mediaProvider.save(media);
        metadataRepository.save(defaultMetadataEntity());
    }

}
