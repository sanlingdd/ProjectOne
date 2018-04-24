package com.linkedin.spider;

public class DateString {
	String year;
	String month;
	String day;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
		if (this.month!=null && this.month.length() == 1) {
			this.month = "0" + this.month;
		}
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {

		this.day = day;
		if (this.day!=null && this.day.length() == 1) {
			this.day = "0" + this.day;
		}
	}

	@Override
	public String toString() {
		String result = "";
		if (year != null) {
			result += year;
		}
		if (year != null && month != null) {
			result += "-" + month;
		} else if (month != null) {
			result += month;
		}
		if (month != null && day != null) {
			result += "-" + day;
		}
		return result;
	}

}
