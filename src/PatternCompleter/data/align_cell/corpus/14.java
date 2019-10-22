package com.hongyb.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 作者:hongyanbo
 * 时间:2018/3/12
 */
public class DefaultCellStyle implements ExcelStyle {
    @Override
    public CellStyle setStyle(CellStyle style) {
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return null;
    }
}
