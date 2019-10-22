package kkr.travel.utils.excel.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class PoiStyles {

    private CellStyle styleErrorCell;
    private CellStyle styleErrorRow;
    private CellStyle styleAuditErrorCell;
    private CellStyle styleAuditOkCell;
    private CellStyle styleAuditTitleCell;
    private CellStyle styleAuditHeaderRowCell;
    private CellStyle styleAuditHeaderColumnCell;

    public PoiStyles(PoiWorkbook poiWorkbook) {
        styleErrorCell = createStyleErrorCell(poiWorkbook);
        styleErrorRow = createStyleErrorRow(poiWorkbook);
        styleAuditErrorCell = createStyleAuditErrorCell(poiWorkbook);
        styleAuditOkCell = createStyleAuditOkCell(poiWorkbook);
        styleAuditTitleCell = createStyleAuditTitleCell(poiWorkbook);
        styleAuditHeaderRowCell = createStyleAuditHeaderRowCell(poiWorkbook);
        styleAuditHeaderColumnCell = createStyleAuditHeaderColumnCell(poiWorkbook);
    }

    private static CellStyle createStyleAuditErrorCell(PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleAuditOkCell(PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleAuditTitleCell(PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT
                .getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleAuditHeaderRowCell(
            PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight((short) 6);
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT
                .getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleAuditHeaderColumnCell(
            PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight((short) 6);
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT
                .getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleErrorCell(PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleErrorRow(PoiWorkbook poiWorkbook) {
        Font font = poiWorkbook.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        CellStyle cellStyle = poiWorkbook.getWorkbook().createCellStyle();

        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    public CellStyle getStyleErrorCell() {
        return styleErrorCell;
    }

    public CellStyle getStyleErrorRow() {
        return styleErrorRow;
    }

    public CellStyle getStyleAuditTitleCell() {
        return styleAuditTitleCell;
    }

    public CellStyle getStyleAuditHeaderRowCell() {
        return styleAuditHeaderRowCell;
    }

    public CellStyle getStyleAuditHeaderColumnCell() {
        return styleAuditHeaderColumnCell;
    }

    public CellStyle getStyleAuditErrorCell() {
        return styleAuditErrorCell;
    }

    public CellStyle getStyleAuditOkCell() {
        return styleAuditOkCell;
    }
}
