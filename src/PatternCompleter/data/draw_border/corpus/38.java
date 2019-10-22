package kdtech.platform.excel;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.*;

/**
 * User: k.dowding
 * Date: 06/04/2010
 * Time: 6:23:58 PM
 */
public class StyleWrapper {
    public XSSFCellStyle cellStyle;

    public StyleWrapper(XSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public StyleWrapper copy() {
        return new StyleWrapper((XSSFCellStyle) cellStyle.clone());
    }

    public void setFill(short colour, short pattern) {
        cellStyle.setFillForegroundColor(colour);
        cellStyle.setFillPattern(pattern);
    }

    public void setFill(Color color, short pattern) {
        cellStyle.setFillForegroundColor(new XSSFColor(color));
        cellStyle.setFillPattern(pattern);
    }

    public void setBorder(short borderStyle, short borderColour) {
        setBorderLeft(borderStyle, borderColour);
        setBorderRight(borderStyle, borderColour);
        setBorderTop(borderStyle, borderColour);
        setBorderBottom(borderStyle, borderColour);
    }

    public void setBorderLeft(short borderStyle, short borderColour) {
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setLeftBorderColor(borderColour);
    }

    public void setBorderRight(short borderStyle, short borderColour) {
        cellStyle.setBorderRight(borderStyle);
        cellStyle.setRightBorderColor(borderColour);
    }

    public void setBorderTop(short borderStyle, short borderColour) {
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setTopBorderColor(borderColour);
    }

    public void setBorderBottom(short borderStyle, short borderColour) {
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBottomBorderColor(borderColour);
    }

    public void setFont(FontWrapper fontWrapper) {
        cellStyle.setFont(fontWrapper.getFont());
    }

    public void setAlignment(HorizontalAlignment horizontal, VerticalAlignment vertical) {
        cellStyle.setAlignment(horizontal);
        cellStyle.setVerticalAlignment(vertical);
    }
}
