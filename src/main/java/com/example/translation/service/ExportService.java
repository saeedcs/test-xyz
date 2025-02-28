package com.example.translation.service;

import com.example.translation.entity.Locale;
import com.example.translation.entity.Translation;
import com.example.translation.repository.LocaleRepository;
import com.example.translation.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportService {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private LocaleRepository localeRepository;

    @Value("${cdn.url}")
    private String cdnUrl;

    public Map<String, Map<String, String>> exportTranslations() {
        Map<String, Map<String, String>> result = new HashMap<>();
        Iterable<Locale> locales = localeRepository.findAll();

        for (Locale locale : locales) {
            Map<String, String> localeTranslations = new HashMap<>();
            List<Translation> translations = translationRepository.findByLocale(locale);

            for (Translation translation : translations) {
                localeTranslations.put(translation.getKey(), translation.getContent());
            }

            result.put(locale.getCode(), localeTranslations);
        }

        return result;
    }

    public String generateCDNUrl(String localeCode) {
        return cdnUrl + localeCode + ".json";
    }
}