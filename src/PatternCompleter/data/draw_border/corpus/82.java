package com.gsitm.it1047_shs.warmup;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/** 
 * @과목명            : GS ITM 인턴사원 자바교육
 * @FileName        : ServeAnFood.java 
 * @Project         : gsitm_java3 
 * @Date            : 2018. 4. 10. 
 * @작성자            : 송현석
 * @프로그램 설명                : 전에 썼던 POI모듈을 이용하여 배달되는 음식을 엑셀파일로 표현해보았다.
 */
public class ServeAnFood {
	HSSFRow row;
	HSSFCell cell;
	ArrayList<Food> foods;//주문된 음식들 리스트 변수
	Person targetPerson;// 주문자
	int price;//결제 가격
	int returnMoney;//거스름돈 변수
	
	ServeAnFood(ArrayList<Food> arrlist, Person p,int returnMoney, int price){//생성자로 초기화 될 때 모든걸 받는다.
		this.foods = arrlist;
		this.targetPerson = p;
		this.price = price;
		this.returnMoney = returnMoney;
	}
	
	public void servingToExcel() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("음식들");
		
		CellStyle greenBack = workbook.createCellStyle();
		CellStyle none = workbook.createCellStyle();
		Font font = workbook.createFont();

		font.setBold(true);  //글씨 bold

		greenBack.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		greenBack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		greenBack.setAlignment(HorizontalAlignment.CENTER); 
		greenBack.setVerticalAlignment(VerticalAlignment.CENTER);
		greenBack.setFont(font);
		
		none.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		none.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		none.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		none.setAlignment(HorizontalAlignment.CENTER); 
		none.setVerticalAlignment(VerticalAlignment.CENTER);
		none.setFont(font);
		
		sheet.setColumnWidth(0,6000);
		sheet.setColumnWidth(1,10000);
		sheet.setColumnWidth(2,6000);
		sheet.setColumnWidth(3,10000);
		
		Cell cell = null;
		int cellcnt = 0;
		short mini = 600;
		short big = 2000;
		//--------------------------------------이까지 스타일 초기화 부분---------------------------
		row = sheet.createRow(0);
		row.setHeight(mini);
		createCell_y(cellcnt++, "성 명",greenBack);
		createCell_n(cellcnt++, targetPerson.getName(),none);//성명 줄
		cellcnt = 0;
		row = sheet.createRow(1);
		row.setHeight(mini);
		createCell_y(cellcnt++, "주문 총 금액",greenBack);//주문금액 줄
		createCell_n(cellcnt++, ""+this.price,none);
		cellcnt = 0;
		row = sheet.createRow(2);
		row.setHeight(mini);
		createCell_y(cellcnt++, "음식",greenBack);
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,1));
		cellcnt = 0;
		for(int i = 0; i < foods.size(); i++) {	//받은 음식 리스트를 한줄씩 표현
			row = sheet.createRow(i+3);
			row.setHeight(mini);
			createCell_n(cellcnt++, foods.get(i).getName(),none);
			cellcnt = 0;
			sheet.addMergedRegion(new CellRangeAddress(i+3,i+3,0,1));
		}
		rangedCellBorder(workbook, sheet, 0, foods.size()+2, 0, 1, BorderStyle.THICK, BorderStyle.THIN);
		System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());
		FileOutputStream outFile;

		try {
			outFile = new FileOutputStream("C:\\aa\\food1047_"+targetPerson.getName()+".xls");
			workbook.write(outFile);
			outFile.close();			
			
			System.out.println("C:\\\\aa\\\\food1047_"+targetPerson.getName()+".xls 에 배달 완료됨.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void createCell_n(int cnt, String str, CellStyle s) {
		cell = row.createCell(cnt);
		cell.setCellValue(str);
		cell.setCellStyle(s);
	}
	
	void createCell_y(int cnt, String str, CellStyle s) {
		cell = row.createCell(cnt);
		cell.setCellValue(str);
		cell.setCellStyle(s);
	}
	
	void rangedCellBorder(HSSFWorkbook workbook,HSSFSheet sheet,int start_row, int end_row, int start_cell, int end_cell, BorderStyle border, BorderStyle innerLines) {
		for(int i = start_row ; i <= end_row; i++) {
			for(int j = start_cell; j <= end_cell; j++) {
				if(sheet.getRow(i) == null) {
					row = sheet.createRow(i);
				}else {
					row = sheet.getRow(i);
				}
				if(row.getCell(j) == null) {
					cell = row.createCell(j);
				}else {
					cell = row.getCell(j);					
				}

				CellStyle tmp = workbook.createCellStyle();
				tmp.cloneStyleFrom(cell.getCellStyle());
				if(j == start_cell && i == start_row) {
					tmp.setBorderRight(innerLines);
					tmp.setBorderLeft(border);
					tmp.setBorderTop(border);
					tmp.setBorderBottom(innerLines);
				}else if (i == start_row && j > start_cell && j < end_cell) {
					tmp.setBorderRight(innerLines);
					tmp.setBorderLeft(innerLines);
					tmp.setBorderTop(border);
					tmp.setBorderBottom(innerLines);
				}else if (i == end_row &&  j > start_cell && j < end_cell) {
					tmp.setBorderRight(innerLines);
					tmp.setBorderLeft(innerLines);
					tmp.setBorderTop(innerLines);
					tmp.setBorderBottom(border);
				}else if (j == start_cell &&  i > start_row && i < end_row) {
					tmp.setBorderRight(innerLines);
					tmp.setBorderLeft(border);
					tmp.setBorderTop(innerLines);
					tmp.setBorderBottom(innerLines);
				}else if (j == end_cell &&  i > start_row && i < end_row) {
					tmp.setBorderRight(border);
					tmp.setBorderLeft(innerLines);
					tmp.setBorderTop(innerLines);
					tmp.setBorderBottom(innerLines);
				}else if( i < end_row && j < end_cell && i>start_row && j>start_cell) {
					tmp.setBorderRight(innerLines);
					tmp.setBorderLeft(innerLines);
					tmp.setBorderTop(innerLines);
					tmp.setBorderBottom(innerLines);
				}else if( i == end_row && j == end_cell) {
					tmp.setBorderRight(border);
					tmp.setBorderLeft(innerLines);
					tmp.setBorderTop(innerLines);
					tmp.setBorderBottom(border);
				}else if( i == end_row && j == start_cell) {
					tmp.setBorderRight(innerLines);
					tmp.setBorderLeft(border);
					tmp.setBorderTop(innerLines);
					tmp.setBorderBottom(border);
				}else if (i == start_row && j == end_cell) {
					tmp.setBorderRight(border);
					tmp.setBorderLeft(innerLines);
					tmp.setBorderTop(border);
					tmp.setBorderBottom(innerLines);
				}
				cell.setCellStyle(tmp);
			}
		}
	}
}
