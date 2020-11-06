package com.linkedin.spider.processor.copy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.automation.CommonSetting;
import com.linkedin.automation.HuntingCompany;
import com.linkedin.jpa.entity.Phone;
import com.linkedin.jpa.entity.Profile;
import com.linkedin.jpa.service.CompanyService;
import com.linkedin.spider.POIHelper;
import com.linkedin.spider.SearchURL;
import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Spider;

@Service
public class LinkedinSpiderHttpMain {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CompanyService companyService;

	@Autowired
	private DispatcherPageProcessor dispather = new DispatcherPageProcessor();

	public void startLinkedProfileSpider() throws IOException {
		Workbook workbook = null;
		FileInputStream inputStream = null;
		String tempFile = "C:/Data/webmagic/LinkedProfilesFresh.xlsx";
		try {
			inputStream = new FileInputStream(tempFile);
			workbook = new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Profiles
		Sheet profileSheet = POIHelper.getSheet(workbook, "profiles");
		for (int rowNum = 0; rowNum <= profileSheet.getLastRowNum(); rowNum++) {
			Row row = POIHelper.getRow(profileSheet, rowNum);
			Cell cell = POIHelper.getCell(row, 0);
			String profileURL = cell.getStringCellValue();
			if (profileURL != null && profileURL.matches("https://www.linkedin.com/in/.+")) {
				if (!SpiderConstants.downloadLinks.contains(profileURL)) {
					SpiderConstants.downloadLinks.add(profileURL);
				} else {
					int nextRowNum = POIHelper.getNextRowNum(profileSheet, row);
					for (int iter = row.getRowNum(); iter < nextRowNum; iter++) {
						profileSheet.removeRow(POIHelper.getRow(profileSheet, iter));
					}
				}
			}
		}

		// Sheet profileSheet1 = POIHelper.getSheet(workbook, "profiles");
		// Sheet profileSheetCopy = POIHelper.getSheet(workbook,
		// "profilesCopy");
		//
		// POIHelper.copySheet(profileSheet1, profileSheetCopy);
		// try {
		// FileOutputStream outputStream = new FileOutputStream(tempFile);
		// workbook.write(outputStream);
		// workbook.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// URLs
		Sheet urlSheet = POIHelper.getSheet(workbook, "urls");
		for (int rowNum = 0; rowNum <= urlSheet.getLastRowNum(); rowNum++) {
			Row row = POIHelper.getRow(urlSheet, rowNum);
			Cell cell = POIHelper.getCell(row, 0);
			String url = cell.getStringCellValue();
			if (url != null && !url.isEmpty() && !url.matches("https://www.linkedin.com/in/[^/]+/")) {
				SearchURL urlObj = null;
				if (SpiderConstants.searchURLs.get(url) != null) {
					urlObj = SpiderConstants.searchURLs.get(url);
				} else {
					urlObj = new SearchURL();
				}

				urlObj.setBaseURL(url);
				Integer currentPageNumber = new Double(POIHelper.getCell(row, 1).getNumericCellValue()).intValue();
				urlObj.setCurrentPageNumber(currentPageNumber);
				int download = new Double(POIHelper.getCell(row, 2).getNumericCellValue()).intValue();
				if (download == 1) {
					urlObj.setAllDownloaded(true);
				} else {
					urlObj.setAllDownloaded(false);
				}
				if (POIHelper.getCell(row, 3) != null) {
					urlObj.setTotal(new Double(POIHelper.getCell(row, 3).getNumericCellValue()).intValue());
				}

				if (urlObj.getTotal() != null) {
					if (urlObj.getCurrentPageNUmber() < 100
							&& urlObj.getTotal() > (urlObj.getCurrentPageNUmber() * 10)) {
						urlObj.setAllDownloaded(false);
					}
					// if (urlObj.getTotal() < ((urlObj.getCurrentPageNUmber() -
					// 1) * 10)) {
					// urlObj.setAllDownloaded(true);
					// }

					// if (urlObj.getTotal() < 10) {
					// urlObj.setAllDownloaded(false);
					// }
				}
				SpiderConstants.searchURLs.put(url, urlObj);
				SpiderConstants.downloadedSearchLinks.put(url, urlObj);
			}
		}
		workbook.removeSheetAt(workbook.getSheetIndex("urls"));
		// schools
		Sheet schoolsSheet = POIHelper.getSheet(workbook, "schools");
		for (int rowNum = 0; rowNum <= schoolsSheet.getLastRowNum(); rowNum++) {
			Row row = POIHelper.getRow(schoolsSheet, rowNum);
			Cell cell = POIHelper.getCell(row, 0);
			String schoolCode = cell.getStringCellValue();

			Cell cell1 = POIHelper.getCell(row, 1);
			String schoolName = cell1.getStringCellValue();
			SpiderConstants.schools.put(schoolCode, schoolName);
		}
		workbook.removeSheetAt(workbook.getSheetIndex("schools"));

		// companies
		Sheet companiesSheet = POIHelper.getSheet(workbook, "companies");
		for (int rowNum = 0; rowNum <= companiesSheet.getLastRowNum(); rowNum++) {
			Row row = POIHelper.getRow(companiesSheet, rowNum);
			Cell cell = POIHelper.getCell(row, 0);
			String companyCode = cell.getStringCellValue();

			Cell cell1 = POIHelper.getCell(row, 1);
			String companyName = cell1.getStringCellValue();
			if (!SpiderConstants.schools.contains(companyName)) {
				SpiderConstants.companys.put(companyCode, companyName);
			}
		}
		workbook.removeSheetAt(workbook.getSheetIndex("companies"));

		Spider spider = MyLinkedInSpider.create(dispather);
		for (String baseURL : SpiderConstants.searchURLs.keySet()) {
			SearchURL urlObj = SpiderConstants.searchURLs.get(baseURL);
			// if (!urlObj.isAllDownloaded()) {
			// spider.addUrl(urlObj.getTargetURL());
			// }
		}

		// companies
		Sheet thisTimeSheet = POIHelper.getSheet(workbook, "thisTime");
		for (int rowNum = 0; rowNum <= thisTimeSheet.getLastRowNum(); rowNum++) {
			Row row = POIHelper.getRow(thisTimeSheet, rowNum);
			Cell cell = POIHelper.getCell(row, 0);
			String newURL = cell.getStringCellValue();
			// if
			// (!LinkedinPeopleProfilePageProcessor.downloadLinks.contains(newURL))
			// {
			// spider.addUrl(newURL);
			// }
		}
		workbook.removeSheetAt(workbook.getSheetIndex("thisTime"));
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		java.util.logging.Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);

//		LinkedInOutputKeeper keeper = new LinkedInOutputKeeper();
//		keeper.start();

//		String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
//				"s", "t", "u", "v", "w", "x", "y", "z" };
//		Integer[] industry = { 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
//				27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52,
//				53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
//				79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102,
//				103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123,
//				124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144,
//				145, 146, 147, 148 };
//		for (String letter : letters) {
//			for (Integer in : industry) {
//
////				spider.addUrl("https://www.linkedin.com/search/results/people/?"
////						+ "facetNetwork=%5B%22F%22%5D&keywords=" + "A" + "&origin=FACETED_SEARCH");
//
//				String url = "https://www.linkedin.com/search/results/people/?facetIndustry=%5B%22" + in
//						+ "%22%5D&facetNetwork=%5B%22F%22%5D&keywords=" + letter + "&origin=FACETED_SEARCH";
//				String pageNumber = SpiderConstants.jedis_master.get(url);
//				if (pageNumber == null) {
//					spider.addUrl(url);
//				} else {
//					String[] pageNumberGroup = pageNumber.split(":");
//					String currentPage = pageNumberGroup[0];
//					String totalPage = pageNumberGroup[1];
//					if (!currentPage.equalsIgnoreCase(totalPage)) {
//						spider.addUrl(url + "&page=" + currentPage);
//					}
//
//					if (totalPage.equalsIgnoreCase("-1")) {
//						SpiderConstants.jedis_master.del(url);
//					}
//				}
//
//			}
//		}
		
		File huntingFirmFile = new File(CommonSetting.cookieFilePrefix + "huntingfirmsPureCode.txt");
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
		SpiderConstants.firmsSet = (List<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);
		for (HuntingCompany firm : SpiderConstants.firmsSet) {
			
			//
			String url = "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22"+firm.getCode()+"%22%5D&facetNetwork=%5B%22F%22%5D&origin=FACETED_SEARCH"+"&page="+firm.getCurrentPage();
			spider.addUrl(url);
		}


		File file = new File("C:\\Profiles\\profiles.txt");
		if (file.exists()) {
			FileInputStream fos;
			try {
				fos = new FileInputStream(file);
				BufferedInputStream bos = new BufferedInputStream(fos);
				String profilesStr = IOUtils.toString(bos);
				Map<String, Profile> profiles = JSON.parseObject(profilesStr,
						new TypeReference<HashMap<String, Profile>>() {
						});
				SpiderConstants.profiles.putAll(profiles);
				bos.close();

				for (String publicidentifier : profiles.keySet()) {
					Profile pro = profiles.get(publicidentifier);
					File publicFile = new File("C:\\CVS\\" + pro.getPublicIdentifier() + "CV.txt");
					if (!publicFile.exists()) {
						continue;
					}
					FileReader fr = new FileReader(publicFile);
					BufferedReader br = new BufferedReader(fr);
					String line = "";
					while ((line = br.readLine()) != null) {
						if (line.startsWith("电话：")) {
							//
							String phone = line.split("：")[1];
							if (!phone.contentEquals("null ")) {
								Phone ph = new Phone();
								ph.setType("手机");
								ph.setNumber(phone);
								pro.getPhones().add(ph);
							}
						}
						if (line.startsWith("邮箱：")) {
							//
							String email = line.split("：")[1];
							if (!email.contentEquals("null ")) {
								pro.setEmailAddress(email);
							}
						}
					}
					br.close();
					fr.close();
				}

				System.out.print("\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String longStr = JSON.toJSONString(SpiderConstants.profiles);
//		String longStr = JSON.toJSONString(SpiderConstants.profiles,
//				SerializerFeature.DisableCircularReferenceDetect);
		File profilesFile = new File("C:\\Profiles\\profiles.txt");

		File fileParent = profilesFile.getParentFile();
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

		if (SpiderConstants.jedis_master.get("allPublicIdentifiers") != null) {
			SpiderConstants.allPublicIdentifiers.addAll(JSON.parseObject(
					SpiderConstants.jedis_master.get("allPublicIdentifiers"), new TypeReference<HashSet<String>>() {
					}));
		}

//		for (String publicIdentifer : SpiderConstants.allPublicIdentifiers) {
//			if (SpiderConstants.profiles.get(publicIdentifer) == null) {
//				//
//				spider.addUrl("https://www.linkedin.com/in/" + publicIdentifer);
//			}
//		}

		for (String pub : SpiderConstants.profiles.keySet()) {
			if (!SpiderConstants.allPublicIdentifiers.contains(pub)) {
				SpiderConstants.allPublicIdentifiers.add(pub);
			}

		}

		SpiderConstants.jedis_master.set("allPublicIdentifiers",
				JSON.toJSONString(SpiderConstants.allPublicIdentifiers));

//		spider.addUrl(
//				"https://www.linkedin.com/search/results/people/?facetIndustry=%5B%2220%22%5D&facetNetwork=%5B%22F%22%5D&keywords=c&origin=GLOBAL_SEARCH_HEADER");

		String chromeDriverPath = CommonSetting.chromeDrivePath;
		LinkedSeleniumDownloader seleniumDownloader = new LinkedSeleniumDownloader(chromeDriverPath);
		spider.setDownloader(seleniumDownloader);
		// spider.setDownloader(new HttpClientDownloader())
		spider.setScheduler(new LinkedinPriorityScheduler())
				// Pipeline.json
				// .addPipeline(new ExcelFilePipeLine())
				// 5
				.thread(1)
				//
				.run();
	}

	public static void main(String[] args) throws IOException {
		new LinkedinSpiderHttpMain().startLinkedProfileSpider();

	}
}