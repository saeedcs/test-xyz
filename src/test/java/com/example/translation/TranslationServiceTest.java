package com.example.translation;

import com.example.translation.entity.Locale;
import com.example.translation.entity.Tag;
import com.example.translation.entity.Translation;
import com.example.translation.exception.TranslationNotFoundException;
import com.example.translation.repository.LocaleRepository;
import com.example.translation.repository.TagRepository;
import com.example.translation.repository.TranslationRepository;
import com.example.translation.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTest {

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private LocaleRepository localeRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TranslationService translationService;

    private Translation testTranslation;
    private Locale testLocale;
    private Tag testTag;

    @BeforeEach
    void setUp() {
        testLocale = new Locale();
        testLocale.setId(1L);
        testLocale.setCode("en");

        testTag = new Tag();
        testTag.setId(1L);
        testTag.setName("web");

        testTranslation = new Translation();
        testTranslation.setId(1L);
        testTranslation.setKey("test.key");
        testTranslation.setContent("test content");
        testTranslation.setLocale(testLocale);
        Set<Tag> tags = new HashSet<>();
        tags.add(testTag);
        testTranslation.setTags(tags);
    }

    @Test
    void createTranslation_shouldReturnCreatedTranslation() {
        when(localeRepository.findById(any(Long.class))).thenReturn(Optional.of(testLocale));
        when(tagRepository.findById(testTag.getId())).thenReturn(Optional.of(testTag));
        when(translationRepository.save(any(Translation.class))).thenReturn(testTranslation);

        Translation createdTranslation = translationService.createTranslation(testTranslation);

        assertEquals(testTranslation, createdTranslation);
        verify(translationRepository, times(1)).save(any(Translation.class));
    }

    @Test
    void updateTranslation_shouldReturnUpdatedTranslation() {
        when(translationRepository.findById(testTranslation.getId())).thenReturn(Optional.of(testTranslation));
        when(localeRepository.findById(testLocale.getId())).thenReturn(Optional.of(testLocale));
        when(tagRepository.findById(testTag.getId())).thenReturn(Optional.of(testTag));
        when(translationRepository.save(any(Translation.class))).thenReturn(testTranslation);

        Translation updatedTranslation = translationService.updateTranslation(testTranslation.getId(), testTranslation);

        assertEquals(testTranslation, updatedTranslation);
        verify(translationRepository, times(1)).save(any(Translation.class));
    }

    @Test
    void getTranslation_shouldReturnTranslation() {
        when(translationRepository.findById(testTranslation.getId())).thenReturn(Optional.of(testTranslation));

        Translation foundTranslation = translationService.getTranslation(testTranslation.getId());

        assertEquals(testTranslation, foundTranslation);
    }

    @Test
    void getTranslation_shouldThrowExceptionWhenNotFound() {
        when(translationRepository.findById(testTranslation.getId())).thenReturn(Optional.empty());

        assertThrows(TranslationNotFoundException.class, () -> translationService.getTranslation(testTranslation.getId()));
    }

    @Test
    void searchByKey_shouldReturnListOfTranslations() {
        List<Translation> translations = new ArrayList<>();
        translations.add(testTranslation);
        when(translationRepository.findByKey(testTranslation.getKey())).thenReturn(translations);

        List<Translation> foundTranslations = translationService.searchByKey(testTranslation.getKey());

        assertEquals(translations, foundTranslations);
    }

    @Test
    void searchByTag_shouldReturnListOfTranslations() {
        List<Translation> translations = new ArrayList<>();
        translations.add(testTranslation);
        when(translationRepository.findByTagName(testTag.getName())).thenReturn(translations);

        List<Translation> foundTranslations = translationService.searchByTag(testTag.getName());

        assertEquals(translations, foundTranslations);
    }

    @Test
    void searchByContent_shouldReturnListOfTranslations() {
        List<Translation> translations = new ArrayList<>();
        translations.add(testTranslation);
        when(translationRepository.findByContentContaining(testTranslation.getContent())).thenReturn(translations);

        List<Translation> foundTranslations = translationService.searchByContent(testTranslation.getContent());

        assertEquals(translations, foundTranslations);
    }

    @Test
    void deleteTranslation_shouldDeleteTranslation() {
        when(translationRepository.findById(testTranslation.getId())).thenReturn(Optional.of(testTranslation));

        translationService.deleteTranslation(testTranslation.getId());

        verify(translationRepository, times(1)).deleteById(testTranslation.getId());
    }

    @Test
    void deleteTranslation_shouldThrowExceptionWhenNotFound() {
        when(translationRepository.findById(testTranslation.getId())).thenReturn(Optional.empty());

        assertThrows(TranslationNotFoundException.class, () -> translationService.deleteTranslation(testTranslation.getId()));
        verify(translationRepository, never()).deleteById(anyLong());
    }

}