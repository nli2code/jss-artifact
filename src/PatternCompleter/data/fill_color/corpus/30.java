package xyz.kemix.excel.painter.java.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-07
 * 
 *         it's too big pixels, can't set enough cell styles for colors.
 * 
 *         It's very very slow to create cell.
 */
public class JavaAwtXssfPixelPainter extends JavaPoiPixelPainter {

	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

	@Override
	protected void setFillForegroundColor(CellStyle style, int index) {
		((XSSFCellStyle) style).setFillForegroundColor(new XSSFColor(colorIndexedMap.getRGB(index), colorIndexedMap));
	}
}
