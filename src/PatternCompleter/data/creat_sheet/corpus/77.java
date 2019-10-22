package excel;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelNew {

	public static void main(String[] args) throws Exception {

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("srinu");
		// XSSFSheet sheet1 =
		// wb.createSheet(WorkbookUtil.createSafeSheetName("what!?"));

		Row rowvalue = sheet.createRow(0);
		Cell cellvalue = rowvalue.createCell(0);

		cellvalue.setCellValue("MynName");

		Cell cellvalue1 = sheet.getRow(0).createCell(1);

		cellvalue1.setCellValue("age");
		try {
			FileOutputStream fo = new FileOutputStream(new File("C:/Users/CHOWDARY/Desktop/sample2.xlsx"));
			wb.write(fo);
			fo.close();
			wb.close();
		} catch (Exception e) {
			System.out.println(e);
			;
		}

	}

}
