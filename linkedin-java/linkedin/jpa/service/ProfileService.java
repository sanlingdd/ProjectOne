package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.Profile;

public interface ProfileService extends EntityService<Profile> {
	List<Profile> listAll();

	Profile getById(Long id);

	Profile saveOrUpdate(Profile profile);

	void delete(Long id);
}
