package com.itqf.lvyou.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 从数据库导出数据的工具类
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class ExcelUtils {

	
	/**根据tmpl在sheetTarget上第row行创建目标行
	 * @param tmpl
	 * @param sheet
	 * @param row
	 * @param setValue
	 * @return
	 */
	public static Row createRowByTmpl (Row tmpl, Sheet sheet, int row, Boolean setValue) {
		// 创建目标行
		Row rowTarget = sheet.createRow(row);
		// 设置高度
		rowTarget.setHeight(tmpl.getHeight());
		// 根据tmpl为目标行创建cell
		for(int i = 0; i < tmpl.getLastCellNum(); i ++) {
			// 为目标行创建单元格
			Cell cellTarget = rowTarget.createCell(i);
			// 将模板单元格中的雷荣克隆到目标单元格上
			cloneCell(tmpl.getCell(i), cellTarget, setValue);
		}
		return rowTarget;
	}
	
	/**
	 * 将tmpl的样式和值设置到target上
	 * @param tmpl
	 * @param target
	 * @param setValue 如果为true，表示需要设置值，否则不必
	 */
	private static void cloneCell (Cell tmpl, Cell target, Boolean setValue) {
		// 设置目标单元格类型
		target.setCellType(tmpl.getCellTypeEnum());
		// 设置目标单元格批注
		target.setCellComment(tmpl.getCellComment());
		if (!setValue) {
			return;
		}
		switch (tmpl.getCellTypeEnum()) {
			case BOOLEAN :
				target.setCellValue(tmpl.getBooleanCellValue());
				break;
			case NUMERIC :
				target.setCellValue(tmpl.getNumericCellValue());
				break;
			default:
				target.setCellValue(tmpl.getStringCellValue());
				break;
		}
	}
}
