package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.UserProfile;

public interface UserProfileService {
	List<UserProfile> listAll();

	UserProfile getById(Long id);

	UserProfile saveOrUpdate(UserProfile product);

	void delete(Long id);
}
