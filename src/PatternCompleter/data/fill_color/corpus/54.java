package br.com.sysloccOficial.Excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.stereotype.Component;

@Component
public class ExcelCelulaBackground {
	
	public static XSSFCellStyle background(XSSFCellStyle estilo, int[] corFundo){
		estilo.setFillPattern(CellStyle.SOLID_FOREGROUND);
		estilo.setFillForegroundColor(new XSSFColor(new java.awt.Color(corFundo[0],corFundo[1],corFundo[2])));
		return estilo;
	}
}
