package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.School;

public interface SchoolService {
	List<School> listAll();

	School getById(Long id);

	School saveOrUpdate(School school);

	void delete(Long id);
}
