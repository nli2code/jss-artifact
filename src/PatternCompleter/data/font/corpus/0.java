/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smi.travel.controller.excel.master;

import org.apache.poi.hssf.usermodel.HSSFFont;

/**
 *
 * @author Jittima
 */
public class UtilityExcelFunction {
    
    public HSSFFont getHeaderFont(HSSFFont font) {

        font.setFontHeightInPoints((short) 30);
        font.setFontName("Calibri");
        font.setItalic(true);

        return font;
    }

    public HSSFFont getHeaderTable(HSSFFont font) {

        font.setFontHeightInPoints((short) 14);
        font.setFontName("Calibri");
        font.setBoldweight(font.BOLDWEIGHT_BOLD);

        return font;
    }

    public HSSFFont getDetailFont(HSSFFont font) {

        font.setFontHeightInPoints((short) 10);
        font.setFontName("Calibri");
        font.setItalic(true);

        return font;
    }

    public HSSFFont getHeadDetailFont(HSSFFont font) {

        font.setFontHeightInPoints((short) 10);
        font.setFontName("Calibri");
        font.setBoldweight((short) 8);

        return font;
    }
    
    public HSSFFont getHeadDetailBoldFont(HSSFFont font) {

        font.setFontHeightInPoints((short) 11);
        font.setFontName("Arial");
        font.setBoldweight(font.BOLDWEIGHT_BOLD);

        return font;
    }
    
    public HSSFFont getTotalDetailBoldFont(HSSFFont font) {

        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setBoldweight(font.BOLDWEIGHT_BOLD);

        return font;
    }
}
