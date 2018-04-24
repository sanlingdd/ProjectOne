package com.linkedin.spider.processor;

import org.openqa.selenium.WebDriver;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.selenium.WebDriverPool;

public class LinkedinPage extends Page {

	private boolean isLinkedInLimitStarted;
	private WebDriverPool webDriverPool;
	private WebDriver webDriver;

	public boolean isLinkedInLimitStarted() {
		return isLinkedInLimitStarted;
	}

	public void setLinkedInLimitStarted(boolean isLinkedInLimitStarted) {
		this.isLinkedInLimitStarted = isLinkedInLimitStarted;
	}

	public WebDriverPool getWebDriverPool() {
		return webDriverPool;
	}

	public void setWebDriverPool(WebDriverPool webDriverPool) {
		this.webDriverPool = webDriverPool;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	
}
