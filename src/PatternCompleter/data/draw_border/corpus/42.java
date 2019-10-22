package com.xiaojiezhu.style;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Style {
	
	/**
	 * 获取标题的样式
	 * 边框
	 * @return
	 */
	public static XSSFCellStyle getTitleStyle(XSSFWorkbook book){
		XSSFCellStyle style = book.createCellStyle();
		
		setBorder(style);
		
		//字体，加粗
		XSSFFont font = book.createFont();
		font.setFontHeightInPoints((short)13);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); 
		style.setFont(font);
		return style;
	}
	/**
	 * 获取普通文本的样式
	 * @param book
	 * @return
	 */
	public static XSSFCellStyle getTextStyle(XSSFWorkbook book){
		XSSFCellStyle style = book.createCellStyle();
		
		setBorder(style);
		return style;
	}
	
	
	/**
	 * 设置边框并居中
	 * @param style
	 */
	private static void setBorder(XSSFCellStyle style){
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		//居中
		style.setAlignment(HorizontalAlignment.CENTER);
	}
}
