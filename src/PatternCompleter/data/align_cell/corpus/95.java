package com.xy.test;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

public class PoiTest {
	
	@Test
	public void run() throws Exception{
		//1.创建工作薄
		Workbook wb = new HSSFWorkbook();
		
		//2.创建表空间
		Sheet sheet = wb.createSheet("笔法");
		//3.创建行
		Row row = sheet.createRow(4);
		
		//4.创建列
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 9));
		Cell cell = row.createCell(4);
		
		//5.填充数据
		cell.setCellValue("hello");
		//cell.setCellStyle(this.bigTitle(wb));
		//6.设置样式
		CellStyle style = wb.createCellStyle();
		//字体对象
		Font font = wb.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short)48);
		font.setFontName("华文行楷");
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		cell.setCellStyle(style);
		//7.保存，io流
		OutputStream os =  new FileOutputStream("F://testPoi.xls");
		wb.write(os);
		
		//8.关闭流
		os.close();
		wb.cloneSheet(0);
		wb.close();
	}
	//大标题的样式
		public CellStyle bigTitle(Workbook wb){
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)48);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗
			
			style.setFont(font);
			
			style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
			
			return style;
		}
}
