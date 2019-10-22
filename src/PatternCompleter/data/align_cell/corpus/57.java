package Controller;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/*
 * Classe qui d�finit le style des fichiers de groupes d'anglais.
 * @author Amine
 * @author Ayoub
 */
public class StyleGrpAng  extends Style{

	/**
	 * Constructeur
	 * @param workbook le workbook
	 */
	public StyleGrpAng(Workbook workbook){
		this.style = workbook.createCellStyle();
		this.style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		this.style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.style.setBorderBottom(BorderStyle.MEDIUM);
		this.style.setBorderTop(BorderStyle.MEDIUM);
		this.style.setBorderRight(BorderStyle.MEDIUM);
		this.style.setBorderLeft(BorderStyle.MEDIUM);
		this.style.setAlignment(HorizontalAlignment.CENTER);
		this.style.setVerticalAlignment(VerticalAlignment.JUSTIFY);
	}
	

}
