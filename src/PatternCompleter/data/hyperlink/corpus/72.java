package poi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HyperlinkDemo {

	public static void main(String[] args) {
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet("Test Report");
        
	           
         XSSFCreationHelper createHelper = wb.getCreationHelper();
         XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
        link.setAddress("E:/Photos/IMG_20141011_212408910.jpg");
        
        //Creating the first row
        XSSFRow r =ws.createRow(0);
        
        //Creating First cell
        XSSFCell c = r.createCell(1);
        
        //Setting the text to the cell
        c.setCellValue("Hyperlink");
        
        //Setting the hyperlink to the cell
        c.setHyperlink(link);
        
        //Adding the style to the cell
        XSSFCellStyle hlink_style = wb.createCellStyle();
        XSSFFont hlink_font = wb.createFont();
        hlink_font.setUnderline(XSSFFont.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);
        
        c.setCellStyle(hlink_style);
         
         try 
 		{
 			//Write the workbook in file system
 		    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Dell\\workspace\\NgPoi\\reportHyperlinkDemo.xlsx"));
 		    wb.write(out);
 		    out.close();
 		    
 		    System.out.println("Report.xlsx written successfully on disk.");
 		     
 		} 
 		catch (Exception e) 
 		{
 		    e.printStackTrace();
 		}
        
	}

}
