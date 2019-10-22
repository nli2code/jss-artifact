/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opn.greenwebs;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Demonstrates how to create hyperlinks.
 */
public class HyperlinkExample {


    public static void main(String[]args) throws Exception{
        Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();

        //cell style for hyperlinks
        //by default hyperlinks are blue and underlined
        CellStyle hlink_style = wb.createCellStyle();
        Font hlink_font = wb.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);

        Cell cell;
        Sheet sheet = wb.createSheet("Hyperlinks");
        //URL
        cell = sheet.createRow(0).createCell((short)0);
        cell.setCellValue("URL Link");

        Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress("http://poi.apache.org/");
        cell.setHyperlink(link);
        cell.setCellStyle(hlink_style);

        //link to a file in the current directory
        cell = sheet.createRow(1).createCell((short)0);
        cell.setCellValue("File Link");
        link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
        link.setAddress("link1.xls");
        cell.setHyperlink(link);
        cell.setCellStyle(hlink_style);

        //e-mail link
        cell = sheet.createRow(2).createCell((short)0);
        cell.setCellValue("Email Link");
        link = createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
        //note, if subject contains white spaces, make sure they are url-encoded
        link.setAddress("mailto:poi@apache.org?subject=Hyperlinks");
        cell.setHyperlink(link);
        cell.setCellStyle(hlink_style);

        //link to a place in this workbook

        //create a target sheet and cell
        Sheet sheet2 = wb.createSheet("Target Sheet");
        sheet2.createRow(0).createCell((short)0).setCellValue("Target Cell");

        cell = sheet.createRow(3).createCell((short)0);
        cell.setCellValue("Worksheet Link");
        Hyperlink link2 = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
        link2.setAddress("'Target Sheet'!A1");
        cell.setHyperlink(link2);
        cell.setCellStyle(hlink_style);

        FileOutputStream out = new FileOutputStream("hyperinks.xlsx");
        wb.write(out);
        out.close();

    }
}