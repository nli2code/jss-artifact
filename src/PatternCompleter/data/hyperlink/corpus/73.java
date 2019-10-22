import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LinkInsertionIntoExel {

	public static void main(String[] args) throws IOException {
		
		
		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();

	    CellStyle hlink_style = wb.createCellStyle();
	    Font hlink_font = wb.createFont();
	    hlink_font.setUnderline(Font.U_SINGLE);
	    hlink_style.setFont(hlink_font);

	    Cell cell;
	    Sheet sheet = wb.createSheet("Hyperlinks");
	    
	    cell = sheet.createRow(0).createCell(0);
	    cell.setCellValue("URL Link");
	    
	    Hyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
	    link.setAddress("http://poi.apache.org/");
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlink_style);
	    
	    FileOutputStream out = new FileOutputStream("hyperinks.xlsx");
	    wb.write(out);
	    out.close();
		

	}

}
