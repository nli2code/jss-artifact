/*
 * Copyright (c) 2016. com.biqasoft
 */

package com.biqasoft.exporter.excel.common.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/16/2016.
 * All Rights Reserved
 */
@Service
public class ExcelHelperService {

    private void fillWhiteTextCell(XSSFWorkbook workbook, XSSFCellStyle xssfCellStyle) {
        XSSFFont xssfFont = workbook.createFont();
        xssfFont.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
        xssfCellStyle.setFont(xssfFont);
    }

    public void fillCellWithErrorColor(Cell cell, XSSFWorkbook workbook) {
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(244, 67, 54)));
        xssfCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        fillWhiteTextCell(workbook, xssfCellStyle);

        cell.setCellStyle(xssfCellStyle);
    }

    public void fillCellWithGreyColor(Cell cell, XSSFWorkbook workbook) {
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(new XSSFColor(Color.decode("#808080")));
        xssfCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        fillWhiteTextCell(workbook, xssfCellStyle);

        cell.setCellStyle(xssfCellStyle);
    }

    public void fillCellWithCustomColor(String color, Cell cell,  XSSFWorkbook workbook) {
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(new XSSFColor(Color.decode(color)));
        xssfCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        fillWhiteTextCell(workbook, xssfCellStyle);

        cell.setCellStyle(xssfCellStyle);
    }

    public void fillCellWithWarningColor(Cell cell, XSSFWorkbook workbook) {
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(211, 172, 46)));
        xssfCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        fillWhiteTextCell(workbook, xssfCellStyle);

        cell.setCellStyle(xssfCellStyle);
    }

    public void fillCellWithSuccessColor(Cell cell, XSSFWorkbook workbook) {
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(76, 175, 80)));
        xssfCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        fillWhiteTextCell(workbook, xssfCellStyle);

        cell.setCellStyle(xssfCellStyle);
    }

    public void fillCellWithHeaderColor(Cell cell, XSSFWorkbook workbook) {
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 150, 243)));
        xssfCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        XSSFFont xssfFont = workbook.createFont();
        xssfFont.setFontHeight(13);
        xssfFont.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
        xssfCellStyle.setFont(xssfFont);

        cell.setCellStyle(xssfCellStyle);
    }
}
