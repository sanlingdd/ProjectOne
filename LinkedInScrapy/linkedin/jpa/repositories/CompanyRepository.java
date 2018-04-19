package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.Company;

/**
 * Created by jt on 1/10/17.
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {
}
