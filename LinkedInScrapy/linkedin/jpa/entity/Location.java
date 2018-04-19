package com.linkedin.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table
public class Location {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private Long locationid;
	@Column
	private String locationName;
	
	@OneToMany(mappedBy="location",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Experience> experience;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getLocationId() {
		return locationid;
	}
	public void setLocationId(Long locationId) {
		this.locationid = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Experience> getExperience() {
		return experience;
	}
	public void setExperience(List<Experience> experience) {
		this.experience = experience;
	}

}
