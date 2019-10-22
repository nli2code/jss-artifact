package com.water.bocai.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by XIONG on 2015/9/6.
 */
public class ExcelStyle {

    /*==============================Style===============================*/

    /**
     * 12号宋体，默认居中
     *
     * @param wb
     * @return
     */
    public static CellStyle getAlignCenterStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平
        return cellStyle;
    }


    /**
     * 默认字体样式，左对齐
     *
     * @param wb
     * @return
     */
    public static CellStyle getAlignLeftStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);//左对齐
        return cellStyle;
    }
    /*=============================Font==================================*/

    /**
     * 默认字体样式12号宋体
     *
     * @param wb
     * @return
     */
    public static Font getDefaultFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//12号字体
        font.setFontName("微软雅黑");//宋体字
        return font;
    }

    /**
     * 默认字体样式12号宋体_粗体
     *
     * @param wb
     * @return
     */
    public static Font getDefBoldFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//12号字体
        font.setFontName("微软雅黑");//宋体字
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;
    }

    /**
     * 默认字体样式12号宋体_绿色
     *
     * @param wb
     * @return
     */
    public static Font getDefGreenFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//12号字体
        font.setFontName("微软雅黑");//宋体字
        font.setColor(HSSFColor.GREEN.index);
        return font;
    }

    /**
     * 默认字体样式12号宋体_红色
     *
     * @param wb
     * @return
     */
    public static Font getDefRedFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);//12号字体
        font.setFontName("微软雅黑");//宋体字
        font.setColor(HSSFColor.RED.index);
        return font;
    }
}
