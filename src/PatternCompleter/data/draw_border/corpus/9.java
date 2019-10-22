package wlg.javaapi.excel.demo;

import java.io.InputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

@Service
public class ExcelUtil {
  
  public static Workbook open(InputStream input) throws Exception {
    return WorkbookFactory.create(input);
  }

  public static void setBorder(CellStyle cs) {
    cs.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
    cs.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
    cs.setBorderTop(CellStyle.BORDER_THIN);// 上边框
    cs.setBorderRight(CellStyle.BORDER_THIN);// 右边框
  }
}
