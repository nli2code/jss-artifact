package junit.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOI {
	@Test
	public void testwrite() throws Exception{
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFCellStyle style = book.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		XSSFFont font = book.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setColor(HSSFColor.YELLOW.index);
		style.setFont(font);
		XSSFSheet sheet = book.createSheet("hello word");
		CellRangeAddress rangAddress = new CellRangeAddress(1, 1, 1, 4);
		sheet.addMergedRegion(rangAddress);
		XSSFRow row = sheet.createRow(1);
		XSSFCell cell = row.createCell(1);
		cell.setCellValue("我是合并后的单元格");
		cell.setCellStyle(style);
		FileOutputStream out = new FileOutputStream("d:\\lfsenior\\test.xlsx");
		book.write(out);
		out.close();
		book.close();
	}

}
