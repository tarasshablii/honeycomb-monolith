package dev.tarasshablii.opora.monolith.apigateway.endpoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tarasshablii.opora.monolith.apigateway.endpoint.rest.dto.InitiativeResponseDto;
import dev.tarasshablii.opora.monolith.containers.TestContainers;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.InitiativesRepository;
import dev.tarasshablii.opora.monolith.initiatives.provider.persistence.entity.InitiativeEntity;
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

import static dev.tarasshablii.opora.monolith.initiatives.domain.InitiativesTestUtil.INITIATIVE_ID;
import static dev.tarasshablii.opora.monolith.initiatives.domain.InitiativesTestUtil.MEDIA_ID;
import static dev.tarasshablii.opora.monolith.initiatives.domain.InitiativesTestUtil.defaultInitiativeEntity;
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
class InitiativesIntegrationTest extends TestContainers {

    private static final String INITIATIVES_URL = "/v1/initiatives";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private InitiativesRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Nested
    class GetAllInitiativesTest {

        @Value("classpath:response/initiatives/200_get_all.json")
        private Resource responseResource;
        private MockHttpServletRequestBuilder get;

        @BeforeEach
        void setUp() {
            get = get(INITIATIVES_URL);
        }

        @Test
        void getAllInitiatives_shouldReturnEmptyList_givenNoInitiatives() throws Exception {
            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$").isEmpty());
        }

        @Test
        void getAllInitiatives_shouldReturnListOfInitiatives() throws Exception {
            repository.save(defaultInitiativeEntity());

            String responseBody = Files.readString(responseResource.getFile().toPath());

            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody));
        }
    }

    @Nested
    class CreateInitiativeTest {

        @Value("classpath:request/initiatives/200_create_initiative.json")
        private Resource validRequestResource;
        @Value("classpath:request/initiatives/400_create_initiative.json")
        private Resource invalidRequestResource;
        private MockHttpServletRequestBuilder post;

        @BeforeEach
        void setUp() {
            post = post(INITIATIVES_URL);
        }

        @Test
        void createInitiative_shouldCreateNewInitiative() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            String responseBody = mvc.perform(post
                            .content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString();

            InitiativeResponseDto dto = objectMapper.readValue(responseBody, InitiativeResponseDto.class);
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isNotNull();
            assertThat(dto.getTitle()).isEqualTo("title");
            assertThat(dto.getDescription()).isEqualTo("description");
            assertThat(dto.getMedia()).isEqualTo(MEDIA_ID);
            assertThat(dto.getStatus()).isEqualTo(InitiativeResponseDto.StatusEnum.ACTIVE);
            assertThat(dto.getProgress()).isEqualTo(50);
            assertThat(dto.getSponsor()).isNotNull();
            assertThat(dto.getContacts()).hasSize(1);
            assertThat(dto.getDirections()).hasSize(2);
            assertThat(dto.getItems()).hasSize(1);

            assertThat(repository.existsById(dto.getId())).isTrue();
        }

        @Test
        void createInitiative_shouldReturnBadRequest_givenNoBody() throws Exception {
            mvc.perform(post.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid request body"))
                    .andExpect(jsonPath("$.details").value(containsString("Required request body is missing")));

            assertThat(repository.findAll()).isEmpty();
        }

        @Test
        void createInitiative_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(post.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));

            assertThat(repository.findAll()).isEmpty();
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

            assertThat(repository.findAll()).isEmpty();
        }
    }

    @Nested
    class GetInitiativeTest {

        @Value("classpath:response/initiatives/200_get_initiative.json")
        private Resource responseResource;
        private MockHttpServletRequestBuilder get;

        @BeforeEach
        void setUp() {
            get = get("%s/%s".formatted(INITIATIVES_URL, INITIATIVE_ID));
        }

        @Test
        void getInitiative_shouldReturnExistingInitiative() throws Exception {
            repository.save(defaultInitiativeEntity());

            String responseBody = Files.readString(responseResource.getFile().toPath());

            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody));
        }

        @Test
        void getInitiative_shouldReturnNotFound_givenNoInitiativeExists() throws Exception {
            mvc.perform(get.contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }
    }

    @Nested
    class UpdateInitiativeTest {

        @Value("classpath:request/initiatives/200_update_initiative.json")
        private Resource validRequestResource;
        @Value("classpath:request/initiatives/400_update_initiative.json")
        private Resource invalidRequestResource;
        private MockHttpServletRequestBuilder put;

        @BeforeEach
        void setUp() {
            put = put("%s/%s".formatted(INITIATIVES_URL, INITIATIVE_ID));
        }

        @Test
        void createInitiative_shouldUpdateExistingInitiative() throws Exception {
            repository.save(defaultInitiativeEntity());

            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(INITIATIVE_ID.toString()))
                    .andExpect(jsonPath("$.title").value("updated title"));

            Optional<InitiativeEntity> updated = repository.findById(INITIATIVE_ID);
            assertThat(updated).isPresent();
            assertThat(updated.get().getTitle()).isEqualTo("updated title");
        }

        @Test
        void updateInitiative_shouldReturnNotFound_givenNoInitiativeExists() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
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

            assertThat(repository.findAll()).isEmpty();
        }

        @Test
        void updateInitiative_shouldReturnBadRequest_givenNoContentType() throws Exception {
            String requestBody = Files.readString(validRequestResource.getFile().toPath());

            mvc.perform(put.content(requestBody)
                            .accept(APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("UNSUPPORTED_MEDIA_TYPE"));

            assertThat(repository.findAll()).isEmpty();
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

            assertThat(repository.findAll()).isEmpty();
        }
    }

    @Nested
    class DeleteInitiativeTest {

        private MockHttpServletRequestBuilder delete;

        @BeforeEach
        void setUp() {
            delete = delete("%s/%s".formatted(INITIATIVES_URL, INITIATIVE_ID));
        }

        @Test
        void deleteInitiative_shouldDeleteExistingInitiative() throws Exception {
            repository.save(defaultInitiativeEntity());

            mvc.perform(delete.contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            assertThat(repository.existsById(INITIATIVE_ID)).isFalse();
        }

        @Test
        void deleteInitiative_shouldReturnNotFound_givenNoInitiativeExists() throws Exception {
            mvc.perform(delete.contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(containsString("not found")));
        }
    }

}
