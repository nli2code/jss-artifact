package com.songtech.ypoi.style.position;

import com.songtech.ypoi.style.ExcelStyleParamsVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * Create By YINN on 2018/1/5 14:57
 * Description :
 */
public class ExcelBaseAlignment implements IExcelBaseAlignment {
    /**
     * 标题居中
     */
    @Override
    public CellStyle setTitleAlignment(CellStyle style, Cell cell, ExcelStyleParamsVO esp) {
        this.setAlignment(style, cell, esp.getTitle_Alignment());
        return style;
    }

    @Override
    public boolean setAlignmentByDiy(CellStyle style, Cell cell, ExcelStyleParamsVO esp) {
        return false;
    }

    public CellStyle setAlignment(CellStyle style, Cell cell, String position){
        if (position != null){
            //全部转换为小写
            position = position.toLowerCase();
            if (position == "left"){
                style.setAlignment(HorizontalAlignment.LEFT);
            }else if (position == "right"){
                style.setAlignment(HorizontalAlignment.RIGHT);
            }else {
                style.setAlignment(HorizontalAlignment.CENTER);
            }
        }else {
            style.setAlignment(HorizontalAlignment.CENTER);
        }
        return style;
    }
}
