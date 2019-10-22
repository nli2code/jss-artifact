package br.com.sysloccOficial.Excel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Component;


@Component
public class ExcelAlinhamento {

	public static XSSFCellStyle horizontal(XSSFCellStyle estilo, short alinhamento){
		estilo.setAlignment(alinhamento);
		return estilo;
	}
}
