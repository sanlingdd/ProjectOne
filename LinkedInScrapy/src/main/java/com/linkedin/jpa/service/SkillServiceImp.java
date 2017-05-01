package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkedin.jpa.entity.Skill;
import com.linkedin.jpa.repositories.SkillRepository;

@Service
public class SkillServiceImp extends EntityServiceImp implements SkillService {

	private SkillRepository skillRepository;

	@Autowired
	public SkillServiceImp(SkillRepository productRepository) {
		this.skillRepository = productRepository;
	}

	@Override
	public List<Skill> listAll() {
		List<Skill> Skills = new ArrayList<Skill>();
		skillRepository.findAll().forEach(Skills::add);// fun with Java 8

		return Skills;
	}

	@Override
	public Skill getById(Long id) {
		return skillRepository.findOne(id);
	}

	@Override
	public Skill saveOrUpdate(Skill Skill) {
		return skillRepository.save(Skill);
	}

	@Override
	public void delete(Long id) {
		skillRepository.delete(id);

	}
}