package me.js.apache.poi.sample;

import me.js.apache.poi.Excel;
import me.js.apache.poi.PoiHelper;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Hyperlink;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Jongsik Kim
 * @since 2017-01-17
 */
public class WriteExcel {
  public static void main(String[] args) throws Exception {

    Excel excel = PoiHelper.createExcel_xlsx();
    excel.createSheet("Sheet_1");
    excel.selectRow(1);

    Hyperlink hyperlink = excel.getCreationHelper().createHyperlink(HyperlinkType.URL);
    hyperlink.setAddress("http://www.naver.com/");

    excel.setCell(1, "테스트1");
    excel.setCell(2, true);
    excel.setCell(3, 123456);
    excel.setCell(4, 10000000000L);
    excel.setCell(5, -123456);
    excel.setCell(6, 123.456);
    excel.setCell(7, new Date());
    excel.setCell(8, Calendar.getInstance());
    excel.setCell(9, "네이버");
    excel.setCell(9, hyperlink);

    excel.write(new FileOutputStream("통합 _ 엑셀 _ 문서 _ 테스트.xlsx"));
  }
}
