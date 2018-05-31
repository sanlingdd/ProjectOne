package com.linkedin.spider.processor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkedin.spider.SpiderConstants;

public class APOutputKeeper extends Thread {
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
		int previousCount = 0;
		while (true) {
			try {
				previousCount = StaticUtil.profiles.size();
				this.sleep(20000);
				try {
					this.writeToExcelFile();
				} catch (Exception e) {
					logger.debug(e.getMessage());
				}

//				if (StaticUtil.profiles.size() - previousCount == 0) {
//					break;
//				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void writeToExcelFile() {

		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Sheet sheet = this.getSheet(workbook, "profiles");
		int rowNum = sheet.getLastRowNum() + 1;

		for (APProfile profile : StaticUtil.profiles) {
			int CellNum = 0;
			Row row = this.getRow(sheet, rowNum++);
			Cell cell1 = row.createCell(CellNum++);
			Cell cell2 = row.createCell(CellNum++);
			Cell cell3 = row.createCell(CellNum++);
			Cell cell4 = row.createCell(CellNum++);
			Cell cell5 = row.createCell(CellNum++);
			Cell cell6 = row.createCell(CellNum++);
			Cell cell7 = row.createCell(CellNum++);
			Cell cell8 = row.createCell(CellNum++);
			cell1.setCellValue(profile.getChinesename());
			cell2.setCellValue(profile.getDepartment());
			cell3.setCellValue(profile.getId());
			cell4.setCellValue(profile.getLocation());
			cell5.setCellValue(profile.getMobilephone());
			cell6.setCellValue(profile.getName());
			cell7.setCellValue(profile.getOffice());
			cell8.setCellValue(profile.getWorkphone());
		}
		FileOutputStream outputStream = null;
		fileName = "C:/data/webmagic/" + UUID.randomUUID().toString() + ".xlsx";
		try {
			outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			IOUtils.closeQuietly(outputStream);

		} catch (Exception e) {
		}

		logger.info("Total Profiles Getted: " + SpiderConstants.profilesAccessedVector.size());

	}
}
