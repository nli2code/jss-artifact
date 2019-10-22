package com.chai.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.json.JSONArray;
import org.json.JSONObject;
import com.chai.util.ExcelStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.chai.model.LabelValueBean;

public class HFStockSummarySheetExcelGen extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			List<LabelValueBean> productlist=(List<LabelValueBean>) model.get("productListWithCustomerName");
			JSONArray data = (JSONArray) model.get("export_data");
			System.out.println("exel data in json" + data);
			HSSFSheet worksheet = workbook.createSheet("dashboard report");
			HSSFRow rowHeader=worksheet.createRow(0);
			int i=0;
			for (LabelValueBean product : productlist) {
				HSSFCell cell=rowHeader.createCell(i);
				cell.setCellValue(product.getLabel());
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyle.setBorderStyle(cellStyle);
				cell.setCellStyle(cellStyle);
				i++;
			}
			for (i = 0; i < data.length(); i++) {
			//	System.out.println("row" + i);
				HSSFRow row = worksheet.createRow(i+1);
				JSONObject rowObject = (JSONObject) data.get(i);
				HSSFCell cell=row.createCell(0);
				cell.setCellValue(rowObject.getString("CUSTOMER_NAME"));
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				ExcelStyle.setBorderStyle(cellStyle);
				cell.setCellStyle(cellStyle);
				int j=0;
				for (LabelValueBean product : productlist) {
					Iterator<?> keys = rowObject.keys();
			    	while(keys.hasNext()){
			    		String key=(String)keys.next();
			    		if(key.contains(product.getLabel())){
			    			HSSFCell cell1=row.createCell(j);
			    			JSONObject obj=(JSONObject) rowObject.get(key);
			    			cell1.setCellValue((int)Double.parseDouble(obj.getString("STOCK_BALANCE")));
			    			HSSFCellStyle cellStyle1 = workbook.createCellStyle();
			    			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    			ExcelStyle.setBorderStyle(cellStyle1);
			    			switch (obj.getString("LEGEND_COLOR")) {
							case "#00B050": // for green
								cellStyle1.setFillForegroundColor((short) 17);
								cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								break;
							case "#FF0000":// for red
								cellStyle1.setFillForegroundColor((short) 10);
								cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								break;
							case "red":// for red
								cellStyle1.setFillForegroundColor((short) 10);
								cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								break;
							case "#FFC000": // for yellow
								cellStyle1.setFillForegroundColor((short) 13);
								cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								break;
							default:
								cellStyle1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
								cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								break;
							}
			    			cell1.setCellStyle(cellStyle1);
			    			break;
			    		}
			    	}
			    	j++;
				}

			}
			System.out.println("leaving... excel builder");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
