package com.ctrip.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRemark {
	public static int remarkIndex = 0;
	public static CellStyle cellstyle = null;
	public static CreationHelper helper = null;
	public static XSSFSheet currentSheet = null;
	
	public static void initStatic(XSSFWorkbook workbook)
	{
		helper = workbook.getCreationHelper();
		cellstyle = workbook.createCellStyle();
		cellstyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
		cellstyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
	}
	/**
	 * �����ɫ���
	 * @param cell
	 * @param comment
	 */
	public static void bgColorRemark(Cell cell)
	{
		cell.setCellStyle(cellstyle);
		remarkIndex++;
	}
	/**
	 * ���comment
	 * @param cell
	 * @param commentStr
	 * @param width
	 * @param height
	 */
	public static void commentRemark(Cell cell,String commentStr,int width,int height)
	{
		if(width == 0) width = 2;
		if(height == 0) height = 2;
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex()+width);
		anchor.setRow1(cell.getRowIndex());
		anchor.setRow2(cell.getRowIndex()+height);
		Drawing drawing = currentSheet.createDrawingPatriarch();
		RichTextString richTextString = helper.createRichTextString(commentStr);
		Comment comment = drawing.createCellComment(anchor);
		comment.setString(richTextString);
		comment.setAuthor("glf���鷼");
		cell.setCellComment(comment);
		remarkIndex++;
	}
	/**
	 * ������ʶ+��ע
	 * @param cell
	 * @param commentStr
	 * @param width
	 * @param height
	 */
	public static void cellRemark(Cell cell,String commentStr,int width,int height)
	{
		bgColorRemark(cell);
		commentRemark(cell,commentStr,width,height);
	}
}
