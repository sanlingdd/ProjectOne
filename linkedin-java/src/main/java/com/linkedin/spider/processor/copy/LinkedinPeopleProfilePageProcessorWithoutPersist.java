package com.linkedin.spider.processor.copy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Arrays;
import org.linkedin.json.JSONArray;
import org.linkedin.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.linkedin.jpa.entity.Company;
import com.linkedin.jpa.entity.Education;
import com.linkedin.jpa.entity.Experience;
import com.linkedin.jpa.entity.Location;
import com.linkedin.jpa.entity.Phone;
import com.linkedin.jpa.entity.Profile;
import com.linkedin.jpa.entity.School;
import com.linkedin.spider.DateString;
import com.linkedin.spider.Pair;
import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.selector.Selectable;

public class LinkedinPeopleProfilePageProcessorWithoutPersist implements PageProcessor {

	private Logger logger = LoggerFactory.getLogger(getClass());
	public String companyFormatPrefix = "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22";
	public String companyFormatSurfix = "%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH";
	public Profile pf = null;

	LinkedinPeopleProfilePageProcessorWithoutPersist() {
	}

	public void process(Page page) {
		SpiderConstants.downloadLinks.add(page.getUrl().toString());
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
		// page.putField("html", page.getHtml());
		// page.getHtml().links();
		// https://www.linkedin.com/in/jessicajia/#

//		LinkedinPage lpage = (LinkedinPage) page;		
//		List<WebElement>  contributorElements  = lpage.getWebDriver().findElements(By.xpath(".//li-icon[@type='linkedin-bug']"));
//		if (!contributorElements.isEmpty()) {
//			try {
//				Thread.sleep(1000 * 60 * 60 * 24 * 24);
//				System.out.println();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}

		pf = new Profile();
		try {
			this.processProfile(page);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> result = page.getResultItems().getAll();
		String resume = String.format("姓名：%s \r\n", result.get("firstName") + " " + result.get("lastName"));

		List<HashMap<String, Object>> phones = (List<HashMap<String, Object>>) result.get("phones");
		if (phones != null) {
			for (HashMap<String, Object> phone : phones) {
				resume = resume + String.format("%s： \r\n", "电话：" + phone.get("number"));
			}
		}

		resume = resume + String.format("邮箱：%s \r\n", result.get("emailAddress"))
				+ String.format("生日：%s \r\n", result.get("birthday"))
				+ String.format("学历：%s \r\n", result.get("highestDegree"))
				+ String.format("领英：%s \r\n", result.get("publicIdentifier"))
				+ String.format("地址：%s \r\n", result.get("locationName")) + "\r\n" + "工作经历: \r\n";

		List<HashMap<String, Object>> experiences = (List<HashMap<String, Object>>) result.get("workExperience");
		if (experiences != null) {
			for (HashMap<String, Object> work : experiences) {
				String endDate = (String) work.get("Wend");
				if (endDate == null) {
					endDate = "今";
				}
				resume = resume + work.get("Wstart") + "-" + endDate + "      " + work.get("title") + "      "
						+ work.get("companyName") + "\r\n" + work.get("description") + "\r\n\r\n";
			}
		}

		resume = resume + "教育经历:\r\n";

		List<HashMap<String, Object>> educationExperiences = (List<HashMap<String, Object>>) result
				.get("educationExperiences");
		if (educationExperiences != null) {
			for (HashMap<String, Object> edu : educationExperiences) {
				resume = resume + edu.get("Estart") + "-" + edu.get("Eend") + "      " + edu.get("schoolName")
						+ "      " + edu.get("fieldOfStudy") + "      " + edu.get("degreeName") + "\r\n";
			}
		}

		if (pf.getPublicIdentifier() == null) {
			String[] pubs = page.getUrl().toString().trim().split("/");
			String publicIdentifier = pubs[pubs.length - 1];
			String urlDecodedPI = null;
			try {
				urlDecodedPI = java.net.URLDecoder.decode(publicIdentifier, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SpiderConstants.allPublicIdentifiers.remove(urlDecodedPI);
			SpiderConstants.jedis_master.set("allPublicIdentifiers",JSON.toJSONString(SpiderConstants.allPublicIdentifiers));			
			return;
		}
		SpiderConstants.profiles.put(pf.getPublicIdentifier(), pf);
		SpiderConstants.jedis_master.set(pf.getPublicIdentifier(), JSON.toJSONString(pf));
		// String longStr = JSON.toJSONString(SpiderConstants.profiles);

		try {
			File file = new File("C:\\CVS\\" + result.get("purePublicIdentifier") + "CV.txt");
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			if (!file.exists()) {
				file.createNewFile();

				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(resume.getBytes(), 0, resume.getBytes().length);
				bos.flush();
				bos.close();
			}

			String longStr = JSON.toJSONString(SpiderConstants.profiles);
//			String longStr = JSON.toJSONString(SpiderConstants.profiles,
//					SerializerFeature.DisableCircularReferenceDetect);
			File profilesFile = new File("C:\\Profiles\\profiles.txt");

			fileParent = profilesFile.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}

			if (profilesFile.exists()) {
				profilesFile.delete();
			}

			profilesFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(profilesFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(longStr.getBytes(), 0, longStr.getBytes().length);
			bos.flush();
			bos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// String longStr = JSONObject.valueToString(SpiderConstants.profiles);
		// System.out.println(longStr);

		SpiderConstants.jedis_master.set(page.getUrl().toString(), "true");
	}

	private JSONArray getJsonArray(Page page) {
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
		return resultArray;
	}

	private void processProfile(Page page) {
		page.putField("publicIdentifier", page.getUrl().toString());
		JSONArray resultArray = this.getJsonArray(page);
		;
		JSONArray includeObj = this.getCorrentIncludeListJson(resultArray);
		if (includeObj == null) {
			return;
		}

		String publicIdentifier = page.getUrl().toString();
		// get the profile
		this.getProfile(page, includeObj);

		// get honors
		getHonors(page);

		// get Publication
		getPublications(page);

		// get Language
		getLanguage(page);

		// get patent
		getPatent(page);
		// get skills
		if (page instanceof LinkedinPage) {
			LinkedinPage lpage = (LinkedinPage) page;
			if (lpage.getWebDriver() != null) { // get skills
				getSkills(page);
				// lpage.getWebDriverPool().returnToPool(lpage.getWebDriver());
			}
		}
		// get contact info
		if (page instanceof LinkedinPage) {
			LinkedinPage lpage = (LinkedinPage) page;
			if (lpage.getWebDriver() != null) { // get skills
				WebDriver webDriver = lpage.getWebDriver();
//				
//				PageOperation obj = new PageOperation();
//				WebElement skillElement = null;
//				skillElement = webDriver.findElement(By.xpath("//a[@data-control-name='contact_see_more']/span"));
//				obj.scrollThePage(webDriver, skillElement);
//				skillElement.click();
				if (!publicIdentifier.endsWith("/")) {
					publicIdentifier = publicIdentifier + "/";
				}
				webDriver.get(publicIdentifier + "detail/contact-info/");
				this.sleep(3000);
				// print(webDriver);
				LinkedinPage newPage = new LinkedinPage();// newPage.getHtml().codes("//code/tidyText()").all()
				WebElement webElement = webDriver.findElement(By.xpath("/html"));
				String content = webElement.getAttribute("outerHTML");
				newPage.setRawText(content);
				newPage.setUrl(new PlainText(webDriver.getCurrentUrl()));
				newPage.setRequest(new Request(webDriver.getCurrentUrl()));
				newPage.setWebDriver(lpage.getWebDriver());
				// lpage.getWebDriverPool().returnToPool(lpage.getWebDriver());

				JSONArray newResultArray = this.getJsonArray(newPage);
				this.getContactInfo(lpage, newResultArray);
				// takescreenShot(lpage.getWebDriver());
			}
		}
		
//		//download profile
//		LinkedinPage lpage = (LinkedinPage) page;
//		WebElement webElement = lpage.getWebDriver().findElement(By.xpath(".//span[text()='发更多…']/.."));
//		if(webElement!=null) {
//			webElement.click();
//			
//			WebElement webElement1 = lpage.getWebDriver().findElement(By.xpath(".//span[text()='发更多…']/.."));
//		}
		
	}

	private void getContactInfo(LinkedinPage page, JSONArray includeObj) {
		JSONObject partitionObj = null;
		String elementTypeORIDToken = "com.linkedin.voyager.identity.profile.ProfileContactInfo";
		for (int iter = 0; iter < includeObj.length(); iter++) {
			JSONObject obj = includeObj.getJSONObject(iter);
			JSONObject dataObject = obj.getJSONObject("data");
			if (dataObject != null) {
				String typeString = dataObject.getString("$type");
				String idString = dataObject.getString("$id");
				if (elementTypeORIDToken.equals(typeString) || elementTypeORIDToken.equals(idString)) {
					partitionObj = dataObject;
				}
			}
		}

		if (partitionObj == null) {
			return;
		}
		JSONObject dataObj = partitionObj;

		// emailAddress
		page.putField("emailAddress", dataObj.getString("emailAddress"));
		
		pf.setAddress(dataObj.getString("emailAddress"));
		// address
		page.putField("address", dataObj.getString("address"));
		pf.setAddress(dataObj.getString("address"));
		// phone
		JSONArray phoneNumbersURLs = dataObj.getJSONArray("phoneNumbers");
		if (phoneNumbersURLs != null) {
			List<HashMap<String, Object>> phones = this.getPhoneNumbers(phoneNumbersURLs, includeObj);
			page.putField("phones", phones);
			
			for(HashMap<String, Object> phone : phones)
			{
				Phone pphone = new Phone();
				pphone.setProfile(pf);
				pphone.setType(phone.get("type").toString());
				pphone.setNumber(phone.get("number").toString());

				pf.getPhones().add(pphone);
			}
			
		}

		// birthday//
		JSONObject birthDateOn = (JSONObject) dataObj.getJSONObject("birthDateOn");
		if (birthDateOn != null) {

			DateString birthDay = this.getDateString(birthDateOn);
			page.putField("birthday", birthDay.toString());
			pf.setBirthday( birthDay.toString());
		}
		// website
		JSONArray websitesURLs = dataObj.getJSONArray("websites");
		if (websitesURLs != null) {
			List<HashMap<String, Object>> websitesAddresses = this.getWebSites(websitesURLs, includeObj);
			page.putField("websites", websitesAddresses);
		}

		// wechat
		JSONObject weChatContactInfo = dataObj.getJSONObject("weChatContactInfo");
		if (weChatContactInfo != null) {
			page.putField("wechatImageURL", weChatContactInfo.getString("qrCodeImageUrl"));

			page.getWebDriver().get(weChatContactInfo.getString("qrCodeImageUrl"));
			this.sleep(3000);
			TakesScreenshot ts = (TakesScreenshot) page.getWebDriver();
			File source = ts.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(source, new File(
						"C:\\QRCode\\" + page.getResultItems().getAll().get("purePublicIdentifier") + "qr.jpg"));
			} catch (Exception e) {
				System.out.println("Exception is handled");
				e.getMessage();
			}
		}

	}

	private List<HashMap<String, Object>> getWebSites(JSONArray websites, JSONArray included) {
		List<HashMap<String, Object>> phones = new ArrayList<HashMap<String, Object>>();
		for (int iter = 0; iter < websites.length(); iter++) {
			JSONObject website = websites.getJSONObject(iter);
			HashMap<String, Object> site = new HashMap<String, Object>();
			site.put("websitesurl", website.getString("url"));
			phones.add(site);
		}
		return phones;
	}

	private List<HashMap<String, Object>> getPhoneNumbers(JSONArray phoneNumbersURLs, JSONArray included) {

		List<HashMap<String, Object>> phones = new ArrayList<HashMap<String, Object>>();

		for (int iter = 0; iter < phoneNumbersURLs.length(); iter++) {
			JSONObject phoneObj = phoneNumbersURLs.getJSONObject(iter);
			// JSONObject phoneObj = this.getElementInIncluded(included, phoneNumberURL);

			HashMap<String, Object> phone = new HashMap<String, Object>();
			phone.put("type", phoneObj.getString("type"));
			phone.put("number", phoneObj.getString("number"));
			phones.add(phone);
		}
		return phones;
	}

	private JSONArray getCorrentIncludeListJson(JSONArray jsonArray) {
		if (jsonArray == null) {
			return null;
		}
		for (int iter = 0; iter < jsonArray.length(); iter++) {
			JSONObject jsonObj = jsonArray.getJSONObject(iter);
			JSONArray included = null;
			String type = null;
			included = (JSONArray) jsonObj.get("included");
			if (included == null) {
				continue;
			}

			for (int iterj = 0; iterj < included.length(); iterj++) {
				JSONObject includeObj = included.getJSONObject(iterj);
				String typeString = includeObj.getString("$type");
				String idString = includeObj.getString("publicIdentifier");
				String trackingString = includeObj.getString("trackingId");

				if ("com.linkedin.voyager.dash.identity.profile.Profile".equals(typeString) && idString != null
						&& trackingString != null) {
					return included;
				}
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

			if ("com.linkedin.voyager.dash.identity.profile.Profile".equals(elementTypeORIDToken)) {
				String trackingString = includeObj.getString("trackingId");
				if (trackingString == null) {
					continue;
				}
			}

			if (elementTypeORIDToken.equals(typeString) || elementTypeORIDToken.equals(idString)) {
				return includeObj;
			}
		}
		return null;
	}

	private void getProfile(Page page, JSONArray includeObj) {
		JSONObject profileElementObj = this.getElementInIncluded(includeObj,
				"com.linkedin.voyager.dash.identity.profile.Profile");

		pf.setFirstName(profileElementObj.getString("firstName"));
		pf.setMaidenName(profileElementObj.getString("maidenName"));
		pf.setLastName(profileElementObj.getString("lastName"));
		pf.setIndustryUrn(profileElementObj.getString("industryUrn"));

		Location location = new Location();
		location.setLocationName(profileElementObj.getString("locationName"));
		pf.setLocation(location);
		pf.setAddress(profileElementObj.getString("address"));
		pf.setSummary(profileElementObj.getString("summary"));
		pf.setHeadline(profileElementObj.getString("headline"));
		pf.setPublicIdentifier(profileElementObj.getString("publicIdentifier"));

		// first name
		page.putField("firstName", profileElementObj.getString("firstName"));
		page.putField("maidenName", profileElementObj.getString("maidenName"));
		// last name
		page.putField("lastName", profileElementObj.getString("lastName"));
		// industry name
		page.putField("industryUrn", profileElementObj.getString("industryUrn"));
		// location name
		page.putField("locationName", profileElementObj.getString("locationName"));
		page.putField("address", profileElementObj.getString("address"));
		// versionTag
		page.putField("versionTag", profileElementObj.getString("versionTag"));
		// summary
		page.putField("summary", profileElementObj.getString("summary"));

		// publicIdentifier
		page.putField("purePublicIdentifier", profileElementObj.getString("publicIdentifier"));
		// headline
		page.putField("headline", profileElementObj.getString("headline"));
		// get the working experience
		this.getWorkingExperience(page, includeObj);
		// get the education experience
		getEducationExperience(page, includeObj);

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
			if ("com.linkedin.voyager.dash.identity.profile.Position".equals(proFileType)) {
				experienceNumber++;
				HashMap<String, Object> experiencePiece = new HashMap<String, Object>();
				experiencePiece.put("title", includeObj.getString("title"));
				experiencePiece.put("companyName", includeObj.getString("companyName"));
				experiencePiece.put("WlocationName", includeObj.getString("locationName"));
				experiencePiece.put("description", includeObj.getString("description"));
				experiencePiece.put("companyUrn", includeObj.getString("companyUrn"));

				Company comp = new Company();
				comp.setCompanyName(includeObj.getString("companyName"));
				String companyUrn = includeObj.getString("companyUrn");
				if (companyUrn != null) {
					String[] slices = companyUrn.split(":");
					if (slices.length != 0) {
						comp.setId(Long.valueOf(slices[slices.length - 1]));
					}
				}

				Experience exp = new Experience();
				exp.setCompany(comp);
				exp.setCompanyName(comp.getCompanyName());
				exp.setTitle(includeObj.getString("title"));
				exp.setCompanyName(includeObj.getString("companyName"));
				exp.setLocationName(includeObj.getString("locationName"));
				exp.setResponsibility(includeObj.getString("description"));

				JSONObject dataRangeObject = includeObj.getJSONObject("dateRange");
				JSONObject dateStartedObject = (JSONObject) dataRangeObject.getJSONObject("start");
				JSONObject dateEndObject = (JSONObject) dataRangeObject.getJSONObject("end");

				DateString startDate = null;
				if (dateStartedObject != null) {
					startDate = this.getDateString(dateStartedObject);
					experiencePiece.put("Wstart", startDate.toString());
					experiencePiece.put("WstartYear", startDate.getYear());

					exp.setFromString(startDate.toString());
				}

				DateString endDate = null;
				if (dateEndObject != null) {
					endDate = this.getDateString(dateEndObject);
					experiencePiece.put("Wend", endDate.toString());
					exp.setToString(endDate.toString());
				}
				workExperience.add(experiencePiece);

				pf.getExperiences().add(exp);

				// company
//				if (includeObj.getString("companyName") != null && includeObj.getString("companyUrn") != null) {
//					Pair company = new Pair();
//					company.setKey(includeObj.getString("companyName"));
//					int splitIndex = includeObj.getString("companyUrn").lastIndexOf(":");
//					String companyLinkedInID = includeObj.getString("companyUrn").substring(splitIndex + 1);
//					company.setValue(companyLinkedInID);
//					SpiderConstants.companys.put(companyLinkedInID, includeObj.getString("companyName"));
//					// && (experienceNumber == 1 ||
//					// isValuableCompany(includeObj.getString("companyName"),
//					// includeObj.getString("title")));
//					String baseURL = this.companyFormatPrefix + companyLinkedInID + this.companyFormatSurfix;
//					// if (SpiderConstants.searchURLs.get(baseURL) == null) {
//					// SearchURL url = new SearchURL();
//					// url.setBaseURL(baseURL);
//					// url.setCurrentPageNumber(1);
//					// url.setAllDownloaded(false);
//					// SpiderConstants.searchURLs.put(baseURL, url);
//					// if
//					// (!SpiderConstants.downloadLinks.contains(url.getTargetURL()))
//					// {
//					// page.addTargetRequest(url.getTargetURL());
//					// //
//					// SpiderConstants.allProfileURLsThisExcution.put(url.getTargetURL(),false);
//					// }
//					// }
//				}
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

				pf.setCurrentCompanyName((String) workExperience.get(0).get("companyName"));
				pf.setCurrentTittleName((String) workExperience.get(0).get("title"));
				pf.setTotalExperienceInYear(Long.valueOf(totalWorkExerience));

				page.putField("currentCompany", workExperience.get(0).get("companyName"));
				page.putField("currentTittle", workExperience.get(0).get("title"));
				page.putField("totalWorkExperience", totalWorkExerience.toString());
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
			if ("com.linkedin.voyager.dash.identity.profile.Education".equals(proFileType)) {

				Education edu = new Education();

				HashMap<String, Object> educationExperience = new HashMap<String, Object>();
				educationExperience.put("degreeName", includeObj.getString("degreeName"));
				educationExperience.put("fieldOfStudy", includeObj.getString("fieldOfStudy"));
				educationExperience.put("schoolName", includeObj.getString("schoolName"));
				educationExperience.put("school", includeObj.getString("school"));

				edu.setSchoolName(includeObj.getString("schoolName"));

				School sc = new School();
				if (includeObj.getString("school") != null) {
					int splitIndexC = includeObj.getString("school").lastIndexOf(":");
					String schoolLinkedInIDC = includeObj.getString("school").substring(splitIndexC + 1);

					sc.setSchoolId(Long.valueOf(schoolLinkedInIDC));
					sc.setSchoolName(includeObj.getString("schoolName"));
				}
				edu.setGraudateSchool(sc);

				JSONObject dataRangeObject = includeObj.getJSONObject("dateRange");
				if (dataRangeObject != null) {
					JSONObject dateStartedObject = dataRangeObject.getJSONObject("start");
					JSONObject dateEndObject = dataRangeObject.getJSONObject("end");

					DateString startDate = this.getDateString(dateStartedObject);
					DateString endDate = this.getDateString(dateEndObject);

					educationExperience.put("Estart", startDate.toString());
					educationExperience.put("Eend", endDate.toString());
					edu.setFromString(startDate.toString());
					edu.setToString(endDate.toString());
				}
				educationExperiences.add(educationExperience);

				if (includeObj.getString("schoolName") != null && includeObj.getString("school") != null) {
					Pair company = new Pair();
					company.setKey(includeObj.getString("schoolName"));
					int splitIndex = includeObj.getString("school").lastIndexOf(":");
					String schoolLinkedInID = includeObj.getString("school").substring(splitIndex + 1);
					company.setValue(schoolLinkedInID);
					SpiderConstants.schools.put(schoolLinkedInID, includeObj.getString("schoolName"));
				}

				pf.getEducations().add(edu);
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

			pf.setHighestDegreeName((String) educationExperiences.get(0).get("degreeName"));
		}
	}

	private DateString getDateString(JSONObject includeObj) {
		DateString date = new DateString();

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