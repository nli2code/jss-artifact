package org.hbhk.test.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelService {

	public static void main(String[] args) throws IOException {
		excel2003();
	}

	public static void excel2003() throws IOException {
		HSSFWorkbook workbook = null;
		File file = new File("C:/Users/jumbo/Desktop/repo/32.xls");
		FileInputStream is = new FileInputStream(file);
		workbook = new HSSFWorkbook(is);
		HSSFFont font = workbook.createFont();
		//font.setColor(HSSFColor.RED.index);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFPatriarch patr = sheet.createDrawingPatriarch();
		//for (int i = 0; i < 5; i++) {
			HSSFRow row = sheet.getRow(7);
			HSSFCell cell = row.getCell(0);
			cell.setCellStyle(cellStyle);
			cell.removeCellComment();
			// 定义注释的大小和位置
			HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0,
					0, (short) 4, 2, (short) 6, 5));
			comment.setString(new HSSFRichTextString("test"));
			// 设置注释内容
			cell.setCellComment(comment);
		//}

		FileOutputStream os = new FileOutputStream(new File(
				"C:/Users/jumbo/Desktop/repo/35.xls"));
		workbook.write(os);
	}

	public static void excel2007() throws IOException {
		XSSFWorkbook workbook = null;
		try {
			File file = new File("C:/Users/jumbo/Desktop/repo/6.xlsx");
			FileInputStream is = new FileInputStream(file);
			workbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.RED.index);
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(7);
		XSSFCell cell = row.getCell(0);
		cell.setCellStyle(cellStyle);
		cell.removeCellComment();
		setc(workbook, cell, sheet);
		FileOutputStream os = new FileOutputStream(new File(
				"C:/Users/jumbo/Desktop/repo/7.xlsx"));
		workbook.write(os);
	}

	public static void setc(XSSFWorkbook workbook, XSSFCell cell, Sheet sheet) {
		CreationHelper factory = workbook.getCreationHelper();
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = factory.createClientAnchor();
		Comment comment1 = drawing.createCellComment(anchor);
		RichTextString str1 = factory.createRichTextString("Hello, World!1");
		comment1.setString(str1);
		comment1.setAuthor("Apache POI");
		cell.setCellComment(comment1);

	}
	
	public static void bg() throws  IOException {  
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("C:/Users/jumbo/Desktop/repo/32.xls"));  
        HSSFSheet sheet = wb.getSheetAt(1);  
        for (int i = 0; i < 3; i++) {  
            for (int j = 0; j < 7; j++) {  
                HSSFRow row = sheet.getRow(j);  
                HSSFCell cell = row.getCell(i); //注意cell不能够没有值或颜色，否则抛空指针异常  
                int backgroundColor = cell.getCellStyle().getFillBackgroundColor();  
                int foregroundColor = cell.getCellStyle().getFillForegroundColor(); // 前景色起作用  
                int backgroundColor2 = (wb.getCellStyleAt((short) 1)).getFillBackgroundColor();//整张表背景色  
                System.out.println(backgroundColor2 + "," + backgroundColor + "," + foregroundColor);  
            }  
        }  
    }  
}
