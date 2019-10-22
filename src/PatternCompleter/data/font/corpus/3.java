package com.hao.schoa.tools;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

public class ExcelUtil {
	
	public static HSSFCellStyle getBaseStyle(HSSFWorkbook wb){
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	public static HSSFCellStyle getHeadStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
//		font.setUnderline(Font.U_DOUBLE);  // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）  
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public static HSSFCellStyle getCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	public static HSSFCellStyle getCellStylePaperAnalysiz(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	public static HSSFCellStyle getCellStyleTitle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public static HSSFCellStyle getCellStyleNoBorder(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		return cellStyle;
	}
	public static HSSFCellStyle getCellStyleNoBorder2(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public static HSSFCellStyle getCellStyleStudents(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	public static HSSFCellStyle getCellStyleKaoqin(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	public static HSSFCellStyle getCellStyleKaoqinKecheng(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 150);
		cellStyle.setFont(font);
		
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}

	public static HSSFCellStyle getCellStyleXiahuaxian(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setUnderline(Font.U_SINGLE);  // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）  
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public static HSSFCellStyle kaopinTitle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		return cellStyle;
	}
	public static HSSFCellStyle kaopin(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public static HSSFCellStyle leftTitleStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setFont(font);
		return cellStyle;
	}
	public static HSSFCellStyle leftStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setFont(font);
		return cellStyle;
	}
	public static HSSFCellStyle kaopinWentiStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = getBaseStyle(wb);
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setFont(font);
		
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
}
