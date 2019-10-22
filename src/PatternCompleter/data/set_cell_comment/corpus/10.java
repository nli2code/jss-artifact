package com.cdvcloud.rms.util;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	@SuppressWarnings("rawtypes")
	public byte[] createNewExcel(List<Map<String, Object>> list, String titles, Map<String, Object> map, Map<String, Object> columnWidthsMap,
			String sheetName) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		CellStyle style = workbook.createCellStyle();
		Sheet sheet = workbook.createSheet(sheetName);

		/**
		 * 设置其它数据 设置风格
		 */
		style.setBorderBottom(CellStyle.BORDER_THIN); // 设置单无格的边框为粗体
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setWrapText(true);//文本区域随内容多少自动调整
		int cellNumber = list.get(0).size();
		int rowNumber = list.size();
		// 创建第一行
		Row row1 = sheet.createRow(0);
		String[] title = titles.split(",");
		for (int i = 0; i < title.length; i++) {
			Cell cell = row1.createCell(i);
			cell.setCellValue((String) map.get(title[i]));
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, (Integer) columnWidthsMap.get(title[i]));
		}
		for (int rown = 0; rown < rowNumber; rown++) {
			Row row = sheet.createRow(rown + 1);
			for (int celln = 0; celln < cellNumber; celln++) {
				Map cellMap = (Map) list.get(rown);
				Cell cell = row.createCell(celln);
				if (celln < 5) {
					String textValue = String.valueOf(cellMap.get(title[celln]));
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
							cell.setCellComment(null);
						} else {
							cell.setCellValue(textValue);
							cell.setCellComment(null);
						}
					}
					cell.setCellStyle(style);
				}
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] content = os.toByteArray();
		return content;
	}

}
