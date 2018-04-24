package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.jpa.entity.Language;
import com.linkedin.jpa.repositories.LanguageRepository;


@Service

public class LanguageServiceImp extends EntityServiceImp<Language> implements LanguageService {

	private LanguageRepository languageRepository;

	@Autowired
	public LanguageServiceImp(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	@Override
	public List<Language> listAll() {
		List<Language> Languages = new ArrayList<Language>();
		languageRepository.findAll().forEach(Languages::add);// fun with Java 8

		return Languages;
	}

	@Override
	public Language getById(Long id) {
		return languageRepository.findOne(id);
	}

	@Override
	@Transactional
	public Language saveOrUpdate(Language Language) {
		return languageRepository.save(Language);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		languageRepository.delete(id);

	}
}
