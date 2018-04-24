package com.linkedin.spider;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class POIHelper {
	public static Row getRow(Sheet sheet, int rowNumber) {
		Row row = sheet.getRow(rowNumber);
		if (row == null) {
			row = sheet.createRow(rowNumber);
		}
		return row;
	}

	public static Cell getCell(Row row, int rowNumber) {
		Cell cell = row.getCell(rowNumber);
		if (cell == null) {
			cell = row.createCell(rowNumber);
		}
		return cell;
	}

	public static Sheet getSheet(Workbook wb, String name) {
		if (wb.getSheet(name) == null) {
			return wb.createSheet(name);
		}
		return wb.getSheet(name);
	}

	public static int getNextRowNum(Sheet sheet, Row row) {
		for (int iter = row.getRowNum() + 1; iter <= sheet.getLastRowNum(); ++iter) {
			Row newRow = POIHelper.getRow(sheet, iter);
			Cell cell = newRow.getCell(0);
			if (cell != null && cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
				return iter;
			}
		}
		return -1;
	}

	public static boolean isEmptyRow(Row row) {
		for (int iter = 0; iter <= row.getLastCellNum(); iter++) {
			Cell cell = row.getCell(iter);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					String cellValue = cell.getStringCellValue();
					if (cellValue != null && !cellValue.isEmpty()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void copySheet(Sheet sheetLeft, Sheet sheetRight) {
		int iterRight = sheetRight.getFirstRowNum();
		for (int iterLeft = sheetLeft.getFirstRowNum(); iterLeft <= sheetLeft.getLastRowNum(); iterLeft++) {
			Row rowLeft = POIHelper.getRow(sheetLeft,iterLeft);
			if (!isEmptyRow(rowLeft)) {
				Row rowRight = POIHelper.getRow(sheetRight, iterRight++);
				for (int iter = 0; iter <= 40; iter++) {
					Cell cellLeft = POIHelper.getCell(rowLeft, iter);
					Cell cellRight = POIHelper.getCell(rowRight, iter);
					switch (cellLeft.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						cellRight.setCellValue(cellLeft.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellRight.setCellValue(cellLeft.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						cellRight.setCellValue(cellLeft.getStringCellValue());
						break;
					}
				}
			}
		}
	}

}
