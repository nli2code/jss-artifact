package com.uhg.ssmo.otnd.excel.decorator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportSheetUtils {
	public static int createHeader(XSSFWorkbook workbook, Row header,
			String[] headers, int headerCtr) {

		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		XSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setFont(headerFont);

		XSSFCellStyle remarks = workbook.createCellStyle();
		remarks.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		remarks.setBorderTop(XSSFCellStyle.BORDER_THIN);
		remarks.setBorderRight(XSSFCellStyle.BORDER_THIN);
		remarks.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		remarks.setAlignment(CellStyle.ALIGN_LEFT);
		remarks.setFont(headerFont);

		int remarksIndex = 999;

		for (int i = 0; i < headers.length; i++) {
			Cell headerCell = header.createCell(headerCtr++);
			headerCell.setCellValue(headers[i]);
			if (headers[i].equals("REMARKS")) {
				remarksIndex = i;
			}
		}

		for (int i = 0; i < headers.length; i++) {
			if(i==remarksIndex){
				header.getCell(i).setCellStyle(remarks);
			}else {
				header.getCell(i).setCellStyle(headerStyle);
			}
			
		}
		return headerCtr;
	}

	public static void createRow(XSSFWorkbook workbook, Row row, int rownum,
			String detail, int cellCtr, boolean isRemarks) {

		Cell pperiod = row.createCell(cellCtr);
		pperiod.setCellValue(detail);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);

		XSSFFont bodyFont = workbook.createFont();
		bodyFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		bodyFont.setFontHeightInPoints((short) 10);
		if (isRemarks) {
			style.setAlignment(CellStyle.ALIGN_LEFT);
			style.setWrapText(true);
		} else {
			style.setAlignment(CellStyle.ALIGN_CENTER);
		}

		style.setFont(bodyFont);

		row.getCell(cellCtr).setCellStyle(style);
	}

	public static void autoSizeWidth(XSSFSheet otnd, String[] headers) {
		for (int i = 0; i < headers.length; i++) {
			otnd.autoSizeColumn(i);
		}
	}

}
