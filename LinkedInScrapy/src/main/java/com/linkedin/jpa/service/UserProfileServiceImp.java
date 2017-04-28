package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.linkedin.jpa.entity.UserProfile;
import com.linkedin.jpa.repositories.UserProfileRepository;

public class UserProfileServiceImp implements UserProfileService {
	
	private UserProfileRepository UserProfileRepository;

	@Autowired
	public UserProfileServiceImp(UserProfileRepository UserProfileRepository) {
		this.UserProfileRepository = UserProfileRepository;
	}

	@Override
	public List<UserProfile> listAll() {
		List<UserProfile> UserProfiles = new ArrayList<UserProfile>();
		UserProfileRepository.findAll().forEach(UserProfiles::add);// fun with Java 8

		return UserProfiles;
	}

	@Override
	public UserProfile getById(Long id) {
		return UserProfileRepository.findOne(id);
	}

	@Override
	public UserProfile saveOrUpdate(UserProfile UserProfile) {
		return UserProfileRepository.save(UserProfile);
	}

	@Override
	public void delete(Long id) {
		UserProfileRepository.delete(id);

	}
}
