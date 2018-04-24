package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.jpa.entity.Company;
import com.linkedin.jpa.repositories.CompanyRepository;

@Service

public class CompanyServiceImp extends EntityServiceImp<Company> implements CompanyService {

	private CompanyRepository companyRepository;


	@Autowired
	public CompanyServiceImp(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public List<Company> listAll() {
		List<Company> companys = new ArrayList<Company>();
		companyRepository.findAll().forEach(companys::add);// fun with Java 8

		return companys;
	}

	@Override
	public Company getById(Long id) {
		return companyRepository.findOne(id);
	}

	@Override
	public Company saveOrUpdate(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public void delete(Long id) {
		companyRepository.delete(id);

	}
}
