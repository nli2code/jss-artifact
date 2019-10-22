//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.poi.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class PoiSheetUtility {
    public PoiSheetUtility() {
    }

    public static void deleteColumn(Sheet sheet, int columnToDelete) {
        short maxColumn = 0;

        int c;
        for(c = 0; c < sheet.getLastRowNum() + 1; ++c) {
            Row row = sheet.getRow(c);
            if(row != null) {
                short lastColumn = row.getLastCellNum();
                if(lastColumn > maxColumn) {
                    maxColumn = lastColumn;
                }

                if(lastColumn >= columnToDelete) {
                    for(int x = columnToDelete + 1; x < lastColumn + 1; ++x) {
                        Cell oldCell = row.getCell(x - 1);
                        if(oldCell != null) {
                            row.removeCell(oldCell);
                        }

                        Cell nextCell = row.getCell(x);
                        if(nextCell != null) {
                            Cell newCell = row.createCell(x - 1, nextCell.getCellType());
                            cloneCell(newCell, nextCell);
                        }
                    }
                }
            }
        }

        for(c = 0; c < maxColumn; ++c) {
            sheet.setColumnWidth(c, sheet.getColumnWidth(c + 1));
        }

    }

    private static void cloneCell(Cell cNew, Cell cOld) {
        cNew.setCellComment(cOld.getCellComment());
        cNew.setCellStyle(cOld.getCellStyle());
        switch(cNew.getCellType()) {
            case 0:
                cNew.setCellValue(cOld.getNumericCellValue());
                break;
            case 1:
                cNew.setCellValue(cOld.getStringCellValue());
                break;
            case 2:
                cNew.setCellFormula(cOld.getCellFormula());
            case 3:
            default:
                break;
            case 4:
                cNew.setCellValue(cOld.getBooleanCellValue());
                break;
            case 5:
                cNew.setCellValue((double)cOld.getErrorCellValue());
        }

    }
}
