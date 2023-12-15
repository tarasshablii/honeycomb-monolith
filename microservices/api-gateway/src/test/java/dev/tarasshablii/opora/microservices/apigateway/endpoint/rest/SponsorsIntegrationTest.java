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
class SponsorsIntegrationTest extends TestContainers {

    private static final String SPONSORS_URL = "/v1/sponsors";
    private static final String SPONSOR_ID = "554bc2a3-2646-47ae-beed-fdcfec0c3566";
    private static final String NOT_FOUND_ID = "ad7375ec-8c33-4d36-860e-bf16ac57d5ab";
    private static final String INVALID_ID = "invalid-id";

    @Autowired
    private MockMvc mvc;

    @Nested
    class GetAllSponsorsTest {

        @Value("classpath:response/sponsors/get_all_200.json")
        private Resource successResponse;

        @Test
        void getAllSponsors_shouldReturnListOfSponsors() throws Exception {
            String responseBody = Files.readString(successResponse.getFile().toPath());

            mvc.perform(get(SPONSORS_URL).accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody));
        }
    }

    @Nested
    class CreateSponsorTest {

        @Value("classpath:request/sponsors/create_sponsor_200.json")
        private Resource validRequestResource;
        @Value("classpath:request/sponsors/create_sponsor_400.json")
        private Resource invalidRequestResource;
        @Value("classpath:response/sponsors/create_sponsor_200.json")
        private Resource createdResource;
        private MockHttpServletRequestBuilder post;

        @BeforeEach
        void setUp() {
            post = MockMvcRequestBuilders.post(SPONSORS_URL);
        }

        @Test
        void createSponsor_shouldCreateNewSponsor() throws Exception {
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
        void createSponsor_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(post.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));
        }

        @Test
        void createSponsor_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }

        @Test
        void createSponsor_shouldReturnBadRequest_givenRequestMissingRequiredFields() throws Exception {
            String requestBody = Files.readString(invalidRequestResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Request failed validation"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("name"))
                    .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
        }
    }

    @Nested
    class GetSponsorTest {

        @Value("classpath:response/sponsors/get_sponsor_200.json")
        private Resource responseResource;

        @Test
        void getSponsor_shouldReturnExistingSponsor() throws Exception {
            String responseBody = Files.readString(responseResource.getFile().toPath());

            mvc.perform(get("%s/%s".formatted(SPONSORS_URL, SPONSOR_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody));
        }

        @Test
        void getSponsor_shouldReturnNotFound_givenNoSponsorExists() throws Exception {
            mvc.perform(get("%s/%s".formatted(SPONSORS_URL, NOT_FOUND_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void getSponsor_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(get("%s/%s".formatted(SPONSORS_URL, INVALID_ID))
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
    class UpdateSponsorTest {

        @Value("classpath:request/sponsors/update_sponsor_200.json")
        private Resource validRequestResource;
        @Value("classpath:request/sponsors/update_sponsor_400.json")
        private Resource invalidRequestResource;
        @Value("classpath:response/sponsors/update_sponsor_200.json")
        private Resource updatedResponseResource;
        private MockHttpServletRequestBuilder put;

        @BeforeEach
        void setUp() {
            put = put("%s/%s".formatted(SPONSORS_URL, SPONSOR_ID));
        }

        @Test
        void updateSponsor_shouldUpdateExistingSponsor() throws Exception {
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
        void updateSponsor_shouldReturnNotFound_givenNoSponsorExists() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put("%s/%s".formatted(SPONSORS_URL, NOT_FOUND_ID))
                            .content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void updateSponsor_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(put.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));
        }

        @Test
        void updateSponsor_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));
        }

        @Test
        void updateSponsor_shouldReturnBadRequest_givenRequestMissingRequiredFields() throws Exception {
            String requestBody = Files.readString(invalidRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Request failed validation"))
                    .andExpect(jsonPath("$.errors").isNotEmpty())
                    .andExpect(jsonPath("$.errors[0].field").value("name"))
                    .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
        }

        @Test
        void updateSponsor_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            String requestBody = Files.readString(invalidRequestResource.getFile().toPath());

            mvc.perform(put("%s/%s".formatted(SPONSORS_URL, INVALID_ID))
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
    class DeleteSponsorTest {

        @Test
        void deleteSponsor_shouldDeleteExistingSponsor() throws Exception {
            mvc.perform(delete("%s/%s".formatted(SPONSORS_URL, SPONSOR_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deleteSponsor_shouldReturnNotFound_givenNoSponsorExists() throws Exception {
            mvc.perform(delete("%s/%s".formatted(SPONSORS_URL, NOT_FOUND_ID))
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }

        @Test
        void deleteSponsor_shouldReturnBadRequest_givenInvalidUUIDFormat() throws Exception {
            mvc.perform(delete("%s/%s".formatted(SPONSORS_URL, INVALID_ID))
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
