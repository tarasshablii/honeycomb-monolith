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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InitiativesIntegrationTest extends TestContainers {

    private static final String INITIATIVES_URL = "/v1/initiatives";
    private static final String INITIATIVE_ID = "93f9b055-a87a-4c7a-8bd1-bd5862182b75";
    private static final String NOT_FOUND_ID = "7632105a-3dfc-4e16-87fe-d743ba5ae922";
    private static final String INVALID_ID = "invalid-id";

    @Autowired
    private MockMvc mvc;

    @Nested
    class GetAllInitiativesTest {

        @Value("classpath:response/initiatives/get_all_200.json")
        private Resource successResponse;

        @Test
        void getAllInitiatives_shouldReturnListOfInitiatives() throws Exception {
            String responseBody = Files.readString(successResponse.getFile().toPath());

            mvc.perform(get(INITIATIVES_URL).accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody));
        }
    }

    @Nested
    class CreateInitiativeTest {

        @Value("classpath:request/initiatives/create_initiative_200.json")
        private Resource validRequestResource;
        @Value("classpath:request/initiatives/create_initiative_400.json")
        private Resource invalidRequestResource;
        @Value("classpath:response/initiatives/create_initiative_200.json")
        private Resource createdResource;
        private MockHttpServletRequestBuilder post;

        @BeforeEach
        void setUp() {
            post = MockMvcRequestBuilders.post(INITIATIVES_URL);
        }

        @Test
        void createInitiative_shouldCreateNewInitiative() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());
            String expectedResponse = Files.readString(createdResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(expectedResponse));
        }

        @Test
        void createInitiative_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(post.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));
        }

        @Test
        void createInitiative_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }

        @Test
        void createInitiative_shouldReturnBadRequest_givenRequestMissingRequiredFields() throws Exception {
            String requestBody = Files.readString(invalidRequestResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Request failed validation"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("title"))
                    .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
        }
    }

    @Nested
    class GetInitiativeTest {

        @Value("classpath:response/initiatives/get_initiative_200.json")
        private Resource responseResource;

        @Test
        void getInitiative_shouldReturnExistingInitiative() throws Exception {
            String responseBody = Files.readString(responseResource.getFile().toPath());

            mvc.perform(get("%s/%s".formatted(INITIATIVES_URL, INITIATIVE_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody));
        }

        @Test
        void getInitiative_shouldReturnNotFound_givenNoInitiativeExists() throws Exception {
            mvc.perform(get("%s/%s".formatted(INITIATIVES_URL, NOT_FOUND_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void getInitiative_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(get("%s/%s".formatted(INITIATIVES_URL, INVALID_ID))
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
    class UpdateInitiativeTest {

        @Value("classpath:request/initiatives/update_initiative_200.json")
        private Resource validRequestResource;
        @Value("classpath:request/initiatives/update_initiative_400.json")
        private Resource invalidRequestResource;
        @Value("classpath:response/initiatives/update_initiative_200.json")
        private Resource updatedResponseResource;
        private MockHttpServletRequestBuilder put;

        @BeforeEach
        void setUp() {
            put = put("%s/%s".formatted(INITIATIVES_URL, INITIATIVE_ID));
        }

        @Test
        void updateInitiative_shouldUpdateExistingInitiative() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());
            String expectedResponse = Files.readString(updatedResponseResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(expectedResponse));
        }

        @Test
        void updateInitiative_shouldReturnNotFound_givenNoInitiativeExists() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put("%s/%s".formatted(INITIATIVES_URL, NOT_FOUND_ID))
                            .content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void updateInitiative_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(put.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));
        }

        @Test
        void updateInitiative_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }

        @Test
        void updateInitiative_shouldReturnBadRequest_givenRequestMissingRequiredFields() throws Exception {
            String requestBody = Files.readString(invalidRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Request failed validation"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("title"))
                    .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
        }

        @Test
        void updateInitiative_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            String requestBody = Files.readString(invalidRequestResource.getFile().toPath());

            mvc.perform(put("%s/%s".formatted(INITIATIVES_URL, INVALID_ID))
                            .content(requestBody)
                            .contentType(APPLICATION_JSON)
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
    class DeleteInitiativeTest {

        @Test
        void deleteInitiative_shouldDeleteExistingInitiative() throws Exception {
            mvc.perform(delete("%s/%s".formatted(INITIATIVES_URL, INITIATIVE_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deleteInitiative_shouldReturnNotFound_givenNoInitiativeExists() throws Exception {
            mvc.perform(delete("%s/%s".formatted(INITIATIVES_URL, NOT_FOUND_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void deleteInitiative_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(delete("%s/%s".formatted(INITIATIVES_URL, INVALID_ID))
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
