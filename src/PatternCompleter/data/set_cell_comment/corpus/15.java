package com.zengrd.mall.util.excel;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtil {
	/**
	 * 获取excel表格的值
	 * @param cell
	 * @return
	 */
	public static String getExcelCellValue(Cell cell){
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
	
	/**
	 * 获取excel表格的值
	 * @param cell
	 * @return
	 */
	public static String getExcelCellValue(Row row, int colIndex){
		Cell cell = row.getCell(colIndex);
		if(cell == null){
			return null;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
	
	/**
	 * @param row
	 * @param colIndex
	 * @return
	 */
	public static Cell getCell(Row row, int colIndex){
		Cell cell = row.getCell(colIndex);
		return ( cell== null) ? row.createCell(colIndex) : cell;
	}
	
	/**
	 * 增加批注
	 * @param cell
	 * @param msg
	 */
	public static void addComment(Cell cell, String msg){
		// 工作表绘制批注
		HSSFPatriarch patriarch = (HSSFPatriarch) cell.getSheet().createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) (cell.getColumnIndex() + 1), 2, (short) (cell.getColumnIndex() + 4), (msg.split("\n").length + 4));
		HSSFComment comment = patriarch.createComment(anchor);
		comment.setString(new HSSFRichTextString(msg));
		cell.setCellComment(comment);
	}
}
