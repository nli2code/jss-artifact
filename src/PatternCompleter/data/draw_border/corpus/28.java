package com.study.util.excel.imports;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ExcelCellFormat {

    public static HSSFCell setCell(HSSFRow row, HSSFCellStyle cellStyle, int index, ExcelBean excelBean) {
        HSSFCell cell = row.createCell(index);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(excelBean.getValue()));
        cell.setCellStyle(cellStyle);
        cellBorder(cellStyle);
        return cell;
    }

    public static void cellBorder(HSSFCellStyle style) {
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    }
}
