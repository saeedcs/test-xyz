package com.example.translation.controller;

import com.example.translation.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/translations")
    public ResponseEntity<Map<String, String>> exportTranslations() {
        Map<String, String> cdnUrls = new HashMap<>();
        Map<String, Map<String, String>> translations = exportService.exportTranslations();
        for (String localeCode : translations.keySet()) {
            cdnUrls.put(localeCode, exportService.generateCDNUrl(localeCode));
        }
        return ResponseEntity.ok(cdnUrls);
    }
}