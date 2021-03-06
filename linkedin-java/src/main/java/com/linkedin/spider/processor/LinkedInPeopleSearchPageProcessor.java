package com.linkedin.spider.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.linkedin.json.JSONArray;
import org.linkedin.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkedin.jpa.entity.Profile;
import com.linkedin.jpa.service.ProfileService;
import com.linkedin.spider.SearchURL;
import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
public class LinkedInPeopleSearchPageProcessor implements PageProcessor {

	public String companyFormatPrefix = "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22";
	public String companyFormatSurfix = "%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH";

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProfileService userProfileService;

	LinkedInPeopleSearchPageProcessor() {

	}

	public void process(Page page) {
		SpiderConstants.downloadLinks.add(page.getUrl().toString());
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
		// page.putField("html", page.getHtml());
		// page.getHtml().links();
		// https://www.linkedin.com/in/jessicajia/#
		this.processSearch(page);
	}

	private void processSearch(Page page) {
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
		// page.putField("html", page.getHtml());
		// page.getHtml().links();
		// https://www.linkedin.com/in/jessicajia/#

		String baseURL = page.getUrl().toString().substring(0, page.getUrl().toString().lastIndexOf("&"));
		int last = org.apache.commons.lang3.StringUtils.lastIndexOf(page.getUrl().toString(), "page=");
		int currentPageNumber = 0;
		if (last != -1) {
			currentPageNumber = Integer.valueOf(page.getUrl().toString().substring(last + 5));
		} else {
			currentPageNumber = 1;
		}
		if (SpiderConstants.searchURLs.get(baseURL) == null) {
			SearchURL url = new SearchURL();
			url.setBaseURL(baseURL);
			url.setCurrentPageNumber(currentPageNumber);
			url.setAllDownloaded(false);
			SpiderConstants.searchURLs.put(baseURL, url);
		}
		SearchURL url = SpiderConstants.searchURLs.get(baseURL);

		List<String> codes = page.getHtml().codes("//code[@id!='clientPageInstance']/tidyText()").all();
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (int iter = 0; iter < codes.size(); iter++) {
			try {
				JSONObject resultObj = new JSONObject(codes.get(iter).toString().replace("\n", ""));
				jsonList.add(resultObj);
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}

		JSONArray resultArray = new JSONArray(jsonList);
		JSONArray included = this.getSearchPageInclude(resultArray);
		if (included == null) {
			url.setAllDownloaded(true);
			return;
		}
		int count = 0;
		Set<String> valuableNames = this.getTargetNames(included);
		if (valuableNames.isEmpty()) {
			url.setAllDownloaded(true);
			return;
		}
		for (int iter = 0; iter < included.length(); iter++) {
			JSONObject includeObj = included.getJSONObject(iter);
			String typeString = includeObj.getString("$type");
			if ("com.linkedin.voyager.identity.shared.MiniProfile".equals(typeString)) {
				String firstName = includeObj.getString("firstName");
				String lastName = includeObj.getString("lastName");
				if (this.isTargetProfile(valuableNames, firstName, lastName)) {
					String publicIdentifier = includeObj.getString("publicIdentifier");
					String format = "https://www.linkedin.com/in/%s/";
					String newURL = String.format(format, publicIdentifier);
					if (!SpiderConstants.downloadLinks.contains(newURL)) {
						Profile profile = userProfileService.getByBusinessKey(Profile.class, "publicIdentifier",
								publicIdentifier);
						if (profile != null) {
							continue;
						}
						// page.addTargetRequest(newURL);
						SpiderConstants.allProfileURLsThisExcution.put(newURL, false);
					}
				}
			}
		}

		JSONObject totalNode = this.getElementInIncluded(included, "com.linkedin.voyager.search.SearchCluster");
		int total = (Integer) totalNode.get("total");
		int pages = total / 10;
		int totalPage = pages + 1;
		url.setTotal(total);
		if (currentPageNumber < totalPage && currentPageNumber <= 100 && !SpiderConstants.stop
				&& !url.isAllDownloaded()) {
			url.setCurrentPageNumber(currentPageNumber + 1);
			if (!SpiderConstants.downloadLinks.contains(url.getTargetURL())) {
				page.addTargetRequest(url.getTargetURL());
				// SpiderConstants.allProfileURLsThisExcution.add(url.getTargetURL());
			}
		} else if (total <= 10) {
			url.setAllDownloaded(true);
		} else if (currentPageNumber != 100) {
			url.setCurrentPageNumber(currentPageNumber + 1);
		}

		if (SpiderConstants.stop) {
			SpiderConstants.allProfileURLsThisExcution.keySet().forEach((profile) -> {
				if (!SpiderConstants.allProfileURLsThisExcution.get(profile)) {
					page.addTargetRequest(profile);
				}
			});
		}
		logger.info("dowloaded profile number:" + SpiderConstants.profilesAccessedVector.size()
				+ " to be downloaded number: " + SpiderConstants.downloadLinks.size());

		// this.print(page);
		// this.takescreenShot(((LinkedinPage)page).getWebDriver());
		
		SpiderConstants.jedis_master.set(page.getUrl().toString(), "true");
	}

	private void print(Page page) {
		String content = page.getRawText();
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(
					new FileWriter("C:/data/webmagic/www.linkedin.com/" + UUID.randomUUID().toString() + ".html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printWriter.write(content);
		printWriter.flush();
		printWriter.close();
	}

	private void print(WebDriver webDriver) {
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		String content = webElement.getAttribute("outerHTML");
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(
					new FileWriter("C:/data/webmagic/www.linkedin.com/" + UUID.randomUUID().toString() + ".html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printWriter.write(content);
		printWriter.flush();
		printWriter.close();
	}

	public void takescreenShot(WebDriver webDriver) {
		try {
			TakesScreenshot ts = (TakesScreenshot) webDriver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("C:\\Selenium_Shubham\\" + UUID.randomUUID().toString() + ".jpg"));

			System.out.println("Screenshot is printed");
		} catch (Exception e) {
			System.out.println("Exception is handled");
			e.getMessage();
		}
	}

	private boolean isTargetProfile(Set<String> valuableNames, String firstName, String lastName) {

		for (String str : valuableNames) {
			if (firstName != null && lastName != null) {
				if (str.contains(firstName) && str.contains(lastName)) {
					return true;
				}
			} else if (firstName != null && lastName == null) {
				return str.contains(firstName);
			} else if (firstName == null && lastName != null) {
				return str.contains(lastName);
			}
		}
		return false;
	}

	private Set<String> getTargetNames(JSONArray included) {
		Set<String> valuableNames = new HashSet<String>();
		for (int iter = 0; iter < included.length(); iter++) {
			JSONObject includeObj = included.getJSONObject(iter);
			String typeString = includeObj.getString("$type");
			if ("com.linkedin.voyager.identity.profile.actions.SaveToPdf".equals(typeString)) {
				//
				String pdfURLString = includeObj.getString("requestUrl");
				int start = pdfURLString.indexOf("pdfFileName=");
				int end = pdfURLString.indexOf("Profile&");

				String name = pdfURLString.substring(start, end);
				valuableNames.add(name);
			}
		}

		return valuableNames;
	}

	private JSONArray getSearchPageInclude(JSONArray resultArray) {
		for (int iter = 0; iter < resultArray.length(); iter++) {
			try {
				JSONObject jsonObj = resultArray.getJSONObject(iter);
				JSONObject dataObj = (JSONObject) jsonObj.get("data");
				if (dataObj != null) {
					JSONObject metadataObj = (JSONObject) dataObj.get("metadata");
					if (metadataObj != null) {
						if ((JSONArray) jsonObj.get("included") != null) {
							return (JSONArray) jsonObj.get("included");
						}
					}
				}
			} catch (Exception e) {
				logger.debug(e.getLocalizedMessage());
			}
		}
		return null;
	}

	private JSONObject getElementInIncluded(JSONArray included, String elementTypeORIDToken) {
		if (included == null || elementTypeORIDToken == null) {
			return null;
		}
		for (int iter = 0; iter < included.length(); iter++) {
			JSONObject includeObj = included.getJSONObject(iter);
			String typeString = includeObj.getString("$type");
			String idString = includeObj.getString("$id");
			if (elementTypeORIDToken.equals(typeString) || elementTypeORIDToken.equals(idString)) {
				return includeObj;
			}
		}
		return null;
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return null;
	}

}