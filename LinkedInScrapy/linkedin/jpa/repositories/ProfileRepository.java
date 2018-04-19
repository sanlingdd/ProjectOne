package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.Profile;

/**
 * Created by jt on 1/10/17.
 */
public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
