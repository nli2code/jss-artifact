package org.ywp.poi.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CustomerColorsXssf
{

	/**
	 * @param args
	 * @throws  Exception 
	 */
	public static void main(String[] args) throws  Exception
	{
		XSSFWorkbook wb = new XSSFWorkbook();
	    XSSFSheet sheet = wb.createSheet();
	    XSSFRow row = sheet.createRow(1);
	    XSSFCell cell = row.createCell( 1);
	    cell.setCellValue("custom XSSF colors");
	   
	    XSSFCellStyle style1 = wb.createCellStyle();
	    style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 202, 202)));
	    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
	 

	    XSSFFont font = wb.createFont();
	    font.setColor(new XSSFColor(new java.awt.Color(0, 0, 202)));//HSSFColor.RED.index
	    font.setFontHeightInPoints((short)24);
	    style1.setFont(font);
	    style1.setAlignment((short) 2);
	    
	    style1.setBorderBottom(CellStyle.BORDER_THIN);
	    style1.setBorderLeft(CellStyle.BORDER_THIN);
	    style1.setBorderRight(CellStyle.BORDER_THIN);
	    style1.setBorderTop(CellStyle.BORDER_THIN);
	    style1.setTopBorderColor(IndexedColors.RED.getIndex());
	    cell.setCellStyle(style1);
	    sheet.addMergedRegion(new CellRangeAddress(
	            1, //first row (0-based)
	            1, //last row  (0-based)
	            1, //first column (0-based)
	            9  //last column  (0-based)
	    ));
	    
	    //save with the default palette
	    FileOutputStream out = new FileOutputStream("d:/default_paletteXssf.xlsx");
	    wb.write(out);
	    out.close();

	    //now, let's replace RED and LIME in the palette
	    // with a more attractive combination
	    // (lovingly borrowed from freebsd.org)
 
	                    
	}

}
