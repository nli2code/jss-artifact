package com.liuhr.excel4j.util;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class ExcelUtils {
	
	/**
	 * the test row is empty
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isEmptyRow(Row row) {
		if (row == null) {
			return true;
		}
		Iterator<Cell> cellbody = row.cellIterator();
		while (cellbody.hasNext()) {
			Cell cell = cellbody.next();
			if (cell != null && !cell.toString().equals("")) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * @param cell
	 * @param comment
	 */
	public static void setCellComment(Cell cell,String comment){
		if(StringUtils.isEmpty(comment)){
			return;
		}
		ClientAnchor  clientAnchor;
		RichTextString richTextString;
		if(cell instanceof HSSFCell){
			clientAnchor=new HSSFClientAnchor(0, 0, 0, 0,(short) 0, 0, (short) 3, 8);
			richTextString=new HSSFRichTextString(comment);
		}else if(cell instanceof XSSFCell){
			clientAnchor=new XSSFClientAnchor(0, 0, 0, 0,(short) 0, 0, (short) 3, 8);
			richTextString=new XSSFRichTextString(comment);
		}else{
			return ;
		}
		Drawing drawing = cell.getSheet().createDrawingPatriarch();
		Comment _comment = drawing.createCellComment(clientAnchor);
		_comment.setString(richTextString);
		cell.setCellComment(_comment);
	}
	
	/**
	 * @param fontColor
	 * @param cellForegroundColor
	 * @return
	 */
	public static CellStyle createCellStyle(Workbook workbook, IndexedColors cellForegroundColor){
		CellStyle cellStyle = workbook.createCellStyle();
		// ָ����Ԫ����ж���
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// ָ����Ԫ��ֱ���ж���
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);	
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(cellForegroundColor.index);
		return cellStyle;
	}
	
	/**
	 * @param workbook
	 * @param fontColor
	 * @return
	 */
	public static Font createFont(Workbook workbook, IndexedColors fontColor){
		Font font = workbook.createFont();
		font.setColor(fontColor.index);
		font.setFontName("����");
		return font;
	}
	
	/**
	 * @param columnIndex
	 * @param rowIndex
	 * @return
	 */
	public static String getCellLocation(int columnIndex,int rowIndex){
		 return CellReference.convertNumToColString(columnIndex)+""+(rowIndex+1);
	}
	
}
