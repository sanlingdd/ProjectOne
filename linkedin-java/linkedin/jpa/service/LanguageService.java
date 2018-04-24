package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.Language;

public interface LanguageService extends EntityService<Language>{
	List<Language> listAll();

	Language getById(Long id);
	
	Language saveOrUpdate(Language language);

	void delete(Long id);
}