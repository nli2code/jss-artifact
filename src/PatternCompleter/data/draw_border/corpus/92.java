package com.rtm.compras.resource;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;


public class Sesion {

	public static String formateaFechaVista(Date fecha) {
		Date fechaIngreso = fecha;
		SimpleDateFormat formateaFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fechaModificada = formateaFecha.format(fechaIngreso);
		return fechaModificada;
	}
	
	public static void formateaExcel(Object document){
		System.out.println("formateaExcel");
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		
		HSSFCellStyle cellStyleH = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyleH.setBorderBottom((short) 1);
		cellStyleH.setBorderLeft((short) 1);
		cellStyleH.setBorderRight((short) 1);
		cellStyleH.setBorderTop((short) 1);
		cellStyleH.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
        cellStyleH.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleH.setFont(font);
        
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
        	sheet.autoSizeColumn(i, true);
        	header.getCell(i).setCellStyle(cellStyleH);
        }
        
        int filas = sheet.getPhysicalNumberOfRows();
        int columnas = header.getPhysicalNumberOfCells();
        
        for(int i=1; i <  filas;i++) {
        	HSSFRow fila = sheet.getRow(i);
        	 for(int j=0; j < columnas;j++) {
             	fila.getCell(j).setCellStyle(cellStyle);
             }
        }
       
	}

}
