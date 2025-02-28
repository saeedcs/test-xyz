package com.example.translation.service;

// TranslationService.java
import com.example.translation.entity.Locale;
import com.example.translation.entity.Tag;
import com.example.translation.entity.Translation;
import com.example.translation.exception.TranslationNotFoundException;
import com.example.translation.repository.LocaleRepository;
import com.example.translation.repository.TagRepository;
import com.example.translation.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;
    @Autowired
    private LocaleRepository localeRepository;
    @Autowired
    private TagRepository tagRepository;

    @CachePut(value = "translations", key = "#result.id")
    public Translation createTranslation(Translation translation) {
        Locale locale = localeRepository.findById(translation.getLocale().getId())
                .orElseGet(() -> localeRepository.save(translation.getLocale()));

        Set<Tag> managedTags = new HashSet<>();
        if (translation.getTags() != null) {
            for (Tag tag : translation.getTags()) {
                Tag managedTag = tagRepository.findById(tag.getId())
                        .orElseGet(() -> tagRepository.save(tag));
                managedTags.add(managedTag);
            }
        }

        translation.setLocale(locale);
        translation.setTags(managedTags);
        return translationRepository.save(translation);
    }

    @CachePut(value = "translations", key = "#updatedTranslation.id")
    public Translation updateTranslation(Long id, Translation updatedTranslation) {
        Translation existingTranslation = translationRepository.findById(id)
                .orElseThrow(() -> new TranslationNotFoundException("Translation not found with id: " + id));

        // Update fields
        existingTranslation.setKey(updatedTranslation.getKey());
        existingTranslation.setContent(updatedTranslation.getContent());

        // Handle locale update
        Locale locale = localeRepository.findById(updatedTranslation.getLocale().getId())
                .orElseGet(() -> localeRepository.save(updatedTranslation.getLocale()));
        existingTranslation.setLocale(locale);

        // Handle tags update
        Set<Tag> managedTags = new HashSet<>();
        if (updatedTranslation.getTags() != null) {
            for (Tag tag : updatedTranslation.getTags()) {
                Tag managedTag = tagRepository.findById(tag.getId())
                        .orElseGet(() -> tagRepository.save(tag));
                managedTags.add(managedTag);
            }
        }
        existingTranslation.setTags(managedTags);

        return translationRepository.save(existingTranslation);
    }

    @Cacheable(value = "translations", key = "#translation.id")
    public Translation getTranslation(Long id) {
        return translationRepository.findById(id)
                .orElseThrow(() -> new TranslationNotFoundException("Translation not found with id: " + id));
    }

    @Cacheable(value = "translationsByKey", key = "#key")
    public List<Translation> searchByKey(String key) {
        return translationRepository.findByKey(key);
    }

    @Cacheable(value = "translationsByTag", key = "#tagName")
    public List<Translation> searchByTag(String tagName) {
        return translationRepository.findByTagName(tagName);
    }

    @Cacheable(value = "translationsByContent", key = "#content")
    public List<Translation> searchByContent(String content) {
        return translationRepository.findByContentContaining(content);
    }

    @CacheEvict(value = "translations", key = "#id")
    public void deleteTranslation(Long id) {
        Optional<Translation> translation = translationRepository.findById(id);
        if(translation.isPresent()){
            translationRepository.deleteById(id);
        } else {
            throw new TranslationNotFoundException("Translation not found with id: " + id);
        }
    }
}