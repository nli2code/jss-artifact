package com.hongwei.futures.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

public class CellStyle {

	static HSSFCellStyle cellStyle = null;
	static HSSFFont fontStyle = null;

	public CellStyle(HSSFWorkbook wb) {
	}

	public CellStyle() {
	}

	/**
	 * 表头格式
	 * 
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle geHeadCellStyle(HSSFWorkbook wb) {
		fontStyle = wb.createFont();
		cellStyle = wb.createCellStyle();
		fontStyle.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontStyle.setFontHeightInPoints((short) 11);
		cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setAlignment(org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER);
		cellStyle.setFont(fontStyle);
		return cellStyle;
	}

	/**
	 * 表内容格式
	 * 
	 * @return
	 */
	public static HSSFCellStyle getContentStyle(HSSFWorkbook wb) {
		fontStyle = wb.createFont();
		cellStyle = wb.createCellStyle();
		fontStyle.setFontHeightInPoints((short) 11);
		cellStyle.setAlignment(org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN);
		cellStyle.setFont(fontStyle);
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	/**
	 * 表的说明信息格式
	 * 
	 * @return
	 */

	public static HSSFCellStyle getHeadInfo(HSSFWorkbook wb) {
		fontStyle = wb.createFont();
		cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER);
		// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT);
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setColor(HSSFColor.DARK_RED.index);
		cellStyle.setFont(fontStyle);
		return cellStyle;
	}

}
