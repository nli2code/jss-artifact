package com.sgrm_2014.os.relatorio;

import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Export_Relatorio_Filtrado {

	private List<ArrayList<Object>> listaRetorno;
	protected OutputStream os;

	public Export_Relatorio_Filtrado(List<ArrayList<Object>> listaRetorno) throws Exception {
		// TODO Auto-generated constructor stub

		this.listaRetorno = listaRetorno;
		os = new java.io.ByteArrayOutputStream();
	}

	public OutputStream createWorkbook() throws Exception{

		XSSFWorkbook w = new XSSFWorkbook();

		//CRIANDO UM STYLE DE DATA
		CreationHelper createHelper = w.getCreationHelper();

		XSSFCellStyle styleDt = w.createCellStyle();
		styleDt.setFillForegroundColor(new XSSFColor(new java.awt.Color(65, 65, 65)));
		//styleDt.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleDt.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		styleDt.setBorderBottom(BorderStyle.THIN);
		styleDt.setBorderLeft(BorderStyle.THIN);
		styleDt.setBorderRight(BorderStyle.THIN);
		styleDt.setBorderTop(BorderStyle.THIN);

		//CRIANDO UM STYLE
		XSSFCellStyle style1 = w.createCellStyle();
		style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(65, 65, 65)));
		style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style1.setBorderBottom(BorderStyle.THIN);
		style1.setBorderLeft(BorderStyle.THIN);
		style1.setBorderRight(BorderStyle.THIN);
		style1.setBorderTop(BorderStyle.THIN);

		//CRIANDO UM STYLE
		XSSFCellStyle style2 = w.createCellStyle();
		style2.setFillForegroundColor(new XSSFColor(new java.awt.Color(161, 158, 158)));
		style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);

		//CRIANDO UM STYLE
		XSSFCellStyle style3 = w.createCellStyle();
		style3.setBorderBottom(BorderStyle.THIN);
		style3.setBorderLeft(BorderStyle.THIN);
		style3.setBorderRight(BorderStyle.THIN);
		style3.setBorderTop(BorderStyle.THIN);

		//CRIANDO UM STYLE DINHEIRO
	    XSSFCellStyle decimalStyle = w.createCellStyle();
	    decimalStyle.setBorderBottom(BorderStyle.THIN);
	    decimalStyle.setBorderLeft(BorderStyle.THIN);
	    decimalStyle.setBorderRight(BorderStyle.THIN);
	    decimalStyle.setBorderTop(BorderStyle.THIN);
	    decimalStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.0#"));

		String sheetName = "Dados";
		w.createSheet(sheetName);
		XSSFSheet sheet = w.getSheet(sheetName);
		String range01 = "A1";
		String range02 = "A1";

		for (int i = 0; i < listaRetorno.size(); i++) {
			
			XSSFRow r = sheet.createRow(i);
			for (int j = 0; j < listaRetorno.get(i).size(); j++) {

				XSSFCell celula = r.createCell(j);
				//PARA CELULA DECIMAL
				if(listaRetorno.get(i).get(j).getClass().toString().equals("class java.math.BigDecimal")){
					celula.setCellValue(new Double(listaRetorno.get(i).get(j).toString()));
					celula.setCellStyle(decimalStyle);
				}
				//PARA CELULA DATA
				else if(listaRetorno.get(i).get(j).getClass().toString().equals("class java.sql.Date")){
					celula.setCellValue((Date)listaRetorno.get(i).get(j));
					celula.setCellStyle(styleDt);
				}
				//PARA CELULA STRING
				else{
					if(i == 0){
						celula.setCellValue(listaRetorno.get(i).get(j).toString());
						celula.setCellStyle(style1);
						range02 = celula.getReference();
					}
					else{
						celula.setCellValue(listaRetorno.get(i).get(j).toString());
						celula.setCellStyle(style3);
						sheet.autoSizeColumn(j);
					}
				}			
			}
		}
		
		sheet.setAutoFilter(CellRangeAddress.valueOf(range01+":"+range02));//PEGAR DINAMICAMENTE
		
		w.write(os);
		os.close();

		return os;
	}
}
