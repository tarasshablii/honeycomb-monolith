package dev.tarasshablii.opora.monolith.apigateway.endpoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.SponsorResponseDto;
import dev.tarasshablii.opora.monolith.containers.TestContainers;
import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.ContactRepository;
import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.SponsorRepository;
import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity.ContactEntity;
import dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity.SponsorEntity;
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

import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

import static dev.tarasshablii.opora.monolith.sponsors.SponsorTestUtils.MEDIA_ID;
import static dev.tarasshablii.opora.monolith.sponsors.SponsorTestUtils.SPONSOR_ID;
import static dev.tarasshablii.opora.monolith.sponsors.SponsorTestUtils.defaultContactEntity;
import static dev.tarasshablii.opora.monolith.sponsors.SponsorTestUtils.defaultSponsorEntity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SponsorsIntegrationTest extends TestContainers {

    private static final String SPONSORS_URL = "/v1/sponsors";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private String sponsorId;

    @AfterEach
    void tearDown() {
        sponsorRepository.deleteAll();
    }

    @Nested
    class GetAllSponsorsTest {

        private MockHttpServletRequestBuilder get;

        @BeforeEach
        void setUp() {
            get = get(SPONSORS_URL);
        }

        @Test
        void getAllSponsors_shouldReturnEmptyList_givenNoSponsors() throws Exception {
            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$").isEmpty());
        }

        @Test
        void getAllInitiatives_shouldReturnListOfInitiatives() throws Exception {
            saveDefaultSponsor();

            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$").isNotEmpty())
                    .andExpect(jsonPath("$[0].id").value(sponsorId))
                    .andExpect(jsonPath("$[0].userName").value("userName"))
                    .andExpect(jsonPath("$[0].name").value("name"))
                    .andExpect(jsonPath("$[0].description").value("description"))
                    .andExpect(jsonPath("$[0].media").value(MEDIA_ID.toString()))
                    .andExpect(jsonPath("$[0].contacts").isNotEmpty())
                    .andExpect(jsonPath("$[0].contacts[0].firstName").value("firstName"))
                    .andExpect(jsonPath("$[0].contacts[0].lastName").value("lastName"))
                    .andExpect(jsonPath("$[0].contacts[0].phones").isNotEmpty())
                    .andExpect(jsonPath("$[0].contacts[0].email").value("email@snail.con"))
                    .andExpect(jsonPath("$[0].contacts[0].links").isNotEmpty());
        }

    }


    @Nested
    class CreateSponsorTest {


        @Value("classpath:request/sponsors/create_sponsor_200.json")
        private Resource validRequestResource;
        @Value("classpath:request/sponsors/create_sponsor_400.json")
        private Resource invalidRequestResource;
        private MockHttpServletRequestBuilder post;

        @BeforeEach
        void setUp() {
            post = post(SPONSORS_URL);
        }

        @Test
        void createSponsor_shouldCreateNewSponsor() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            String responseBody = mvc.perform(post
                            .content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString();

            SponsorResponseDto dto = objectMapper.readValue(responseBody, SponsorResponseDto.class);
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isNotNull();
            assertThat(dto.getUserName()).isEqualTo("userName");
            assertThat(dto.getName()).isEqualTo("name");
            assertThat(dto.getDescription()).isEqualTo("description");
            assertThat(dto.getMedia()).isEqualTo(MEDIA_ID);
            assertThat(dto.getContacts()).hasSize(1);

            assertThat(sponsorRepository.existsById(dto.getId())).isTrue();
        }

        @Test
        void createSponsor_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(post.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));

            assertThat(sponsorRepository.findAll()).isEmpty();
        }

        @Test
        void createSponsor_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));

            assertThat(sponsorRepository.findAll()).isEmpty();
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
                    .andExpect(jsonPath("$.errors[0].field").value("userName"))
                    .andExpect(jsonPath("$.errors[0].message").value("must not be null"));

            assertThat(sponsorRepository.findAll()).isEmpty();
        }
    }

    @Nested
    class GetSponsorTest {

        @Test
        void getSponsor_shouldReturnExistingSponsor() throws Exception {
            saveDefaultSponsor();

            mvc.perform(get("%s/%s".formatted(SPONSORS_URL, sponsorId))
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$").isNotEmpty())
                    .andExpect(jsonPath("$.id").value(sponsorId))
                    .andExpect(jsonPath("$.userName").value("userName"))
                    .andExpect(jsonPath("$.name").value("name"))
                    .andExpect(jsonPath("$.description").value("description"))
                    .andExpect(jsonPath("$.media").value(MEDIA_ID.toString()))
                    .andExpect(jsonPath("$.contacts").isNotEmpty())
                    .andExpect(jsonPath("$.contacts[0].firstName").value("firstName"))
                    .andExpect(jsonPath("$.contacts[0].lastName").value("lastName"))
                    .andExpect(jsonPath("$.contacts[0].phones").isNotEmpty())
                    .andExpect(jsonPath("$.contacts[0].email").value("email@snail.con"))
                    .andExpect(jsonPath("$.contacts[0].links").isNotEmpty());
        }

        @Test
        void getSponsor_shouldReturnNotFound_givenNoSponsorExists() throws Exception {
            mvc.perform(get("%s/%s".formatted(SPONSORS_URL, SPONSOR_ID))
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }
    }

    @Nested
    class UpdateSponsorTest {

        @Value("classpath:request/sponsors/update_sponsor_200.json")
        private Resource validRequestResource;
        @Value("classpath:request/sponsors/update_sponsor_400.json")
        private Resource invalidRequestResource;
        private MockHttpServletRequestBuilder put;

        @BeforeEach
        void setUp() {
            put = put("%s/%s".formatted(SPONSORS_URL, SPONSOR_ID));
        }

        @Test
        void updateSponsor_shouldUpdateExistingSponsor() throws Exception {
            saveDefaultSponsor();

            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put("%s/%s".formatted(SPONSORS_URL, sponsorId))
                            .content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(sponsorId))
                    .andExpect(jsonPath("$.description").value("updated description"));

            Optional<SponsorEntity> updated = sponsorRepository.findById(UUID.fromString(sponsorId));
            assertThat(updated).isPresent();
            assertThat(updated.get().getDescription()).isEqualTo("updated description");
        }

        @Test
        void updateSponsor_shouldReturnNotFound_givenNoSponsorExists() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
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
                    .andExpect(jsonPath("$.errors[0].field").value("userName"))
                    .andExpect(jsonPath("$.errors[0].message").value("must not be null"));
        }
    }

    @Nested
    class DeleteSponsorTest {

        @Test
        void deleteSponsor_shouldDeleteExistingSponsor() throws Exception {
            saveDefaultSponsor();

            mvc.perform(delete("%s/%s".formatted(SPONSORS_URL, sponsorId))
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            assertThat(sponsorRepository.existsById(UUID.fromString(sponsorId))).isFalse();
        }

        @Test
        void deleteSponsor_shouldReturnNotFound_givenNoSponsorExists() throws Exception {
            mvc.perform(delete("%s/%s".formatted(SPONSORS_URL, SPONSOR_ID))
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }
    }

    private void saveDefaultSponsor() {
        ContactEntity contactEntity = contactRepository.save(defaultContactEntity());
        SponsorEntity sponsorEntity = defaultSponsorEntity();
        sponsorEntity.setContacts(singletonList(contactEntity));
        SponsorEntity saved = sponsorRepository.save(sponsorEntity);
        sponsorId = saved.getId().toString();
    }

}
