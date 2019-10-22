package unicorn.common.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 设置Title的单元格背景颜色.
 * 
 * @author 周峰
 * @version 1.0, 2014-10-22
 */
public class BackGroundColorStyle2Title extends CellStyleMaker {
  private ExcelCellStyle ecs = null;

  /**
   * 构造方法.
   * 
   * @param ecs 单元格样式
   */
  public BackGroundColorStyle2Title(ExcelCellStyle ecs) {
    this.ecs = ecs;
    if (ecs == null) {
      throw new InstantiationError("无法创建对象，传入数据不合法");
    }
  }

  @Override
  public CellStyle getStyle() {
    //ecs.getStyle().setFillForegroundColor((short) 44);// 设置背景色
    ecs.getStyle().setFillPattern(CellStyle.SOLID_FOREGROUND);
    return ecs.getStyle();
  }
  
  public void setBgColor(int color){
    ecs.getStyle().setFillForegroundColor((short) color);// 设置背景色
  }
}
