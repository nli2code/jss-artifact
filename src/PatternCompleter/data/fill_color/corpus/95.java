/*
 * Created on 08-oct-2009
 *
 */
package cl.cencosud.informes;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @author jdroguett
 *  
 */
public class Estilos {
   
   private static HSSFCellStyle celda(HSSFWorkbook objWB, HSSFFont fuente, short alineacion) {
      HSSFCellStyle estiloCelda = objWB.createCellStyle();
      estiloCelda.setWrapText(true);
      estiloCelda.setAlignment(alineacion);
      estiloCelda.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      estiloCelda.setFont(fuente);
      estiloCelda.setBorderBottom((short) 1);
      estiloCelda.setBorderLeft((short) 1);
      estiloCelda.setBorderRight((short) 1);
      estiloCelda.setBorderTop((short) 1);
      return estiloCelda;
   }

   /**
    * @return
    */
   private static HSSFFont fuente(HSSFWorkbook objWB, short bold, short sizeFont, short color) {
      HSSFFont fuente = objWB.createFont();
      fuente.setColor(color);
      fuente.setFontHeightInPoints(sizeFont);
      fuente.setFontName(HSSFFont.FONT_ARIAL);
      fuente.setBoldweight(bold);
      return fuente;
   }
   
   public static HSSFCellStyle pequegna(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_NORMAL, (short) 8, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_CENTER);
      estiloCelda.setFillForegroundColor(HSSFColor.WHITE.index);
      estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      return estiloCelda;
   }
   public static HSSFCellStyle pequegnaIzq(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_NORMAL, (short) 8, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_LEFT);
      estiloCelda.setFillForegroundColor(HSSFColor.WHITE.index);
      estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      estiloCelda.setWrapText(false);
      return estiloCelda;
   }


   public static HSSFCellStyle titulo(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_BOLD, (short) 9, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_CENTER);
      estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
      estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      return estiloCelda;
   }

   public static HSSFCellStyle numero(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_NORMAL, (short) 9, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_RIGHT);
      return estiloCelda;
   }

   public static HSSFCellStyle porcentaje(HSSFWorkbook objWB, String format) {
      short pctFormat = HSSFDataFormat.getBuiltinFormat(format);
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_NORMAL, (short) 9, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_RIGHT);
      estiloCelda.setDataFormat(pctFormat);
      return estiloCelda;
   }

   public static HSSFCellStyle numeroBold(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_BOLD, (short) 11, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_RIGHT);
      estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
      estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      return estiloCelda;
   }
   
   public static HSSFCellStyle porcentajeBold(HSSFWorkbook objWB, String format) {
      short pctFormat = HSSFDataFormat.getBuiltinFormat(format);
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_BOLD, (short) 11, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_RIGHT);
      estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
      estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      estiloCelda.setDataFormat(pctFormat);
      return estiloCelda;
   }

   public static HSSFCellStyle texto(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_NORMAL, (short) 9, (short) 8);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_LEFT);
      return estiloCelda;
   }
   
   public static HSSFCellStyle numeroDestacado(HSSFWorkbook objWB) {
      HSSFFont fuente = fuente(objWB, HSSFFont.BOLDWEIGHT_BOLD, (short) 9, HSSFColor.WHITE.index);
      HSSFCellStyle estiloCelda = celda(objWB, fuente, HSSFCellStyle.ALIGN_RIGHT);
      estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
      estiloCelda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      return estiloCelda;
   }
}
