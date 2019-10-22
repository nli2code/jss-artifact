//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jeecgframework.poi.excel.export.styler;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.export.styler.AbstractExcelExportStyler;
import org.jeecgframework.poi.excel.export.styler.IExcelExportStyler;

public class ExcelExportStylerDefaultImpl extends AbstractExcelExportStyler implements IExcelExportStyler {
    public ExcelExportStylerDefaultImpl(Workbook workbook) {
        super.createStyles(workbook);
    }

    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        titleStyle.setAlignment((short)2);
        titleStyle.setVerticalAlignment((short)1);
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setDataFormat(STRING_FORMAT);
        if(isWarp) {
            style.setWrapText(true);
        }

        return style;
    }

    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short)12);
        titleStyle.setFont(font);
        titleStyle.setAlignment((short)2);
        titleStyle.setVerticalAlignment((short)1);
        return titleStyle;
    }

    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setDataFormat(STRING_FORMAT);
        if(isWarp) {
            style.setWrapText(true);
        }

        return style;
    }
}
