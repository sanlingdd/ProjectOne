package com.linkedin.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Profile {

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
	@Column
	private Long totalExperienceInYear;
	@Column
	private String currentCompanyName;
	@Column
	private String currentTittleName;
	@Column
	private String highestDegreeName;
	@Column
	private String emailAddress;
	@Column
	private String industryName;
	@Column
	private String address;
	@Column
	private String interests;
	@Column
	private String versionTag;
	@Column
	private String wechatImageURL;
	@Column(length = 5120)
	private String summary;
	@Column
	private DateTime updateTime;
	
	@Column
	private String industryUrn;
	
	@Column
	private String headline;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company currentCompany;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School currentSchool;

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<Education> educations = new ArrayList<Education>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<Experience> experiences = new ArrayList<Experience>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<ProfileLanguage> languages = new ArrayList<ProfileLanguage>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<Honor> honors = new ArrayList<Honor>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<Patent> patents = new ArrayList<Patent>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<Publication> publications = new ArrayList<Publication>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<ProfileSkill> skills = new ArrayList<ProfileSkill>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<Phone> phones = new ArrayList<Phone>();

	@OneToMany(mappedBy = "profile", cascade = { CascadeType.ALL })
	private List<WebSite> sites = new ArrayList<WebSite>();

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

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public DateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}

	public List<ProfileLanguage> getLanguages() {
		return languages;
	}

	public void setLanguages(List<ProfileLanguage> languages) {
		this.languages = languages;
	}

	public List<Honor> getHonors() {
		return honors;
	}

	public void setHonors(List<Honor> honors) {
		this.honors = honors;
	}

	public List<Patent> getPatents() {
		return patents;
	}

	public void setPatents(List<Patent> patents) {
		this.patents = patents;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public List<ProfileSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<ProfileSkill> skills) {
		this.skills = skills;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public List<WebSite> getSites() {
		return sites;
	}

	public void setSites(List<WebSite> sites) {
		this.sites = sites;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getIndustryUrn() {
		return industryUrn;
	}

	public void setIndustryUrn(String industryUrn) {
		this.industryUrn = industryUrn;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}	

}
