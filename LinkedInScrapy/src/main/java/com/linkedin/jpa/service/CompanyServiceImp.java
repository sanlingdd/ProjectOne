package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkedin.jpa.entity.Company;
import com.linkedin.jpa.repositories.CompanyRepository;

@Service
public class CompanyServiceImp implements CompanyService {

	private CompanyRepository companyRepository;

	@Autowired
	public CompanyServiceImp(CompanyRepository productRepository) {
		this.companyRepository = productRepository;
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
		companyRepository.save(company);
		return company;
	}

	@Override
	public void delete(Long id) {
		companyRepository.delete(id);

	}
}
