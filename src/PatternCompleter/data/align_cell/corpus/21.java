package com.speed.cscenter.css.statistics.util.poi.xls.common.css;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class XlsStyle {
	public static XSSFCellStyle xlsxStyleTitle(XSSFWorkbook wk) {
		XSSFCellStyle style = wk.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
	public static XSSFCellStyle xlsxStyleData(XSSFWorkbook wk) {
		XSSFCellStyle style = wk.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
	public static HSSFCellStyle xlsStyleTitle(HSSFWorkbook wk) {
		HSSFCellStyle style = wk.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
	public static HSSFCellStyle xlsStyleData(HSSFWorkbook wk) {
		HSSFCellStyle style = wk.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
}
