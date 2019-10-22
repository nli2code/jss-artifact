package com.magnabyte.POI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ejemploPoiColor {

	
	public static void main(String[] args) throws IOException {
//		   Workbook wb = new HSSFWorkbook();
//		    Sheet sheet = wb.createSheet("new sheet");
//
//		    // Create a row and put some cells in it. Rows are 0 based.
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");
        Row row = sheet.createRow(4);

		    // Create a cell and put a value in it.
		    Cell cell = row.createCell(4);
		    cell.setCellValue("00005");

		    // Style the cell with borders all around.
		    CellStyle style = workbook.createCellStyle();
		    style.setBorderTop(CellStyle.BORDER_THIN);
//		    style.setTopBorderColor(IndexedColors.BLUE.getIndex());
		    style.setBorderRight(CellStyle.BORDER_THIN);
//		    style.setRightBorderColor(IndexedColors.BLUE.getIndex());
		    style.setBorderBottom(CellStyle.BORDER_THIN);
//		    style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		    style.setBorderLeft(CellStyle.BORDER_THIN);
//		    style.setAlignment(CellStyle.VERTICAL_CENTER);
		    style.setAlignment(CellStyle.ALIGN_CENTER);
//		    style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		    cell.setCellStyle(style);

		    // Write the output to a file
		    FileOutputStream fileOut = new FileOutputStream("C:/Users/Roberto/Documents/EJEMPLO-COLOR.xlsx");
		    workbook.write(fileOut);
		    fileOut.close();
		   System.out.println("reporte generado");
	}
}
