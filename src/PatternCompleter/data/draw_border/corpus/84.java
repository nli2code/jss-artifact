package com.moolbuy.poi.exportor;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Assist {
	public static HSSFCellStyle getStyle1(HSSFWorkbook workbook){
		// 设置这些样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(IndexedColors.VIOLET.getIndex());
		font.setFontHeightInPoints((short) 12);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		return style;
	}
	
	public static HSSFCellStyle getStyle2(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 生成另一个字体
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
	
	public static XSSFCellStyle getStyle1(XSSFWorkbook workbook){
		// 设置这些样式
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(IndexedColors.VIOLET.getIndex());
		font.setFontHeightInPoints((short) 12);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		return style;
	}
	
	public static XSSFCellStyle getStyle2(XSSFWorkbook workbook){
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 生成另一个字体
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
}
