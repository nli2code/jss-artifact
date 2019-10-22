package com.wb.excel.api.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created on 2015/1/29.
 *
 * @author
 * @since 2.0.0
 */
public class ErrorNumberCellStyle extends ErrorCellStyle {
    public ErrorNumberCellStyle(Workbook workbook) {
        super(workbook);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
    }
}
