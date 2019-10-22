package com.kendy.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 自定义Excel单元格样式
 * 
 * @author 林泽涛
 * @time 2018年7月1日 下午3:42:15
 */
public class ExcelCss {
	
	private static short blackCode= HSSFColor.HSSFColorPredefined.BLACK.getIndex();

	
	  /* 
     * 列头单元格样式
     */    
      public static CellStyle getColumnTopStyle(Workbook workbook) {
          
            // 设置字体
          Font font = workbook.createFont();
          //设置字体大小
          font.setFontHeightInPoints((short)11);
          //字体加粗
          font.setBold(true);
          //设置样式; 
          CellStyle style = workbook.createCellStyle();
          //设置底边框; 
          style.setBorderBottom(BorderStyle.THIN);
          //设置底边框颜色;  
          style.setBottomBorderColor(blackCode);
          //设置左边框;   
          style.setBorderLeft(BorderStyle.THIN);
          //设置左边框颜色; 
          style.setLeftBorderColor(blackCode);
          //设置右边框; 
          style.setBorderRight(BorderStyle.THIN);
          //设置右边框颜色; 
          style.setRightBorderColor(blackCode);
          //设置顶边框; 
          style.setBorderTop(BorderStyle.THIN);
          //设置顶边框颜色;  
          style.setTopBorderColor(blackCode);
          //在样式用应用设置的字体;  
          style.setFont(font);
          //设置自动换行; 
          style.setWrapText(false);
          //设置水平对齐的样式为居中对齐;  
          style.setAlignment(HorizontalAlignment.CENTER);
          //设置垂直对齐的样式为居中对齐; 
          style.setVerticalAlignment(VerticalAlignment.CENTER);
          
          return style;
          
      }
      
      /*  
     * 列数据信息单元格样式
     */  
      public static CellStyle getStyle(Workbook workbook) {
            // 设置字体
            Font font = workbook.createFont();
            //设置样式; 
            CellStyle style = workbook.createCellStyle();
            //设置底边框; 
            style.setBorderBottom(BorderStyle.THIN);
            //设置底边框颜色;  
            style.setBottomBorderColor(blackCode);
            //设置左边框;   
            style.setBorderLeft(BorderStyle.THIN);
            //设置左边框颜色; 
            style.setLeftBorderColor(blackCode);
            //设置右边框; 
            style.setBorderRight(BorderStyle.THIN);
            //设置右边框颜色; 
            style.setRightBorderColor(blackCode);
            //设置顶边框; 
            style.setBorderTop(BorderStyle.THIN);
            //设置顶边框颜色;  
            style.setTopBorderColor(blackCode);
            //在样式用应用设置的字体;  
            style.setFont(font);
            //设置自动换行; 
            style.setWrapText(false);
            //设置水平对齐的样式为居中对齐;  
            style.setAlignment(HorizontalAlignment.CENTER);
            //设置垂直对齐的样式为居中对齐; 
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            return style;
      }
}
