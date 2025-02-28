package com.example.translation.controller;

import com.example.translation.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/export")
@Tag(name = "Export", description = "Operations related to exporting translations")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/translations")
    @Operation(summary = "Export all translations")
    @ApiResponse(responseCode = "200", description = "Translations exported")
    public ResponseEntity<Map<String, String>> exportTranslations() {
        Map<String, String> cdnUrls = new HashMap<>();
        Map<String, Map<String, String>> translations = exportService.exportTranslations();
        for (String localeCode : translations.keySet()) {
            cdnUrls.put(localeCode, exportService.generateCDNUrl(localeCode));
        }
        return ResponseEntity.ok(cdnUrls);
    }
}