package Controller;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/*
 * Classe qui d�finit le style des cases des �tudiants en mobilit�.
 * @author Amine
 * @author Ayoub
 */

public class StyleMobilite extends Style{
	
	
	
	/**
	 * Constructeur
	 * @param workbook le workbook
	 */
	public StyleMobilite(Workbook workbook){
		this.style = workbook.createCellStyle();
		this.style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		this.style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.style.setBorderBottom(BorderStyle.MEDIUM);
		this.style.setBorderTop(BorderStyle.MEDIUM);
		this.style.setBorderRight(BorderStyle.MEDIUM);
		this.style.setBorderLeft(BorderStyle.MEDIUM);
	}
	
	
	
}
