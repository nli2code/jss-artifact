package iscas.util;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class SpreadsheetToTypesheet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		changeSpreadsheetToColorByFloder("F:\\CellArray\\MrT.xls");
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
		HSSFWorkbook workbook = POIUtil.openSpreadsheet(pathAndFileName);
		if (workbook == null)
			return;
		if (pathAndFileName.endsWith("_typed.xls"))
			return;
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style4 = workbook.createCellStyle();
		style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style5 = workbook.createCellStyle();
		style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style6 = workbook.createCellStyle();
		style6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style7 = workbook.createCellStyle();
		style7.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			HSSFSheet sheet = workbook.getSheetAt(i);

			int rows = sheet.getLastRowNum();
			for (int r = sheet.getFirstRowNum(); r < rows; r++) {
				HSSFRow row = sheet.getRow(r);

				if (row == null) {
					continue;
				}
				int cells = row.getLastCellNum();
				for (int c = row.getFirstCellNum(); c < cells; c++) {
					HSSFCell cell = row.getCell(c);

					short bgcolor = 0;

					if (cell == null)
						continue;

					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_BLANK:
						bgcolor = HSSFColor.WHITE.index;
						style.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style);
						cell.setCellValue("-");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						bgcolor = HSSFColor.BLACK.index;
						style1.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style1);
						cell.setCellValue("BL");
						break;

					case HSSFCell.CELL_TYPE_ERROR:
						bgcolor = HSSFColor.RED.index;
						style2.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style2);
						cell.setCellValue("ER");
						break;

					case HSSFCell.CELL_TYPE_FORMULA:
						bgcolor = HSSFColor.WHITE.index;
						style3.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style3);
				 //   	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue("FM");
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						bgcolor = HSSFColor.YELLOW.index;
						style4.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style4);
						
						cell.setCellValue("NM");
						break;
					case HSSFCell.CELL_TYPE_STRING:
						bgcolor = HSSFColor.GREEN.index;
						style5.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style5);
						cell.setCellValue("LB");
						break;

					default:
						bgcolor = HSSFColor.PINK.index;
						style6.setFillForegroundColor(bgcolor);
						// ccell.setCellStyle(style6);
						cell.setCellValue("label");
						break;
					}

				}

			}
		}
		String colorPathandName = pathAndFileName.substring(0, pathAndFileName.lastIndexOf(".xls")) + "_typed.xls";
		POIUtil.saveSpreadsheet(workbook, colorPathandName);
	}

	private static String getType(HSSFCell cell) {
		String type="";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:

			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:

			break;

		case HSSFCell.CELL_TYPE_ERROR:

			break;

		case HSSFCell.CELL_TYPE_FORMULA:

			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			
			break;
		case HSSFCell.CELL_TYPE_STRING:


			break;

		default:


			break;
		}
		return type;
	}

}
