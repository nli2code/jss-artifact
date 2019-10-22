package com.hpe.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ��ȭ������������һ����
 * @author Administrator
 *
 */
public class CreateExcel {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet("sheet1");
		Row row = sheet1.createRow(0);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		String[] headers = {"���", "����", "����", "�Ա�"};
		for(int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(cellStyle);
		}
		
	    FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\workbook.xlsx");
	    wb.write(fileOut);
	    fileOut.close();
	}
}
