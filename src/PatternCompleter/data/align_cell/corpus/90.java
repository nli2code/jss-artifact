package cn.net.yto.poi.template.base;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 通用导出模板
 *
 * @author xiaoY
 */
public abstract class BaseExportTemplate {

  /**
   * 通用标题模板单元格样式
   *
   * @param workbook 表对象
   * @return HSSFCellStyle 样式
   */
  public HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook) {
    HSSFFont font = workbook.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short) 12);
    // 设置标题字体
    font.setFontName(HSSFFont.FONT_ARIAL);
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    // 设置列标题样式
    cellStyle.setFont(font);
    // 居中
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    return cellStyle;
  }

  public HSSFCellStyle createHrefCellStyle(HSSFWorkbook workbook) {
    HSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) 8);
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    // 设置字体
    font.setFontName(HSSFFont.FONT_ARIAL);
    //font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
    cellStyle.setFont(font);
    // 居中
    cellStyle.setAlignment(HorizontalAlignment.LEFT);
    return cellStyle;
  }

  /**
   * 标题单元格内容
   *
   * @param sheet     工作表
   * @param cellStyle 样式
   */
  public abstract void writeTitleContent(HSSFSheet sheet, HSSFCellStyle cellStyle);

  /**
   * 正文单元格样式
   *
   * @param workbook 表对象
   */
  public abstract HSSFCellStyle createBodyCellStyle(HSSFWorkbook workbook);

}
