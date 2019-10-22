package com.djw.web.common;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelExport extends BaseService{
	/*
	 * @author Mark Deng
	 * 将数据库中的数据导出成为excel工作表
	 */
	public HSSFWorkbook dataToExcle(String[] excelHeader){
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet("信息表");
		HSSFRow row=sheet.createRow(0);
		HSSFFont headerfont=wb.createFont();
		headerfont.setFontName("黑体");
		headerfont.setFontHeightInPoints((short) 16);//字体大小
		headerfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体
		
		HSSFCellStyle headerStyle=wb.createCellStyle();
		headerStyle.setFont(headerfont);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell=row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(headerStyle);
			sheet.autoSizeColumn(i);
		}
		
		return wb;
	}
}
