package com.linkedin.jpa.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table
public class UserProfile {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String publicIdentifier;
	@Column
	private String firstName;
	@Column
	private String maidenName;
	@Column
	private String lastName;
	@Column
	private String birthday;
	@Column(nullable = false)
	private Long totalExperienceInYear;
	@Column
	private String currentCompanyName;
	@Column(nullable = false)
	private String currentTittleName;
	@Column(nullable = false)
	private String highestDegreeName;
	@Column
	private String emailAddress;
	@Column
	private String industryName;
	@Column(nullable = false)
	private String locationName;
	@Column(nullable = false)
	private Long locationId;
	@Column
	private String address;
	@Column
	private String interests;
	@Column
	private String versionTag;
	@Column
	private String wechatImageURL;
	@Column
	private String summary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company currentCompany;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School currentSchool;

	@OneToMany(mappedBy = "profile")
	private List<UserEducation> educations;

	@OneToMany(mappedBy = "profile")
	private List<UserExperience> experiences;

	@Column
	private DateTime updateTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPublicIdentifier() {
		return publicIdentifier;
	}

	public void setPublicIdentifier(String publicIdentifier) {
		this.publicIdentifier = publicIdentifier;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Long getTotalExperienceInYear() {
		return totalExperienceInYear;
	}

	public void setTotalExperienceInYear(Long totalExperienceInYear) {
		this.totalExperienceInYear = totalExperienceInYear;
	}

	public String getCurrentCompanyName() {
		return currentCompanyName;
	}

	public void setCurrentCompanyName(String currentCompanyName) {
		this.currentCompanyName = currentCompanyName;
	}

	public String getCurrentTittleName() {
		return currentTittleName;
	}

	public void setCurrentTittleName(String currentTittleName) {
		this.currentTittleName = currentTittleName;
	}

	public String getHighestDegreeName() {
		return highestDegreeName;
	}

	public void setHighestDegreeName(String highestDegreeName) {
		this.highestDegreeName = highestDegreeName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getVersionTag() {
		return versionTag;
	}

	public void setVersionTag(String versionTag) {
		this.versionTag = versionTag;
	}

	public String getWechatImageURL() {
		return wechatImageURL;
	}

	public void setWechatImageURL(String wechatImageURL) {
		this.wechatImageURL = wechatImageURL;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Company getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(Company currentCompany) {
		this.currentCompany = currentCompany;
	}

	public School getCurrentSchool() {
		return currentSchool;
	}

	public void setCurrentSchool(School currentSchool) {
		this.currentSchool = currentSchool;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<UserEducation> getEducations() {
		return educations;
	}

	public void setEducations(List<UserEducation> educations) {
		this.educations = educations;
	}

	public List<UserExperience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<UserExperience> experiences) {
		this.experiences = experiences;
	}

	public DateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
