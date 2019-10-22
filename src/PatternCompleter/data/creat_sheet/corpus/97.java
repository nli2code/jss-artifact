package com.ome.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUtil {
	

	/**
	 * 创建新工作簿
	 * 
	 * @return
	 */
	public static Workbook createWorkbook() {
		Workbook wb = new HSSFWorkbook();
		return wb;
	};

	/**
	 * 创建工作表
	 * 
	 * @param wb
	 * @return
	 */
	public static Workbook createSheet(Workbook wb) {
		// 用于创建有效名称的安全方法，此实用程序用空格（''）替换无效字符
		String sheetName = WorkbookUtil.createSafeSheetName("sheet3");
		Sheet sheet1 = wb.createSheet("sheet1");
		Sheet sheet2 = wb.createSheet("sheet2");
		Sheet sheet3 = wb.createSheet(sheetName);
		try {
			FileOutputStream fileOut = new FileOutputStream("e:/workbook.xls");
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 创建单元格
	 * 
	 * @param wb
	 * @return
	 */
	public static Workbook creaeteCell(Workbook wb) {
		String safeSheetName = WorkbookUtil.createSafeSheetName("sheet1");
		Sheet sheet = wb.createSheet(safeSheetName);
		CreationHelper createHelper = wb.getCreationHelper();

		// 创建一行并在其中放置一些单元格。行基于0。
		Row row0 = sheet.createRow(0);

		// 在行中创建一个单元格，并在其中添加一个值
		Cell cell = row0.createCell(0);
		cell.setCellValue(1);

		// 或在一行内完成
		row0.createCell(1).setCellValue(1.2);
		row0.createCell(2).setCellValue(createHelper.createRichTextString("这是一个字符串"));
		row0.createCell(3).setCellValue(true);
		return wb;
	}
	
	/**
	 * 创建日期单元格
	 * @param wb
	 * @return
	 */
	public static Workbook createDateCell(Workbook wb) {
		CreationHelper createHelper = wb.getCreationHelper();
		String safeSheetName = WorkbookUtil.createSafeSheetName("sheet1");
		Sheet sheet = wb.createSheet(safeSheetName);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(new Date());

		// 把第二个单元格作为日期(和时间)
		// 从工作簿中创建一个新的单元格样式
		// 修改内置样式并且不仅影响这个单元而且影响其他单元。
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("M/d/yy hh:mm"));
		cell = row.createCell(1);
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);

		// 也可以将日期设置为java.util.Calender
		cell = row.createCell(2);
		cell.setCellValue(Calendar.getInstance());
		cell.setCellStyle(cellStyle);
		
		return wb;
	}
	
	/**
	 * 测试使用不同类型的单元格
	 * @param wb
	 * @return
	 */
	public static Workbook createDifferentTypesCell(Workbook wb) {
		String safeSheetName = WorkbookUtil.createSafeSheetName("sheet1");
		Sheet sheet = wb.createSheet(safeSheetName);
		Row row = sheet.createRow(2);
		row.createCell(0).setCellValue(1.1);
		row.createCell(1).setCellValue(new Date());
		row.createCell(2).setCellValue(Calendar.getInstance());
		row.createCell(3).setCellValue("a string");
		row.createCell(4).setCellValue(true);
		row.createCell(5).setCellType(CellType.ERROR);
		return wb;
	}
	
	
	@SuppressWarnings("resource")
	public static Workbook fileVSInputStreams() {
		// 打开工作簿时，可以从.xls HSSFWorkbook或.xlsx XSSFWorkbook打开工作簿
		// 也可以从File 或InputStream加载工作簿。使用File对象可以降低内存消耗，而InputStream需要更多内存，因为它必须缓冲整个文件。
		// 如果使用WorkbookFactory，则使用其中一个或另一个非常简单：
		try {
			// 使用file文件方式
			Workbook wb1 = WorkbookFactory.create(new File("d:/Linux命令.xls"));

			// 使用InputStream方式,需要更多内存
			Workbook wb2 = WorkbookFactory.create(new FileInputStream("d:/Linux命令.xls"));
			// 如果直接使用HSSFWorkbook或XSSFWorkbook，则通常应该通过NPOIFSFileSystem或
			// OPCPackage来完全控制生命周期（包括在完成时关闭文件）。
			// NPOIFSFileSystem只支持HSSFWorkbook
			// OPCPackage只支持XSSFWorkbook
			NPOIFSFileSystem fs1 = new NPOIFSFileSystem(new File("d:/Linux命令.xls"));// 使用file文件方式
			HSSFWorkbook wb3 = new HSSFWorkbook(fs1.getRoot(), true);
			fs1.close();

			NPOIFSFileSystem fs2 = new NPOIFSFileSystem(new FileInputStream("d:/Linux命令.xls"));// 使用InputStream方式,需要更多内存
			HSSFWorkbook wb4 = new HSSFWorkbook(fs2.getRoot(), true);
			fs2.close();

			OPCPackage pkg1 = OPCPackage.open(new File("d:/Linux命令.xlsx"));// 使用file文件方式
			XSSFWorkbook wb5 = new XSSFWorkbook(pkg1);
//			pkg1.close();
			
			OPCPackage pkg2 = OPCPackage.open(new FileInputStream("d:/Linux命令.xlsx"));// 使用InputStream方式,需要更多内存
			XSSFWorkbook wb6 = new XSSFWorkbook(pkg2);
//			pkg2.close();
			return wb6;
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Workbook wb = createWorkbook();
		Workbook testWb = fileVSInputStreams();
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("e:/workbook.xlsx");
			testWb.write(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			fileOut.close();
			System.out.println("输出流关闭成功");
		}
		System.out.println("ok");
		
//		OPCPackage opc = null;
//		XSSFWorkbook wb;
//		FileOutputStream fileOut = null;
//		try {
//			opc = OPCPackage.open(new File("d:/Linux命令.xlsx"));
//			wb = new XSSFWorkbook(opc);
//			fileOut = new FileOutputStream("e:/workbook.xlsx");
//			wb.write(fileOut);
//			opc.close();
//			fileOut.close();
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
