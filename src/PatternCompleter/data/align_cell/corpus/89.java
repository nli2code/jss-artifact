package com.ibm.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

public class FillDetails {

    public static void fillDataSheet(HSSFSheet worksheet, List<List<String>> dataValues) {
			   
		  // Create cell style for the body
		  HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		  bodyCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		  bodyCellStyle.setWrapText(false);
		  HSSFRow row = null;
		  int index =0;
		  if(dataValues!=null){
			  for (int i=0; i<dataValues.size(); i++) {
				  row = worksheet.createRow(++index);
            	  row.setRowStyle(bodyCellStyle);
            	  List<String> values = dataValues.get(i);
            	  if(values!=null){
                	  for (int j=0; j<values.size(); j++) {
                		  HSSFCell cell = row.createCell(j);
                		  cell.setCellValue(values.get(j));
                		  cell.setCellStyle(bodyCellStyle);
                	  }
            	  }
			  }
		  }
    }

}
