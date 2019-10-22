package br.com.sysloccOficial.ListaProducao.Excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeraCelulaFormula {
	
	
	private XSSFWorkbook novoExcel;


	public GeraCelulaFormula(XSSFWorkbook novoExcel){
		this.novoExcel = novoExcel;
		
	}
	
	
	public void celulaComFormula(XSSFCell cell,XSSFRow linha,String valor,int posicaoCelula,XSSFCellStyle corCelula,XSSFFont font){
			corCelula.setDataFormat(novoExcel.createDataFormat().getFormat("R$    #,##0.00"));
			corCelula.setAlignment(XSSFCellStyle.ALIGN_LEFT);
			corCelula.setFont(font);

			cell = linha.createCell(posicaoCelula);	
			cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
			cell.setCellStyle(corCelula);
			cell.setCellFormula(valor);
	}
	

}
