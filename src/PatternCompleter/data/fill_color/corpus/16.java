package server.essp.timesheet.report;

import c2s.essp.timesheet.report.ISumLevel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import java.awt.Color;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LevelCellStyleUtils {

    private static String STYLE_NAME_0 = "sumLevelStyleName_0";
    private static String STYLE_NAME_1 = "sumLevelStyleName_1";
    private static String STYLE_NAME_2 = "sumLevelStyleName_2";
    private static String STYLE_NAME_3 = "sumLevelStyleName_3";
    private static String STYLE_NAME_4 = "sumLevelStyleName_4";
    private static String STYLE_NAME_5 = "sumLevelStyleName_5";

    public static HSSFCellStyle getStyleByName(String styleName,
                                               HSSFCellStyle cellStyle) {
        if (STYLE_NAME_0.equals(styleName)) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            return cellStyle;
        } else if (STYLE_NAME_1.equals(styleName)) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.TAN.index);
            return cellStyle;
        } else if (STYLE_NAME_2.equals(styleName)) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
            return cellStyle;
        } else if (STYLE_NAME_3.equals(styleName)) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
            return cellStyle;
        } else if (STYLE_NAME_4.equals(styleName)) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            return cellStyle;
        }
        return null;
    }

    public static String getStyleName(ISumLevel level) {
        switch (level.getSumLevel()) {
        case 0:
            return STYLE_NAME_0;
        case 1:
            return STYLE_NAME_1;
        case 2:
            return STYLE_NAME_2;
        case 3:
            return STYLE_NAME_3;
        case 5:
            return STYLE_NAME_4;
        default:
            return "";
        }
    }
}
