package com.ibaixiong.manage.web.util.export;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	 public static HSSFWorkbook getHSSFWorkbook(String sheetName,String[] title,String[][] values, HSSFWorkbook wb){
         // ��һ��������һ��webbook����Ӧһ��Excel�ļ�  
        if(wb == null){
            wb = new HSSFWorkbook();
        }
        // �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet  
        HSSFSheet sheet = wb.createSheet(sheetName);  
        // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short  
        HSSFRow row = sheet.createRow(0);  
        // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
        
        HSSFCell cell = null;  
        //��������
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);  
            cell.setCellValue(title[i]);  
            cell.setCellStyle(style);  
        }
        //��������
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1); 
            for(int j=0;j<values[i].length;j++){
                 row.createCell(j).setCellValue(values[i][j]);
            }
        }
        
       return wb;
    }
}
