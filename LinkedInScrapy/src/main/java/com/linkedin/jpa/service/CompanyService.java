package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.Company;

public interface CompanyService {
	List<Company> listAll();

	Company getById(Long id);

	Company saveOrUpdate(Company product);

	void delete(Long id);
}
