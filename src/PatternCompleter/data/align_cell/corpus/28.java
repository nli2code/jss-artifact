package cn.tarena.film.tool;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;


public class StyleUtil {
	
	public static CellStyle borderThinAndCenter(CellStyle cellStyle){
		cellStyle.setAlignment(HorizontalAlignment.CENTER); //单元格左右居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//上下居中
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return cellStyle;
	}
}
