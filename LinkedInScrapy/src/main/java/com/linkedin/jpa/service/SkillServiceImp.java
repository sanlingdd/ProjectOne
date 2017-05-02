package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedin.jpa.entity.Skill;
import com.linkedin.jpa.repositories.SkillRepository;

@Service

public class SkillServiceImp extends EntityServiceImp<Skill> implements SkillService {

	private SkillRepository skillRepository;

	@Autowired
	public SkillServiceImp(SkillRepository skillRepository) {
		this.skillRepository = skillRepository;
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
	@Transactional
	public Skill saveOrUpdate(Skill Skill) {
		return skillRepository.save(Skill);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		skillRepository.delete(id);

	}
}