package com.helper;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataConfig {
	ArrayList<String> list;
	XSSFWorkbook objExcel = null;
	XSSFSheet sheetobj;

	public DataConfig(String excelPath) {
		try {
			File src = new File(excelPath);

			FileInputStream fis = new FileInputStream(src);

			objExcel = new XSSFWorkbook(fis);
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<String> fetchData(String sheetName) {
		list = new ArrayList<String>();
		sheetobj = objExcel.getSheet(sheetName);
		int row = objExcel.getSheet(sheetName).getLastRowNum();
		int col = sheetobj.getRow(0).getPhysicalNumberOfCells();

		for (int i = 1; i < row + 1; i++) {
			for (int j = 0; j < col; j++) {
				DataFormatter fmt = new DataFormatter();
				String val = fmt.formatCellValue(sheetobj.getRow(i).getCell(j));
				list.add(val);
			}
		}

		return list;
	}

	public String getDate() {
		DateFormat df = new SimpleDateFormat("dd_MMM_yyyy h:mm");
		df.setTimeZone(TimeZone.getTimeZone("IST"));
		return df.format(new Date());
	}
    public void WritingToExcelResults1(String action, String expectedResult,String position, int rownumber, HSSFWorkbook workbook,  HSSFSheet worksheet, boolean firstRow )
    {
          try {

                
                //****************************For Row Headings*******************//
                if(firstRow == true)
                {  

                      HSSFRow headerDataRow = worksheet.createRow(0);
                      HSSFCellStyle headerCellStyle = workbook.createCellStyle();
                      
                      HSSFCell headerActionCell = headerDataRow.createCell(0);
                      headerActionCell.setCellValue("Product");
                      headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                      headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      headerActionCell.setCellStyle(headerCellStyle);

                      HSSFCell headerExpectedResultCell = headerDataRow.createCell(1);
                      headerExpectedResultCell.setCellValue("Score");
                      headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                      headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      headerExpectedResultCell.setCellStyle(headerCellStyle);
                      
                      HSSFCell headerExpectedRankCell1 = headerDataRow.createCell(2);
                      headerExpectedRankCell1.setCellValue("Rank");
                      headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                      headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      headerExpectedRankCell1.setCellStyle(headerCellStyle);
                      

                      HSSFCell headerStatusCell = headerDataRow.createCell(3);
                      headerStatusCell.setCellValue("Status");
                      headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                      headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      headerStatusCell.setCellStyle(headerCellStyle);

                      HSSFCell headerExecutionTimeCell = headerDataRow.createCell(4);
                      headerExecutionTimeCell.setCellValue("Date and Time of Execution");
                      headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                      headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      headerExecutionTimeCell.setCellStyle(headerCellStyle);

                }                       
                //********************Dynamic data*******************//
                if(firstRow == false)
                {
                      HSSFRow testStepDataRow = worksheet.createRow(rownumber);
                      HSSFCellStyle testStepDataCellstyle = workbook.createCellStyle();
                      HSSFCellStyle statusDataCellstyle = workbook.createCellStyle();
                      
                      HSSFCell actionDataCell = testStepDataRow.createCell(0);
                      actionDataCell.setCellValue(action);
                  testStepDataCellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                      testStepDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      actionDataCell.setCellStyle(testStepDataCellstyle);

                      HSSFCell expectedResultDataCell = testStepDataRow.createCell(1);
                      expectedResultDataCell.setCellValue(expectedResult);
                  testStepDataCellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                      testStepDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      expectedResultDataCell.setCellStyle(testStepDataCellstyle);
                      
                      HSSFCell expectedRankCell1 = testStepDataRow.createCell(2);
                      expectedRankCell1.setCellValue(position);
                  testStepDataCellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                      testStepDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      expectedResultDataCell.setCellStyle(testStepDataCellstyle);
                      
                      HSSFCell statusDataCell = testStepDataRow.createCell(3);
                      if(expectedResult.toLowerCase().contains("error occured") || expectedResult.toLowerCase().contains("exception"))
                      {                 
                            statusDataCell.setCellValue("Fail");
                            //testStepDataCellstyle.setFillBackgroundColor(HSSFColor.BLACK.index);
                            statusDataCellstyle.setFillForegroundColor(IndexedColors.RED.getIndex());     
                            statusDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            statusDataCell.setCellStyle(testStepDataCellstyle);
                      }
                      else if(expectedResult.toUpperCase().contains("N/A"))
                      {
                            statusDataCell.setCellValue("N/A");
                            //testStepDataCellstyle.setFillBackgroundColor(HSSFColor.BLACK.index);
                            statusDataCellstyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());    
                            statusDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            statusDataCell.setCellStyle(testStepDataCellstyle);
                      }
                      else
                      {
                            statusDataCell.setCellValue("Pass");
                            //testStepDataCellstyle.setFillBackgroundColor(HSSFColor.BLACK.index);
                            statusDataCellstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());   
                            statusDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                            statusDataCell.setCellStyle(testStepDataCellstyle);
                      }

                      HSSFCell executionTimeDataCell = testStepDataRow.createCell(4);
                      executionTimeDataCell.setCellValue(getDate());
                  testStepDataCellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                      testStepDataCellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                      executionTimeDataCell.setCellStyle(testStepDataCellstyle);
                }

          } catch (Exception e) {
                e.printStackTrace();
          }
    }

	public void WritingToExcelResults(String action, String expectedResult, int rownumber, HSSFWorkbook workbook,  HSSFSheet worksheet, boolean firstRow )
	{
		try {

			//****************************For Row Headings*******************//
			if(firstRow == true)
			{  

				HSSFRow headerDataRow = worksheet.createRow(0);
				HSSFCellStyle headerCellStyle = workbook.createCellStyle();
				
				HSSFCell headerActionCell = headerDataRow.createCell(0);
				headerActionCell.setCellValue("Testcase");
				headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
				headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerActionCell.setCellStyle(headerCellStyle);

				HSSFCell headerExpectedResultCell = headerDataRow.createCell(1);
				headerExpectedResultCell.setCellValue("Test Result");
				headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
				headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerExpectedResultCell.setCellStyle(headerCellStyle);

				HSSFCell headerStatusCell = headerDataRow.createCell(2);
				headerStatusCell.setCellValue("Status");
				headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
				headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerStatusCell.setCellStyle(headerCellStyle);

				HSSFCell headerExecutionTimeCell = headerDataRow.createCell(3);
				headerExecutionTimeCell.setCellValue("Date and Time of Execution");
				headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
				headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerExecutionTimeCell.setCellStyle(headerCellStyle);

			}				
			//********************Dynamic data*******************//
			if(firstRow == false)
			{
				HSSFRow testStepDataRow = worksheet.createRow(rownumber);
				HSSFCellStyle testStepDataCellstyle = workbook.createCellStyle();
				
				HSSFCell actionDataCell = testStepDataRow.createCell(0);
				actionDataCell.setCellValue(action);
				testStepDataCellstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				testStepDataCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				actionDataCell.setCellStyle(testStepDataCellstyle);

				HSSFCell expectedResultDataCell = testStepDataRow.createCell(1);
				expectedResultDataCell.setCellValue(expectedResult);
				testStepDataCellstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				testStepDataCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				expectedResultDataCell.setCellStyle(testStepDataCellstyle);

				HSSFCell statusDataCell = testStepDataRow.createCell(2);
				if(expectedResult.toLowerCase().contains("error occured") || expectedResult.toLowerCase().contains("exception"))
				{			
					statusDataCell.setCellValue("Fail");
					//testStepDataCellstyle.setFillForegroundColor(HSSFColor.BLACK.index);
					testStepDataCellstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());	
					testStepDataCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					statusDataCell.setCellStyle(testStepDataCellstyle);
				}
				else if(expectedResult.toUpperCase().contains("N/A"))
				{
					statusDataCell.setCellValue("N/A");
					//testStepDataCellstyle.setFillForegroundColor(HSSFColor.BLACK.index);
					testStepDataCellstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());	
					testStepDataCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					statusDataCell.setCellStyle(testStepDataCellstyle);
				}
				else
				{
					statusDataCell.setCellValue("Pass");
					//testStepDataCellstyle.setFillForegroundColor(HSSFColor.BLACK.index);
					testStepDataCellstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());	
					testStepDataCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					statusDataCell.setCellStyle(testStepDataCellstyle);
				}

				HSSFCell executionTimeDataCell = testStepDataRow.createCell(3);
				executionTimeDataCell.setCellValue(getDate());
				testStepDataCellstyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
				testStepDataCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				executionTimeDataCell.setCellStyle(testStepDataCellstyle);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, String> fetchInputData(String sheetName) {
		String keys = "";
		String value = "";
		HashMap<String, String> list = new HashMap<String, String>();
		sheetobj = objExcel.getSheet(sheetName);
		int row = objExcel.getSheet(sheetName).getLastRowNum();
		int col = sheetobj.getRow(0).getPhysicalNumberOfCells();

		for (int i = 0; i <= row; i++) {
			for (int j = 0; j < col; j++) {
				DataFormatter fmt = new DataFormatter();
				if (j == 0) {
					keys = fmt.formatCellValue(sheetobj.getRow(i).getCell(j));// System.out.println(fmt.formatCellValue(sheetobj.getRow(i).getCell(j)));
				} else {
					value = fmt.formatCellValue(sheetobj.getRow(i).getCell(j));// System.out.println(fmt.formatCellValue(sheetobj.getRow(i).getCell(j)));
				}
			}
			list.put(keys, value);
		}
		return list;
	}
}