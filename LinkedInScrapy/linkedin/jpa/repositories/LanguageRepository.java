package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.Language;

public interface LanguageRepository extends CrudRepository<Language, Long> {
}