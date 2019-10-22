package com.vxichina.wfmpx.utils.beans;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**apache poi cell处理对象.
 * 
 * @author xiaolong.li
 *
 */
public class ExcelPoiCell {

    /**单元格显示值.
     * 
     */
    private String  cellValue = "";

    /**poi 单元格样式对象那个.
     * <p/>
     * 示例：
     *   //样式定义有数量显示必须根据业务情况指定定义
        XSSFCellStyle cellStyleRequired = ExcelPoiUtils.getCellStyle(workbookCfg.getWorkbook(), 
                null, IndexedColors.PINK.index);

     * 
     */
    private XSSFCellStyle cellStyle = null;


    /**单元格批注.
     * 
     */
    private String cellComment = null;

    /**
     * @return the cellValue
     */
    public String getCellValue() {
        return cellValue;
    }

    /**
     * @param cellValue the cellValue to set
     */
    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }


    /**
     * @return the cellComment
     */
    public String getCellComment() {
        return cellComment;
    }

    /**
     * @param cellComment the cellComment to set
     */
    public void setCellComment(String cellComment) {
        this.cellComment = cellComment;
    }

    /**
     * @return the cellStyle
     */
    public XSSFCellStyle getCellStyle() {
        return cellStyle;
    }

    /**
     * @param cellStyle the cellStyle to set
     */
    public void setCellStyle(XSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }



}
