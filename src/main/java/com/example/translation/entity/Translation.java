package com.example.translation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Translation {
    @Id
    private Long id;
    private String key;
    private String content;
    @ManyToOne
    private Locale locale;
    @ManyToMany
    private Set<Tag> tags;
}
