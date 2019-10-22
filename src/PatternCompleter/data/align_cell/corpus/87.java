package com.javapractice.poi;

import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class CreateExcelPractice {

	public static void main(String[] args) {
		try {
			UUID uuid = UUID.randomUUID();
			String filePath = "/home/shiliu/Test/poiPractice/excel" + "test" + ".xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("云主机列表信息");
			
			HSSFCellStyle style = workbook.createCellStyle();
			/*style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中*/
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.TOP);
			/*style.setWrapText(true);//自动换行
			style.setIndention((short)5);//缩进
			style.setRotation((short)60);//文本旋转，这里的取值是从-90到90，而不是0-180度。
*/			
			// 创建标题行
			int rowI = 1;
			int cellI = 1;
			HSSFRow row = sheet.createRow(1);
			
			HSSFCell cell=row.createCell(cellI++);
			String aa = "1111111111111111111111111111111111111";
			cell.setCellValue(aa);
			/*cell.setCellValue(aa);
			cell.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));*/
			//sheet.setColumnWidth(m, “列名”.getBytes().length*2*256);
			
			System.out.println(aa.getBytes().length * 256);
			sheet.setColumnWidth(cellI-1, aa.getBytes().length * 260);
			
			row.createCell(cellI++).setCellValue("主机名称");
			row.createCell(cellI++).setCellValue("状态");
			row.createCell(cellI++).setCellValue("可用区");
			row.createCell(cellI++).setCellValue("内网IP");
			row.createCell(cellI++).setCellValue("配置");
			row.createCell(cellI++).setCellValue("创建时间");
			row.createCell(cellI++).setCellValue("可用区");
			
			

			// 将excel 写入到磁盘
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			System.out.print(e);
		}
	}

}
