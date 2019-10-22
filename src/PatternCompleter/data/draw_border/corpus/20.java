package arrow.businesstraceability.search;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;


/**
 * The Class ExcelUtils.
 */
public class ExcelUtils {

    /**
     * Sets the full border style.
     *
     * @param myStyle the my_style
     * @return the HSSF cell style
     */
    public static HSSFCellStyle setFullBorderStyle(final HSSFCellStyle myStyle) {
        myStyle.setBorderLeft(CellStyle.BORDER_THIN);
        myStyle.setBorderRight(CellStyle.BORDER_THIN);
        myStyle.setBorderTop(CellStyle.BORDER_THIN);
        myStyle.setBorderBottom(CellStyle.BORDER_THIN);
        return myStyle;
    }
}
