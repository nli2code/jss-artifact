package com.crland.scm.core.excel.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class DefaultExcelStyle implements IExcelStyle {


    @Override
    public HSSFCellStyle designTitle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        //设置居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 背景色
        cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
        //粗体
        HSSFFont hssfFont = wb.createFont();
        hssfFont.setBold(true);
        hssfFont.setFontHeightInPoints((short) 14);
        cellStyle.setFont(hssfFont);
        //设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    @Override
    public HSSFCellStyle designHeader(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        //设置居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //粗体
        HSSFFont hssfFont = wb.createFont();
        hssfFont.setBold(true);
        cellStyle.setFont(hssfFont);
        //设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }

    @Override
    public HSSFCellStyle designData(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        //垂直居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        return cellStyle;
    }
}
