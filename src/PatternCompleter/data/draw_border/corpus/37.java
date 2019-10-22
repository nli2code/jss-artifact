package util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class GenericCellStyle {

	public static CellStyle getStyle(Workbook libro, short color) {
		
		CellStyle cs = libro.createCellStyle();
		cs.setFillForegroundColor(color);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		cs.setBorderBottom(CellStyle.BORDER_MEDIUM);
		cs.setBorderLeft(CellStyle.BORDER_MEDIUM);
		cs.setBorderRight(CellStyle.BORDER_MEDIUM);
		cs.setBorderTop(CellStyle.BORDER_MEDIUM);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setWrapText(true);
		
		return cs;
		
	}
	
}
