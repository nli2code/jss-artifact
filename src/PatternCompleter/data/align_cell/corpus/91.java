package me.lq.moewxservice.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author LQ
 * @date 创建时间：2017年12月8日 下午4:42:56
 * @version
 *
 */
public class ExportExcelUtil {

	
	public void ExportXls(List list, OutputStream os, String[] colIds, String[] colNames) {
		Workbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = (HSSFSheet) wb.createSheet("sheet");

		HSSFRow row = sheet1.createRow(0);

		CellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		CellStyle style_right_align = wb.createCellStyle();
		style_right_align.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_right_align.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		CellStyle style_left_align = wb.createCellStyle();
		style_left_align.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_left_align.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		Font font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		// 生成表头
		{
			HSSFCell cell = null;
			for (int index = 0; index < colNames.length; index++) {
				cell = row.createCell(index);
				cell.setCellStyle(style);
				cell.setCellValue(colNames[index]);
			}
		}
		
		// 生成数据格子
		if(list != null && list.size()>0){
			style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
			
			String value = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int indexList = 0; indexList < list.size(); indexList++) {
				row = sheet1.createRow((short) indexList + 1);
				Object item = list.get(indexList);
				Map obj = item instanceof Map ? (Map) item : new BeanMap(item);
				for (int index = 0; index < colIds.length; index++) {
					Object o = obj.get(colIds[index]);
					CellStyle cellStyle = style;
					HSSFCell cell = row.createCell(index);
					if (o != null) {
//						String cname = colIds[index].toString();
						if (o instanceof Date) {
							if (StringUtils.isNotBlank(o.toString())) {
								// value=sdf.format(DateFormat.getDateInstance().parse(o.toString()))
								value = sdf.format(o);
							} else {
								value = "";
							}
						} else if (o instanceof Integer || o instanceof Long
								|| o instanceof BigDecimal
								|| o.getClass() == int.class
								|| o.getClass() == long.class
								|| o.getClass() == float.class) {
							cellStyle = style_right_align;
							value = String.valueOf(o);
						} else {
							cellStyle = style_left_align;
							value = String.valueOf(o).replace("<br>", "\r\n");
						}
					}
					// cell = row.createCell(index);
					try {
						Double v = Double.valueOf(value);
						cell.setCellValue(v);
					} catch (Exception e) {
						cell.setCellValue(value);
					}
					cell.setCellStyle(cellStyle);
					// 加快导出速度
					// sheet1.autoSizeColumn((short) index);
					value = "";
				}
			}
		}
		try {
			wb.write(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:

				BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
				if (bigDecimal.scale() > 0) {
					bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
				}

				strCell = bigDecimal.toString();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
			}
			if (strCell.equals("") || strCell == null) {
				return "";
			}
		}
		return strCell;
	}
}
