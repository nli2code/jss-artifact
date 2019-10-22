package com.wdxy.poi;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class XLSCreatest {
	
	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
		
		 Sheet sheet1 = wb.createSheet("new sheet");
		    Sheet sheet2 = wb.createSheet("second sheet");

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
		    String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
		    Sheet sheet3 = wb.createSheet(safeName);

		    
	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
}
