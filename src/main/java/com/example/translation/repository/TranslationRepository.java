package com.example.translation.repository;

import com.example.translation.entity.Locale;
import com.example.translation.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findByKey(String key);
    @Query("SELECT t FROM Translation t JOIN t.tags tag WHERE tag.name = :tagName")
    List<Translation> findByTagName(@Param("tagName") String tagName);
    List<Translation> findByContentContaining(String content);
    List<Translation> findByLocale(Locale locale);
}