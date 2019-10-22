package com.ozz.demo.office.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelCellStyleUtil {

  // IndexedColors.GREY_25_PERCENT.getIndex()
  public void setFillForegroundColor(CellStyle cs, short color) {
    cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    cs.setFillForegroundColor(color);
  }

  public void setAlignment(CellStyle cs, HorizontalAlignment ha, VerticalAlignment va) {
    cs.setAlignment(ha);// 水平
    cs.setVerticalAlignment(va);// 垂直
  }

  public void addMergedRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
    CellRangeAddress regin = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
    sheet.addMergedRegion(regin);
  }

  public void setBorder(CellStyle cs) {
    cs.setBorderBottom(BorderStyle.THIN); // 下边框
    cs.setBorderLeft(BorderStyle.THIN);// 左边框
    cs.setBorderTop(BorderStyle.THIN);// 上边框
    cs.setBorderRight(BorderStyle.THIN);// 右边框
  }

  public void createFreezePane(Sheet sheet, int i, int j) {
    sheet.createFreezePane(i, j);
  }

  public void createFreezeTopRow(Sheet sheet) {
    sheet.createFreezePane(0, 1);
  }
}
