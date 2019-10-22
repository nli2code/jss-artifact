package com.chai.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class LGAAggStockDashboardExelGen extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse respones) throws Exception {
		try {
			JSONArray data = (JSONArray) model.get("lgaaggstockData");
			System.out.println("exel data   "+data);
			HSSFSheet worksheet = workbook.createSheet("dashboard report");
			int i=0;
			int count=0;
			for (i = 0; i < data.length(); i++) {
				System.out.println("row" + i);
				HSSFRow row = worksheet.createRow(i);
				JSONObject rowObject = (JSONObject) data.get(i);
				HSSFCell cell=row.createCell(0);
				cell.setCellValue(rowObject.getString("STATE_NAME"));
					if(!String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")).equals("0")){
						count++;
					}if(!String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")).equals("0")){
						count++;
					}if(!String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")).equals("0")){
						count++;	
					}
					HSSFCellStyle style=workbook.createCellStyle();
					if(count==1){
						worksheet.addMergedRegion(new CellRangeAddress(i, i, count, 3));
						HSSFCell cell1=row.createCell(1);
						if(!String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")).equals("0")){
							cell1.setCellValue(String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
							style.setFillForegroundColor(HSSFColor.GREEN.index);
						}else if(!String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")).equals("0")){
							cell1.setCellValue(String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")));
							style.setFillForegroundColor(HSSFColor.YELLOW.index);
						}else if(!String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")).equals("0")){
							cell1.setCellValue(String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
							style.setFillForegroundColor(HSSFColor.RED.index);
						}
						style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cell1.setCellStyle(style);
					}else if(count==2){
						HSSFCell cell1=row.createCell(1);
						HSSFCell cell2=row.createCell(2);
						HSSFCellStyle style1=workbook.createCellStyle();
						HSSFCellStyle style2=workbook.createCellStyle();
						if(!String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")).equals("0")
								&& !String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")).equals("0")){
							cell1.setCellValue(String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
							style1.setFillForegroundColor(HSSFColor.RED.index);
							style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cell1.setCellStyle(style1);
							cell2.setCellValue(String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")));
							style2.setFillForegroundColor(HSSFColor.YELLOW.index);
							style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cell2.setCellStyle(style1);
						}else if(!String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")).equals("0")
								&& !String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")).equals("0")){
							cell1.setCellValue(String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
							style1.setFillForegroundColor(HSSFColor.GREEN.index);
							style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cell1.setCellStyle(style1);
							cell2.setCellValue(String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")));
							style2.setFillForegroundColor(HSSFColor.YELLOW.index);
							style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cell2.setCellStyle(style1);
						}else if(!String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")).equals("0")
								&& !String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")).equals("0")){
							cell1.setCellValue(String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
							style1.setFillForegroundColor(HSSFColor.GREEN.index);
							style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cell1.setCellStyle(style1);
							cell2.setCellValue(String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
							style2.setFillForegroundColor(HSSFColor.RED.index);
							style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cell2.setCellStyle(style2);
						}
						worksheet.addMergedRegion(new CellRangeAddress(i, i, count, 3));
					}else if(count==3){
						HSSFCell cell1=row.createCell(1);
						HSSFCellStyle style1=workbook.createCellStyle();
						cell1.setCellValue(String.valueOf(rowObject.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
						style1.setFillForegroundColor(HSSFColor.GREEN.index);
						style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cell1.setCellStyle(style1);
						
						HSSFCell cell2=row.createCell(2);
						HSSFCellStyle style2=workbook.createCellStyle();
						cell2.setCellValue(String.valueOf(rowObject.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
						style2.setFillForegroundColor(HSSFColor.RED.index);
						style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cell2.setCellStyle(style2);
						
						HSSFCell cell3=row.createCell(3);
						HSSFCellStyle style3=workbook.createCellStyle();
						cell3.setCellValue(String.valueOf(rowObject.get("LESS_3_ANTIGENS_TOTAL_HF_PER")));
						style3.setFillForegroundColor(HSSFColor.YELLOW.index);
						style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cell3.setCellStyle(style3);
					}
					count=0;
			}
			System.out.println("leaving... excel builder");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
