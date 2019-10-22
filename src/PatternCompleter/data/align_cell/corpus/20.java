package com.lu.excel.support.handler;

import com.lu.excel.handler.AbstractCellHandler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;


public abstract class CellHandlerWrapper<T> implements AbstractCellHandler<T> {
    /**
     * 设置居中
     */
    protected CellStyle centerAlign(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }
}
