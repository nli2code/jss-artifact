package excelWriterExample;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Working with borders
 */
public class WorkingWithBorders {
    public static void main(String[] args) throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {  //or new HSSFWorkbook();
            Sheet sheet = wb.createSheet("borders");

            // Create a row and put some cells in it. Rows are 0 based.
            Row row = sheet.createRow((short) 1);

            // Create a cell and put a value in it.
            Cell cell = row.createCell((short) 1);
            cell.setCellValue(4);

            // Style the cell with borders all around.
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom(BorderStyle.THICK);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(BorderStyle.THICK);
            style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
            style.setBorderRight(BorderStyle.THICK);
            style.setRightBorderColor(IndexedColors.BLUE.getIndex());
            style.setBorderTop(BorderStyle.MEDIUM_DASHED);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cell.setCellStyle(style);

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream("xssf-borders.xlsx")) {
                wb.write(fileOut);
            }
        }
    }
}
