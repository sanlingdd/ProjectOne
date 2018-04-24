package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.jpa.entity.School;
import com.linkedin.jpa.repositories.SchoolRepository;

@Service

public class SchoolServiceImp extends EntityServiceImp<School> implements SchoolService {

	private SchoolRepository schoolRepository;

	@Autowired
	public SchoolServiceImp(SchoolRepository schoolRepository) {
		this.schoolRepository = schoolRepository;
	}

	@Override
	public List<School> listAll() {
		List<School> Schools = new ArrayList<School>();
		schoolRepository.findAll().forEach(Schools::add);// fun with Java 8

		return Schools;
	}

	@Override
	public School getById(Long id) {
		return schoolRepository.findOne(id);
	}

	@Override
	@Transactional
	public School saveOrUpdate(School School) {
		return schoolRepository.save(School);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		schoolRepository.delete(id);

	}
}
