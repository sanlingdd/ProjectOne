package com.linkedin.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table
public class UserEducation {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private UserProfile profile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School graudateSchool;

	@Column(nullable = false)
	private String universityName;
	@Column(nullable = false)
	private String major;
	@Column(nullable = false)
	private Long fromLong;
	@Column(nullable = false)
	private Long toLong;
	@Column(nullable = false)
	private String from;
	@Column(nullable = false)
	private String to;
	@Column
	@Enumerated(EnumType.STRING)
	private Degree degree;
	
	@Column
	private DateTime updateTime;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
