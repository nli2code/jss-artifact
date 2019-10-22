package com.poi.excel.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class X_02_NewSheetTest {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet1 = wb.createSheet("sheet1");
		Sheet sheet2 = wb.createSheet("sheet2");
		
		// Note that sheet name is Excel must not exceed 31 characters
	    // and must not contain any of the any of the following characters:
	    // 0x0000
	    // 0x0003
	    // colon (:)
	    // backslash (\)
	    // asterisk (*)
	    // question mark (?)
	    // forward slash (/)
	    // opening square bracket ([)
	    // closing square bracket (])
		
		// You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
	    // for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
		String safeName = WorkbookUtil.createSafeSheetName("test?*");
		System.out.println(safeName);
//		Sheet sheet3 = wb.createSheet(safeName);
//		Sheet sheet3 = wb.createSheet("???");
		
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

}
