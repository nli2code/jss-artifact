package com.mss.sb.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.sl.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteExcelSheet {
	XSSFWorkbook wb;
	XSSFSheet sheet;
	File f;
	FileInputStream fis;
public WriteExcelSheet(String path){
	try {
		f= new File(path);
		fis= new FileInputStream(f);
		wb= new XSSFWorkbook(fis);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public void removeCellvalue(String index,int row, int cell){
	sheet=wb.getSheet(index);
	Row r=sheet.getRow(row);
	Cell cell1= r.getCell(cell);
	if(cell1!=null){
		r.removeCell(cell1);
	}
	
}
public void writeData(String index,int row, int cell, String result, boolean status){
	try {
		CellStyle style=wb.createCellStyle();
		style.setWrapText(true);
		Font font=wb.createFont();
		//font.setBold(true);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		
		if(result.equalsIgnoreCase("Pass")){
		font.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else if(result.contains("Input")){
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else if(result.equalsIgnoreCase("Warning")){
			font.setColor(IndexedColors.WHITE.getIndex());
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else if(result.contains("Keyword")){
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setUnderline(XSSFFont.U_SINGLE);
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.PINK.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else if(result.equalsIgnoreCase("INFO")){
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else if(result.equalsIgnoreCase("Fail")){
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else{
			if(status){
				style.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
				font.setColor(IndexedColors.GREEN.getIndex());
				style.setFont(font);
			}
			else{
				style.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
				font.setColor(IndexedColors.RED.getIndex());
				style.setFont(font);
				}
		}
		sheet=wb.getSheet(index);
		Row r=sheet.getRow(row);
		Cell cell1= r.createCell(cell);
		cell1.setCellValue(result);
		cell1.setCellStyle(style);
		if(result.contains("Keyword")){
		CreationHelper createHelper = wb.getCreationHelper();
	    XSSFHyperlink hyperlink= (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
	    hyperlink.setLocation("'SUMMARY'!A1");
	    hyperlink.setTooltip("This is Invalid Keyword. Please click on LINK. Find correct KEYWORD in SUMMARY sheet. Thank you!!!");
	    cell1.setHyperlink(hyperlink);
		}
		FileOutputStream dest= new FileOutputStream(f);
		wb.write(dest);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public void countOfResult(String sheetName,int row, int cell, int count){
	try {
		sheet=wb.getSheet(sheetName);
		Cell cell1= sheet.getRow(row).createCell(cell);
		cell1.setCellValue(count);
		FileOutputStream destination= new FileOutputStream(f);
		wb.write(destination);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void writeData(String sheetName, int row, int cell, int total,String result) {
	try {
		CellStyle style=wb.createCellStyle();
		Font font=wb.createFont();
		//font.setBold(true);
		
		if(result.equalsIgnoreCase("pass")){
		font.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		}
		else if(result.equalsIgnoreCase("Warning")){
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		}
		else if(result.equalsIgnoreCase("fail")){
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
		}
		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		sheet=wb.getSheet(sheetName);
		Cell cell1= sheet.getRow(row).createCell(cell);
		cell1.setCellValue(total);
		cell1.setCellStyle(style);
		FileOutputStream dest= new FileOutputStream(f);
		wb.write(dest);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
