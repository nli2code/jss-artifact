package com.boco.generator.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @author sunyu
 */
public class ExcelExportFontStyle {
    private static final String FONT_NAME = "宋体";

    /**
     * create excel header font
     * @param workbook HSSFWorkbook
     * @return HSSFFont
     */
    public static HSSFFont getHeaderFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName(FONT_NAME);
        font.setBoldweight((short) 100);
        font.setFontHeight((short) 250);
        // font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        return font;
    }

    /**
     * create excel title font
     * @param workbook HSSFWorkbook
     * @return HSSFFont
     */
    public static HSSFFont getTitleFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName(FONT_NAME);
        // font.setBoldweight((short) 100);
        // font.setFontHeight((short) 250);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        return font;
    }
}
