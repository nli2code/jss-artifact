package com.laobei.utils;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {

	
	public static HSSFWorkbook exportExcel(String sheetName, List<String> titleList, List<List<String>> contentList) {
        HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFCellStyle titleStyle = wb.createCellStyle();   
        HSSFCellStyle contentStyle = wb.createCellStyle();
        
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充单元格
        titleStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        titleStyle.setFont(font);
        
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        contentStyle.setWrapText(true);//单元格自动换行
        
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow titleRow = sheet.createRow(0);    
        titleRow.setHeight((short)500);
        for (int i = 0; i < titleList.size(); i++) {    
            HSSFCell cell = titleRow.createCell(i);    
            cell.setCellValue(titleList.get(i));    
            cell.setCellStyle(titleStyle);    
        }    
    
        for (int i = 0; i < contentList.size(); i++) {    
            Row row = sheet.createRow(i + 1);    
            List<String> cells = contentList.get(i);
            
            for (int j = 0; j < cells.size(); j++) {
            	Cell cell = row.createCell(j);
            	cell.setCellStyle(contentStyle);
            	cell.setCellValue(cells.get(j));
            	sheet.setColumnWidth(j, 20 * 256);
			}
        }    
        return wb;    
	}
	
}
