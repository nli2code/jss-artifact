package by.matskevich.menuturist.bean;

import by.matskevich.menuturist.persistence.DatabaseManager;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author a_matskevich
 */
@Named("ExportUtils")
@SessionScoped
public class ExportUtils implements Serializable {

    @EJB
    DatabaseManager dbm;
    public void postProcessXLS(Object document) {

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);


        HSSFFont f1 = wb.createFont();
        f1.setFontName("Arial");
        f1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        //processing data
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(f1);
        cellStyle.setBorderBottom(Short.valueOf("1"));
        cellStyle.setBorderTop(Short.valueOf("1"));
        cellStyle.setBorderLeft(Short.valueOf("1"));
        cellStyle.setBorderRight(Short.valueOf("1"));
      
        HSSFFont f2 = wb.createFont();
        f2.setFontName("Arial");
        f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setFont(f2);
        headerStyle.setBorderBottom(Short.valueOf("1"));
        headerStyle.setBorderTop(Short.valueOf("1"));
        headerStyle.setBorderLeft(Short.valueOf("1"));
        headerStyle.setBorderRight(Short.valueOf("1"));

        for (Row row : sheet) {

            for (Cell cell : row) {
                cell.setCellValue(cell.getStringCellValue().toUpperCase());
                if (row.getRowNum() == 0) {
                    cell.setCellStyle(headerStyle);
                    sheet.autoSizeColumn(cell.getColumnIndex());
                } else {
                    cell.setCellStyle(cellStyle);
                }
            }

        }
    }
}