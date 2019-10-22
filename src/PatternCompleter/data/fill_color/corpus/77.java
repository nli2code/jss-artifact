package com.jadic.excel;

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

/**
 * @author Jadic
 * @created 2012-12-4 
 */
public class ExcelDemo {
	
	public static void main(String[] args) {
		createNewTransDataXLSFile();
//		Workbook wb = new HSSFWorkbook();
//	    //Workbook wb = new XSSFWorkbook();
//	    CreationHelper createHelper = wb.getCreationHelper();
//	    Sheet sheet = wb.createSheet("new sheet");
//
//	    // Create a row and put some cells in it. Rows are 0 based.
//	    Row row = sheet.createRow(0);
//
//	    // Create a cell and put a date value in it.  The first cell is not styled
//	    // as a date.
//	    Cell cell = row.createCell(0);
//	    cell.setCellValue(new Date());
//
//	    // we style the second cell as a date (and time).  It is important to
//	    // create a new cell style from the workbook otherwise you can end up
//	    // modifying the built in style and effecting not only this cell but other cells.
//	    CellStyle cellStyle = wb.createCellStyle();
//	    cellStyle.setDataFormat(
//	        createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
//	    cell = row.createCell(1);
//	    cell.setCellValue(new Date());
//	    cell.setCellStyle(cellStyle);
//
//	    //you can also set date as java.util.Calendar
//	    cell = row.createCell(2);
//	    cell.setCellValue(Calendar.getInstance());
//	    cell.setCellStyle(cellStyle);
//		Workbook wb = new HSSFWorkbook();
//	    Sheet sheet = wb.createSheet("new sheet");
//	    Row row = sheet.createRow((short)2);
//	    row.createCell(0).setCellValue(1.1);
//	    row.createCell(1).setCellValue(new Date());
//	    row.createCell(2).setCellValue(Calendar.getInstance());
//	    row.createCell(3).setCellValue("a string");
//	    row.createCell(4).setCellValue(true);
//	    row.createCell(5).setCellType(Cell.CELL_TYPE_ERROR);
		Workbook wb = new HSSFWorkbook(); //or new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow((short) 1);

	    CellStyle style = wb.createCellStyle();
	    Cell cell = row.createCell((short) 0);
	    style.setFillForegroundColor(IndexedColors.RED.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(1);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(2);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(3);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(4);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(5);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(6);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(7);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.MAROON.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(8);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(9);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.ORCHID.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(10);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.CORAL.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(11);
	    style = wb.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell.setCellStyle(style);
	    
	    cell = row.createCell(12);
	    style = wb.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    cell.setCellValue("aaa");
	    cell.setCellStyle(style);
	    ExcelUtils.setCellBackgroudColor(cell, IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//	    style = cell.getCellStyle();
//	    style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        Sheet sheet = wb.createSheet();
//        Row row = sheet.createRow((short) 2);
//        row.setHeightInPoints(30);
//
//        createCell(wb, row, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
//        createCell(wb, row, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
//        createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER);
//        createCell(wb, row, (short) 3, CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER);
//        createCell(wb, row, (short) 4, CellStyle.ALIGN_JUSTIFY, CellStyle.VERTICAL_JUSTIFY);
//        createCell(wb, row, (short) 5, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_TOP);
//        createCell(wb, row, (short) 6, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_TOP);
//        sheet.autoSizeColumn(2);
//        sheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
//	    Workbook wb = new HSSFWorkbook();
//	    Sheet sheet = wb.createSheet("new sheet");
//
//	    Row row = sheet.createRow((short) 1);
//	    Cell cell = row.createCell((short) 1);
//	    cell.setCellValue("This is a test of merging");
//
//	    sheet.addMergedRegion(new CellRangeAddress(
//	            1, //first row (0-based)
//	            1, //last row  (0-based)
//	            1, //first column (0-based)
//	            2  //last column  (0-based)
//	    ));
		try {
			FileOutputStream fos = new FileOutputStream("d:/交易明细.xls");
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue("Align It");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }
	
	public static void createNewTransDataXLSFile() {
		Workbook wb = ExcelUtils.getWorkbook4Fuzhou("d:/icbc.xls");
		int lastRowNum = wb.getSheetAt(0).getLastRowNum();
		System.out.println(lastRowNum);
		try {
			FileOutputStream fos = new FileOutputStream("d:/工行交易明细.xls");
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
