package com.coderdream.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Admin http://poi.apache.org/spreadsheet/quick-guide.html#NewWorkbook
 *
 */
public class Ex02NewSheet {


	public void createNewSheet(String filename) {
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = null;
		try {
			// fileOut = new FileOutputStream("workbook.xlsx");
		    fileOut = new FileOutputStream(filename);
		    //Sheet sheet1 = 
		    wb.createSheet("new sheet");
		    //Sheet sheet2 = 
		    wb.createSheet("second sheet");

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
		    //Sheet sheet3 = 
		    		wb.createSheet(safeName);
		   
		    wb.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOut.close();
				if (null != wb) {
					wb.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
