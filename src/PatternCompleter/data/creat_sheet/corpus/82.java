package com.sportlegue;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.junit.Test;

//import com.google.common.collect.Table.Cell;

public class MakeXls {

	@Test
	public void test() throws IOException {
		Workbook wb = new HSSFWorkbook();
		//Создать листы
		Sheet sheet0 = wb.createSheet("Издатели");
		Row row = sheet0.createRow(3);
		Cell cell = row.createCell(4);
		cell.setCellValue("O'Reilly");
		
		Sheet sheet1 = wb.createSheet("Книги");
		Sheet sheet2 = wb.createSheet("Авторы");
		
		//Создать лист с невалидным названием
		Sheet sheet3 = wb.createSheet(WorkbookUtil.createSafeSheetName("879dfj()*&*(6789(^&%^"));
		
		FileOutputStream fos = new FileOutputStream("me.xls");
		wb.write(fos);
		fos.close();
	}

}
