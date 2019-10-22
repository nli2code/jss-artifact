package dbk.adapter.Poi;

import dbk.adapter.Style;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denis on 31.01.17.
 */
public class PoiStyle extends Style {


    private static Map<Color, HSSFColor> colorMap = new HashMap<>();

    static {
        colorMap.put(Color.GREEN, new HSSFColor.BRIGHT_GREEN());
        colorMap.put(Color.RED, new HSSFColor.RED());
        colorMap.put(Color.LIGHT_GRAY, new HSSFColor.GREY_25_PERCENT());
    }

    private final HSSFCellStyle hssfStyle;
    private final HSSFWorkbook workbook;

    public PoiStyle(HSSFCellStyle cellStyle, PoiWorkbook poiWorkbook) {
        this.hssfStyle = cellStyle;
        this.workbook = poiWorkbook.getWorkbook();
    }

    @Override
    public void setFontSize(int size) {
        HSSFFont font = workbook.createFont();
        font.setFontHeight((short) size);
        hssfStyle.setFont(font);
    }

    @Override
    public void setBold() {
        final HSSFFont boldFont = workbook.createFont();
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        hssfStyle.setFont(boldFont);
    }

    @Override
    public void setWrapped(boolean wrapped) {
        final HSSFFont boldFont = workbook.createFont();
        hssfStyle.setWrapText(true);
    }

    @Override
    public void setBackgroundColor(Color lightGray) {

        HSSFColor hssfColor = colorMap.get(lightGray);
        if (hssfColor == null) {
            throw new IllegalArgumentException("Color " + lightGray + " is not in map");
        }
        hssfStyle.setFillBackgroundColor(hssfColor.getIndex());
    }

    public void setHorStyleWithBorder() {
        hssfStyle.setBorderBottom(CellStyle.BORDER_THIN);
        hssfStyle.setBorderTop(CellStyle.BORDER_THIN);
        //hssfStyle.setBorderRight(CellStyle.BORDER_THIN);
        //hssfStyle.setBorderLeft(CellStyle.BORDER_THIN);

        //hssfStyle.setAlignment(CellStyle.ALIGN_CENTER);

        alignmentCenter();
    }

    public void alignmentCenter() {
        hssfStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
    }

    public void setVertStyleWithBorder() {


        //hssfStyle.setBorderBottom(CellStyle.BORDER_THIN);
        //hssfStyle.setBorderTop(CellStyle.BORDER_THIN);
        hssfStyle.setBorderRight(CellStyle.BORDER_THIN);
        hssfStyle.setBorderLeft(CellStyle.BORDER_THIN);
        hssfStyle.setAlignment(CellStyle.ALIGN_CENTER);
        alignmentCenter();

    }

    public void setThinBorder() {
        hssfStyle.setBorderBottom(CellStyle.BORDER_THIN);
        hssfStyle.setBorderTop(CellStyle.BORDER_THIN);
        hssfStyle.setBorderRight(CellStyle.BORDER_THIN);
        hssfStyle.setBorderLeft(CellStyle.BORDER_THIN);

        hssfStyle.setAlignment(CellStyle.ALIGN_CENTER);

        //alignmentCenter();
    }


    public HSSFCellStyle getStyle() {
        return hssfStyle;
    }


}
