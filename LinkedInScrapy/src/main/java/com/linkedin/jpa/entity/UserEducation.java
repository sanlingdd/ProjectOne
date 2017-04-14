package com.linkedin.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserEducation {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private UserProfile profile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School graudateSchool;

	private String universityName;
	private String major;
	private Long fromLong;
	private Long toLong;
	private String from;
	private String to;

	@Enumerated(EnumType.STRING)
	private Degree degree;

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public School getGraudateSchool() {
		return graudateSchool;
	}

	public void setGraudateSchool(School graudateSchool) {
		this.graudateSchool = graudateSchool;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Long getFromLong() {
		return fromLong;
	}

	public void setFromLong(Long fromLong) {
		this.fromLong = fromLong;
	}

	public Long getToLong() {
		return toLong;
	}

	public void setToLong(Long toLong) {
		this.toLong = toLong;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

}
