package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.jpa.entity.Profile;
import com.linkedin.jpa.repositories.ProfileRepository;

@Service

public class ProfileServiceImp extends EntityServiceImp<Profile> implements ProfileService {
	
	private ProfileRepository UserProfileRepository;

	@Autowired
	public ProfileServiceImp(ProfileRepository userProfileRepository) {
		this.UserProfileRepository = userProfileRepository;
	}

	@Override
	public List<Profile> listAll() {
		List<Profile> UserProfiles = new ArrayList<Profile>();
		UserProfileRepository.findAll().forEach(UserProfiles::add);// fun with Java 8

		return UserProfiles;
	}

	@Override
	public Profile getById(Long id) {
		return UserProfileRepository.findOne(id);
	}

	@Override
	@Transactional
	public Profile saveOrUpdate(Profile UserProfile) {
		return UserProfileRepository.save(UserProfile);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		UserProfileRepository.delete(id);

	}
}
