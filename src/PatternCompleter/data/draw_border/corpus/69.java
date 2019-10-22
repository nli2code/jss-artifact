package com.gil.bridge;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFTextBox;
import org.openxmlformats.schemas.drawingml.x2006.main.STShapeType;

import static org.apache.poi.ss.usermodel.CellStyle.*;

/**
 * Created by IntelliJ IDEA.
 * User: anya.grinberg
 * Date: 16/02/15
 * Time: 10:38
 * To change this template use File | Settings | File Templates.
 */
public abstract class BridgeLevelSheet extends BridgeSheet{
    protected String title3;

    BridgeLevelSheet(BridgeWorkbook workbook ,String sheetName) {
        super(workbook, sheetName);
    }

    void fillParamTable(boolean isFirstPage){
        setCellValue("H2", bridgeParams.getRoadName())
                .setFont(workbook.getHeader1Font()).setAlignment(ALIGN_RIGHT);

        setCellValue("H3",  bridgeParams.getBridgeName())
                .setFont(workbook.getHeader1Font()).setAlignment(ALIGN_RIGHT);

        setCellValue("H4",  title3)
                .setFont(workbook.getHeader1Font()).setAlignment(ALIGN_RIGHT);

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFTextBox textbox1 = drawing.createTextbox(new XSSFClientAnchor(38100, 38100, 285750,152400,  3, 4,  4, 6));
        textbox1.setShapeType(STShapeType.RECT.intValue());
        XSSFRichTextString textboxString = new  XSSFRichTextString();
        //textboxString.applyFont(workbook.getBodyFont());
        textboxString.append("\u05db\u05dc \u05d4\u05de\u05d9\u05d3\u05d5\u05ea \u05d1\u05de\u05d8\u05e8", workbook.getBodyFont());
        textbox1.setText(textboxString);
        textbox1.setFillColor(255,255,255);
        textbox1.setLineStyleColor(0,0,0);


        setCellValue("A2", "")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_CENTER)
                .setBorderTop(BORDER_THIN);

        setCellValue("B2", "DATA")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_CENTER)
                .setBorderTop(BORDER_THIN);

        setCellValue("C2", "\u05e0\u05ea\u05d5\u05e0\u05d9\u05dd")     //נתונים
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_CENTER)
                .setBorderTop(BORDER_THIN)
                .setBorderRight(BORDER_THIN);

        setCellValue("B3", "R=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT);

        setCellValue("C3", isFirstPage ? bridgeParams.getR() :"='" + firstSheetName + "'!" + bridgeParams.R)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setAlignment(ALIGN_LEFT)
                .setBorderRight(BORDER_THIN);

        setCellValue("A4", "PCV")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_RIGHT)
               .setBorderTop(BORDER_THIN);

        setCellValue("B4", "x=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        setCellValue("C4", isFirstPage ? bridgeParams.getPCVx() :"='" + firstSheetName + "'!" + bridgeParams.PCVx)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setDataFormat(workbook.getDataFormat("#0.00"))
                .setAlignment(ALIGN_LEFT)
                .setBorderTop(BORDER_THIN)
                .setBorderRight(BORDER_THIN);

        setCellValue("B5", "elev=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT);

        setCellValue("C5", isFirstPage ? bridgeParams.getPCVelev() :"='" + firstSheetName + "'!" + bridgeParams.PCVelev)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setDataFormat(workbook.getDataFormat("#0.000"))
                .setAlignment(ALIGN_LEFT)
                .setBorderRight(BORDER_THIN);

        setCellValue("A6", "PIV")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        setCellValue("B6", "x=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        setCellValue("C6", isFirstPage ? bridgeParams.getPIVx() :"='" + firstSheetName + "'!" + bridgeParams.PIVx)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setDataFormat(workbook.getDataFormat("#0.00"))
                .setAlignment(ALIGN_LEFT)
                .setBorderTop(BORDER_THIN)
                .setBorderRight(BORDER_THIN);

        setCellValue("B7", "elev=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT);

        setCellValue("C7", isFirstPage ? bridgeParams.getPIVelev() :"='" + firstSheetName + "'!" + bridgeParams.PIVelev)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setDataFormat(workbook.getDataFormat("#0.000"))
                .setAlignment(ALIGN_LEFT)
                .setBorderRight(BORDER_THIN);

        setCellValue("A8", "PTV")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_RIGHT)
               .setBorderTop(BORDER_THIN);

        setCellValue("B8", "x=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        setCellValue("C8", isFirstPage ? bridgeParams.getPTVx() :"='" + firstSheetName + "'!" + bridgeParams.PTVx)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setDataFormat(workbook.getDataFormat("#0.00"))
                .setAlignment(ALIGN_LEFT)
                .setBorderTop(BORDER_THIN)
                .setBorderRight(BORDER_THIN);

        setCellValue("B9", "elev=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT);

        setCellValue("C9", isFirstPage ? bridgeParams.getPTVelev() :"='" + firstSheetName + "'!" + bridgeParams.PTVelev)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setDataFormat(workbook.getDataFormat("#0.000"))
                .setAlignment(ALIGN_LEFT)
                .setBorderRight(BORDER_THIN);

        setCellValue("B9", "elev=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT);

        setCellValue("A10", "")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_CENTER)
                .setBorderTop(BORDER_THIN)
                .setBorderBottom(BORDER_THIN);

        setCellValue("B10", "asfalt=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        setCellValue("C10", isFirstPage ? bridgeParams.getAsfalt() :"='" + firstSheetName + "'!" + bridgeParams.asfalt)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setAlignment(ALIGN_LEFT)
                .setBorderRight(BORDER_THIN)
                .setBorderRight(BORDER_THIN);

        setCellValue("B11", "L=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN).setBorderLeft(BORDER_THIN).setBorderBottom(BORDER_THIN);

        String L = resolveFormula("PTVx-PCVx");
        setCellValue("C11", "=" + L)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.000"))
                .setBorderTop(BORDER_THIN).setBorderRight(BORDER_THIN).setBorderBottom(BORDER_THIN);


        setCellValue("D8", "i1=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        String i1 = resolveFormula("(PIVelev-PCVelev)/(PIVx-PCVx)");
        setCellValue("E8", "="+i1)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.0000"))
                .setBorderTop(BORDER_THIN).setBorderRight(BORDER_THIN);

        setCellValue("D9", "\u03b11=") //α1=
                .setFont(workbook.getGreekFont()).setAlignment(ALIGN_RIGHT);

        String alpha1 = "E8*180/PI()";
        setCellValue("E9", "="+alpha1)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.0000"))
                .setBorderRight(BORDER_THIN);

        setCellValue("D10", "\u0063\u006f\u0073\u03b11=")    //cosα1=
                .setFont(workbook.getGreekFont()).setAlignment(ALIGN_RIGHT);

        String cosAlpha1 = "COS(E9*PI()/180)";
        setCellValue("E10", "="+cosAlpha1)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.000"))
                .setBorderRight(BORDER_THIN);

        setCellValue("F8", "i2=")
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN);

        String i2 = resolveFormula("(PIVelev-PTVelev)/(PTVx-PIVx)");
        setCellValue("G8", "="+i2)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.0000"))
                .setBorderTop(BORDER_THIN).setBorderRight(BORDER_THIN);

        setCellValue("F9", "\u03b12=") //α2=
                .setFont(workbook.getGreekFont()).setAlignment(ALIGN_RIGHT);

        String alpha2 = resolveFormula("i2*180/PI()");
        setCellValue("G9", "="+alpha2)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.00000"))
                .setBorderRight(BORDER_THIN);

        setCellValue("F10", "\u0063\u006f\u0073\u03b12=")    //cosα1=
                .setFont(workbook.getGreekFont()).setAlignment(ALIGN_RIGHT);

        String cosAlpha2 = "COS(G9*PI()/180)";
        setCellValue("G10", "="+cosAlpha2)
                .setFont(workbook.getBodyFont()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.00000"))
                .setBorderRight(BORDER_THIN);

        setCellValue("D11", "m=")
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN)
                .setBorderBottom(BORDER_THIN)
                .setFillForegroundColor(255,255,153)
                .setFillPattern(SOLID_FOREGROUND);

        String m = resolveFormula("C11*(i2+E8)/8");
        setCellValue("E11", "=" + m)
                .setFont(workbook.getHeader2Font()).setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.0000"))
                .setBorderTop(BORDER_THIN)
                .setBorderBottom(BORDER_THIN)
                .setBorderRight(BORDER_THIN)
                .setFillForegroundColor(255,255,153)
                .setFillPattern(SOLID_FOREGROUND);

        setCellValue("F11", "\u03b1=")
                .setFont(workbook.getGreekFont()).setAlignment(ALIGN_RIGHT)
                .setBorderTop(BORDER_THIN)
                .setBorderBottom(BORDER_THIN);

        setCellValue("G11", isFirstPage ? bridgeParams.getAlfa() :"='" + firstSheetName + "'!" + bridgeParams.alfa)
                .setFont(isFirstPage ? workbook.getParamFont() : workbook.getHeader2Font())
                .setAlignment(ALIGN_LEFT)
                .setDataFormat(workbook.getDataFormat("#0.00"))
                .setBorderTop(BORDER_THIN)
                .setBorderBottom(BORDER_THIN)
                .setBorderRight(BORDER_THIN);

    }


}
