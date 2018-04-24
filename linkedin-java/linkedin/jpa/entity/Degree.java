package com.linkedin.jpa.entity;

public enum Degree {
	NONE, ELEMENTARY, MIDDLE, HIGH, COLLEGE, BACHELOR, MASTER, DOCTOR;

	public static Degree VValueOf(String degree) {
		if (degree == null) {
			return NONE;
		}
		try {
			String[] names = { "NONE", "ELEMENTARY", "MIDDLE", "HIGH", "COLLEGE", "BACHELOR", "MASTER", "DOCTOR" };
			for (String name : names) {
				if (degree.toUpperCase().contains(name)) {
					return Degree.valueOf(name);
				}
			}
			return NONE;
		} catch (Exception e) {
			return NONE;
		}
	}
}
