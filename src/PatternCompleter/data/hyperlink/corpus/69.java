import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SetEmail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		
		
		CellStyle hlink_style =wb.createCellStyle();
		Font hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);
		
		Cell cell ;
		Sheet sheet = wb.createSheet();
		//URL
		cell = sheet.createRow(0).createCell(0);
		cell.setCellValue("601097836@qq.com");
		
		Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
		
		link.setAddress("mailto:601097836@qq.com?subject=Hyperlinks");
		cell.setHyperlink(link);
		cell.setCellStyle(hlink_style);
		try {
			FileOutputStream fileout = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\123\\setEmail.xlsx");
			wb.write(fileout);
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
