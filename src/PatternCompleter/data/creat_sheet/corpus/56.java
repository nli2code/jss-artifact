package com.demo_poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;

public class DemoPoi {

	public static void main(String[] args) {
		System.out.println("���ԣ�������");
		//����һ�����
//		createTable();
		//����һ�����ҳ�� -- �Ƽ�
//		creatSafeExcel();
	}

	@SuppressWarnings({ "unused", "resource" })
	private static void creatSafeExcel() {
		//����һ�����ҳ�� -- �Ƽ�
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		HSSFWorkbook workbook = new HSSFWorkbook();
		String createSafeSheetName = WorkbookUtil.createSafeSheetName("��ȫ��ҳ��");
		Sheet sheet = workbook.createSheet(createSafeSheetName);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream("D:/��ȫ�ļ�-"+format.format(new Date())+".xls");
			workbook.write(fileOutputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//����һ���ձ��
	@SuppressWarnings({ "unused", "resource" })
	private static void createTable() {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			FileOutputStream outputStream = new FileOutputStream("D:/�������ļ�-"+format.format(new Date())+".xls");
			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("��������ļ��Ǳ���");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����д���ļ�ʱ������");
			e.printStackTrace();
		}
	}
}
