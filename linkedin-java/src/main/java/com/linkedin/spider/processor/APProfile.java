package com.linkedin.spider.processor;

public class APProfile implements Comparable<APProfile>{

	String name;
	String id;
	String chinesename;
	String department;
	String workphone;
	String mobilephone;
	String office;
	String location;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getWorkphone() {
		return workphone;
	}
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	
	@Override
	public String toString() {
		return "APProfile [name=" + name + ", id=" + id + ", chinesename=" + chinesename + ", department=" + department
				+ ", workphone=" + workphone + ", mobilephone=" + mobilephone + ", office=" + office + ", location="
				+ location + "]";
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	@Override
	public int compareTo(APProfile o) {
		return this.getId().trim().compareTo(o.getId().trim());
	}
	
	
	
}
