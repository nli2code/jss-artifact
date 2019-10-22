package org.apache.poi.hssf.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;

@SuppressWarnings({ "unused", "resource" })
public class demo {
	public static void main(String[] args) throws IOException {
	}

	//׷�ӵ�Ԫ����ʽ�ͳ�����
	private static void appendCellStyleAndHyperLink() throws IOException,
			FileNotFoundException {
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("E:\\eclipse-jee-kepler-SR2-win32\\WorkSpace\\poi_demo\\workbook.xls"));
		HSSFSheet sheet = wb.getSheet("new sheet");
		
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setUnderline(Font.U_SINGLE);
		font.setColor(IndexedColors.BLUE.getIndex());
		cellStyle.setFont(font);
		
		Hyperlink link0 = wb.getCreationHelper().createHyperlink(Hyperlink.LINK_DOCUMENT);
		link0.setAddress("'Sheet1'!A1");
		
		sheet.getRow(2).getCell(0).setCellStyle(cellStyle);
		sheet.getRow(2).getCell(0).setHyperlink(link0);
		
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	//������Ԫ��
	private static void createCell() throws FileNotFoundException, IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
	        HSSFSheet sheet = wb.createSheet("new sheet");
	        HSSFRow row = sheet.createRow(2);
	        row.createCell(0).setCellValue(1.1);
	        row.createCell(1).setCellValue(new Date());
	        row.createCell(2).setCellValue("a string");
	        row.createCell(3).setCellValue(true);
	        row.createCell(4).setCellType(HSSFCell.CELL_TYPE_ERROR);

	        // Write the output to a file
	        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	        wb.write(fileOut);
	        fileOut.close();
	}

	//����������
	private static void createHyperLink() throws FileNotFoundException,
			IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();

		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setUnderline(Font.U_SINGLE);
		font.setColor(IndexedColors.BLUE.getIndex());
		cellStyle.setFont(font);

		Sheet sheet0 = wb.createSheet("Ŀ¼����");
		Sheet sheet1 = wb.createSheet("�ӱ�1");
		Sheet sheet2 = wb.createSheet("�ӱ�2");

		Cell cell0 = sheet0.createRow(0).createCell(0);
		cell0.setCellValue("�ӱ�1");
		Hyperlink link0 = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
		link0.setAddress("'�ӱ�1'!A1");
		cell0.setHyperlink(link0);
		cell0.setCellStyle(cellStyle);

		Cell cell1 = sheet0.createRow(1).createCell(0);
		cell1.setCellValue("�ӱ�2");
		Hyperlink link1 = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
		link1.setAddress("'�ӱ�2'!A1");
		cell1.setHyperlink(link1);
		cell1.setCellStyle(cellStyle);

		FileOutputStream out = new FileOutputStream(
				"E:\\eclipse-jee-kepler-SR2-win32\\WorkSpace\\poi_demo\\workbook.xls");
		wb.write(out);
		out.close();
	}

}
