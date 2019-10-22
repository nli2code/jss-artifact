

import com.oracle.xmlns.internal.webservices.jaxws_databinding.SoapBindingParameterStyle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hemp85
 */
public class NewClass {
    
    public static void main(String[] args) throws MalformedURLException, FileNotFoundException, IOException 
    {
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
    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link);
    cell.setCellStyle(hlink_style);

    //link to a file in the current directory
    cell = sheet.createRow(1).createCell((short)0);
    cell.setCellValue("File Link");
    link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
    link.setAddress("link1.xls");
    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link);
    cell.setCellStyle(hlink_style);

    //e-mail link
    cell = sheet.createRow(2).createCell((short)0);
    cell.setCellValue("Email Link");
    link = createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
    //note, if subject contains white spaces, make sure they are url-encoded
    link.setAddress("mailto:poi@apache.org?subject=Hyperlinks");
    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link);
    cell.setCellStyle(hlink_style);

    //link to a place in this workbook

    //create a target sheet and cell
    Sheet sheet2 = wb.createSheet("Target Sheet");
    sheet2.createRow(0).createCell((short)0).setCellValue("Target Cell");

    cell = sheet.createRow(3).createCell((short)0);
    cell.setCellValue("Worksheet Link");
    Hyperlink link2 = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
    link2.setAddress("'Target Sheet'!A1");
    cell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link2);
    cell.setCellStyle(hlink_style);

    FileOutputStream out = new FileOutputStream("hyperinks.xlsx");
    wb.write(out);
    out.close();
    }
}
     