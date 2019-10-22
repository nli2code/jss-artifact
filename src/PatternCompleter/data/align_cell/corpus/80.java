package com.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {

	@SuppressWarnings("deprecation")
	public static HSSFWorkbook createExcel(String[][] excel,String sheetName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		// sheet创建一个工作页
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth((short) 8);
		HSSFCellStyle style = wb.createCellStyle();//设置边框及对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		for (short i = 0; i < excel.length; i++) {
			// HSSFRow,对应一行
			HSSFRow row = sheet.createRow(i);
			for (short j = 0; j < excel[i].length; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(excel[i][j]);
							cell.setCellStyle(style);
			}
		}
		return wb;

	}
}
