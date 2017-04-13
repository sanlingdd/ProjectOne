package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.UserProfile;

/**
 * Created by jt on 1/10/17.
 */
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
}
