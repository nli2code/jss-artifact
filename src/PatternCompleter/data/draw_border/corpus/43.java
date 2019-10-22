/*
 * Max Value
 */
package com.allexess.dbtoexcel.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Alejandro López Arteaga
 */
public class Styles {
    
    private XSSFWorkbook workbook;
    private XSSFCellStyle basic;
    private XSSFCellStyle header;
    private XSSFCellStyle body;

    public Styles(XSSFWorkbook workbook) {
        this.workbook = workbook;
        createBasic();
        createHeader();
        createBody();
    }

    private void createBasic() {
        basic = workbook.createCellStyle();
        basic.setBorderTop(BorderStyle.THIN);
        basic.setBorderBottom(BorderStyle.THIN);
        basic.setBorderLeft(BorderStyle.THIN);
        basic.setBorderRight(BorderStyle.THIN);
    }
    
    private void createHeader() {
        header = workbook.createCellStyle();
        header.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        header.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        
        header.setBorderTop(BorderStyle.THIN);
        header.setBorderBottom(BorderStyle.THIN);
        header.setBorderLeft(BorderStyle.THIN);
        header.setBorderRight(BorderStyle.THIN);
        
        XSSFFont font = workbook.createFont();
        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
        font.setFontHeightInPoints((short) 12);
        font.setColor(XSSFFont.DEFAULT_FONT_COLOR);
        font.setBold(true);
        header.setFont(font);
    }
    
    private void createBody() {
        body = workbook.createCellStyle();
        body.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        body.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        
        body.setBorderTop(BorderStyle.THIN);
        body.setBorderBottom(BorderStyle.THIN);
        body.setBorderLeft(BorderStyle.THIN);
        body.setBorderRight(BorderStyle.THIN);
        
        XSSFFont font = workbook.createFont();
        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
        font.setFontHeightInPoints((short) 10);
        font.setColor(XSSFFont.DEFAULT_FONT_COLOR);
        body.setFont(font);
    }

    public XSSFCellStyle getBasic() {
        return basic;
    }

    public XSSFCellStyle getHeader() {
        return header;
    }

    public XSSFCellStyle getBody() {
        return body;
    }
    
}
