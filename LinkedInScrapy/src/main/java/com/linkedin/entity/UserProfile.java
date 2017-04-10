package com.linkedin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String publicIdentifier;
	private String firstName;
	private String maidenName;
	private String lastName;
	private String birthday;
	private Integer totalExperienceInYear;
	private String currentCompanyName;
	private String currentTittleName;
	private String highestDegreeName;
	private String emailAddress;
	private String industryName;
	private String locationName;
	private Integer locationId;
	private String address;
	private String interests;
	private String versionTag;
	private String wechatImageURL;
	private String summary;

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

	public Integer getTotalExperienceInYear() {
		return totalExperienceInYear;
	}

	public void setTotalExperienceInYear(Integer totalExperienceInYear) {
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

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
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

}
