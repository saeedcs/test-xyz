package com.example.translation.controller;

import com.example.translation.entity.Translation;
import com.example.translation.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/translations")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        Translation createdTranslation = translationService.createTranslation(translation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTranslation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translation> updateTranslation(@PathVariable Long id, @RequestBody Translation updatedTranslation) {
        Translation updated = translationService.updateTranslation(id, updatedTranslation);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslation(@PathVariable Long id) {
        Translation translation = translationService.getTranslation(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/search/key")
    public ResponseEntity<List<Translation>> searchByKey(@RequestParam String key) {
        List<Translation> translations = translationService.searchByKey(key);
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/search/tag")
    public ResponseEntity<List<Translation>> searchByTag(@RequestParam String tagName) {
        List<Translation> translations = translationService.searchByTag(tagName);
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/search/content")
    public ResponseEntity<List<Translation>> searchByContent(@RequestParam String content) {
        List<Translation> translations = translationService.searchByContent(content);
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id) {
        translationService.deleteTranslation(id);
        return ResponseEntity.noContent().build();
    }
}