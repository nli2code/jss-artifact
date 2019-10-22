package assignment.afterclass11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import class7.Student;

public class FileStudent {

	public static void main(String[] args) {
		String fileName="student.xls";
		//System.out.println(System.getProperty("user.dir"));
		fileName=System.getProperty("user.dir")+File.separator+"src"+File.separator+"assignment"+File.separator+"afterclass11"+File.separator+fileName;
		//File fp1=new File(fileName);
		//fp1.createNewFile();
		try{
		FileOutputStream fos=new FileOutputStream(fileName);
		System.out.println("enter no.of students : ");
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		Student[] std=new Student[n];
		Student s;
		for(int i=0;i<n;i++)
		{
			s=new Student();
			System.out.println("enter student "+i+" details");
			System.out.println("enter student id:");
			s.setId(in.nextInt());
			System.out.println("enter student name:");
			s.setName(in.next());
			System.out.println("enter student fee:");
			s.setFee(in.nextDouble());
			System.out.println("enter student marks:");
			s.setMarks(in.nextInt());
			std[i]=s;
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
		// index from 0,0... cell A1 is cell(0,0)
		HSSFRow[] row=new HSSFRow[n+1];	
		row[0] = worksheet.createRow((short) 0);
		HSSFCell cellA1 = row[0].createCell((short) 0);
		cellA1.setCellValue("id");
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);
		
		
		cellA1 = row[0].createCell((short) 1);
		cellA1.setCellValue("name");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);
		
		cellA1 = row[0].createCell((short) 2);
		cellA1.setCellValue("fee");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);
		
		cellA1 = row[0].createCell((short) 3);
		cellA1.setCellValue("marks");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellA1.setCellStyle(cellStyle);
		
		for(int i=1;i<=n;i++)
		{
			row[i] = worksheet.createRow((short) i);
			cellA1 = row[i].createCell((short) 0);
			cellA1.setCellValue(std[i-1].getId());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);
			
			
			cellA1 = row[i].createCell((short) 1);
			cellA1.setCellValue(std[i-1].getName());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.RED.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);
			
			cellA1 = row[i].createCell((short) 2);
			cellA1.setCellValue(std[i-1].getFee());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);
			
			cellA1 = row[i].createCell((short) 3);
			cellA1.setCellValue(std[i-1].getMarks());
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.ORANGE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);
			
			
		}
		workbook.write(fos);
		fos.flush();
		fos.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
		
		}
		// TODO Auto-generated method stub

	}

}
