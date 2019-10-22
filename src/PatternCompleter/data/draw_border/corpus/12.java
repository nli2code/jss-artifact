package com.chai.util;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelStyle {
	public static void setBorderStyle(HSSFCellStyle cellStyle) {
		cellStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		cellStyle.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
		cellStyle.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	}
}
