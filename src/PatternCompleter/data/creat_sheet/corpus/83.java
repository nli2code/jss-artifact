package model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.*;
import java.util.Date;

/**
 * Created by chenhui on 7/28/16.
 * This is testing class for POI functionalities
 */
public class Tester {
    public void buttonTest(){
        System.out.println("button clicked");
    }

    public static void createWb() throws IOException {
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("new sheet");
        Sheet sheet2 = wb.createSheet("second sheet");

        // Note that sheet name is Excel must not exceed 31 characters
        // and must not contain any of the any of the following characters:
        // 0x0000
        // 0x0003
        // colon (:)
        // backslash (\)
        // asterisk (*)
        // question mark (?)
        // forward slash (/)
        // opening square bracket ([)
        // closing square bracket (])

        // You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
        // for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
        String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
        Sheet sheet3 = wb.createSheet(safeName);

        Row row = sheet1.createRow(0);
        row.createCell(0).setCellValue("a");
        row.createCell(1).setCellValue(1.2);
        row.createCell(2).setCellValue(true);

        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    public static void readWb() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("workbook.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet worksheet = workbook.getSheet("new sheet");
        HSSFRow row1 = worksheet.getRow(0);
        HSSFCell cellA1 = row1.getCell(0);
        String a1Val = cellA1.getStringCellValue();
        HSSFCell cellB1 = row1.getCell(1);
        double b1Val = cellB1.getNumericCellValue();

        System.out.println("A1: " + a1Val);
        System.out.println("B1: " + b1Val);
    }
}


