package jarTool.poiExcel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/** 
 * @Description: 描述 
 * @date 2017年1月17日  上午11:05:37
 */
public class Test10 {
	
	//(6)背景和纹理
	public static void main(String[] args) throws IOException
	{
		//String filePath="d:\\users\\lizw\\桌面\\POI\\sample.xls";//文件路径
		String filePath= "D:\\Users\\Desktop\\sample10.xls";
		HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
//		HSSFSheet sheet = workbook.createSheet();//创建工作表(Sheet)
//		sheet = workbook.createSheet("Test");//创建工作表(Sheet)
		
		HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)
		HSSFRow row=sheet.createRow(0);
		
		HSSFCell cell=row.createCell(0);
		
		
		//第一个
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREEN.index);//设置图案颜色
		style.setFillBackgroundColor(HSSFColor.RED.index);//设置图案背景色
		style.setFillPattern(HSSFCellStyle.SQUARES);//设置图案样式
		cell.setCellStyle(style);
		
		//第二个
		HSSFCell cell2=row.createCell(2);
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.GREEN.index);//设置图案颜色
		//style.setFillBackgroundColor(HSSFColor.RED.index);//设置图案背景色
		//style.setFillPattern(HSSFCellStyle.SQUARES);//设置图案样式
		cell2.setCellStyle(style2);
		
		//第三个
		HSSFCell cell3=row.createCell(3);
		HSSFCellStyle style3 = workbook.createCellStyle();
		//style3.setFillForegroundColor(HSSFColor.GREEN.index);//设置图案颜色
		style.setFillBackgroundColor(HSSFColor.RED.index);//设置图案背景色
		//style.setFillPattern(HSSFCellStyle.SQUARES);//设置图案样式
		cell3.setCellStyle(style3);

		//第四个
		HSSFCell cell4=row.createCell(4);
		HSSFCellStyle style4 = workbook.createCellStyle();
		//style.setFillForegroundColor(HSSFColor.GREEN.index);//设置图案颜色
		//style.setFillBackgroundColor(HSSFColor.RED.index);//设置图案背景色
		style4.setFillPattern(HSSFCellStyle.FINE_DOTS);//设置图案样式
		cell4.setCellStyle(style4);
		
		
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);//保存Excel文件
		out.close();//关闭文件流
		System.out.println("OK!");
	}
}
