package com.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputKeeper extends Thread {
	private static String fileName = null;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public Row getRow(Sheet sheet, int rowNumber) {
		Row row = sheet.getRow(rowNumber);
		if (row == null) {
			row = sheet.createRow(rowNumber);
		}
		return row;
	}

	public Cell getCell(Row row, int rowNumber) {
		Cell cell = row.getCell(rowNumber);
		if (cell == null) {
			cell = row.createCell(rowNumber);
		}
		return cell;
	}

	private Sheet getSheet(Workbook wb, String name) {
		if (wb.getSheet(name) == null) {
			return wb.createSheet(name);
		}
		return wb.getSheet(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		int currentCount = 0;
		long twoHour = 1000 * 60 * 60 * 3;
		while (true) {
			int tempCount = SpiderConstants.profilesAccessedVector.size();
			Date now = new Date();
			long timeUsed = now.getTime() - SpiderConstants.startDate.getTime();
			if (timeUsed > twoHour) {
				SpiderConstants.stop = true;
			}
			try {
				if (tempCount - currentCount != 0) {
					if (tempCount > currentCount + 100) {
						try {
							this.writeToExcelFile();
						} catch (Exception e) {
							logger.debug(e.getMessage());
						}
						this.sleep(100000);
						currentCount = tempCount;
					} else {
						this.sleep(100000);
					}
				} else if (SpiderConstants.profilesAccessedVector.isEmpty() && SpiderConstants.searchURLs.isEmpty()) {
					this.sleep(100000);
				} else {
					try {
						this.writeToExcelFile();
					} catch (Exception e) {
						logger.debug(e.getMessage());
					}
					this.sleep(100000);
					break;
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Collections.sort(SpiderConstants.profilesAccessedVector, new
		// Comparator() {
		// public int compare(Object o1, Object o2) {
		// if (!(o1 instanceof HashMap<?, ?>)) {
		// return 0;
		// }
		// if (!(o2 instanceof HashMap<?, ?>)) {
		// return 0;
		// }
		// HashMap<String, Object> map1 = (HashMap<String, Object>) o1;
		// HashMap<String, Object> map2 = (HashMap<String, Object>) o2;
		// if (map2.get("currentCompany") != null && map1.get("currentCompany")
		// != null) {
		// return
		// map2.get("currentCompany").toString().compareTo(map1.get("currentCompany").toString());
		// } else if (map2.get("currentCompany") != null) {
		// return 1;
		// } else {
		// return -1;
		// }
		// }
		//
		// });
	}

	public void writeToExcelFile() {

		Workbook workbook = null;
		Map<String, Integer> columnMapping = new HashMap<String, Integer>();
		Integer startColumnNumber = 0;
		columnMapping.put("publicIdentifier", startColumnNumber++);
		columnMapping.put("firstName", startColumnNumber++);
		columnMapping.put("maidenName", startColumnNumber++);
		columnMapping.put("lastName", startColumnNumber++);
		columnMapping.put("birthday", startColumnNumber++);
		columnMapping.put("totalWorkExperience", startColumnNumber++);
		columnMapping.put("currentCompany", startColumnNumber++);
		columnMapping.put("currentTittle", startColumnNumber++);
		columnMapping.put("highestDegree", startColumnNumber++);
		columnMapping.put("emailAddress", startColumnNumber++);
		columnMapping.put("number", startColumnNumber++);
		columnMapping.put("type", startColumnNumber++);
		columnMapping.put("websitesurl", startColumnNumber++);
		columnMapping.put("industryName", startColumnNumber++);
		columnMapping.put("locationName", startColumnNumber++);
		columnMapping.put("address", startColumnNumber++);
		columnMapping.put("interests", startColumnNumber++);
		columnMapping.put("versionTag", startColumnNumber++);
		columnMapping.put("wechatImageURL", startColumnNumber++);
		columnMapping.put("summary", startColumnNumber++);
		columnMapping.put("WlocationName", startColumnNumber++);
		columnMapping.put("companyName", startColumnNumber++);
		columnMapping.put("Wstart", startColumnNumber++);
		columnMapping.put("Wend", startColumnNumber++);
		columnMapping.put("description", startColumnNumber++);
		columnMapping.put("title", startColumnNumber++);
		columnMapping.put("school", startColumnNumber++);
		columnMapping.put("Estart", startColumnNumber++);
		columnMapping.put("Eend", startColumnNumber++);
		columnMapping.put("degreeName", startColumnNumber++);
		columnMapping.put("schoolName", startColumnNumber++);
		columnMapping.put("fieldOfStudy", startColumnNumber++);

		String tempFile = null;
		if (fileName == null) {
			tempFile = "C:/data/webmagic/www.linkedin.com/LinkedProfiles.xlsx";
		} else {
			tempFile = fileName;
		}
		try {
			FileInputStream inputStream = new FileInputStream(tempFile);
			workbook = new XSSFWorkbook(inputStream);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet sheet = this.getSheet(workbook, "profiles");
		int startRowNum = sheet.getLastRowNum() + 1;
		Row row = this.getRow(sheet, startRowNum);

		for (HashMap<String, Object> profile : SpiderConstants.profilesAccessedVector) {
			if (profile.get("publicIdentifier") == null) {
				continue;
			}
			if (SpiderConstants.profilesWritten.contains(profile.get("publicIdentifier").toString())) {
				continue;
			}
			int rowIncrement = 0;
			try {
				for (String string : profile.keySet()) {
					if (profile.get(string) instanceof String) {
						Cell cell = this.getCell(row, columnMapping.get(string));
						cell.setCellValue((String) profile.get(string));
						String str = (String) profile.get(string);

						str.toString();
					} else if (profile.get(string) instanceof List) {
						int lines = 0;
						List<HashMap<String, String>> values = (List<HashMap<String, String>>) profile.get(string);
						int currentRowNum = startRowNum;
						for (HashMap<String, String> map : values) {
							for (String attribute : map.keySet()) {
								if (columnMapping.get(attribute) != null) {
									Cell cell = this.getCell(row, columnMapping.get(attribute));
									cell.setCellValue((String) map.get(attribute));
								}
							}
							row = this.getRow(sheet, ++currentRowNum);
							lines++;
						}
						row = this.getRow(sheet, startRowNum);
						if (rowIncrement < lines) {
							rowIncrement = lines;
						}
					}
				}
				startRowNum = startRowNum + rowIncrement;
				row = this.getRow(sheet, startRowNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			SpiderConstants.profilesWritten.add(profile.get("publicIdentifier").toString());
			logger.info("writing the profile to excel: " + profile.get("publicIdentifier"));
		}

		try {
			Sheet sheet1 = this.getSheet(workbook, "companies");
			Sheet sheet2 = this.getSheet(workbook, "schools");
			Sheet sheet3 = this.getSheet(workbook, "urls");

			int start = sheet1.getLastRowNum();
			for (String str : SpiderConstants.companys.keySet()) {
				row = this.getRow(sheet1, start++);
				Cell cell = this.getCell(row, 0);
				cell.setCellValue((String) str);
				Cell cell1 = this.getCell(row, 1);
				cell1.setCellValue((String) SpiderConstants.companys.get(str));

			}

			start = sheet2.getLastRowNum();
			for (String str : SpiderConstants.schools.keySet()) {
				row = this.getRow(sheet2, start++);
				Cell cell = this.getCell(row, 0);
				cell.setCellValue((String) str);
				Cell cell1 = this.getCell(row, 1);
				cell1.setCellValue((String) SpiderConstants.schools.get(str));
			}

			start = 1;
			for (String str : SpiderConstants.searchURLs.keySet()) {
				try {
					SearchURL url = SpiderConstants.searchURLs.get(str);
					row = this.getRow(sheet3, start++);
					Cell cell = this.getCell(row, 0);
					cell.setCellValue((String) str);

					Cell cell1 = this.getCell(row, 1);
					cell1.setCellValue(url.getCurrentPageNUmber());

					Cell cell2 = this.getCell(row, 2);
					if (url.isAllDownloaded()) {
						cell2.setCellValue(1);
					} else {
						cell2.setCellValue(0);
					}

					if (url.getTotal() != null) {
						Cell cell3 = this.getCell(row, 3);
						cell3.setCellValue(url.getTotal());
					}
				} catch (Exception e) {
					logger.debug(e.getLocalizedMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			fileName = "C:/data/webmagic/" + UUID.randomUUID().toString() + ".xlsx";
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Total Profiles Getted: " + SpiderConstants.profilesAccessedVector.size());

	}
}
