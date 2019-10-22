package org.net.plat4j.common.excelutil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * @author 刘正
 */
public class CellFontUtil {
	
	public static HSSFFont createTitleFont(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();       
		font.setFontHeightInPoints((short) 14); 		 // 字体大小
	   // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗   
		font.setFontName("宋体_GB2312");
		return font;
	}
	
	public static HSSFFont createContentFont(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();       
		font.setFontHeightInPoints((short) 14); 	
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("宋体_GB2312");
		return font;
	}	
	//导出评标条excel样式
	public static HSSFFont createContentFont2(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();       
		font.setFontHeightInPoints((short) 14); 	
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		return font;
	}	
	
	public static HSSFFont createLittleFont(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();       
		font.setFontHeightInPoints((short)14); 	
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("宋体_GB2312");
		return font;
	}	
}
