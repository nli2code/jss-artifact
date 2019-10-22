package com.flipkart.ads.report;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by rahul.sachan on 13/07/17.
 */
public class StyleSheet {
    public CellStyle getHeadingCss(Workbook xssfSheets) {
        CellStyle style = xssfSheets.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = xssfSheets.createFont();
        //font.setColor(IndexedColors.RED.getIndex());
        style.setFont(font);
        return style;
    }

    public CellStyle getDataCss(Workbook xssfSheets) {
        CellStyle style = xssfSheets.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = xssfSheets.createFont();
        style.setFont(font);
        return style;
    }

}
