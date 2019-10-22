package com.hotyum.stars.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class HeaderCellRenderDefault implements CellRender<String> {
	public void updateHeaderStyle(HSSFWorkbook wb, HSSFSheet sheet, HSSFCell cell, int index) {
		HSSFCellStyle style = wb.createCellStyle();
		// style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font2 = wb.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		font2.setColor(HSSFColor.BLACK.index);
		style.setWrapText(true);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFont(font2);
		cell.setCellStyle(style);
	}

	public void updateBodyStyle(HSSFWorkbook wb, HSSFSheet sheet, HSSFCell cell, String data, String fieldName,
			int index) {
		throw new UnsupportedOperationException();
	}
}
