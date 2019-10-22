package com.ist.common.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {
	/**
	 * ����ͷ������ʽ
	 * @param wb
	 * @return
	 */
	public static CellStyle getHeaderCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//������ʾ
		//����������ʽ
		Font font = wb.createFont();
	    font.setFontHeightInPoints((short)12);
	    font.setFontName("Courier New");
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    cellStyle.setFont(font);
	    
	    //���õ�Ԫ��߿�
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //�±߿�
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//��߿�
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//�ϱ߿�
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//�ұ߿�
		
		return cellStyle;
	}
	
	/**
	 * ���õ�Ԫ�����ʽ(��ӱ߿�)
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getBorderCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
	    //���õ�Ԫ��߿�
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//�±߿�
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//��߿�
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//�ϱ߿�
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//�ұ߿�
	    
		return cellStyle;
	}
	
	
	/**
	 * ���ɱ���ı���
	 * @param row
	 * @param headers
	 */
	public static void generateTitleRow(HSSFRow row, String title, int size) {
		row.setHeightInPoints(24);
	    //�ϲ�������
		row.getSheet().addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)size));
	    Cell cell = row.createCell(0);
	    cell.setCellValue(title);
	    cell.setCellStyle(getTitleCellStyle(row.getSheet().getWorkbook()));
	    
	    CellStyle borderStyle = getBorderCellStyle(row.getSheet().getWorkbook());
	    //ѭ������������,��ӱ߿�
	    for (int i = 0; i < size; i++) {
	    	Cell celltemp = row.createCell(i + 1);
	    	celltemp.setCellStyle(borderStyle);
	    }
	}
	
	/**
	 * ���ñ������ʽ
	 * @param wb
	 * @return
	 */
	public static CellStyle getTitleCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//������ʾ
		
		//����������ʽ
		Font font = wb.createFont();
	    font.setFontHeightInPoints((short)14);
	    font.setFontName("Courier New");
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    cellStyle.setFont(font);
	    
	    //���õ�Ԫ��߿�
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //�±߿�
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//��߿�
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//�ϱ߿�
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//�ұ߿�
	    
		return cellStyle;
	}
}
