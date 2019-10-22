package com.NetworkAnalysis.rsc;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 *
 * @author hagedogu
 */
public class ExcelUtils {
    private final CellStyle stylePercentage;
    private final CellStyle styleDate;
    private final CellStyle styleHeader1;
    private final CellStyle styleHeader2;
    private final CellStyle styleHeader3;
    private final CellStyle styleMoney;
    
    public ExcelUtils(Workbook wb) {
        stylePercentage = wb.createCellStyle();
        stylePercentage.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
        
        styleDate = wb.createCellStyle();
        styleDate.setDataFormat(wb.createDataFormat().getFormat("dd/mm/yyyy"));
        
        Font boldFont = wb.createFont();
        boldFont.setBold(true);
        
        styleHeader1 = wb.createCellStyle();
        styleHeader1.setFont(boldFont);
        styleHeader1.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        styleHeader1.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        
        styleHeader2 = wb.createCellStyle();
        styleHeader2.setFont(boldFont);
        styleHeader2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        styleHeader2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        
        styleHeader3 = wb.createCellStyle();
        styleHeader3.setFont(boldFont);
        styleHeader3.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        styleHeader3.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        
        styleMoney = wb.createCellStyle();
        styleMoney.setDataFormat(wb.createDataFormat().getFormat("$0.00"));      
    }
    
    public CellStyle getStylePercentage() {
        return stylePercentage;
    }
    
    public CellStyle getStyleDate() {
        return styleDate;
    }
    
    public CellStyle getStyleHeader1() {
        return styleHeader1;
    }
    
    public CellStyle getStyleHeader2() {
        return styleHeader2;
    }
    
    public CellStyle getStyleHeader3() {
        return styleHeader3;
    }
    
    public CellStyle getStyleMoney() {
        return styleMoney;
    }
    
}

