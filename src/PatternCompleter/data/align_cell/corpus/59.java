package pers.silonest.component.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * 文本对齐方式的样式.装饰模式，基类为CellStyleMaker，是一个单元格样式生成器。调用该类可以为原始样式提供全文的文本居中对齐格式。
 * 
 * @author 陈晨
 * @time 2014年12月12日
 * @version v1.0.0
 * @since v0.0.1
 */
public class AlignStyle extends CellStyleMaker {
  private ExcelCellStyle ecs = null;

  /**
   * 构造方法.
   * 
   * @param ecs 单元格样式
   */
  public AlignStyle(ExcelCellStyle ecs) {
    this.ecs = ecs;
    if (ecs == null) {
      throw new InstantiationError("Illegal data,Can't to create object.");
    }
  }

  @Override
  public CellStyle getStyle() {
    ecs.getStyle().setAlignment(HorizontalAlignment.CENTER);
    ecs.getStyle().setVerticalAlignment(VerticalAlignment.CENTER);
    return ecs.getStyle();
  }
}
