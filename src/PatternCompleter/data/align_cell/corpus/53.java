package com.ashim.excel.launch;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.ashim.excel.common.ExcelConstant;
import com.ashim.excel.common.ExcelUtility;

public class ExcelFormat {

	public static void main(String[] args) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("ExcelEx");

		Cell cell = sheet.createRow(0).createCell(0);

		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.RED.getIndex());
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		Font font = workbook.createFont();
		font.setColor(IndexedColors.YELLOW.getIndex());
		font.setBold(true);
		font.setItalic(true);
		font.setFontHeightInPoints((short) 32);
		font.setUnderline(Font.U_DOUBLE);
		font.setFontName("Helvetica");

		style.setFont(font);
		cell.setCellStyle(style);
		cell.setCellValue("Hello, This is Excel Example");

		ExcelUtility.generateExcel(workbook, ExcelConstant.EXCEL_FORMAT);
	}

}
