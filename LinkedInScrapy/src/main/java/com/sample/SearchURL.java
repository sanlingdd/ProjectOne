package com.sample;

public class SearchURL {
	private String baseURL;
	private Integer currentPageNumber;
	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private boolean allDownloaded;

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public int getCurrentPageNUmber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public boolean isAllDownloaded() {
		return allDownloaded;
	}

	public void setAllDownloaded(boolean allDownloaded) {
		this.allDownloaded = allDownloaded;
	}

	public String getTargetURL() {
		return new String(baseURL + "&page=" + currentPageNumber);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allDownloaded ? 1231 : 1237);
		result = prime * result + ((baseURL == null) ? 0 : baseURL.hashCode());
		result = prime * result + currentPageNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchURL other = (SearchURL) obj;
		if (baseURL.equals(other.baseURL)) {
			return true;
		}
		return false;
	}

}
