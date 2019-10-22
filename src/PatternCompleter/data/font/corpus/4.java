package com.ty.utilities;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by ty on 2017/9/18.
 */
public class ExcelUtil {

    /**
     * 生成标题栏的基本样式
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newHeadStyle(XSSFWorkbook workbook) {

        //自定义单元格
        XSSFCellStyle head = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("楷体");
        font.setFontHeightInPoints((short) 9);
//        font.setBold(true);
        head.setFont(font);
        head.setAlignment(HorizontalAlignment.CENTER);
        return head;
    }

    /**
     * 生成数据栏的基本样式
     *
     * @param workbook
     * @return
     */
    public static XSSFCellStyle newDataStyle(XSSFWorkbook workbook) {

        XSSFCellStyle body = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("楷体");
        font.setFontHeightInPoints((short) 8);
        body.setFont(font);
//        body.setBorderRight(BorderStyle.THIN);
        body.setAlignment(HorizontalAlignment.RIGHT);
        return body;

    }

}
