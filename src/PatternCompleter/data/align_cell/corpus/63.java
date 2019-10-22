package com.offcn.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Poi01 {
       public static void main(String[] args) throws IOException {
		  //д�ļ� 03��exc   07 ʹ��XssfWorkBook      �ļ������׺  xlsx
    	   XSSFWorkbook workBook=new XSSFWorkbook();//�ĵ�
    	   XSSFSheet sheet=workBook.createSheet();//sheep
    	   
    	   CellRangeAddress cra=new CellRangeAddress(4,7,8,10);//�ϲ��ĵ�Ԫ��
    	   sheet.addMergedRegion(cra);//���ϲ�����ӵ� sheet
    	   
    	   XSSFRow r=sheet.createRow(4);
    	   XSSFCell c=r.createCell(8);
    	   
    	   c.setCellValue("�ϲ�");
    	   
    	   //������Ԫ�� ��ʽ
    	   
    	   CellStyle cs=workBook.createCellStyle();
    	   cs.setAlignment(HorizontalAlignment.CENTER);//ˮƽ����
    	   cs.setVerticalAlignment(VerticalAlignment.CENTER);//��ֱ����
    	   
    	   c.setCellStyle(cs);
    	   
    	   
    	   
    	   
    	   workBook.write(new FileOutputStream(new File("D:\\Program Files\\poi\\test05.xlsx")));
    	   
    	   System.out.println(11111);
	}
}
