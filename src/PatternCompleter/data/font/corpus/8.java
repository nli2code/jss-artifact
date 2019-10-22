/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hepo.model.poi.cellStyling;

import java.util.HashMap;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 62
 */
public class CellStylesStore{
    

    protected    CellStylePOIAdapter     cellStylePOIAdapter;
    protected    XSSFWorkbook workbook;
    protected    HashMap<StyleKind,CellStyle> cellStylesHashMap;
    protected    HashMap<StyleKind,CellStyle> cellStylesHashMapTnpReport;
    protected    XSSFSheet spreadsheet;
    protected    Cell cell;
    protected    CellStylesStore сellStylesStore=null;
    
    

    public CellStylesStore(XSSFWorkbook workbook, XSSFSheet spreadsheet) {
    
        this.workbook = workbook;
        this.cellStylesHashMap = new HashMap();
        this.spreadsheet=spreadsheet;
        
        
        createStylesPrice();
        createStylesReportTNP();

    }
    

    public enum StyleKind {
        
        NAMECOMPANY,
        WRAPTEXT,
        CONTACTS_HEPO,
        CONTACTS_HEPO_TITLE,
        PRICE_DATE,
        STATEMENT,
        TABLEHEAD,
        TABLEHEADPRODACSTNAME,
        CUSTOMERNAMETITLE,
        CUSTOMERNAMEVALUE,
        TABLECATEGORY,
        PERMANENTDATASTRING,
        PERMANENTDANUMERIC_NotNegative,
        PERMANENTDANUMERIC_withNehative
 
        , 
        TITLE_REPORT_TNP, 
        DATE_REPORT_TNP,
        REPORT_TNP_TABLE_HEAD, 
        PRODUCT_BLOCK__REPORT_TNP_VALUE, 
        REPORT_TNP_TABLE_LEFT
        
        , LEVE1_CATEGORY, LEVE2_CATEGORY,
        REPORT_TNP_DETALE_HEADER,
        REPORT_TNP_DETALE_TEXT, 
        REPORT_TNP_DETALE_HEADER_LEVEL2,
        REPORT_TNP_DETALE_TEXT_LEFT, 
        REPORT_TNP_DETALE_TEXT_RIGHT;
    }
    public HashMap getCellStyles() {
        return cellStylesHashMap;
    }
    public  CellStyle getConcreteCellStyle(StyleKind skp){
        
        return cellStylesHashMap.get(skp);
    }
    protected  void createStylesPrice(){
        
        this.cellStylesHashMap.put(StyleKind.NAMECOMPANY, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(18)
                         .setFontName("Calibri")
                         .setFontBold()
                         .getСellStyle()
                
                
                );
        
        this.cellStylesHashMap.put(StyleKind.WRAPTEXT, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                        .setWrapText()
                        .getСellStyle()
                
                
                );
        
        this.cellStylesHashMap.put(StyleKind.STATEMENT, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(14)
                         .setFontName("Calibri")
                         .getСellStyle()
                
                
                );
        
        
        
        this.cellStylesHashMap.put(StyleKind.CONTACTS_HEPO, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                          .setFontHeight(11)
                          .setFontName("Calibri")
                          .getСellStyle()
           
                
                
                );
        
        this.cellStylesHashMap.put(StyleKind.CONTACTS_HEPO_TITLE, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                          .setFontHeight(11)
                          .setFontName("Calibri")
                          .setFontBold()
                          .getСellStyle()
           
                
                
                );
        
        this.cellStylesHashMap.put(StyleKind.PRICE_DATE, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                          .setFontHeight(14)
                          .setFontName("Calibri")
                          .getСellStyle()
                       

                
                );
        
        

        
       this.cellStylesHashMap.put(StyleKind.CUSTOMERNAMETITLE, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                          .setFontHeight(12)
                          .setFontColor(HSSFColor.GREY_80_PERCENT.index)
                          .setVerticalAlignmentCENTER()
                          .setFontName("Calibri")
                          .setWrapText()
                          .setFontBold()
                          .getСellStyle()
                         
                       
                
                
                );
       
       this.cellStylesHashMap.put(StyleKind.CUSTOMERNAMEVALUE, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                          .setFontHeight(22)
                          .setFontColor(IndexedColors.GREY_50_PERCENT.index)
                          .setFontName("Calibri")
                          .setVerticalAlignmentCENTER()
                          .setFontBold()
                          .getСellStyle()
                         
                       
                
                
                );
    
       this.cellStylesHashMap.put(StyleKind.TABLEHEAD, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                            .setFontHeight(11)
                            .setFontColor(HSSFColor.BLACK.index)                           
                            .setFontBold()
                            .setWrapText()
                            .setHorizontalAlignmentCENTER()
                            .setVerticalAlignmentCENTER()
                            .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index).
                                    setFontName("Calibri")
                          .getСellStyle()
                          
                         
                       
                
                
                );
       
       this.cellStylesHashMap.put(StyleKind.TABLEHEADPRODACSTNAME, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                            .setFontHeight(18)
                            .setFontColor(HSSFColor.BLACK.index)
                            .setFontBold()
                            .setHorizontalAlignmentCENTER()
                            .setVerticalAlignmentCENTER()
                            .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index).
                                    setFontName("Calibri")
                          .getСellStyle()
                          
                         
                       
                
                
                );
    
       this.cellStylesHashMap.put(StyleKind.TABLECATEGORY, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                   .setFontHeight(10)
                   .setFontColor(HSSFColor.BLACK.index)
                   .setFontBold()
                   .setFillForegroundCol(IndexedColors.YELLOW.index)
                   .setFillPattern(FillPatternType.SOLID_FOREGROUND)
                   .setFontName("Times New Roman")
                   .setBorder(
                           BorderStyle.THIN,
                           BorderStyle.THIN,
                           BorderStyle.THIN,
                           BorderStyle.THIN, 
                           IndexedColors.BLACK.index)
                    .getСellStyle()
                                         
                             
                
                
                );
                  
    
    
                  
             this.cellStylesHashMap.put(StyleKind.PERMANENTDATASTRING, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                    .setFontHeight(10)
                    .setFontColor(HSSFColor.BLACK.index)
                    .setBorder(BorderStyle.THIN, 
                              BorderStyle.THIN,
                              BorderStyle.THIN,
                              BorderStyle.THIN, 
                              IndexedColors.BLACK.index)
                         .getСellStyle()

                                  
                
                );

    
                  
             this.cellStylesHashMap.put(StyleKind.PERMANENTDANUMERIC_NotNegative, 
                new CellStylePOIAdapter(workbook, spreadsheet)
//                    .setCellType(CellType.NUMERIC)
                    .setFontHeight(10)
                    .setFontColor(HSSFColor.BLACK.index)
                    .setDataFormatForNumericNotNegative()
                    .setBorder(BorderStyle.THIN, 
                              BorderStyle.THIN,
                              BorderStyle.THIN,
                              BorderStyle.THIN, 
                              IndexedColors.BLACK.index)
                         .getСellStyle()

                                  
                
                );
             
             this.cellStylesHashMap.put(StyleKind.PERMANENTDANUMERIC_withNehative, 
                new CellStylePOIAdapter(workbook, spreadsheet)
//                    .setCellType(CellType.NUMERIC)
                    .setFontHeight(10)
                    .setFontColor(HSSFColor.BLACK.index)
                    .setDataFormatForNumericWithNegative()
                    .setBorder(BorderStyle.THIN, 
                              BorderStyle.THIN,
                              BorderStyle.THIN,
                              BorderStyle.THIN, 
                              IndexedColors.BLACK.index)
                         .getСellStyle()

                                  
                
                );

    };

    protected  void createStylesReportTNP(){
    
          this.cellStylesHashMap.put(StyleKind.TITLE_REPORT_TNP, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(25)
                         .setFontName("Calibri")
                         .setFontColor(HSSFColor.WHITE.index)
                         .setFontBold()
                         .setHorizontalAlignmentCENTER()
                         .setVerticalAlignmentCENTER()
                         .setFontColorRGB(255, 255, 255)
                         .setFillForegroundColRGB(95,158,160)
                         .setFillPattern(FillPatternType.SOLID_FOREGROUND)
                         .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          this.cellStylesHashMap.put(StyleKind.DATE_REPORT_TNP, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(14)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setFontColor(HSSFColor.ORANGE.index)
                         .setFillForegroundColRGB(255,222,173)
                         .setFillPattern(FillPatternType.SOLID_FOREGROUND)
                         .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          this.cellStylesHashMap.put(StyleKind.REPORT_TNP_TABLE_HEAD, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(12)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setWrapText()
                         .setFontHeight(12)
                         .setFontColorRGB(255, 255, 255)
                         .setFillForegroundColRGB(244, 176, 132)
                         .setFillPattern(FillPatternType.SOLID_FOREGROUND)
                         .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          this.cellStylesHashMap.put(StyleKind.REPORT_TNP_TABLE_LEFT, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(14)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setWrapText()
                         .setFontColor(HSSFColor.TEAL.index)
                         .setFillForegroundColRGB(255,222,173)
                         .setFillPattern(FillPatternType.SOLID_FOREGROUND)
                         .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          this.cellStylesHashMap.put(StyleKind.LEVE1_CATEGORY, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(28)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setWrapText()
                         .setHorizontalAlignmentCENTER()
                         .setVerticalAlignmentCENTER()
                         .setFontColorRGB(0, 128, 128)
                         .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          this.cellStylesHashMap.put(StyleKind.LEVE2_CATEGORY, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(18)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setWrapText()
                         .setFontColorRGB(0, 128, 128)
                         .setBorderRGB(BorderStyle.NONE, 
                                    BorderStyle.NONE, 
                                    BorderStyle.THIN,
                                    BorderStyle.NONE,  
                                    new XSSFColor(new java.awt.Color(54, 96, 146)))
                        .getСellStyle()
                
                
                );
          this.cellStylesHashMap.put(StyleKind.PRODUCT_BLOCK__REPORT_TNP_VALUE, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(11)
                         .setFontName("Calibri")
                         .setFontColorRGB(0, 128, 128)
                         .setDataFormatForNumericNotNegative()
                         .setBorder(BorderStyle.THIN, 
                                    BorderStyle.THIN,
                                    BorderStyle.THIN,
                                    BorderStyle.THIN, 
                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          
          this.cellStylesHashMap.put(StyleKind.REPORT_TNP_DETALE_HEADER, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(14)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setHorizontalAlignmentCENTER()
                         .setFontColorRGB(33, 89, 103)
//                         .setFillForegroundColRGB(255, 247, 239)
//                         .setFillPattern(FillPatternType.SOLID_FOREGROUND)
//                         .setBorder(BorderStyle.THIN, 
//                                    BorderStyle.THIN,
//                                    BorderStyle.THIN,
//                                    BorderStyle.THIN, 
//                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          
          
          this.cellStylesHashMap.put(StyleKind.REPORT_TNP_DETALE_TEXT_LEFT, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(12)
                         .setFontName("Calibri")
                         .setHorizontalAlignmentRight()
                         .setFontColorRGB(33, 89, 103)
                         .setWrapText()
//                         .setBorder(BorderStyle.NONE, 
//                                    BorderStyle.NONE,
//                                    BorderStyle.NONE,
//                                    BorderStyle.THIN, 
//                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                
                );
          
          this.cellStylesHashMap.put(StyleKind.REPORT_TNP_DETALE_TEXT_RIGHT, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(12)
                         .setFontName("Calibri")
                         .setFontColorRGB(33, 89, 103)
                         .setWrapText()
                         .setHorizontalAlignmentLeft()
                         .setDataFormatForNumericNotNegative()
//                         .setBorder(BorderStyle.NONE, 
//                                    BorderStyle.THIN,
//                                    BorderStyle.NONE,
//                                    BorderStyle.NONE, 
//                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                
                );
          
          this.cellStylesHashMap.put(StyleKind.REPORT_TNP_DETALE_TEXT, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(12)
                         .setFontName("Calibri")
                         .setFontColorRGB(33, 89, 103)
                         .setWrapText()
                        .getСellStyle()
                
                
                );
          
          
          
          
          
          
             this.cellStylesHashMap.put(StyleKind.REPORT_TNP_DETALE_HEADER_LEVEL2, 
                new CellStylePOIAdapter(workbook, spreadsheet)
                         .setFontHeight(12)
                         .setFontName("Calibri")
                         .setFontBold()
                         .setHorizontalAlignmentCENTER()
                         .setFontColorRGB(33, 89, 103)
//                         .setBorder(BorderStyle.THIN, 
//                                    BorderStyle.THIN,
//                                    BorderStyle.THIN,
//                                    BorderStyle.THIN, 
//                                    IndexedColors.BLACK.index)
                        .getСellStyle()
                
                
                );
          
          
          
    
    }
    


   
    
    

    
    
}
