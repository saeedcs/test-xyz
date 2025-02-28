package com.example.translation;

import com.example.translation.entity.Locale;
import com.example.translation.entity.Tag;
import com.example.translation.entity.Translation;
import com.example.translation.repository.LocaleRepository;
import com.example.translation.repository.TagRepository;
import com.example.translation.repository.TranslationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class TranslationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private LocaleRepository localeRepository;

    @Autowired
    private TagRepository tagRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    private Translation testTranslation;
    private Locale testLocale;
    private Tag testTag;

    @BeforeEach
    void setUp() {
        translationRepository.deleteAll();
        localeRepository.deleteAll();
        tagRepository.deleteAll();

        testLocale = new Locale();
        testLocale.setCode("en");
        testLocale = localeRepository.save(testLocale);

        testTag = new Tag();
        testTag.setName("web");
        testTag = tagRepository.save(testTag);

        testTranslation = new Translation();
        testTranslation.setKey("test.key");
        testTranslation.setContent("test content");
        testTranslation.setLocale(testLocale);
        Set<Tag> tags = new HashSet<>();
        tags.add(testTag);
        testTranslation.setTags(tags);
    }

    @Test
    void createTranslation_shouldReturnCreatedTranslation() throws Exception {
        mockMvc.perform(post("/translations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTranslation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.key").value("test.key"));
    }

    @Test
    void getTranslation_shouldReturnTranslation() throws Exception {
        Translation savedTranslation = translationRepository.save(testTranslation);

        mockMvc.perform(get("/translations/" + savedTranslation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("test.key"));
    }

    // Add similar tests for updateTranslation, search, delete, and export endpoints
}