package com.example.translation.controller;

import com.example.translation.entity.Translation;
import com.example.translation.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/translations")
@Tag(name = "Translations", description = "Operations related to translations")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    @Operation(summary = "Create a new translation")
    @ApiResponse(responseCode = "201", description = "Translation created", content = @Content(schema = @Schema(implementation = Translation.class)))
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        Translation createdTranslation = translationService.createTranslation(translation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTranslation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing translation")
    @ApiResponse(responseCode = "200", description = "Translation updated", content = @Content(schema = @Schema(implementation = Translation.class)))
    public ResponseEntity<Translation> updateTranslation(@PathVariable Long id, @RequestBody Translation updatedTranslation) {
        Translation updated = translationService.updateTranslation(id, updatedTranslation);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a translation by ID")
    @ApiResponse(responseCode = "200", description = "Translation found", content = @Content(schema = @Schema(implementation = Translation.class)))
    public ResponseEntity<Translation> getTranslation(@PathVariable Long id) {
        Translation translation = translationService.getTranslation(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/search/key")
    @Operation(summary = "Search translations by key")
    @ApiResponse(responseCode = "200", description = "Translations found", content = @Content(schema = @Schema(implementation = Translation.class)))
    public ResponseEntity<List<Translation>> searchByKey(@RequestParam String key) {
        List<Translation> translations = translationService.searchByKey(key);
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/search/tag")
    @Operation(summary = "Search translations by tag")
    @ApiResponse(responseCode = "200", description = "Translations found", content = @Content(schema = @Schema(implementation = Translation.class)))
    public ResponseEntity<List<Translation>> searchByTag(@RequestParam String tagName) {
        List<Translation> translations = translationService.searchByTag(tagName);
        return ResponseEntity.ok(translations);
    }

    @GetMapping("/search/content")
    @Operation(summary = "Search translations by content")
    @ApiResponse(responseCode = "200", description = "Translations found", content = @Content(schema = @Schema(implementation = Translation.class)))
    public ResponseEntity<List<Translation>> searchByContent(@RequestParam String content) {
        List<Translation> translations = translationService.searchByContent(content);
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a translation by ID")
    @ApiResponse(responseCode = "204", description = "Translation deleted")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id) {
        translationService.deleteTranslation(id);
        return ResponseEntity.noContent().build();
    }
}