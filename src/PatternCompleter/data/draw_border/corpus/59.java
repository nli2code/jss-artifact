package com.jason.trade.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SetStyleUtils {
    /**
     * 无背景颜色普通字体
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle setStyleNoColor(XSSFWorkbook workbook) {
        XSSFCellStyle styleNoColor = workbook.createCellStyle();
        styleNoColor.setAlignment(HorizontalAlignment.CENTER);
        styleNoColor.setVerticalAlignment(VerticalAlignment.CENTER);
        styleNoColor.setBorderBottom(BorderStyle.THIN);
        styleNoColor.setBorderLeft(BorderStyle.THIN);
        styleNoColor.setBorderRight(BorderStyle.THIN);
        styleNoColor.setBorderTop(BorderStyle.THIN);
        styleNoColor.setWrapText(true);
        return styleNoColor;
    }

    /**
     * 背景颜色，普通字体
     *
     * @param workbook
     * @param colorIndex
     * @return
     */
    public static XSSFCellStyle setStyle(XSSFWorkbook workbook,short colorIndex,boolean bold) {
        XSSFCellStyle styleLightYellow = workbook.createCellStyle();
        styleLightYellow.setAlignment(HorizontalAlignment.CENTER);
        styleLightYellow.setVerticalAlignment(VerticalAlignment.CENTER);
        styleLightYellow.setBorderBottom(BorderStyle.THIN);
        styleLightYellow.setBorderLeft(BorderStyle.THIN);
        styleLightYellow.setBorderRight(BorderStyle.THIN);
        styleLightYellow.setBorderTop(BorderStyle.THIN);
        //IndexedColors.LIGHT_YELLOW.getIndex()
        styleLightYellow.setFillForegroundColor(colorIndex);
        styleLightYellow.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont fontStrong = workbook.createFont();
        fontStrong.setBold(bold);
        styleLightYellow.setFont(fontStrong);
        styleLightYellow.setWrapText(true);
        return styleLightYellow;
    }

}
