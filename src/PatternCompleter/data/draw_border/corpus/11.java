package com.study.excel.common;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;

public class Excel {

	public void CreateExcel() throws Exception {
		InputStream inputStream = this.getClass().getResourceAsStream("/template/template.xlsx");
		Workbook workbook = WorkbookFactory.create(inputStream);

		Sheet sht = workbook.getSheet("Sheet1");
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		int i;
		for (i=2; i < 10000; i++) {
			Row row = CellUtil.getRow(i, sht);

			for (int j=0; j < 3; j++) {
				Cell cell = CellUtil.getCell(row, j);
				switch (j) {
				case 0:
					cell.setCellValue("aaa" + i);
					break;
				case 1:
					cell.setCellValue(i);
					break;
				case 2:
					cell.setCellValue(1000+i);
					break;
				}
				cell.setCellStyle(cellStyle);
			}
		}
		/*
		CellStyle st = cell.getCellStyle();
		st.setBorderTop(BorderStyle.THIN);
		st.setBorderLeft(BorderStyle.THIN);
		st.setBorderBottom(BorderStyle.THIN);
		st.setBorderRight(BorderStyle.THIN);
*/
		FileOutputStream fout = new FileOutputStream("c:/work/out.xlsx");
		workbook.write(fout);
	}

}
