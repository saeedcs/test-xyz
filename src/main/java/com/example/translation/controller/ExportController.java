package com.example.translation.controller;

import com.example.translation.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("/translations")
    public ResponseEntity<Map<String, Map<String, String>>> exportTranslations() {
        Map<String, Map<String, String>> translations = exportService.exportTranslations();
        return ResponseEntity.ok(translations);
    }
}