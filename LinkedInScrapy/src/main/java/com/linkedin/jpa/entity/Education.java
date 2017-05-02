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
public class Education {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School graudateSchool;

	@Column
	private String schoolName;
	@Column
	private String major;
	@Column
	private DateTime fromLong;
	@Column
	private DateTime toLong;
	@Column
	private String fromString;
	@Column
	private String toString;
	@Column
	@Enumerated(EnumType.STRING)
	private Degree degree;

	@Column
	private DateTime updateTime;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public School getGraudateSchool() {
		return graudateSchool;
	}

	public void setGraudateSchool(School graudateSchool) {
		this.graudateSchool = graudateSchool;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public DateTime getFromLong() {
		if (fromString != null && fromLong != null
				&& fromLong.getMillis() - new DateTime("2100-01-01").getMillis() == 0) {
			return new DateTime();
		}
		return fromLong;
	}

	public void setFromLong(DateTime fromLong) {
		this.fromLong = fromLong;
	}

	public DateTime getToLong() {
		if (toString != null && toLong != null && toLong.getMillis() - new DateTime("2100-01-01").getMillis() == 0) {
			return new DateTime();
		}
		return toLong;
	}

	public void setToLong(DateTime toLong) {
		this.toLong = toLong;
	}

	public String getFromString() {
		if (fromString != null && fromLong != null
				&& fromLong.getMillis() - new DateTime("2100-01-01").getMillis() == 0) {
			return new DateTime().toString();
		}
		return fromString;
	}

	public void setFromString(String fromString) {
		this.fromString = fromString;
	}

	public String getToString() {
		if (toString != null && toLong != null && toLong.getMillis() - new DateTime("2100-01-01").getMillis() == 0) {
			return new DateTime().toString();
		}
		return toString;
	}

	public void setToString(String toString) {
		this.toString = toString;
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

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}
