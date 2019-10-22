package com.sample.parsers.excel.utils;

import com.sample.parsers.excel.core.BaseExcel;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

public class CellStyleUtils {

    public static CellStyle getCellSHeaderStyle(BaseExcel baseExcel) {
        CellStyle style = baseExcel.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setWrapText(true);

        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        return style;
    }
    public static CellStyle getStandardCellStyle(BaseExcel baseExcel) {

        CellStyle style = baseExcel.createCellStyle();

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }
    public static CellStyle getHyperLinkCellStyle(BaseExcel baseExcel) {

        CellStyle style = baseExcel.createCellStyle();

        Font font = baseExcel.createFont();
        font.setColor(HSSFColor.LIGHT_BLUE.index);
        style.setFont(font);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }
}
