package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.Location;


public interface LocationRepository extends CrudRepository<Location, Long> {
}
