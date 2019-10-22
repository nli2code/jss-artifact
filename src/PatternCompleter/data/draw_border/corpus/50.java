package org.zcl.mytest.poiExcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class ExcelCellStyleHelper {
	
	public static void setCellStyleBorder(CellStyle style, boolean border){
		if (border){
		    style.setBorderBottom(CellStyle.BORDER_THIN);
		    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    style.setBorderLeft(CellStyle.BORDER_THIN);
		    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		    style.setBorderRight(CellStyle.BORDER_THIN);
		    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		    style.setBorderTop(CellStyle.BORDER_THIN);
		    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		}else{
		    style.setBorderBottom(CellStyle.BORDER_NONE);
		    style.setBorderLeft(CellStyle.BORDER_NONE);
		    style.setBorderRight(CellStyle.BORDER_NONE);
		    style.setBorderTop(CellStyle.BORDER_NONE);
		}
	}
	
	public static Font createFont(HSSFWorkbook workbook, String fontName, short size, boolean bold, boolean italic, boolean underLine, boolean deleteLine){
		if (workbook==null) throw new IllegalArgumentException("please create workbook first");
		Font result=workbook.createFont();
		result.setFontName(fontName);
		result.setFontHeightInPoints(size);
		result.setItalic(italic);
		result.setStrikeout(deleteLine);
		result.setUnderline(underLine? Font.U_SINGLE: Font.U_NONE);
		result.setBoldweight(bold? Font.BOLDWEIGHT_BOLD: Font.BOLDWEIGHT_NORMAL);
		return result;
	}
	
}
