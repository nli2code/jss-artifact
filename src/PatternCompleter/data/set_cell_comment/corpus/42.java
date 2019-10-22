package com.liuhr.excel4j.excel.impl;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.liuhr.excel4j.processors.ExcelProcessor;
import com.liuhr.excel4j.util.ExcelUtils;
import com.liuhr.excel4j.util.StringUtils;

/**
 * default template creator
 * 
 * @author nc-wl001
 *
 */
public class DefaultTemplateCreator extends AbstractExporter {
	
	private final static String PROMPT_MESSAGE="�밴�մ�ʾ����д����д��ϣ���ɾ��ʾ�������е�Ԫ��!";

	public DefaultTemplateCreator() {
		super(new HSSFWorkbook());
	}
	
	public DefaultTemplateCreator(Workbook workbook) {
		super(workbook);
	}
	
	/* ���� Javadoc��
	 * @see com.liuhr.excel4j.excel.impl.DefaultExporter#createdHeaderCell(org.apache.poi.ss.usermodel.Cell, com.liuhr.excel4j.processors.ExcelProcessor)
	 */
	@Override
	protected void createdHeaderCell(Cell cell,ExcelProcessor excelProcessor) {		
		ExcelUtils.setCellComment(cell, StringUtils.merge(excelProcessor.comments()));
	}

	/* ���� Javadoc��
	 * 	set prompt
	 * 
	 * @see com.liuhr.excel4j.excel.impl.AbstractExporter#exportCompletedOf(org.apache.poi.ss.usermodel.Sheet)
	 */
	@Override
	protected void exportCompletedOf(Sheet sheet) {

		int headerRowIndex=this.getHeaderRowIndex();
		int lastRowNum=sheet.getLastRowNum();
		
		CellRangeAddress address = new CellRangeAddress(lastRowNum+1, lastRowNum + 3, 0,sheet.getRow(headerRowIndex).getLastCellNum()-1);
		sheet.addMergedRegion(address);

		Row promptRow = sheet.createRow(lastRowNum+1);		
		Cell promptCell = promptRow.createCell(0);
		promptCell.setCellType(Cell.CELL_TYPE_STRING);		
		promptCell.setCellValue(PROMPT_MESSAGE);
		Workbook workbook=sheet.getWorkbook();
		CellStyle cellStyle=ExcelUtils.createCellStyle(workbook,IndexedColors.GREY_25_PERCENT);
		Font font=ExcelUtils.createFont(workbook, IndexedColors.RED);
		font.setBold(true);
		cellStyle.setFont(font);
		promptCell.setCellStyle(cellStyle);

	}

	/* ���� Javadoc��
	 * @see com.liuhr.excel4j.excel.impl.AbstractExporter#createdHeaderRow(org.apache.poi.ss.usermodel.Row)
	 */
	@Override
	protected void createdHeaderRow(Row headerRow) {
		
	}

	/* ���� Javadoc��
	 * @see com.liuhr.excel4j.excel.impl.AbstractExporter#createdContentCell(org.apache.poi.ss.usermodel.Cell, com.liuhr.excel4j.processors.ExcelProcessor)
	 */
	@Override
	protected void createdContentCell(Cell cell, ExcelProcessor excelProcessor) {
		
	}

	/* ���� Javadoc��
	 * @see com.liuhr.excel4j.excel.impl.AbstractExporter#createdContentRow(org.apache.poi.ss.usermodel.Row)
	 */
	@Override
	protected void createdContentRow(Row contentRow) {
		
	}

	/* ���� Javadoc��
	 * @see com.liuhr.excel4j.excel.impl.AbstractExporter#exportCompleted(org.apache.poi.ss.usermodel.Workbook)
	 */
	@Override
	protected void exportCompleted(Workbook workbook) {

	}

}
