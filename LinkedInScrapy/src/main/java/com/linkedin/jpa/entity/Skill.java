package com.linkedin.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Skill {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String skillName;
	@Column
	private String skillEndorseCount;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private Profile profile;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getSkillName() {
		return skillName;
	}


	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}


	public String getSkillEndorseCount() {
		return skillEndorseCount;
	}


	public void setSkillEndorseCount(String skillEndorseCount) {
		this.skillEndorseCount = skillEndorseCount;
	}


	public Profile getProfile() {
		return profile;
	}


	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
