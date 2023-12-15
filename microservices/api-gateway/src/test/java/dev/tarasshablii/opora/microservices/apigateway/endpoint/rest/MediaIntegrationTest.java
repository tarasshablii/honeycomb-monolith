package dev.tarasshablii.opora.microservices.apigateway.endpoint.rest;

import dev.tarasshablii.opora.microservices.apigateway.endpoint.rest.containers.TestContainers;
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
    private static final String MEDIA_ID = "ae92aed5-c5ec-4b8d-99f2-5894d9629a26";
    private static final String NOT_FOUND_ID = "ad7375ec-8c33-4d36-860e-bf16ac57d5ab";
    private static final String INVALID_ID = "invalid-id";

    @Autowired
    private MockMvc mvc;

    @Nested
    class CreateMediaTest {

        @Value("classpath:request/media/create_media_200.jpeg")
        private Resource requestResource;
        private MockHttpServletRequestBuilder post;

        @BeforeEach
        void setUp() {
            post = post(MEDIA_URL);
        }

        @Test
        void createMedia_shouldCreateNewMedia() throws Exception {
            mvc.perform(post.content(requestResource.getContentAsByteArray())
                            .contentType(IMAGE_JPEG)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(MEDIA_ID))
                    .andReturn().getResponse().getContentAsString();
        }

        @Test
        void createMedia_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(post.contentType(IMAGE_JPEG)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));
        }

        @Test
        void createMedia_shouldReturnBadRequest_givenNoContentType() throws Exception {
            mvc.perform(post.content(requestResource.getContentAsByteArray())
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }

        @Test
        void createMedia_shouldReturnBadRequest_givenWrongRequestType() throws Exception {
            mvc.perform(post.content(requestResource.getContentAsByteArray())
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }
    }

    @Nested
    class GetMediaTest {

        @Value("classpath:response/media/get_media_200.jpeg")
        private Resource responseResource;

        @Test
        void getMedia_shouldReturnExistingMedia() throws Exception {
            mvc.perform(get("%s/%s".formatted(MEDIA_URL, MEDIA_ID))
                            .accept(IMAGE_JPEG))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(IMAGE_JPEG))
                    .andExpect(content().bytes(responseResource.getContentAsByteArray()));
        }

        @Test
        void getMedia_shouldReturnNotFound_givenNoMediaExists() throws Exception {
            mvc.perform(get("%s/%s".formatted(MEDIA_URL, NOT_FOUND_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void getMedia_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(get("%s/%s".formatted(MEDIA_URL, INVALID_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Type mismatch occurred, see errors for details"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("id"))
                    .andExpect(jsonPath("$.errors[0].message").value(containsString("Invalid UUID string: invalid-id")));
        }
    }

    @Nested
    class UpdateMediaTest {

        @Value("classpath:request/media/update_media_200.jpeg")
        private Resource updateResource;
        private MockHttpServletRequestBuilder put;

        @BeforeEach
        void setUp() {
            put = put("%s/%s".formatted(MEDIA_URL, MEDIA_ID));
        }

        @Test
        void updateMedia_shouldUpdateExistingMedia() throws Exception {
            mvc.perform(put.content(updateResource.getContentAsByteArray())
                            .contentType(IMAGE_JPEG))
                    .andExpect(status().isOk());
        }

        @Test
        void updateMedia_shouldReturnNotFound_givenNoMediaExists() throws Exception {
            mvc.perform(put("%s/%s".formatted(MEDIA_URL, NOT_FOUND_ID))
                            .content(updateResource.getContentAsByteArray())
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

        @Test
        void updateMedia_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(put("%s/%s".formatted(MEDIA_URL, INVALID_ID))
                            .accept(APPLICATION_JSON)
                            .contentType(IMAGE_JPEG))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Type mismatch occurred, see errors for details"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("id"))
                    .andExpect(jsonPath("$.errors[0].message").value(containsString("Invalid UUID string: invalid-id")));
        }
    }

    @Nested
    class DeleteMediaTest {

        @Test
        void deleteMedia_shouldDeleteExistingMedia() throws Exception {
            mvc.perform(delete("%s/%s".formatted(MEDIA_URL, MEDIA_ID))
                            .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deleteMedia_shouldReturnNotFound_givenNoMediaExists() throws Exception {
            mvc.perform(delete("%s/%s".formatted(MEDIA_URL, NOT_FOUND_ID))
                            .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void deleteMedia_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(delete("%s/%s".formatted(MEDIA_URL, INVALID_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Type mismatch occurred, see errors for details"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("id"))
                    .andExpect(jsonPath("$.errors[0].message").value(containsString("Invalid UUID string: invalid-id")));
        }
    }

}
