package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.School;

/**
 * Created by jt on 1/10/17.
 */
public interface SchoolRepository extends CrudRepository<School, Long> {
}
