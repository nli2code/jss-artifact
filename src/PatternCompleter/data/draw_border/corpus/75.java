package com.zsf.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 */
public final class ExcelUtils {

    private ExcelUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void export(SXSSFWorkbook wb, Sheet sh, String columnNames[], String[] keys, List<Map<String,Object>> list, int beginSize, int size) {
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        Font f = wb.createFont();
        Font f2 = wb.createFont();

        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        cs.setFont(f);
        cs.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        if(0 == beginSize) {
            Row rowTitle = sh.createRow(0);

            for(int i=0;i<columnNames.length;i++){
                Cell cell = rowTitle.createCell(i);
                cell.setCellValue(columnNames[i]);
                cell.setCellStyle(cs);
            }
        }

        for(int i=0; i<size; i++) {
            Row row = sh.createRow(beginSize+i+1);

            for(int k=0; k<keys.length; k++) {
                Cell cell = row.createCell(k);
                cell.setCellValue(list.get(i).get(keys[k]) == null?" ": list.get(i).get(keys[k]).toString());
                cell.setCellStyle(cs2);
            }
        }
    }
}
