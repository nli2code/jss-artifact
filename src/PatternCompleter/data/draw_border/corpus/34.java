package org.zkoss.zarcillo;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author saisa
 */
public class EstiloCelda {

    public static CellStyle estiloCabecera(XSSFWorkbook book) {
        //////estilo
        Font headerFont = book.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setFontName("Abadi MT Condensed Light");


        CellStyle style = book.createCellStyle();
        style.setFont(headerFont);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        //estilo
        return style;
    }

    public static CellStyle estiloDetalle(XSSFWorkbook book) {
        //////estilo
        Font headerFont = book.createFont();
        headerFont.setFontName("Abadi MT Condensed Light");
        CellStyle style = book.createCellStyle();
        style.setFont(headerFont);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        //estilo
        return style;
    }
}
