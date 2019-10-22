package poisamples;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import poisamples.util.Util;

public class SampleWorkbookOne {

	public static void main(String[] args) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("Sheet1"));
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("#");
		
		String content = cell.getRichStringCellValue().toString();
		System.out.println(content);
		
		for (int i=1; i<=10; i++ ){
			sheet.createRow(i).createCell(0).setCellValue(i);
		}
		
		
		for (int i=1; i<=10; i++ ){
			row.createCell(i).setCellValue(i);
		}
		
		Util.writeBook("SampleOne.xlsx", workbook);
	}
}
