package com.linkedin.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.linkedin.jpa.entity.Skill;

public interface SkillRepository extends CrudRepository<Skill, Long> {
}

