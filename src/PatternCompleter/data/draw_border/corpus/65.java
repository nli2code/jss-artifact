package common;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class Style {

    public static Style instance;
    
    public CellStyle ALIGN_LEFT = null;
    public CellStyle ALIGN_CENTER = null;
    public CellStyle SUM = null;
    
    private Workbook book;
    
    public static Style get(Workbook book) {
        if (instance == null) {
            instance = new Style(book);
        }
        
        return instance;
    }
    
    public Style(Workbook book) {
        this.book = book;
        this.buildStyle();
    }
    
    private void buildStyle() {
        ALIGN_LEFT = book.createCellStyle();
        ALIGN_LEFT.setAlignment(CellStyle.ALIGN_LEFT);
        ALIGN_LEFT.setBorderTop(CellStyle.BORDER_THIN);
        ALIGN_LEFT.setBorderRight(CellStyle.BORDER_THIN);
        ALIGN_LEFT.setBorderLeft(CellStyle.BORDER_THIN);
        ALIGN_LEFT.setBorderBottom(CellStyle.BORDER_THIN);
        ALIGN_LEFT.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        ALIGN_LEFT.setWrapText(true);
        
        ALIGN_CENTER = book.createCellStyle();
        ALIGN_CENTER.setAlignment(CellStyle.ALIGN_CENTER);
        ALIGN_CENTER.setBorderTop(CellStyle.BORDER_THIN);
        ALIGN_CENTER.setBorderRight(CellStyle.BORDER_THIN);
        ALIGN_CENTER.setBorderLeft(CellStyle.BORDER_THIN);
        ALIGN_CENTER.setBorderBottom(CellStyle.BORDER_THIN);
        ALIGN_CENTER.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        ALIGN_CENTER.setWrapText(true);
        
        SUM = book.createCellStyle();
        SUM.setAlignment(CellStyle.ALIGN_CENTER);
        SUM.setBorderTop(CellStyle.BORDER_THIN);
        SUM.setBorderRight(CellStyle.BORDER_THIN);
        SUM.setBorderLeft(CellStyle.BORDER_THIN);
        SUM.setBorderBottom(CellStyle.BORDER_THIN);
        SUM.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        SUM.setFillPattern((short)1);
        SUM.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
    }
}
