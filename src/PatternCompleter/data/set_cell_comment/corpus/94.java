package com.will.framework.util.tool.execl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class SimpleExcelWriter implements ExcelWriter {
	private ExcelReader		reader;						//reader
															
	private Workbook		wb;							// workbook
															
	private Sheet			sheet;							// sheet
															
	private CreationHelper	factory;
	
	private String			SHEET_NAME_PREFIX	= "sheet";
	
	private int				alreadyDelRowNum;
	
	public SimpleExcelWriter() {
		this(ExcelVersion.VER_2003);
	}
	
	public SimpleExcelWriter(ExcelVersion version) {
		switch (version) {
			case VER_2003:
				wb = new HSSFWorkbook();
				break;
			default:
			case VER_2007:
				wb = new XSSFWorkbook();
				break;
		}
		init();
	}
	
	/**
	 * 根据in 创建一个 wookbook
	 * 
	 * 构建一个<code>SimpleExcelWrite.java</code>
	 * @param in
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public SimpleExcelWriter(ExcelReader er) {
		this.reader = er;
		wb = er.getWorkbook();
		init();
	}
	
	@Override
	public <T> void fillSheet(String[] headerInfo, List<T> list, Map<String, String> propertyMap) {
		if (CollectionUtils.isEmpty(list))
			return;
		cleanSheet();
		fillHeaderInfo(headerInfo);
		
		for (int i = 0; i < list.size(); i++) {
			int rn = i + 1;
			T ss = list.get(i);
			List<Object> lp = convertBeanToList(headerInfo, ss, propertyMap);
			setRowValue(rn, lp);
		}
	}
	
	@Override
	public <T> void fillSheet(String[] headerInfo, List<T> list) {
		if (CollectionUtils.isEmpty(list))
			return;
		// 根据headerInfo 把 t的所有 title转换成 属性数组
		Map<String, String> propertyMap = getPropertyMap(list.get(0));
		fillSheet(headerInfo, list, propertyMap);
	}
	
	@Override
	public void fillSheetStr(String[] headerInfo, List<String[]> list) {
		cleanSheet();
		for (int i = 0; i < list.size(); i++) {
			int rn = i + 1;
			String[] ss = list.get(i);
			fillHeaderInfo(headerInfo);
			setRowValue(rn, ss);
		}
	}
	
	@Override
	public <T> void setCellValue(int row, int cell, T t) {
		if (t == null)
			return;
		Row r = sheet.getRow(row);
		if (r == null) {
			r = sheet.createRow(row);
		}
		Cell c = r.getCell(cell);
		if (c == null) {
			c = r.createCell(cell);
		}
		try {
			String simpleName = t.getClass().getSimpleName();
			
			CellStyle cellStyle = wb.createCellStyle();
			if ("String".equals(simpleName)) {
				
				c.setCellStyle(cellStyle);
				c.setCellValue((String) t);
			} else if ("Double".equals(simpleName)) {
				c.setCellValue((Double) t);
			} else if ("Date".equals(simpleName)) {
				cellStyle.setDataFormat(factory.createDataFormat().getFormat("yyyy-MM-dd"));
				cellStyle.setHidden(false);
				c.setCellStyle(cellStyle);
				c.setCellValue((Date) t);
			} else if ("Calendar".equals(simpleName)) {
				c.setCellValue(((Calendar) t).getTime());
			} else if ("Boolean".equals(simpleName)) {
				c.setCellValue((Boolean) t);
			} else {
				c.setCellValue(t.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setCellErrorComment(int row, int cell, String msg) {
		if(row<0||cell<0){
			return;
		}
		row = row - alreadyDelRowNum;
		Row r = sheet.getRow(row);
		if (r == null) {
			r = sheet.createRow(row);
		}
		Cell c = r.getCell(cell);
		if (c == null) {
			c = r.createCell(cell);
		}
		setCellComment(r, c, msg);
		setCellErrorBackground(c);
	}
	
	private void setCellComment(Row r, Cell c, String msg) {
		//添加注释
		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(c.getColumnIndex());
		anchor.setCol2(c.getColumnIndex() + 1);
		anchor.setRow1(r.getRowNum());
		anchor.setRow2(r.getRowNum() + 2);
		
		Drawing drawing = sheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString(msg);
		comment.setString(str);
		comment.setAuthor("易积购");
		
		c.setCellComment(comment);
	}
	
	private void setCellErrorBackground(Cell c) {
		//设置背景红色
		CellStyle style = wb.createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setWrapText(true);
		style.setLocked(false);
		c.setCellStyle(style);
	}
	
	@Override
	public void setRowErrorComment(int row, String msg) {
		row = row - alreadyDelRowNum;
		Row r = sheet.getRow(row);
		int firstIndex = 0;
		Cell firstc = r.getCell(firstIndex);
		if (firstc == null) {
			firstc = r.createCell(firstIndex);
		}
		setCellComment(r, firstc, msg);
		for (short i = 0; i < r.getLastCellNum(); i++) {
			Cell c = r.getCell(i);
			if (c == null) {
				c = r.createCell(i);
			}
			setCellErrorBackground(c);
		}
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		wb.write(out);
	}
	
	
	@Override
	public int delRow(int rowIndex) {
		rowIndex = rowIndex - alreadyDelRowNum++;
		int lastRowNum = sheet.getLastRowNum();
		//TODO 删除行 在莫些情况下有问题.
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
		return alreadyDelRowNum;
	}
	
	@Override
	public int getColIndex(String propertName) {
		if (this.reader == null) {
			throw new ExcelException("只有复制了 reader 的writer 可以得到列信息");
		}
		return this.reader.getColIndex(propertName);
	}
	
	@Override
	public int getLastColIndex() {
		if (this.reader == null) {
			throw new ExcelException("只有复制了 reader 的writer 可以得到列信息");
		}
		return this.reader.getLastColIndex();
	}
	
	private void init() {
		factory = wb.getCreationHelper();
		setWorkSheet(1);
		initDelNum();
	}
	
	private void initDelNum() {
		alreadyDelRowNum = 0;
	}
	
	private void setWorkSheet(int index) {
		Sheet t = null;
		try {
			t = wb.getSheetAt(index - 1);
		} catch (Exception e) {
			t = wb.createSheet(SHEET_NAME_PREFIX + index);
		}
		if (t == null) {
			throw new ExcelException("excel第" + index + "页为空");
		}
		sheet = t;
	}
	
	private void cleanSheet() {
		for (int i = sheet.getLastRowNum(); i > 0; i--) {
			removeRow(i);
		}
		initDelNum();
		
	}
	
	private <T> Map<String, String> getPropertyMap(T t) {
		Map<String, String> propertyMap = new HashMap<String, String>();
		for (Field f : t.getClass().getDeclaredFields()) {
			if (!PropertyUtils.isReadable(t, f.getName())) {
				continue;
			}
			Title tt = f.getAnnotation(Title.class);
			if (null == tt) {
				continue;
			}
			propertyMap.put(tt.value(), f.getName());
			
		}
		return propertyMap;
	}
	
	private <T> List<Object> convertBeanToList(String[] headerInfo, T t,
												Map<String, String> propertyMap) {
		List<Object> list = new ArrayList<Object>();
		
		for (String s : headerInfo) {
			String propertyName = propertyMap.get(s);
			try {
				list.add(PropertyUtils.getProperty(t, propertyName));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
		}
		return list;
	}
	
	private void fillHeaderInfo(String[] headerInfo) {
		setRowValue(0, headerInfo);
	}
	
	private void setRowValue(int row, List<Object> t) {
		for (int i = 0; i < t.size(); i++) {
			setCellValue(row, i, t.get(i));
		}
		
	}
	
	private void setRowValue(int row, String[] ss) {
		for (int i = 0; i < ss.length; i++) {
			setCellValue(row, i, ss[i]);
		}
	}
	
	private void removeRow(int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}

	@Override
	public void setLastCellErrorComment(int row,int lastCell, String msg) {
		if(row<0){
			return;
		}
		Row firstRow =  sheet.getRow(sheet.getFirstRowNum());
		Cell tipCell = firstRow.getCell(lastCell);
		if(tipCell == null){
			tipCell = firstRow.createCell(lastCell);
			tipCell.setCellValue("错误信息");
			setCellErrorBackground(tipCell);
		}
		
		row = row - alreadyDelRowNum;
		Row errRow = sheet.getRow(row);
		if (errRow == null) {
			errRow = sheet.createRow(row);
		}
		Cell cell = errRow.createCell(lastCell);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		setCellErrorBackground(cell);
		cell.setCellValue(new HSSFRichTextString(msg));
		
	}
	
}
