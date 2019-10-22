package xlsx.tutorial100;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

/**
 * 调整单元格的格式
 * @author ECNUJason
 *
 */
public class XlsxWriteAdvance {
	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// 单元格合并
		XSSFSheet sheet = workbook.createSheet("cellstyle");
		XSSFRow row = sheet.createRow(1);
		XSSFCell cell = row.createCell(1);
		cell.setCellValue("test of merging");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 5000);

		// 设置单元格的对齐
		row = sheet.createRow(5);
		row.setHeight((short) 800);
		cell = row.createCell(0);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		cell.setCellStyle(style);
		cell.setCellValue("Top left");

		row = sheet.createRow(6);
		row.setHeight((short) 600);
		cell = row.createCell(1);
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cell.setCellValue("Center Aligned");
		cell.setCellStyle(style2);

		row = sheet.createRow(7);
		cell = row.createCell(2);
		XSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		style3.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
		cell.setCellValue("Bottom right");
		cell.setCellStyle(style3);
		row.setHeight((short) 800);

		sheet.setColumnWidth(3, 3000);
		row = sheet.createRow(8);
		cell = row.createCell(3);
		XSSFCellStyle style4 = workbook.createCellStyle();
		style4.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);    // 两端对齐
		style4.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY); // 上下对齐
		cell.setCellStyle(style4);
		cell.setCellValue("Contents are justifyed in Alignment");
		row.setHeight((short) 1000);

		
		
		row = sheet.createRow(9);
		cell = row.createCell(1);
		XSSFCellStyle style5 = workbook.createCellStyle();
		style5.setBorderTop(BorderStyle.DASH_DOT);
		style5.setTopBorderColor(IndexedColors.RED.getIndex());
		cell.setCellValue("border test");
		cell.setCellStyle(style5);
		
		
		
		
		
		
		FileOutputStream fileOutputStream = new FileOutputStream(new File("xlsxRepo.xlsx"));
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		workbook.close();
		System.out.println("xlsxRepo.xlsx write successfully.");

	}
}
