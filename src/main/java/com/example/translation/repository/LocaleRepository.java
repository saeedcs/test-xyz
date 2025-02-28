package com.example.translation.repository;

import com.example.translation.entity.Locale;
import org.springframework.data.repository.CrudRepository;

public interface LocaleRepository extends CrudRepository<Locale, Long> {
}
