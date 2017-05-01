package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.Company;

public interface CompanyService extends EntityService<Company>{
	List<Company> listAll();

	Company getById(Long id);
	
	Company saveOrUpdate(Company company);

	void delete(Long id);
}
