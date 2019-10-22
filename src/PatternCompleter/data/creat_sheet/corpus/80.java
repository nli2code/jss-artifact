package io.github.caiwan.spreadsheet.builder.hssf;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import io.github.caiwan.spreadsheet.builder.SpreadsheetFactory;

final public class HSSFFactory implements SpreadsheetFactory {
    @Override
    public Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

    @Override
    public Sheet createSheet(Workbook workbook, String title) {
        return ((HSSFWorkbook) workbook).createSheet(WorkbookUtil.createSafeSheetName(title));
    }

    @Override
    public Row createRow(Sheet sheet, int row) {
        return ((HSSFSheet) sheet).createRow(row);

    }

    @Override
    public Cell createCell(Row row, int col) {
        return ((HSSFRow) row).createCell(col);
    }

}
