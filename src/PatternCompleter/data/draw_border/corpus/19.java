 
package com.xjh.support.excel.wirte;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class GlobalStyle {
        /**
         * 超链接样式
         * @return HSSFCellStyle
         */
        public static CellStyle linkStyle(Workbook work) {
            HSSFCellStyle linkStyle = (HSSFCellStyle) work.createCellStyle();
            linkStyle.setBorderBottom(Short.valueOf("1"));
            linkStyle.setBorderLeft(Short.valueOf("1"));
            linkStyle.setBorderRight(Short.valueOf("1"));
            linkStyle.setBorderTop(Short.valueOf("1"));
            linkStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            linkStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            Font font = work.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setUnderline((byte)1);
            font.setColor(HSSFColor.BLUE.index);
            linkStyle.setFont(font);
            return linkStyle;
        }
        
        /**s
         * 单元格样式
         * @return Workbook
         */
        public static CellStyle nameStyle(Workbook work) {
            HSSFCellStyle nameStyle = (HSSFCellStyle) work.createCellStyle();
            nameStyle.setBorderBottom(Short.valueOf("1"));
            nameStyle.setBorderLeft(Short.valueOf("1"));
            nameStyle.setBorderRight(Short.valueOf("1"));
            nameStyle.setBorderTop(Short.valueOf("1"));
            nameStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
            nameStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            return nameStyle;
        }
        
        /**
         * 时间样式
         * @return HSSFCellStyle
         */
        public static CellStyle dataStyle(Workbook work) {
            HSSFCellStyle dataStyle = (HSSFCellStyle) work.createCellStyle();
            dataStyle.setBorderBottom(Short.valueOf("1"));
            dataStyle.setBorderLeft(Short.valueOf("1"));
            dataStyle.setBorderRight(Short.valueOf("1"));
            dataStyle.setBorderTop(Short.valueOf("1"));
            dataStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
            dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            return dataStyle;
        }
        
      

}

