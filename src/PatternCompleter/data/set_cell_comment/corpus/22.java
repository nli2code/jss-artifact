package sorinpo.scr.edu.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;



public class POIUtils {
	private static final int CELL_TRUE = 1;
	private static final String CELL_FALSE = "";
	
	public static void setCellComment(Cell cell, String commentText, String commentAuthor){
		
		if(commentText == null || commentAuthor == null)
			throw new IllegalArgumentException("commentText and commentAuthor are required");
		
		CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();
		Drawing drawing = cell.getSheet().createDrawingPatriarch();
		
	    // When the comment box is visible, have it show in a 3x3 space
	    ClientAnchor anchor = factory.createClientAnchor();
	    anchor.setCol1(cell.getColumnIndex());
	    anchor.setCol2(cell.getColumnIndex()+3);
	    anchor.setRow1(cell.getRowIndex());
	    anchor.setRow2(cell.getRowIndex()+3);

	    // Create the comment and set the text+author
	    Comment comment = drawing.createCellComment(anchor);
	    RichTextString str = factory.createRichTextString(commentText);
	    comment.setString(str);
	    comment.setAuthor(commentAuthor);
	    
	    // Assign the comment to the cell
	    cell.setCellComment(comment);
	}
	
	public static void setCellValueAs1Blank(Cell cell, Boolean value){
		
		if(value){
			cell.setCellValue(CELL_TRUE);
		} else {
			cell.setCellValue(CELL_FALSE);
		}

	}
	
	public static boolean getCellValueAs1Blank(Cell cell){
		
		if(cell.getCellType() == Cell.CELL_TYPE_BLANK || 
				(cell.getCellType() == Cell.CELL_TYPE_STRING && "".equals(cell.getStringCellValue()))){
			return false;
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && (1==cell.getNumericCellValue())){
			return true;
		} else {
			throw new IllegalArgumentException("the cell is not a known boolean type");
		}
		
	}
	
	public static CellStyle boldCellStyle(Workbook wb){
		CellStyle cellStyle = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		return cellStyle;
	}	
}
