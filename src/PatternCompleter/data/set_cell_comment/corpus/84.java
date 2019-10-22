package com.zhongweixian.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author caoliang1918@aliyun.com
 * @Date 2017/11/5:23:52
 */
public class PoiSheetUtility {
    public static void deleteColumn(Sheet sheet, int columnToDelete) {
        int maxColumn = 0;
        for (int r = 0; r < sheet.getLastRowNum() + 1; r++) {
            Row row = sheet.getRow(r);

            // if no row exists here; then nothing to do; next!
            if (row == null) {
                continue;
            }

            // if the row doesn't have this many columns then we are good; next!
            int lastColumn = row.getLastCellNum();
            if (lastColumn > maxColumn) {
                maxColumn = lastColumn;
            }

            if (lastColumn < columnToDelete) {
                continue;
            }

            for (int x = columnToDelete + 1; x < lastColumn + 1; x++) {
                Cell oldCell = row.getCell(x - 1);
                if (oldCell != null) {
                    row.removeCell(oldCell);
                }

                Cell nextCell = row.getCell(x);
                if (nextCell != null) {
                    Cell newCell = row.createCell(x-1 , nextCell.getCellTypeEnum());
                    cloneCell(newCell, nextCell);
                }
            }
        }

        // Adjust the column widths
        for (int c = 0; c < maxColumn; c++) {
            sheet.setColumnWidth(c, sheet.getColumnWidth(c + 1));
        }
    }

    /*
     * Takes an existing Cell and merges all the styles and forumla
     * into the new one
     */
    private static void cloneCell(Cell cNew, Cell cOld) {
        cNew.setCellComment(cOld.getCellComment());
        cNew.setCellStyle(cOld.getCellStyle());

        switch (cNew.getCellTypeEnum()) {
            case BOOLEAN: {
                cNew.setCellValue(cOld.getBooleanCellValue());
                break;
            }
            case NUMERIC: {
                cNew.setCellValue(cOld.getNumericCellValue());
                break;
            }
            case STRING: {
                cNew.setCellValue(cOld.getStringCellValue());
                break;
            }
            case ERROR: {
                cNew.setCellValue(cOld.getErrorCellValue());
                break;
            }
            case FORMULA: {
                cNew.setCellFormula(cOld.getCellFormula());
                break;
            }
            default:
                cNew.setCellValue(cOld.getStringCellValue());
        }

    }
}
