package com.linkedin.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserExperience {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private UserProfile profile;

	private String location;
	private String companyName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(nullable = false)
	private String from;
	@Column(nullable = false)
	private String to;
	@Column(nullable = false)
	private Long fromLong;
	@Column(nullable = false)
	private Long toLong;
	@Column(nullable = false)
	private String occupation;
	@Column(nullable = false)
	private String responsibility;

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
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

}
