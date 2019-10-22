package com.songtech.ypoi.style.color;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Create By YINN on 2018/1/5 14:57
 * Description :
 */
public class ExcelBaseColor implements IExcelBaseColor{

    final short DEFAULT_BACKGROUND_COLOR = IndexedColors.WHITE.getIndex();

    /**
     * 设置默认背景色
     */
    @Override
    public CellStyle setBackgroundColor(CellStyle style) {
        return setBackgroundColor(style,DEFAULT_BACKGROUND_COLOR);
    }

    /**
     * 设置背景色
     */
    @Override
    public CellStyle setBackgroundColor(CellStyle style, Short color) {
        try {
            if (color != null){
                style.setFillForegroundColor(color);
            }else {
                style.setFillForegroundColor(DEFAULT_BACKGROUND_COLOR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

}
