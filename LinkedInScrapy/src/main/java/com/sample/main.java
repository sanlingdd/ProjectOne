package com.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

public class main {

	public static void main(String[] args) {

		Workbook workbook = null;
		FileInputStream inputStream = null;
		String tempFile = "C:/data/webmagic/www.linkedin.com/LinkedProfiles.xlsx";
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
				if (!LinkedinPeopleProfilePageProcessor.downloadLinks.contains(profileURL)) {
					LinkedinPeopleProfilePageProcessor.downloadLinks.add(profileURL);
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
//					if (urlObj.getTotal() < ((urlObj.getCurrentPageNUmber() - 1) * 10)) {
//						urlObj.setAllDownloaded(true);
//					}

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

//		for (String url : SpiderConstants.searchURLs.keySet()) {
//			SearchURL ul = SpiderConstants.searchURLs.get(url);
//			if (!ul.isAllDownloaded() && ul.getCurrentPageNUmber() == 1) {
//				ul.setTotal(0);
//			}
//		}
		
		Spider spider = MySpider.create(new LinkedinPeopleProfilePageProcessor());
		for (String baseURL : SpiderConstants.searchURLs.keySet()) {
			SearchURL urlObj = SpiderConstants.searchURLs.get(baseURL);
			if (!urlObj.isAllDownloaded()) {
				spider.addUrl(urlObj.getTargetURL());
			}
		}

		// companies
		Sheet thisTimeSheet = POIHelper.getSheet(workbook, "thisTime");
		for (int rowNum = 0; rowNum <= thisTimeSheet.getLastRowNum(); rowNum++) {
			Row row = POIHelper.getRow(thisTimeSheet, rowNum);
			Cell cell = POIHelper.getCell(row, 0);
			String newURL = cell.getStringCellValue();
			if (!LinkedinPeopleProfilePageProcessor.downloadLinks.contains(newURL)) {
				spider.addUrl(newURL);
			}
		}
		workbook.removeSheetAt(workbook.getSheetIndex("thisTime"));

		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		OutputKeeper keeper = new OutputKeeper();
		keeper.start();
		// String url =
		// "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%2262435%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%5D&facetNetwork=%5B%22F%22%5D&facetPastCompany=%5B%22166244%22%5D&origin=FACETED_SEARCH&page=1";
		// 从https://github.com/code4craft开始抓
		// .addUrl("https://www.linkedin.com/in/xiaohaizixiaoyao/")
		// .addUrl("https://www.linkedin.com/in/jessicajia/")
		// SAP
		// .addUrl("https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%221009%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=1")
		// marine software 14
		// String url =
		// "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8883%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetNetwork=%5B%22F%22%5D&origin=FACETED_SEARCH&page=6";
		// LinkedinPeopleProfilePageProcessor.downloadLinks.add(url);
		// mags associates 33
		// https://www.linkedin.com/search/results/people/?facetNetwork=%5B%22F%22%5D&keywords=tai%20helen&origin=FACETED_SEARCH
		// .addUrl("https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%221179160%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=1")
		// .addUrl("https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%226279%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetIndustry=%5B%22137%22%2C%22104%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=1")
		// 设置Scheduler，使用Redis来管理URL队列

		spider.setScheduler(new PriorityScheduler())
				// 设置Pipeline，将结果以json方式保存到文件
				.addPipeline(new ExcelFilePipeLine())
				// 开启5个线程同时执行
				.thread(3)
				// 启动爬虫
				.run();

	}
}