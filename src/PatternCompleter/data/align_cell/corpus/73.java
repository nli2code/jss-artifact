package poiDemo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 * 
 * @Action 9单元格样式
 * @author 周孟禹
 * @date 2017年6月17日
 */
public class POIDemo08 {

	public static void main(String[] args) throws Exception {
		//创建Excel工作簿
		Workbook wk=new HSSFWorkbook();
		//创建名为第一个的sheet
		Sheet sheet = wk.createSheet("第一个");
		//创建行   从0开始
		Row row = sheet.createRow(0);
		//行高
		row.setHeightInPoints(30);  //居中                                           下对齐
		createStyle(wk, row, 0, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_BOTTOM);
									//水平   默认                                         左
		createStyle(wk, row, 1, HSSFCellStyle.ALIGN_FILL, HSSFCellStyle.ALIGN_LEFT);
		FileOutputStream fos=new FileOutputStream("C://Users//周孟禹//Desktop//poi.xls");
		wk.write(fos);
		fos.close();
	}
	
	/*
	 * 设置单元格样式定义成静态方法
	 * 
	 */
	public static void createStyle(Workbook wk,Row row,int column,short shuiping,short chuizhi){
		Cell cell = row.createCell(column);//创建单元格
		cell.setCellValue("hahaha");
		CellStyle cellStyle = wk.createCellStyle();
		cellStyle.setAlignment(shuiping);//水平
		cellStyle.setVerticalAlignment(chuizhi);//垂直
		cell.setCellStyle(cellStyle);
		
	}
}
