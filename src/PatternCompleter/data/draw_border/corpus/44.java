package com.lazy.cheetah.core.excel.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.awt.Color;
import java.util.Map;

/**
 * Created by laizhiyuan on 2017/6/5.
 */
public class DefaultSetStyle extends AbstractSetStyle {


    @Override
    public void setStyle(Workbook workbook, Map<String, CellStyle> styleMap) {
        // 生成一个样式
        XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
        /**
         * 标题样式
         */
        // 前景色(蓝色)
        XSSFColor myColor = new XSSFColor(Color.LIGHT_GRAY);
        style1.setFillForegroundColor(myColor);
        // 设置单元格填充样式
        style1.setFillPattern(FillPatternType.NO_FILL);
        // 设置单元格边框
        style1.setBorderBottom(BorderStyle.NONE);
        style1.setBorderLeft(BorderStyle.NONE);
        style1.setBorderRight(BorderStyle.NONE);
        style1.setBorderTop(BorderStyle.NONE);
        style1.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style1.setFont(font);
        styleMap.put(TITLE_STYLE_KEY, style1);

        /**
         * 内容样式
         */
        XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
        style2.setBorderBottom(BorderStyle.NONE);
        style2.setBorderLeft(BorderStyle.NONE);
        style2.setBorderRight(BorderStyle.NONE);
        style2.setBorderTop(BorderStyle.NONE);
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        XSSFFont font2 = (XSSFFont) workbook.createFont();
        // 把字体应用到当前的样式
        style2.setFont(font2);
        styleMap.put(CONTENT_STYLE_KEY, style2);
    }
}
