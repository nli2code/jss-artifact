package ru.unlimit.javapro.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JavaExcelApp {

	public static void main(String[] args) throws IOException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet0 = wb.createSheet(WorkbookUtil.createSafeSheetName("���"));
		Row row = sheet0.createRow(1);
		Cell cell = row.createCell(8);
		cell.setCellValue("152"); //����� ����
		
		Cell cell1 = row.createCell(10);
		cell1.setCellValue("25.06.1968"); //�� ������ �����
		
		Row row1 = sheet0.createRow(3);
		Cell cell2 = row1.createCell(3);
		cell2.setCellValue("01.06.1968"); //������ � 
		
		Cell cell21 = row1.createCell(6);
		cell21.setCellValue("25.06.1968");//������ ��
		
		Row row11 = sheet0.createRow(4);
		Cell cell211 = row11.createCell(10);
		cell211.setCellValue("25.06.1968"); //���� �����������
		
		Row row111 = sheet0.createRow(10);
		Cell cell2111 = row111.createCell(1);
		cell2111.setCellValue("��� 1241268"); //����� ������
		Cell cell21111 = row111.createCell(2);
		cell21111.setCellValue("������ ���� ��������"); //������������
		Cell cell211111 = row111.createCell(3);
		cell211111.setCellValue("25.11.2016"); //������ �������� ������
		Cell cell2111111 = row111.createCell(4);
		cell2111111.setCellValue("24.11.2017"); //����� �������� ������
		Cell cell2211 = row111.createCell(6);
		cell2211.setCellValue("12200"); //���������� ������
		Cell cell2311 = row111.createCell(7);
		cell2311.setCellValue("25.11.2016"); //���� ������
		Cell cell2411 = row111.createCell(8);
		cell2411.setCellValue("�� 132670"); //����� ���������
		
		Row row121 = sheet0.createRow(21);
		Cell cell2113 = row121.createCell(2);
		cell2113.setCellValue("������ �.�."); //����� ������
		
		//Sheet sheet1 = wb.createSheet("�����");
		//Row row1 = sheet1.createRow(3);
		//Cell cell1 = row1.createCell(4);
		//cell1.setCellValue("PRIRODA");
		//Cell cell2 = row1.createCell(5);
		//cell2.setCellValue("PRIRODA");
		//Sheet sheet2 = wb.createSheet("������");
		//Sheet sheet3 = wb.createSheet(WorkbookUtil.createSafeSheetName("���������5��������%"));//������ ��� ���� ��� ����� �� ������������ ���� �������� ��������
		FileOutputStream fos = new FileOutputStream("C:/Ceyf Java/my.xls");

wb.write(fos);
fos.close();
	}

}
