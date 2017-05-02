package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.Skill;

public interface SkillService extends EntityService<Skill>{
	List<Skill> listAll();

	Skill getById(Long id);

	Skill saveOrUpdate(Skill skill);

	void delete(Long id);
}
