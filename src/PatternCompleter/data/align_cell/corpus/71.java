package com.zhukm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

public class ExportUtils {
	/**
	 * 将JTable里面的数据导出到excel表格中
	 * 
	 * @param table
	 *            JTable表格
	 * @param fileName
	 *            excel表格文件名
	 */
	public static void ExportAsExcel(JTable table, String fileName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		File f = null;
		try {
			f = new File("D:\\"
					+ table.getValueAt(0, table.getColumnCount() / 2) + ".xls");
			if (!f.exists()) {
				f.createNewFile();
				FileOutputStream fileOut = new FileOutputStream(f);

				HSSFSheet sheet = wb.createSheet();
				int r = table.getRowCount();
				int c = table.getColumnCount();
				for (int i = 0; i < r; i++) {
					HSSFRow row = sheet.createRow(i);
					for (int j = 0; j < c; j++) {
						if (i == 0) {
							HSSFCellStyle style = wb.createCellStyle();
							style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
							style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
							style.setFillForegroundColor(HSSFColor.GREEN.index);
							style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							sheet.addMergedRegion(new Region(0, (short) 0, 0,
									(short) (c - 1)));
							HSSFCell cell = row.createCell(0);
							cell.setCellStyle(style);
							cell.setCellValue((String) table.getValueAt(0,
									c / 2));
							break;
						} else {
							HSSFCell cell = row.createCell(j);
							cell.setCellValue((String) table.getValueAt(i, j));
						}
					}

				}

				wb.write(fileOut);
				fileOut.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
