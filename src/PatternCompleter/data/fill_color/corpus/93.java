package docgen;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.*;

public class Sample3{
  public static void main(String[] args){
    Workbook wb = new HSSFWorkbook();
    Sheet sheet = wb.createSheet();
    sheet.setColumnWidth(0, 4096);
    sheet.setColumnWidth(1, 4096);
    sheet.setColumnWidth(2, 4096);

    Row row1 = sheet.createRow(1);
    row1.setHeightInPoints(70);

    Cell cell1_0 = row1.createCell(0);
    Cell cell1_1 = row1.createCell(1);
    Cell cell1_2 = row1.createCell(2);

    cell1_0.setCellValue("THIN_VERT_BANDS");
    cell1_1.setCellValue("BIG_SPOTS");
    cell1_2.setCellValue("THICK_HORZ_BANDS");

    CellStyle style1 = wb.createCellStyle();
    style1.setFillPattern(CellStyle.THIN_VERT_BANDS);
    style1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
    style1.setFillBackgroundColor(IndexedColors.BLUE.getIndex());

    CellStyle style2 = wb.createCellStyle();
    style2.setFillPattern(CellStyle.BIG_SPOTS);
    style2.setFillForegroundColor(IndexedColors.RED.getIndex());
    style2.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

    CellStyle style3 = wb.createCellStyle();
    style3.setFillPattern(CellStyle.THICK_HORZ_BANDS);
    style3.setFillForegroundColor(IndexedColors.PINK.getIndex());
    style3.setFillBackgroundColor(IndexedColors.BROWN.getIndex());

    cell1_0.setCellStyle(style1);
    cell1_1.setCellStyle(style2);
    cell1_2.setCellStyle(style3);

    FileOutputStream out = null;
    try{
      out = new FileOutputStream("sample3_1.xls");
      wb.write(out);
    }catch(IOException e){
      System.out.println(e.toString());
    }finally{
      try {
        out.close();
      }catch(IOException e){
        System.out.println(e.toString());
      }
    }
  }
}