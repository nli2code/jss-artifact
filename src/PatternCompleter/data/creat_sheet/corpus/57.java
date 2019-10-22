package javaExcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileOutputStream;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {

        Workbook wb = new HSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet1 = wb.createSheet(WorkbookUtil.createSafeSheetName("Лист1"));
        org.apache.poi.ss.usermodel.Row row = sheet1.createRow(3);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(4);
        cell.setCellValue("123on");

        org.apache.poi.ss.usermodel.Sheet sheet2 = wb.createSheet(WorkbookUtil.createSafeSheetName("Лист2"));
        FileOutputStream fos = new FileOutputStream("D:\\!!15! Java-материалы\\tmpFille/123.xls");
        wb.write(fos);
        fos.close();
    }
}
