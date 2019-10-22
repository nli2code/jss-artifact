package com.galaxy.util.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Front {

	public Front() {
	}
	
	/** 宋体 加粗 10号字体 */
	public static HSSFFont defaultFont(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		return font;
	}
	
}
