package com.linkedin.spider.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Arrays;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.linkedin.jpa.entity.Company;
import com.linkedin.jpa.entity.Degree;
import com.linkedin.jpa.entity.Location;
import com.linkedin.jpa.entity.School;
import com.linkedin.jpa.entity.UserEducation;
import com.linkedin.jpa.entity.UserExperience;
import com.linkedin.jpa.entity.UserProfile;
import com.linkedin.jpa.service.CompanyService;
import com.linkedin.jpa.service.LocationService;
import com.linkedin.jpa.service.SchoolService;
import com.linkedin.jpa.service.UserProfileService;
import com.linkedin.spider.DateString;
import com.linkedin.spider.Pair;
import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.selector.Selectable;

public class LinkedinPeopleProfilePageProcessor implements PageProcessor {

	private Logger logger = LoggerFactory.getLogger(getClass());
	public String companyFormatPrefix = "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22";
	public String companyFormatSurfix = "%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH";
	@Autowired
	private CompanyService companyService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private LocationService locationService;

	private UserProfile userProfile = new UserProfile();

	LinkedinPeopleProfilePageProcessor() {
	}

	public void process(Page page) {
		SpiderConstants.downloadLinks.add(page.getUrl().toString());
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
		// page.putField("html", page.getHtml());
		// page.getHtml().links();
		// https://www.linkedin.com/in/jessicajia/#

		this.processProfile(page);
	}

	private void processProfile(Page page) {
		userProfile.setPublicIdentifier(page.getUrl().toString());
		userProfile.setUpdateTime(new DateTime());
		page.putField("publicIdentifier", page.getUrl().toString());

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
		// get the profile
		this.getProfile(page, resultArray);
		// get contact info
		this.getContactInfo(page, resultArray);

		// get honors
		getHonors(page);

		// get Publication
		getPublications(page);

		// get Language
		getLanguage(page);

		// get patent
		getPatent(page);
		if (page instanceof LinkedinPage) {
			LinkedinPage lpage = (LinkedinPage) page;
			if (lpage.getWebDriver() != null) { // get skills
				getSkills(page);
				lpage.getWebDriverPool().returnToPool(lpage.getWebDriver());
			}
		}

	}

	private void getContactInfo(Page page, JSONArray jsonList) {
		JSONObject partitionObj = this.getPartitionJson(jsonList,
				"com.linkedin.voyager.identity.profile.ProfileContactInfo");
		if (partitionObj == null) {
			return;
		}
		JSONObject dataObj = partitionObj.getJSONObject("data");

		JSONArray included = (JSONArray) partitionObj.get("included");
		if (included == null) {
			return;
		}

		// emailAddress
		page.putField("emailAddress", dataObj.getString("emailAddress"));
		// address
		page.putField("address", dataObj.getString("address"));
		// phone
		JSONArray phoneNumbersURLs = (JSONArray) dataObj.get("phoneNumbers");
		if (phoneNumbersURLs != null) {
			List<HashMap<String, Object>> phones = this.getPhoneNumbers(phoneNumbersURLs, included);
			page.putField("phones", phones);
		}

		// birthday//
		String birthDateOn = dataObj.getString("birthDateOn");
		if (birthDateOn != null) {
			DateString birthDay = this.getDateString(birthDateOn, included);
			page.putField("birthday", birthDay.toString());
		}
		// website
		JSONArray websitesURLs = (JSONArray) dataObj.get("websites");
		if (websitesURLs != null) {
			List<HashMap<String, Object>> websitesAddresses = this.getWebSites(websitesURLs, included);
			page.putField("websites", websitesAddresses);
		}

		// wechat
		String weChatContactInfo = dataObj.getString("weChatContactInfo");
		JSONObject wechatElement = this.getElementInIncluded(included, weChatContactInfo);
		if (wechatElement != null) {
			page.putField("wechatImageURL", wechatElement.get("qrCodeImageUrl"));
		}

	}

	private List<HashMap<String, Object>> getWebSites(JSONArray websites, JSONArray included) {
		List<HashMap<String, Object>> phones = new ArrayList<HashMap<String, Object>>();
		for (int iter = 0; iter < websites.length(); iter++) {
			String website = websites.getString(iter);
			JSONObject phoneObj = this.getElementInIncluded(included, website);
			HashMap<String, Object> phone = new HashMap<String, Object>();
			phone.put("websitesurl", phoneObj.getString("url"));
			phones.add(phone);
		}
		return phones;
	}

	private List<HashMap<String, Object>> getPhoneNumbers(JSONArray phoneNumbersURLs, JSONArray included) {

		List<HashMap<String, Object>> phones = new ArrayList<HashMap<String, Object>>();

		for (int iter = 0; iter < phoneNumbersURLs.length(); iter++) {
			String phoneNumberURL = phoneNumbersURLs.getString(iter);
			JSONObject phoneObj = this.getElementInIncluded(included, phoneNumberURL);
			HashMap<String, Object> phone = new HashMap<String, Object>();
			phone.put("type", phoneObj.getString("type"));
			phone.put("number", phoneObj.getString("number"));
			phones.add(phone);
		}
		return phones;
	}

	private JSONObject getPartitionJson(JSONArray jsonArray, String partitionTypeToken) {
		if (jsonArray == null || partitionTypeToken == null) {
			return null;
		}
		for (int iter = 0; iter < jsonArray.length(); iter++) {
			JSONObject jsonObj = jsonArray.getJSONObject(iter);
			JSONObject dataObj = null;
			String type = null;
			dataObj = (JSONObject) jsonObj.get("data");
			if (dataObj == null) {
				continue;
			}
			Object tempObj = dataObj.get("$type");
			type = tempObj == null ? null : tempObj.toString();
			if (partitionTypeToken.equals(type)) {
				return jsonObj;
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

	private void getProfile(Page page, JSONArray jsonList) {
		JSONObject partitionObj = this.getPartitionJson(jsonList, "com.linkedin.voyager.identity.profile.ProfileView");
		if (partitionObj == null) {
			return;
		}

		JSONArray included = (JSONArray) partitionObj.get("included");
		if (included == null) {
			return;
		}

		JSONObject profileElementObj = this.getElementInIncluded(included,
				"com.linkedin.voyager.identity.profile.Profile");

		// first name
		page.putField("firstName", profileElementObj.getString("firstName"));
		userProfile.setFirstName(profileElementObj.getString("firstName"));
		page.putField("maidenName", profileElementObj.getString("maidenName"));
		userProfile.setMaidenName(profileElementObj.getString("maidenName"));

		// last name
		page.putField("lastName", profileElementObj.getString("lastName"));
		userProfile.setLastName(profileElementObj.getString("lastName"));
		// industry name
		page.putField("industryName", profileElementObj.getString("industryName"));
		userProfile.setIndustryName(profileElementObj.getString("industryName"));
		// location name
		page.putField("locationName", profileElementObj.getString("locationName"));
		userProfile.setLocationName(profileElementObj.getString("locationName"));

		page.putField("address", profileElementObj.getString("address"));
		userProfile.setAddress(profileElementObj.getString("address"));

		page.putField("interests", profileElementObj.getString("interests"));
		userProfile.setInterests(profileElementObj.getString("interests"));
		// versionTag
		page.putField("versionTag", profileElementObj.getString("versionTag"));
		userProfile.setVersionTag(profileElementObj.getString("versionTag"));
		// summary
		page.putField("summary", profileElementObj.getString("summary"));
		userProfile.setSummary(profileElementObj.getString("summary"));

		// get the working experience
		this.getWorkingExperience(page, included);
		// get the education experience
		getEducationExperience(page, included);

	}

	private String getItemText(Selectable select, String code) {
		try {
			List<String> texts = select.codes(code).all();
			if (!Arrays.isNullOrEmpty(texts.toArray())) {
				return texts.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private String getItemLinks(Selectable select, String code) {
		try {
			List<String> texts = select.codes(code).links().all();
			if (!Arrays.isNullOrEmpty(texts.toArray())) {
				return texts.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private void getSkills(Page page) {
		try {
			Selectable selectionArea = page.getHtml().codes(
					"//section[contains(@class,'pv-profile-section artdeco-container-card pv-featured-skills-section')]");
			if (selectionArea == null) {
				return;
			}
			List<Selectable> selects = selectionArea.codes("//li").nodes();
			LinkedinPage lpage = (LinkedinPage) page;
			WebDriver webDriver = lpage.getWebDriver();

			for (Selectable select : selects) {
				String skillName = this.getItemText(select,
						"//span[contains(@class,'pv-skill-entity__skill-name')]/text()");
				String skillEndorseCount = this.getItemText(select,
						"//span[contains(@class,'pv-skill-entity__endorsement-count')]/text()");

				if (skillName == null) {
					continue;
				}
				String name = skillName.split("\\W")[0];
				try {
					WebElement skillElement = null;
					skillElement = webDriver.findElement(By.xpath(
							// "//span[contains(text(),'SQL') and
							// contains(@class,'pv-skill-entity__skill-name')]"));
							"//span[contains(text(),'" + name
									+ "') and contains(@class,'pv-skill-entity__skill-name')]"));
					skillElement.click();
					this.sleep(500);
					// print(webDriver);
					Page newPage = new Page();
					WebElement webElement = null;
					List<WebElement> webElements = webDriver.findElements(By.xpath("//artdeco-modal[@role='dialog']"));
					webElement = webElements.get(webElements.size() - 1);
					String content = webElement.getAttribute("outerHTML");
					newPage.setRawText(content);
					newPage.setUrl(new PlainText(webDriver.getCurrentUrl()));
					newPage.setRequest(new Request(webDriver.getCurrentUrl()));
					List<String> links = newPage.getHtml().codes("//li[contains(@class,'pv-endorsement-entity')]")
							.links().all();
					// webElement.findElement(By.xpath("//a[contains(@class,'pv-endorsement-entity__link')]")).isDisplayed();
					WebElement exitPopupElement = null;
					// exitPopupElements =
					// webElement.findElement(By.xpath("//button[contains(@class,'artdeco-dismiss')]"));
					// ((JavascriptExecutor)webDriver).executeScript("window.document.getElementByClassName('artdeco-dismiss').click()");
					// new WebDriverWait(webDriver,
					// 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'artdeco-dismiss')]")));
					List<WebElement> exitPopupElements = webElement
							.findElements(By.xpath("//button[contains(@class,'artdeco-dismiss')]"));
					exitPopupElement = exitPopupElements.get(exitPopupElements.size() - 1);
					((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", exitPopupElement);
					// exitPopupElements.getAttribute("outerHTML");exitPopupElements.getCssValue("visibility");
					// new
					// Actions(webDriver).moveToElement(exitPopupElements).click().perform();
					// exitPopupElements.click();
					// arguments[0].style.height='auto';
					// arguments[0].style.visibility='visible';takescreenShot(webDriver)
					// print(webDriver);((JavascriptExecutor)driver).executeScript("arguments[0].checked
					// = true;", inputElement);
					// takescreenShot(webDriver);
				} catch (Exception e) {
					logger.info(e.getMessage());
					return;
				}

			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return;
		}

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

	private void sleep(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void getPatent(Page page) {
		Selectable selectionArea = page.getHtml().codes(
				"//section[contains(@class,'pv-profile-section accordion-panel pv-accomplishments-block patents')]");
		if (selectionArea == null) {
			return;
		}
		List<Selectable> selects = selectionArea.codes("//li").nodes();
		for (Selectable select : selects) {
			String patentTitle = this.getItemText(select,
					"//h4[contains(@class,'pv-accomplishment-entity__title')]/text()");
			String patentDescription = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__description')]/text()");
			String honorIssues = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__issuer')]/text()");
		}
	}

	private void getLanguage(Page page) {
		Selectable selectionArea = page.getHtml().codes(
				"//section[contains(@class,'pv-profile-section accordion-panel pv-accomplishments-block languages languages')]");
		if (selectionArea == null) {
			return;
		}
		List<Selectable> selects = selectionArea.codes("//li").nodes();
		for (Selectable select : selects) {
			String languageTitle = this.getItemText(select,
					"//h4[contains(@class,'pv-accomplishment-entity__title')]/text()");
			String languageProficiency = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__proficiency')]/text()");
		}
	}

	private void getHonors(Page page) {
		Selectable selectionArea = page.getHtml().codes(
				"//section[contains(@class,'pv-profile-section accordion-panel pv-accomplishments-block honors')]");
		if (selectionArea == null) {
			return;
		}
		List<Selectable> selects = selectionArea.codes("//li").nodes();
		for (Selectable select : selects) {
			String honorTitle = this.getItemText(select,
					"//h4[contains(@class,'pv-accomplishment-entity__title')]/text()");
			String honorDescription = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__description')]/text()");
			String honorDate = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__date')]/text()");
			String honorIssues = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__issuer')]/text()");
		}
	}

	private void getPublications(Page page) {

		Selectable selectionArea = page.getHtml().codes(
				"//section[contains(@class,'pv-profile-section accordion-panel pv-accomplishments-block publications')]");
		if (selectionArea == null) {
			return;
		}
		List<Selectable> selects = selectionArea.codes("//li").nodes();

		for (Selectable select : selects) {
			String publicationTitle = this.getItemText(select,
					"//h4[contains(@class,'pv-accomplishment-entity__title')]/text()");
			String publicationDescription = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__description')]/text()");
			String publicationDate = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__date')]/text()");
			String publicationPublishers = this.getItemText(select,
					"//p[contains(@class,'pv-accomplishment-entity__publisher')]/text()");
			String externalURL = getItemLinks(select,
					"//a[contains(@class,'pv-accomplishment-entity__external-source')]");

			// WebElement contributorElement = null;
			// try {
			// contributorElement = webDriver
			// .findElement(By.xpath("//a[contains(@data-control-name,'publication_contributors')]"));
			// contributorElement.click();
			// Page newPage = new Page();
			// WebElement webElement = webDriver.findElement(By.xpath("/html"));
			// String content = webElement.getAttribute("outerHTML");
			// newPage.setRawText(content);
			// newPage.setUrl(new PlainText(webDriver.getCurrentUrl()));
			// newPage.setRequest(new Request(webDriver.getCurrentUrl()));
			// List<String> links =
			// newPage.getHtml().codes("//ul[contains(@class,'entity-list')]").links()
			// .all();
			// webDriver.navigate().back();
			// } catch (Exception e) {
			// logger.info(e.getMessage());
			// }
		}

	}

	private void getWorkingExperience(Page page, JSONArray included) {
		List<HashMap<String, Object>> workExperience = new ArrayList<HashMap<String, Object>>();

		int experienceNumber = 1;
		for (int iter = 0; iter < included.length(); iter++) {
			JSONObject includeObj = included.getJSONObject(iter);
			String proFileType = includeObj.getString("$type");
			if ("com.linkedin.voyager.identity.profile.Position".equals(proFileType)) {
				experienceNumber++;

				UserExperience experience = new UserExperience();
				experience.setUpdateTime(new DateTime());

				HashMap<String, Object> experiencePiece = new HashMap<String, Object>();
				experiencePiece.put("title", includeObj.getString("title"));
				experience.setTitle(includeObj.getString("title"));
				experiencePiece.put("companyName", includeObj.getString("companyName"));
				experience.setCompanyName(includeObj.getString("companyName"));
				experiencePiece.put("WlocationName", includeObj.getString("locationName"));
				experience.setLocationName(includeObj.getString("locationName"));
				experiencePiece.put("description", includeObj.getString("description"));
				experience.setResponsibility(includeObj.getString("description"));

				String dateString = includeObj.getString("timePeriod");
				String dateStringStarted = dateString + ",startDate";
				String dateStringEnd = dateString + ",endDate";

				DateString startDate = this.getDateString(dateStringStarted, included);
				DateString endDate = this.getDateString(dateStringEnd, included);
				experiencePiece.put("Wstart", startDate.toString());
				experience.setFromString(startDate.toString());
				experiencePiece.put("Wend", endDate.toString());
				experience.setToString(endDate.toString());
				experiencePiece.put("WstartYear", startDate.getYear());

				experience.setFromLong(DateTime.parse(startDate.toString()));
				experience.setToLong(DateTime.parse(endDate.toString()));
				workExperience.add(experiencePiece);

				userProfile.getExperiences().add(experience);

				if (includeObj.getString("region") != null) {
					// urn:li:fs_region:(cn,8909)
					Location location = new Location();
					String region = includeObj.getString("region");
					Pattern p = Pattern.compile("\\D+(\\d+)\\D+");
					Matcher m = p.matcher(region);
					if (m.find()) {
						String regionId = m.group(1);
						location.setLocationId(regionId);
					}
					location.setLocationName(includeObj.getString("locationName"));

					location = locationService.saveOrUpdate(location);
					experience.setLocation(location);
				}

				// company
				if (includeObj.getString("companyName") != null && includeObj.getString("companyUrn") != null) {
					Pair pcompany = new Pair();
					pcompany.setKey(includeObj.getString("companyName"));
					int splitIndex = includeObj.getString("companyUrn").lastIndexOf(":");
					String companyLinkedInID = includeObj.getString("companyUrn").substring(splitIndex + 1);
					pcompany.setValue(companyLinkedInID);
					SpiderConstants.companys.put(companyLinkedInID, includeObj.getString("companyName"));
					// && (experienceNumber == 1 ||
					// isValuableCompany(includeObj.getString("companyName"),
					// includeObj.getString("title")));
					String baseURL = this.companyFormatPrefix + companyLinkedInID + this.companyFormatSurfix;
					// if (SpiderConstants.searchURLs.get(baseURL) == null) {
					// SearchURL url = new SearchURL();
					// url.setBaseURL(baseURL);
					// url.setCurrentPageNumber(1);
					// url.setAllDownloaded(false);
					// SpiderConstants.searchURLs.put(baseURL, url);
					// if
					// (!SpiderConstants.downloadLinks.contains(url.getTargetURL()))
					// {
					// page.addTargetRequest(url.getTargetURL());
					// //
					// SpiderConstants.allProfileURLsThisExcution.put(url.getTargetURL(),false);
					// }
					// }
					Company company = new Company();
					company.setCompanyId(Long.valueOf(companyLinkedInID));
					company.setCompanyName(includeObj.getString("companyName"));
					company.setUpdateTime(new DateTime());

					company = companyService.saveOrUpdate(company);
					experience.setCompany(company);
				}
			}
		}
		Collections.sort(workExperience, new Comparator<HashMap<String, Object>>() {
			public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
				return map2.get("Wstart").toString().compareTo(map1.get("Wstart").toString());
			}

		});
		page.putField("workExperience", workExperience);
		if (!workExperience.isEmpty()) {
			try {
				Integer totalWorkExerience = Integer.valueOf(workExperience.get(0).get("WstartYear").toString())
						- Integer.valueOf(workExperience.get(workExperience.size() - 1).get("WstartYear").toString());
				page.putField("currentCompany", workExperience.get(0).get("companyName"));
				userProfile.setCurrentCompanyName((String) workExperience.get(0).get("companyName"));
				page.putField("currentTittle", workExperience.get(0).get("title"));
				userProfile.setCurrentTittleName((String) workExperience.get(0).get("title"));
				page.putField("totalWorkExperience", totalWorkExerience.toString());

				userProfile.setTotalExperienceInYear(Long.valueOf(totalWorkExerience.toString()));
			} catch (Exception e) {
				logger.equals(e.getMessage());
			}
		}
	}

	private void getEducationExperience(Page page, JSONArray included) {
		List<HashMap<String, Object>> educationExperiences = new ArrayList<HashMap<String, Object>>();
		for (int iter = 0; iter < included.length(); iter++) {
			JSONObject includeObj = included.getJSONObject(iter);
			String proFileType = includeObj.getString("$type");
			if ("com.linkedin.voyager.identity.profile.Education".equals(proFileType)) {

				UserEducation userEducation = new UserEducation();
				HashMap<String, Object> educationExperience = new HashMap<String, Object>();
				educationExperience.put("degreeName", includeObj.getString("degreeName"));
				userEducation.setDegree(Degree.valueOf(includeObj.getString("degreeName")));
				educationExperience.put("fieldOfStudy", includeObj.getString("fieldOfStudy"));
				userEducation.setMajor(includeObj.getString("fieldOfStudy"));

				educationExperience.put("schoolName", includeObj.getString("schoolName"));
				userEducation.setSchoolName(includeObj.getString("schoolName"));
				educationExperience.put("school", includeObj.getString("school"));
				// userEducation.setSchoolName(schoolName);

				String dateString = includeObj.getString("timePeriod");
				String dateStringStarted = dateString + ",startDate";
				String dateStringEnd = dateString + ",endDate";

				DateString startDate = this.getDateString(dateStringStarted, included);
				DateString endDate = this.getDateString(dateStringEnd, included);
				educationExperience.put("Estart", startDate.toString());
				userEducation.setFromString(startDate.toString());
				educationExperience.put("Eend", endDate.toString());
				userEducation.setToString(endDate.toString());

				userEducation.setFromLong(new DateTime(startDate.toString()));
				userEducation.setToLong(new DateTime(endDate.toString()));
				educationExperiences.add(educationExperience);

				if (includeObj.getString("schoolName") != null && includeObj.getString("school") != null) {
					Pair company = new Pair();
					company.setKey(includeObj.getString("schoolName"));
					int splitIndex = includeObj.getString("school").lastIndexOf(":");
					String companyLinkedInID = includeObj.getString("school").substring(splitIndex + 1);
					company.setValue(companyLinkedInID);
					SpiderConstants.schools.put(companyLinkedInID, includeObj.getString("schoolName"));
					School school = new School();
					school.setSchoolId(Long.valueOf(companyLinkedInID));
					school.setSchoolName(includeObj.getString("schoolName"));
					school = schoolService.saveOrUpdate(school);
					userEducation.setGraudateSchool(school);
				}
				userProfile.getEducations().add(userEducation);
			}
		}
		Collections.sort(educationExperiences, new Comparator<HashMap<String, Object>>() {
			public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
				return map2.get("Eend").toString().compareTo(map1.get("Eend").toString());
			}

		});
		page.putField("educationExperiences", educationExperiences);
		if (!educationExperiences.isEmpty()) {
			page.putField("highestDegree", educationExperiences.get(0).get("degreeName"));
			userProfile.setHighestDegreeName((String)educationExperiences.get(0).get("degreeName"));
		}
	}

	private DateString getDateString(String dateString, JSONArray included) {
		DateString date = new DateString();

		JSONObject includeObj = this.getElementInIncluded(included, dateString);
		if (includeObj == null) {
			return date;
		}
		String year = includeObj.getString("year");
		String month = includeObj.getString("month");
		String day = includeObj.getString("day");

		date.setYear(year);
		date.setMonth(month);
		date.setDay(day);
		return date;
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return null;
	}
}