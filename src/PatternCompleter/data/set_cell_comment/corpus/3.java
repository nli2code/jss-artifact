package sorinpo.scr.edu.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class POIUtilsTest {
	
	@Test
	public void createBoldFont(){
		XSSFWorkbook wb = new XSSFWorkbook();
		assertNotNull(POIUtils.boldCellStyle(wb));
	}
	
	@Test
	public void setCellComment(){
		XSSFWorkbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		Row row  = sheet.createRow(3);
	    Cell cell = row.createCell(5);
		
	    POIUtils.setCellComment(cell, "Comment", "Author");
	    
	    cell = sheet.getRow(3).getCell(5);
	    Comment comment = cell.getCellComment();
	    
	    assertEquals("Comment", comment.getString().getString());
		assertEquals("Author", comment.getAuthor());
	    
	    //  alternatively you can retrieve cell comments by (row, column)
	    comment = sheet.getCellComment(3, 5);
	    
		assertEquals("Comment", comment.getString().getString());
		assertEquals("Author", comment.getAuthor());
	}
	
	@Test
	public void setCellValueAs1BlankTrue(){
		XSSFWorkbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		Row row  = sheet.createRow(3);
	    Cell cell = row.createCell(5);
	    POIUtils.setCellValueAs1Blank(cell, true);
	    
	    assertEquals(true, POIUtils.getCellValueAs1Blank(cell));
	}
	
	@Test
	public void setCellValueAs1BlankFalse(){
		XSSFWorkbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		Row row  = sheet.createRow(3);
	    Cell cell = row.createCell(5);
	    POIUtils.setCellValueAs1Blank(cell, false);
	    
	    assertEquals(false, POIUtils.getCellValueAs1Blank(cell));
	}

	@Test(expected=IllegalArgumentException.class)
	public void setCellValueAs1BlankFail(){
		XSSFWorkbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		Row row  = sheet.createRow(3);
	    Cell cell = row.createCell(5);
	    cell.setCellValue("1");
	    POIUtils.getCellValueAs1Blank(cell);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setCellCommentNullCommentText(){
		XSSFWorkbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		Row row  = sheet.createRow(3);
	    Cell cell = row.createCell(5);
		
	    POIUtils.setCellComment(cell, null, "Author");
	    
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setCellCommentNullAuthor(){
		XSSFWorkbook wb = new XSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		Row row  = sheet.createRow(3);
	    Cell cell = row.createCell(5);
		
	    POIUtils.setCellComment(cell, "Comment", null);
	    
	}
}
