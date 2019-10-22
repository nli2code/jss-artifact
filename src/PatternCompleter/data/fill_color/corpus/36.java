package iscas.util;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class SpreadsheetToColorsheet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		changeSpreadsheetToColorByFloder("E:\\155_11_fomreq");
		System.out.println("=============done=========================");
	}

	public static void changeSpreadsheetToColorByFloder(String path) {
		File file = new File(path);
		if (!file.exists())
			return;
		if (file.isFile()) {
			changeSpreadsheetToColorsheet(path);
			return;
		}
		File[] files = file.listFiles();
		for (File tfile : files) {
			if (tfile.isDirectory())
				changeSpreadsheetToColorByFloder(tfile.getAbsolutePath());
			else
				changeSpreadsheetToColorsheet(tfile.getAbsolutePath());
		}

	}

	public static void changeSpreadsheetToColorsheet(String pathAndFileName) {
		HSSFWorkbook colorbook = POIUtil.createSpreadsheet();
		HSSFWorkbook workbook = POIUtil.openSpreadsheet(pathAndFileName);
		if (workbook == null)
			return;
		if(pathAndFileName.endsWith("_colored.xls"))
			return;
		HSSFCellStyle style = colorbook.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style1 = colorbook.createCellStyle();
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style2 = colorbook.createCellStyle();
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style3 = colorbook.createCellStyle();
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style4 = colorbook.createCellStyle();
		style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style5 = colorbook.createCellStyle();
		style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style6 = colorbook.createCellStyle();
		style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style7 = colorbook.createCellStyle();
		style7.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			HSSFSheet sheet = workbook.getSheetAt(i);
			HSSFSheet colorsheet = colorbook.createSheet(sheet.getSheetName());
			int rows = sheet.getLastRowNum();
			for (int r = sheet.getFirstRowNum(); r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				
				HSSFRow crow = colorsheet.createRow(r);
				if (row == null) {
					continue;
				}
				int cells = row.getLastCellNum();
				for (int c = row.getFirstCellNum(); c < cells; c++) {
					HSSFCell cell = row.getCell(c);
					HSSFCell ccell = crow.createCell(c);
			
					short bgcolor = 0;
					
					if(cell!=null){
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_BLANK:
						bgcolor = HSSFColor.WHITE.index;
						style.setFillForegroundColor(bgcolor);
						//ccell.setCellStyle(style);
						ccell.setCellValue("-");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						bgcolor = HSSFColor.BLACK.index;
						style1.setFillForegroundColor(bgcolor);
					//	ccell.setCellStyle(style1);
						ccell.setCellValue("data");
						break;

					case HSSFCell.CELL_TYPE_ERROR:
						bgcolor = HSSFColor.RED.index;
						style2.setFillForegroundColor(bgcolor);
				//		ccell.setCellStyle(style2);
						ccell.setCellValue("data");
						break;

					case HSSFCell.CELL_TYPE_FORMULA:
						bgcolor = HSSFColor.WHITE.index;
						style3.setFillForegroundColor(bgcolor);
				//		ccell.setCellStyle(style3);
						ccell.setCellValue("data");
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						bgcolor = HSSFColor.YELLOW.index;
						style4.setFillForegroundColor(bgcolor);
					//	ccell.setCellStyle(style4);
						ccell.setCellValue("data");
						break;
					case HSSFCell.CELL_TYPE_STRING:
						bgcolor = HSSFColor.GREEN.index;
						style5.setFillForegroundColor(bgcolor);
					//	ccell.setCellStyle(style5);
						ccell.setCellValue("label");
						break;

					default:
						bgcolor = HSSFColor.PINK.index;
						style6.setFillForegroundColor(bgcolor);
					//	ccell.setCellStyle(style6);
						ccell.setCellValue("label");
						break;
					}
				
				}
					else{
						bgcolor=HSSFColor.ORANGE.index;
						style7.setFillForegroundColor(bgcolor);
						ccell.setCellStyle(style7);
						ccell.setCellValue("Null");
					}
				}
				
			}
		}
		String colorPathandName = pathAndFileName.substring(0, pathAndFileName.lastIndexOf(".xls")) + "_colored.xls";
		POIUtil.saveSpreadsheet(colorbook, colorPathandName);
	}

}
