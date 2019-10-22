package com.vroozi.customerui.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XlsxWriter {

  public static void createXlsx(OutputStream out) throws IOException {
    Workbook wb = new XSSFWorkbook();

    CreationHelper factory = wb.getCreationHelper();

    Sheet sheet = wb.createSheet();

    Cell cell1 = sheet.createRow(3).createCell(5);
    cell1.setCellValue("F4");

    Drawing drawing = sheet.createDrawingPatriarch();

    ClientAnchor anchor = factory.createClientAnchor();

    Comment comment1 = drawing.createCellComment(anchor);
    RichTextString str1 = factory.createRichTextString("Hello, World!");
    comment1.setString(str1);
    comment1.setAuthor("Apache POI");
    cell1.setCellComment(comment1);

    Cell cell2 = sheet.createRow(2).createCell(2);
    cell2.setCellValue("C3");

    Comment comment2 = drawing.createCellComment(anchor);
    RichTextString str2 = factory.createRichTextString("XSSF can set cell comments");
    //apply custom font to the text in the comment
    Font font = wb.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short)14);
    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    font.setColor(IndexedColors.RED.getIndex());
    str2.applyFont(font);

    comment2.setString(str2);
    comment2.setAuthor("Apache POI");
    comment2.setColumn(2);
    comment2.setRow(2);

    String fname = "comments.xlsx";
    wb.write(out);
  }
}
