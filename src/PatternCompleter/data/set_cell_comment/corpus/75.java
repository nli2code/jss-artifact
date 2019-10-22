package jp.que.ti.ysu.xls;

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

class WrappedCell<CELL extends Cell> implements Cell {
	protected CELL wrappedCell;

	public WrappedCell(CELL cell) {
		this.wrappedCell = cell;

	}

	// protected Cell cell() {
	// return wrappedCell;
	// }

	@Override
	public Sheet getSheet() {
		return wrappedCell.getSheet();
	}

	@Override
	public Row getRow() {
		return wrappedCell.getRow();
	}

	@Override
	public int getRowIndex() {
		return wrappedCell.getRowIndex();
	}

	// @deprecated
	// public short getCellNum() {
	// return cell.getCellNum();
	// }

	@Override
	public int getColumnIndex() {
		return wrappedCell.getColumnIndex();
	}

	@Override
	public int getCellType() {
		return wrappedCell.getCellType();
	}

	@Override
	public String getCellFormula() {
		return wrappedCell.getCellFormula();
	}

	@Override
	public double getNumericCellValue() {
		return wrappedCell.getNumericCellValue();
	}

	@Override
	public Date getDateCellValue() {
		return wrappedCell.getDateCellValue();
	}

	@Override
	public String getStringCellValue() {
		return wrappedCell.getStringCellValue();
	}

	@Override
	public RichTextString getRichStringCellValue() {
		return wrappedCell.getRichStringCellValue();
	}

	@Override
	public boolean getBooleanCellValue() {
		return wrappedCell.getBooleanCellValue();
	}

	@Override
	public byte getErrorCellValue() {
		return wrappedCell.getErrorCellValue();
	}

	@Override
	public CellStyle getCellStyle() {
		return wrappedCell.getCellStyle();
	}

	@Override
	public Comment getCellComment() {
		return wrappedCell.getCellComment();
	}

	@Override
	public Hyperlink getHyperlink() {
		return wrappedCell.getHyperlink();
	}

	@Override
	public int getCachedFormulaResultType() {
		return wrappedCell.getCachedFormulaResultType();
	}

	@Override
	public CellRangeAddress getArrayFormulaRange() {
		return wrappedCell.getArrayFormulaRange();
	}

	// @Deprecated
	// public void setCellNum(short num) {
	// cell.setCellNum(num);
	// }

	@Override
	public void setCellType(int cellType) {
		wrappedCell.setCellType(cellType);
	}

	@Override
	public void setCellValue(double value) {
		wrappedCell.setCellValue(value);
	}

	@Override
	public void setCellValue(Date value) {
		wrappedCell.setCellValue(value);
	}

	@Override
	public void setCellValue(Calendar value) {
		wrappedCell.setCellValue(value);
	}

	@Override
	public void setCellValue(String value) {
		wrappedCell.setCellValue(value);
	}

	@Override
	public void setCellValue(RichTextString value) {
		wrappedCell.setCellValue(value);
	}

	@Override
	public void setCellFormula(String formula) {
		wrappedCell.setCellFormula(formula);
	}

	@Override
	public void setCellValue(boolean value) {
		wrappedCell.setCellValue(value);
	}

	@Override
	public void setCellErrorValue(byte errorCode) {
		wrappedCell.setCellErrorValue(errorCode);
	}

	@Override
	public void setCellStyle(CellStyle style) {
		wrappedCell.setCellStyle(style);
	}

	@Override
	public void setAsActiveCell() {
		wrappedCell.setAsActiveCell();
	}

	@Override
	public void setCellComment(Comment comment) {
		wrappedCell.setCellComment(comment);
	}

	@Override
	public void removeCellComment() {
		wrappedCell.removeCellComment();
	}

	@Override
	public void setHyperlink(Hyperlink hyperlink) {
		wrappedCell.setHyperlink(hyperlink);
	}

	@Override
	public boolean isPartOfArrayFormulaGroup() {
		return wrappedCell.isPartOfArrayFormulaGroup();
	}

}
