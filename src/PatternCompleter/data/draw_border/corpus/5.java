package com.linde;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by cn40580 at 2017-10-10 8:58 AM.
 */
public class ExcelHelper {

    public final static String column0 = "年月";
    public final static String column1 = "销售组织";
    public final static String column2 = "销售组";
    public final static String column3 = "目标";
    public final static String column4 = "总量";
    public final static String column5 = "参量";
    public final static String column6 = "备注";

    public static XSSFCellStyle getCityStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);

        return style2;
    }

    public static XSSFCellStyle getDistStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.NONE);
        style2.setBorderBottom(BorderStyle.NONE);
        style2.setBorderLeft(BorderStyle.NONE);
        style2.setBorderRight(BorderStyle.NONE);

        return style2;
    }

    public static XSSFCellStyle getRegnStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.THICK);
        style2.setBorderBottom(BorderStyle.THICK);

        return style2;
    }

    public static XSSFCellStyle getBranStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.THICK);
        style2.setBorderBottom(BorderStyle.THICK);

        return style2;
    }

    public static short getPercentageFormat() {
        return HSSFDataFormat.getBuiltinFormat("0.00%");
    }
}
