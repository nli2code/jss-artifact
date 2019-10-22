package com.wb.excel.api.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created on 2015/1/29.
 *
 * @author
 * @since 2.0.0
 */
public class NormalNumberCellStyle extends NormalCellStyle {
    public NormalNumberCellStyle(Workbook workbook) {
        super(workbook);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
    }
}
