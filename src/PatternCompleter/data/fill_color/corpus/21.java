package com.victor.utilities.report.excel.generator;

import com.victor.utilities.report.excel.generator.common.TabWriterBase;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * base class for sheet generation template
 */
public abstract class TabWriterBaseForAppData extends TabWriterBase {

    protected XSSFCellStyle headerStyleTemplateFontCenter;

    public TabWriterBaseForAppData(XSSFWorkbook wb) {
        super(wb);
    }

    @Override
    protected void initOwnCellStyles() {
        headerStyle = getCloneCellStyle(null);
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(27, 145, 197)));
        XSSFFont headerFont = wb.createFont();
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setWrapText(true);

        subHeaderStyle = getCloneCellStyle(headerStyle);
        subHeaderStyle.setAlignment(CellStyle.ALIGN_LEFT);
        subHeaderStyle.setFont(boldFont);
        subHeaderStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(157, 222, 237)));

        subSubHeaderStyle = getCloneCellStyle(subHeaderStyle);
        subSubHeaderStyle.setAlignment(CellStyle.ALIGN_LEFT);
        subSubHeaderStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(150, 150, 150)));

        headerStyleTemplateFontCenter = getCloneCellStyle(subHeaderStyle);
        headerStyleTemplateFontCenter.setAlignment(CellStyle.ALIGN_CENTER);

        greenColor = new XSSFColor(new java.awt.Color(50, 196, 100));
        amberColor = new XSSFColor(new java.awt.Color(246, 197, 76));
        redColor = new XSSFColor(new java.awt.Color(243, 123, 83));

        greenStyle = getCloneCellStyle(null);
        greenStyle.setAlignment(CellStyle.ALIGN_CENTER);
        greenStyle.setFont(boldFont);
        greenStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        greenStyle.setFillForegroundColor(greenColor);
        amberStyle = getCloneCellStyle(greenStyle);
        amberStyle.setFillForegroundColor(amberColor);
        redStyle = getCloneCellStyle(greenStyle);
        redStyle.setFillForegroundColor(redColor);
    }

}