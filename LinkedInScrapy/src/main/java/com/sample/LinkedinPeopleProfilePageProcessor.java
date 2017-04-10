package com.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class LinkedinPeopleProfilePageProcessor implements PageProcessor {

	public String companyFormatPrefix = "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22";
	public String companyFormatSurfix = "%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH";
	public static Set<String> downloadLinks = new HashSet<String>();
	public static Set<String> companies = new HashSet<String>();
	public static Set<String> schools = new HashSet<String>();
	private Site site = Site.me().setRetryTimes(3).setSleepTime(15000).setTimeOut(10000);
	private Logger logger = LoggerFactory.getLogger(getClass());

	LinkedinPeopleProfilePageProcessor() {
		site.setCharset("UTF-8");
		site.addHeader("accept-encoding", "gzip, deflate, sdch, br");
		site.addHeader("accept-language", "zh-CN,zh;q=0.8");
		site.addHeader("upgrade-insecure-requests", "1");
		site.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		site.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		site.addHeader("cache-control", "max-age=0");
		site.addHeader("avail-dictionary", "YFA7RCTS");
		site.addHeader("authority", "www.linkedin.com");
		site.addHeader("cookie",
				"bcookie=\"v=2^&9fbee1ca-79a5-47cc-8810-eb18e753ff44\"; bscookie=\"v=1^&201610010831302ee66e54-22ff-4608-84ac-0f2f7511eea0AQHT-9IP6dXurZuyOFX72-6doqjLS_kN\"; visit=\"v=1^&M\"; PLAY_SESSION=1d7015caf51403846a8d7547384b8e8e62726d1a-chsInfo=bcd05c05-2007-4d33-b640-58399974402f+vsrp_people_facets_proposal_accepts; SID=4b5771bc-b4f9-4836-a8f4-74bcc65d9517; VID=V_2016_12_12_01_217755; share_setting=PUBLIC; hc_survey=true; _cb_ls=1; _chartbeat2=B4SLZSCbyTk6B5AWo_.1479287732844.1491303514668.0000000000000001; lang=\"v=2^&lang=en-us\"; lidc=\"b=SB94:g=24:u=91:i=1491348304:t=1491434704:s=AQE_L6H5smI32WXTsrsgTLZAn4f4X365\"; li_at=AQEDARxstRYDDHP_AAABWzgIuVUAAAFbPSjs9lEAg1P1Fh85UggkZEc4G1AeoG6UXqposYGHMyV7TnLkO-u82RXR8REVUEOt_52bx-KHOVw8zWYPPKLQkVxu-SpJqYwT2Xd4lvApQTegZrUD2p_eYUTO; liap=true; JSESSIONID=\"ajax:4840042334868528098\"; sdsc=1^%^3A1SZM1shxDNbLt36wZwCgPgvN58iw^%^3D; _ga=GA1.2.1251577009.1475482809; RT=s=1491370989903^&r=https^%^3A^%^2F^%^2Fwww.linkedin.com^%^2Fpulse^%^2Fadvanced-search-gone-new-linkedin-interface-robert-knop; _chartbeat4=t=DKB9ZOB9LXS7BHWMJvCtaMFCMZc_z^&E=132^&x=0^&c=1124.59^&y=9066^&w=1070; _lipt=CwEAAAFbPKNh6Z3qBeMf7_JBddW2Qjg3yN-e9UHjtNlsMNhVlyYg5BAGj7ZmI5IeRgTL5060xT8GeuiKMdFXwlBYNHYiG6eAKnm6iyXF6RMUc14Djv9QnSo86RbnJclsv9UdB7Kl68LJObbYpkG-CfacU8vyumXjTUMHmMZ4pNoNjyhP9R3xQW-yXQ-6DdyZSHbLA2pTIoYYMVhCJOlD5w0ivtoYVw8v7tlvbhTkkjLILUgEPnBYNKKWxmImInDCb_PY5Feq-qSSEKPHE_gSgZuuzwWuQlHsWjGFxGSp0JzMd1RDSesQ0-UeL9JK-WviLU-hbJOD4ldXUXCejAB_FUNLJCgt3bQTGYhe61UEIQMFr-FvXj-XH1k1VZPkEWH7I0TIuUoI04F3O0-trWODXsIjHTKB40AEiSV2odTj7ED3baO6wf-qy7-EkPh7arm7V8jT0xzvY7bNuI0WcvOChKq3Ek57XOQRVrhUGk3yt5DIbMl9T4Pbx6kIk4-NjYVSxQJ3658OPSVyGTAZgy6u_RYHUHAPP5u5yBoWKAerSBOqSGKTnLay-Jl2QJKtqCTtLHx6OPHJ0k0X1eQJucJyVDyZ3z12o6cfwqK7A10UF6uPgyYj_-6dQQUn8XrlTG8");

		site.addHeader("referer", "https://www.linkedin.com/");
		site.addHeader("origin", "https://www.linkedin.com");
		site.setHttpProxy(new HttpHost("proxy.pal.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sin.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sha.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.fra.sap.corp", 8080));

	}

	public void process(Page page) {
		downloadLinks.add(page.getUrl().toString());
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
		// page.putField("html", page.getHtml());
		// page.getHtml().links();
		// https://www.linkedin.com/in/jessicajia/#

		if (page.getUrl().toString().matches("https://www.linkedin.com/in/[^/]+/")
				|| page.getUrl().toString().matches("https://www.linkedin.com/in/[^/]+")) {
			this.processProfile(page);
		} else {
			this.processSearch(page);
		}
	}

	private void processSearch(Page page) {
		// page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
		// page.putField("html", page.getHtml());
		// page.getHtml().links();
		// https://www.linkedin.com/in/jessicajia/#
		
		if (SpiderConstants.targetRequests == null) {
			SpiderConstants.targetRequests = page.getTargetRequests();
		}
		String baseURL = page.getUrl().toString().substring(0, page.getUrl().toString().lastIndexOf("&"));
		int last = StringUtils.lastIndexOf(page.getUrl().toString(), "=");
		int currentPageNumber = Integer.valueOf(page.getUrl().toString().substring(last + 1));
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
				//
				String firstName = includeObj.getString("firstName");
				String lastName = includeObj.getString("lastName");
				if (this.isTargetProfile(valuableNames, firstName, lastName)) {
					String publicIdentifier = includeObj.getString("publicIdentifier");
					String format = "https://www.linkedin.com/in/%s/";
					String newURL = String.format(format, publicIdentifier);
					if (!downloadLinks.contains(newURL)) {
						page.addTargetRequest(newURL);
						SpiderConstants.allProfileURLsThisExcution.add(newURL);
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
			if (!downloadLinks.contains(url.getTargetURL())) {
				page.addTargetRequest(url.getTargetURL());
				SpiderConstants.allProfileURLsThisExcution.add(url.getTargetURL());
			}
		} else if (!SpiderConstants.stop) {
			url.setAllDownloaded(true);
		} else if (total <= 10) {
			url.setAllDownloaded(true);
		} else if (currentPageNumber != 100) {
			url.setCurrentPageNumber(currentPageNumber + 1);
		}
		logger.info("dowloaded profile number:" + SpiderConstants.profilesAccessedVector.size()
				+ " to be downloaded number: " + downloadLinks.size());

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
			JSONObject jsonObj = resultArray.getJSONObject(iter);
			JSONObject dataObj = (JSONObject) jsonObj.get("data");
			if (dataObj != null) {
				JSONObject metadataObj = (JSONObject) dataObj.get("metadata");
				if (metadataObj != null) {
					String id = metadataObj.getString("origin");
					if ("FACETED_SEARCH".equals(id)) {
						return (JSONArray) jsonObj.get("included");
					}
				}
			}
		}
		return null;
	}

	private void processProfile(Page page) {
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

		page.putField("maidenName", profileElementObj.getString("maidenName"));

		// last name
		page.putField("lastName", profileElementObj.getString("lastName"));
		// industry name
		page.putField("industryName", profileElementObj.getString("industryName"));
		// location name
		page.putField("locationName", profileElementObj.getString("locationName"));

		page.putField("address", profileElementObj.getString("address"));

		page.putField("interests", profileElementObj.getString("interests"));
		// versionTag
		page.putField("versionTag", profileElementObj.getString("versionTag"));
		// summary
		page.putField("summary", profileElementObj.getString("summary"));

		// get the working experience
		this.getWorkingExperience(page, included);
		// get the education experience
		getEducationExperience(page, included);

	}

	private boolean isValuableCompany(String companyName, String titleName) {
		String[] keywordsCompany = { "Resource", "猎头", "Recruitment", "HR", "International", "Consult", "顾问", "人力",
				"Resources", "man", "career", "咨询" };
		String[] titlesKeywords = { "猎头", "Recruitment", "HR", "Consult", "顾问", "人力", "Resources", "man", "career",
				"咨询" };
		for (String keyword : keywordsCompany) {
			if (companyName.toLowerCase().contains(keyword.toLowerCase())) {
				return true;
			}
		}
		for (String keyword : titlesKeywords) {
			if (titleName.toLowerCase().contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	private void getWorkingExperience(Page page, JSONArray included) {
		List<HashMap<String, Object>> workExperience = new ArrayList<HashMap<String, Object>>();

		int experienceNumber = 1;
		for (int iter = 0; iter < included.length(); iter++) {
			JSONObject includeObj = included.getJSONObject(iter);
			String proFileType = includeObj.getString("$type");
			if ("com.linkedin.voyager.identity.profile.Position".equals(proFileType)) {
				experienceNumber++;
				HashMap<String, Object> experiencePiece = new HashMap<String, Object>();
				experiencePiece.put("title", includeObj.getString("title"));
				experiencePiece.put("companyName", includeObj.getString("companyName"));
				experiencePiece.put("WlocationName", includeObj.getString("locationName"));
				experiencePiece.put("description", includeObj.getString("description"));

				String dateString = includeObj.getString("timePeriod");
				String dateStringStarted = dateString + ",startDate";
				String dateStringEnd = dateString + ",endDate";

				DateString startDate = this.getDateString(dateStringStarted, included);
				DateString endDate = this.getDateString(dateStringEnd, included);
				experiencePiece.put("Wstart", startDate.toString());
				experiencePiece.put("Wend", endDate.toString());
				experiencePiece.put("WstartYear", startDate.getYear());
				workExperience.add(experiencePiece);

				// company
				if (includeObj.getString("companyName") != null && includeObj.getString("companyUrn") != null) {
					Pair company = new Pair();
					company.setKey(includeObj.getString("companyName"));
					int splitIndex = includeObj.getString("companyUrn").lastIndexOf(":");
					String companyLinkedInID = includeObj.getString("companyUrn").substring(splitIndex + 1);
					company.setValue(companyLinkedInID);
					SpiderConstants.companys.put(companyLinkedInID, includeObj.getString("companyName"));
					 //&& (experienceNumber == 1	|| isValuableCompany(includeObj.getString("companyName"), includeObj.getString("title")));
					if (!SpiderConstants.stop) {
						companies.add(companyLinkedInID);
						String baseURL = this.companyFormatPrefix + companyLinkedInID + this.companyFormatSurfix;
						if (SpiderConstants.searchURLs.get(baseURL) == null) {
							SearchURL url = new SearchURL();
							url.setBaseURL(baseURL);
							url.setCurrentPageNumber(1);
							url.setAllDownloaded(false);
							SpiderConstants.searchURLs.put(baseURL, url);
							if (!downloadLinks.contains(url.getTargetURL())) {
								page.addTargetRequest(url.getTargetURL());
								SpiderConstants.allProfileURLsThisExcution.add(url.getTargetURL());
							}
						}
					}
				}
			}
		}
		Collections.sort(workExperience, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (!(o1 instanceof HashMap<?, ?>)) {
					return 0;
				}
				if (!(o2 instanceof HashMap<?, ?>)) {
					return 0;
				}
				HashMap<String, String> map1 = (HashMap<String, String>) o1;
				HashMap<String, String> map2 = (HashMap<String, String>) o2;
				return map2.get("Wstart").compareTo(map1.get("Wstart"));
			}

		});
		page.putField("workExperience", workExperience);
		if (!workExperience.isEmpty()) {
			try {
				Integer totalWorkExerience = Integer.valueOf(workExperience.get(0).get("WstartYear").toString())
						- Integer.valueOf(workExperience.get(workExperience.size() - 1).get("WstartYear").toString());
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
			if ("com.linkedin.voyager.identity.profile.Education".equals(proFileType)) {

				HashMap<String, Object> educationExperience = new HashMap<String, Object>();
				educationExperience.put("degreeName", includeObj.getString("degreeName"));
				educationExperience.put("fieldOfStudy", includeObj.getString("fieldOfStudy"));
				educationExperience.put("schoolName", includeObj.getString("schoolName"));
				educationExperience.put("school", includeObj.getString("school"));

				String dateString = includeObj.getString("timePeriod");
				String dateStringStarted = dateString + ",startDate";
				String dateStringEnd = dateString + ",endDate";

				DateString startDate = this.getDateString(dateStringStarted, included);
				DateString endDate = this.getDateString(dateStringEnd, included);
				educationExperience.put("Estart", startDate.toString());
				educationExperience.put("Eend", endDate.toString());
				educationExperiences.add(educationExperience);

				if (includeObj.getString("schoolName") != null && includeObj.getString("school") != null) {
					Pair company = new Pair();
					company.setKey(includeObj.getString("schoolName"));
					int splitIndex = includeObj.getString("school").lastIndexOf(":");
					String companyLinkedInID = includeObj.getString("school").substring(splitIndex + 1);
					company.setValue(companyLinkedInID);
					SpiderConstants.schools.put(companyLinkedInID, includeObj.getString("schoolName"));
				}
			}
		}
		Collections.sort(educationExperiences, new Comparator() {
			public int compare(Object o1, Object o2) {
				if (!(o1 instanceof HashMap<?, ?>)) {
					return 0;
				}
				if (!(o2 instanceof HashMap<?, ?>)) {
					return 0;
				}
				HashMap<String, String> map1 = (HashMap<String, String>) o1;
				HashMap<String, String> map2 = (HashMap<String, String>) o2;
				return map2.get("Eend").compareTo(map1.get("Eend"));
			}

		});
		page.putField("educationExperiences", educationExperiences);
		if (!educationExperiences.isEmpty()) {
			page.putField("highestDegree", educationExperiences.get(0).get("degreeName"));
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

	public Site getSite() {
		return site;
	}
}