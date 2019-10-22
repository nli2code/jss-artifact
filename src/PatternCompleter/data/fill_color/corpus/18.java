package util;

import bean.ExcelBean;
import common.Common;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xu on 2015/2/1.
 */
public class ExcelUtil {

    //标记单元格
    public static void signCell(Cell cell, CellStyle style, int mode) {
        if (mode == Common.ORANGE_MODE) {
            style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        } else if (mode == Common.YELLOW_MODE) {
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        } else if (mode == Common.AQUA_MODE) {
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        } else if (mode == Common.GREEN_MODE) {
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }

    //涂色
    public static void color(ExcelBean[] excelBeans) {

        try {
            for (ExcelBean excelBean : excelBeans) {
                FileOutputStream out = new FileOutputStream(excelBean.getFile());
                excelBean.getWorkbook().write(out);
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            MyLog.appendSpec(ex.getMessage());
        }

    }


    public static void main(String args[]) {
        String a = "艾利安人才服务(上海)有限公司";
        String b = "艾利安人才服务（上海）有限公司";
        System.out.println(a.equals(b));

    }
}
