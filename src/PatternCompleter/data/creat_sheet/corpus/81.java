package io.github.caiwan.spreadsheet.builder.xssf;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.caiwan.spreadsheet.builder.SpreadsheetFactory;

public class XSSFFactory implements SpreadsheetFactory {

    @Override
    public Workbook createWorkbook() {
        return new XSSFWorkbook();
    }

    @Override
    public Sheet createSheet(Workbook workbook, String title) {
        return ((XSSFWorkbook) workbook).createSheet(WorkbookUtil.createSafeSheetName(title));
    }

    @Override
    public Row createRow(Sheet sheet, int row) {
        return ((XSSFSheet) sheet).createRow(row);

    }

    @Override
    public Cell createCell(Row row, int col) {
        return ((XSSFRow) row).createCell(col);
    }

}
