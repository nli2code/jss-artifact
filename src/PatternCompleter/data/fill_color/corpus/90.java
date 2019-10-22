package com.capgemini.ktestmachine.component.exceladapter.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.capgemini.ktestmachine.component.exceladapter.TStyle;

public class TStylePoi implements TStyle {

	private CellStyle cellStyle;
	private short color;

	public TStylePoi(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
		color = 0;
	}

	public TStylePoi(Workbook workbook, short id) {
		color = id;
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(id);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	}
	
	public CellStyle getCellStyle() {
		return cellStyle;
	}
	
	public short getColor() {
		return color;
	}
}
