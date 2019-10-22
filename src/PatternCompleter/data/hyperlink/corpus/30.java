package excel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;

public class AdvacenWrite4 {

	public static void main(String[] args) throws Exception  {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet spreadsheet = workbook.createSheet("Hyperlinks");
		HSSFCell cell;
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFCellStyle hlinkstyle = workbook.createCellStyle();
		HSSFFont hlinkfont = workbook.createFont();
		hlinkfont.setUnderline(HSSFFont.U_SINGLE);
		hlinkfont.setColor(HSSFColor.BLUE.index);
		hlinkstyle.setFont(hlinkfont);
		// URL Link
		cell = spreadsheet.createRow(1).createCell((short) 1);
		cell.setCellValue("URL Link");
		HSSFHyperlink link = (HSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
		link.setAddress("http://www.baidu.com/");
		cell.setHyperlink((HSSFHyperlink) link);
		cell.setCellStyle(hlinkstyle);
		// Hyperlink to a file in the current directory
		cell = spreadsheet.createRow(2).createCell((short) 1);
		cell.setCellValue("File Link");
		link = (HSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
		link.setAddress("c:/07.24-file/hyperlink.xls");
		cell.setHyperlink(link);
		cell.setCellStyle(hlinkstyle);
		// e-mail link
		cell = spreadsheet.createRow(3).createCell((short) 1);
		cell.setCellValue("Email Link");
		link = (HSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
		link.setAddress("mailto:contact@haha.com?" + "subject=Hyperlink");
		cell.setHyperlink(link);
		cell.setCellStyle(hlinkstyle);
		FileOutputStream out = new FileOutputStream(new File("c:/07.24-file/hyperlink.xls"));
		workbook.write(out);
		out.close();
		System.out.println("hyperlink.xlsx written successfully");

	}

}
