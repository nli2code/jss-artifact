package org.test.poi;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class CreateRowTest extends BasePoiClass {

	@Test
	public void createRowTwiceTest() throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);
		row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue("1");
		row = sheet.createRow(0);
		Cell cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("2");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setIndention((short) 2);
		cell.setCellStyle(cellStyle);
		writeWorkbook(workbook, "createRowTewiceTest.xlsx");
	}

}
