package Classes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;

public class ExcelUtil {

    public static void setCellColorAndFontColor(Cell cell, IndexedColors fgColor, IndexedColors FontColor ){
        Workbook wb = cell.getRow().getSheet().getWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(FontColor.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(fgColor.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
    }

    public static void setCellFont (SXSSFCell cell, IndexedColors fontColor, boolean isBold, boolean isItalic,
                                    boolean isUnderline, boolean needBorder) {
        Workbook wb = cell.getRow().getSheet().getWorkbook();
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(fontColor.getIndex());
        font.setBold(isBold);
        font.setItalic(isItalic);
        if (isUnderline)
            font.setUnderline(Font.U_SINGLE);
        style.setFont(font);
        if (needBorder) {
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
        }
        cell.setCellStyle(style);
    }

    // метод добавляет ячейке cell комментарий с текстом message
    public static void setCellComment(Cell cell, String message)
    {
        Drawing drawing = cell.getSheet().createDrawingPatriarch();
        CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();

        ClientAnchor anchor = factory.createClientAnchor();

        int width = 1; // ширина блока с комментарием (измеряется в количестве ширин текущей ячейки)
        int height = 1; // высота блока с комментарием (измеряется в количестве высот текущей ячейки)
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex() + width);
        anchor.setRow1(cell.getRowIndex());
        anchor.setRow2(cell.getRowIndex() + height);

        Comment comment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(message);
        comment.setString(str);

        cell.setCellComment(comment);
    }

}
