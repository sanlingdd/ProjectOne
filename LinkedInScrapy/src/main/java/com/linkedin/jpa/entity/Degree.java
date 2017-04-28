package com.linkedin.jpa.entity;

public enum Degree {
	NONE, ELEMENTARY, MIDDLE, HIGH, COLLEGE, BACHELOR, MASTER, DOCTOR;

	public Degree ValueOf(String degree) {
		try {
			return Degree.valueOf(degree);
		} catch (Exception e) {
			return NONE;
		}
	}
}
