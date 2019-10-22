package com.travel.utils.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class DefaultWriteExcelCallback<T> {
	protected CellStyle defaultStyle(CellStyle style) {
		style.setBorderBottom(BorderStyle.NONE);
		style.setBorderLeft(BorderStyle.NONE);
		style.setBorderRight(BorderStyle.NONE);
		style.setBorderTop(BorderStyle.NONE);
		style.setAlignment(HorizontalAlignment.CENTER);
		return style;
	}

	public abstract void writeTitle(Workbook arg0, Sheet arg1, Row arg2, Cell arg3, String arg4);

	public abstract void writeData(Workbook arg0, Sheet arg1, Row arg2, Cell arg3, String arg4, Object arg5);
}
