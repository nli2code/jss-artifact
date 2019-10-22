package kr.co.ibksystem.closing_report_automation.poi.sample;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewWorkbook {
	public static void main(String[] args) throws IOException {
		Workbook wb = new XSSFWorkbook();
	    FileOutputStream fileOut = new FileOutputStream("out\\workbook.xlsx");
	    String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
	    Sheet sheet3 = wb.createSheet(safeName);

	    wb.write(fileOut);
	    fileOut.close();
	}
}
