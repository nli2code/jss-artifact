package com.hhw.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class MyPoi
{
	public void test_poi1()
	{
		POIFSFileSystem fs;
		try
		{
			fs = new POIFSFileSystem(new FileInputStream("F:\\����\\1_Java����\\5_workspace\\hhw\\poi\\src\\com\\hhw\\poi\\hirecar_report.xls"));

			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			HSSFSheet sheet = wb.getSheetAt(0);

			CellStyle style = wb.createCellStyle();
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());

			for (int i = 3; i < 10; i++)
			{

				HSSFRow row = sheet.createRow(i);

				for (int j = 0; j < 14; j++)
				{
					HSSFCell cell = row.createCell(j);

					cell.setCellValue("a test");

					cell.setCellStyle(style);
				}

			}
			// Write the output to a file

			FileOutputStream fileOut = new FileOutputStream("workbook1.xls");
			
			wb.write(fileOut);

			fileOut.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
