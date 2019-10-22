/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.Color;
import java.awt.Font;

 
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
 /**
 *
 * @author eleazar
 */
public class ReporteStyle {
    XSSFCellStyle colorGray(XSSFWorkbook wb){
		XSSFCellStyle style = wb.createCellStyle();
	        final XSSFFont Font = wb.createFont();
		XSSFColor color = new XSSFColor(new Color(220, 220, 220));
		Font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(Font);
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
	
	
	XSSFCellStyle colorLightBlue(XSSFWorkbook wb){
		XSSFCellStyle style = wb.createCellStyle();
		final XSSFFont Font = wb.createFont();
		XSSFColor color = new XSSFColor(new Color(3,127, 235));
		XSSFColor colorletra = new XSSFColor(new Color(255,255, 255));
		
		Font.setColor(colorletra);
		Font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(Font);
		
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
	XSSFCellStyle colorVerde(XSSFWorkbook wb){
		XSSFCellStyle style = wb.createCellStyle();
		final XSSFFont Font = wb.createFont();
		XSSFColor color = new XSSFColor(new Color(8,154,6));
		XSSFColor colorletra = new XSSFColor(new Color(255,255, 255));
		
		Font.setColor(colorletra);
		Font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(Font);
		
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
	XSSFCellStyle colorAmarillo(XSSFWorkbook wb){
		XSSFCellStyle style = wb.createCellStyle();
		final XSSFFont Font = wb.createFont();
		XSSFColor color = new XSSFColor(new Color(252,225, 49));
		XSSFColor colorletra = new XSSFColor(new Color(255,255, 255));
		
		Font.setColor(colorletra);
		Font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(Font);
		
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
	XSSFCellStyle colorRojo(XSSFWorkbook wb){
		XSSFCellStyle style = wb.createCellStyle();
		final XSSFFont Font = wb.createFont();
		XSSFColor color = new XSSFColor(new Color(200,4, 4));
		XSSFColor colorletra = new XSSFColor(new Color(255,255, 255));
		
		Font.setColor(colorletra);
		Font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(Font);
		
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		return style;
	}
}
