package kj.poi;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.junit.Test;

public class QuickGuide02 {
	/*
	 * How to create a sheet
	 * http://poi.apache.org/spreadsheet/quick-guide.html#NewSheet
	 */

	@Test
	public void test() {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet1 = wb.createSheet("new sheet");
		Sheet sheet2 = wb.createSheet("second sheet");
		String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]");
		Sheet sheet3 = wb.createSheet(safeName);
		
		FileOutputStream os;
		try {
			os = new FileOutputStream("workbook.xls");
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail("fail");
		}
		assertTrue(true);
	}
}
