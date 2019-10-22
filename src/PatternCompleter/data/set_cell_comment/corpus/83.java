package com.ibm.spe.comparetool4.bigxlsx;

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 用于屏蔽不用的方法
 * @author Luis
 */
public abstract class SpeCellAdapter implements Cell {

	@Override
	public CellRangeAddress getArrayFormulaRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBooleanCellValue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCachedFormulaResultType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Comment getCellComment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCellFormula() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CellStyle getCellStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDateCellValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getErrorCellValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Hyperlink getHyperlink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RichTextString getRichStringCellValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Row getRow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sheet getSheet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPartOfArrayFormulaGroup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeCellComment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsActiveCell() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellComment(Comment arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellErrorValue(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellFormula(String arg0) throws FormulaParseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellStyle(CellStyle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellValue(Date arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellValue(Calendar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellValue(RichTextString arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCellValue(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHyperlink(Hyperlink arg0) {
		// TODO Auto-generated method stub
		
	}

}
