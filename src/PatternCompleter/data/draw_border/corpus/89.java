package com.mh.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 设置Excel样式
 * @author Victor.Chen
 *
 */
public class ExcelStyle {
	
	/**
	 * 设置头样式
	 * @param workbook
	 * @param style
	 * @return
	 */
	public static HSSFCellStyle setHeadStyle(HSSFWorkbook workbook, HSSFCellStyle style) {

		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font = workbook.createFont(); // 生成字体   
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		  
		style.setFont(font); // 把字体应用到当前的样样式 
		return style;

	}

	/**
	 * 设置数据体样式
	 * @param workbook
	 * @param style
	 * @return
	 */
	public static HSSFCellStyle setBodyStyle(HSSFWorkbook workbook, HSSFCellStyle style) {
		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont font = workbook.createFont(); //生成字体   
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		
		style.setFont(font); //把字体应用到当前的样样式   
		return style;
	}
}
