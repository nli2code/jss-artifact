package com.wolfjc.common.utils.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author xdd
 * @date 2018/3/9.
 */
public class AbstractExcelBookProcessor {


    private static final Logger LOGGER = Logger.getLogger(AbstractExcelBookProcessor.class);

    /**
     * 单元格样式
     */
    private CellStyle cellStyle;

    /**
     *
     */
    private Font font;

    /**
     * 设置字体
     *
     */
    protected void setFont() {
    }

    /**
     * 设置对其方式
     *
     */
    protected void setAlignment() {
    }

    /**
     * 设置边框
     *
     */
    protected void setCellBorder() {

    }

    /**
     * 设置样式
     *
     */
    private void setCellStyle() {

    }


    private void init(){

    }
}
