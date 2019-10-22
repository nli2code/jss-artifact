package ht.xporter.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Header implements Style {
	private CellStyle headerStyle;

	public Header(SXSSFWorkbook workbook) {
		headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_LEFT);
		Border.fill(headerStyle);
	}

	public CellStyle get() {
		return headerStyle;
	}
}