/**
 * 
 */
package com.sinosoft.antifake.helpers.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author chenshaoao
 *
 */
public class ExcelStyle {
	
	//默认表头字体
	private static Font getDefaultTitleFont(HSSFSheet worksheet) {
		Font titleFont = worksheet.getWorkbook().createFont();
		titleFont.setFontName("Arial"); 					// 字体
		titleFont.setFontHeightInPoints((short) 8); 		// 大小
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	// 加粗
		titleFont.setColor(HSSFColor.WHITE.index);			// 字体颜色 白色
		return titleFont;
	}
	
	//报表表头样式
	public static HSSFCellStyle getTitleStyle(HSSFSheet worksheet,short titleColor) {
		HSSFWorkbook workbook = worksheet.getWorkbook();
		// 创建表头样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 	// 上边框
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 	// 下边框
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 	// 左边框
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 	// 右边框
		titleStyle.setFillForegroundColor(titleColor); 				// 设置背景-前景色
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 	// 设置背景-背景色
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 		// 水平居中
//		titleStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER); 	// 垂直居中
//		headerCellStyle.setWrapText(true);							// 自动换行
		titleStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));	// 文字
		// 创建表头样式字体
		titleStyle.setFont(getDefaultTitleFont(worksheet));
		return titleStyle;
	}
}
