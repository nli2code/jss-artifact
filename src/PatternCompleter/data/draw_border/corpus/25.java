package com.uc.utils.export.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class ExcelHelper {

	public static void setBorder(CellStyle style, BorderStyle border){
		setBorder(style, border, border, border, border);		
	}
	
	public static void setBorder(CellStyle style, BorderStyle left, BorderStyle top, BorderStyle right, BorderStyle bottom){
		style.setBorderLeft(left);
		style.setBorderRight(right);
		style.setBorderTop(top);
		style.setBorderBottom(bottom);
	}
	
	public static void setRegionBorder(Sheet sheet, CellRangeAddress region, int thin){
		RegionUtil.setBorderLeft(thin, region, sheet);
		RegionUtil.setBorderRight(thin, region, sheet);
		RegionUtil.setBorderTop(thin, region, sheet);
		RegionUtil.setBorderBottom(thin, region, sheet);
	}
	public static int getCellWidth(String value){
		return value.getBytes().length * 256 + 256;
	}
	public static void setRegionBorder(Sheet sheet, CellRangeAddress cellRangeAddress, BorderStyle thin) {
		
		setRegionBorder(sheet, cellRangeAddress, thin.getCode());
	}
}
