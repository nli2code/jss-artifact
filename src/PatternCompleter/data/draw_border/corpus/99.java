package com.simple.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExcelUtil {
	/**
     * 设置单元格的边框（细）且为红色
     * @param workbook
     * @param cellnum
     * @return
     */
    public static HSSFCellStyle cellStyleTitle(HSSFWorkbook workbook){
    	 // 创建表头style
    	  HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
    	  HSSFFont fontStyle = workbook.createFont();   
    	  fontStyle.setFontName("黑体");   
    	  fontStyle.setFontHeightInPoints((short)16);   
    	  fontStyle.setBold(true);;//粗体显示 
    	  cellStyleTitle.setFont(fontStyle);
    	  cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);// //居中显示
    	  cellStyleTitle.setFillForegroundColor((short) 13);// 设置背景色    
    	  cellStyleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	  cellStyleTitle.setBorderBottom(BorderStyle.THIN);
    	  cellStyleTitle.setBorderTop(BorderStyle.THIN);
    	  cellStyleTitle.setBorderRight(BorderStyle.THIN);
    	  cellStyleTitle.setBorderLeft(BorderStyle.THIN);
    	  workbook.getSheetAt(0).setColumnWidth(0, 10*256);
    	  workbook.getSheetAt(0).setColumnWidth(1, 25*256);
    	  workbook.getSheetAt(0).setColumnWidth(2, 45*256);
          return cellStyleTitle;
    }
    
    /**
     * 设置单元格的边框（细）且为红色
     * @param workbook
     * @param cellnum
     * @return
     */
    public static HSSFCellStyle cellStyle(HSSFWorkbook workbook){
    	 // 创建表头style
    	  HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
    	  cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);// //居中显示
    	  cellStyleTitle.setBorderBottom(BorderStyle.THIN);
    	  cellStyleTitle.setBorderTop(BorderStyle.THIN);
    	  cellStyleTitle.setBorderRight(BorderStyle.THIN);
    	  cellStyleTitle.setBorderLeft(BorderStyle.THIN);
          return cellStyleTitle;
    }
}
