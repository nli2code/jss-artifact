package com.jsd.web.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiStyle {
	public HSSFCellStyle title(HSSFWorkbook wb, HSSFFont curFont){
		HSSFCellStyle curStyle = wb.createCellStyle();
		curStyle.setWrapText(false);  						//换行   
		curStyle.setFont(curFont);
		
		curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	} 
}
