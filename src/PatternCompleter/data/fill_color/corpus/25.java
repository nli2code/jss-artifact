package com.gra.utils.txtexcel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.gra.utils.txtexcel.domain.User;

/**
 * Excel helper that helps inserting rows and actually creating the file.
 * 
 * @author gabrielaradu
 *
 */
public class ExcelHelper {

	public static void makeExcelFile(List<User> users, final String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("Excel Registration");

			// index from 0,0... cell A1 is cell(0,0)
			HSSFRow row1 = worksheet.createRow((short) 0);
			ExcelHelper.createHeaders(row1, workbook);

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = formatter.parse(filename);
			String cellDate = formatter.format(date);

			for (int i = 0; i < users.size(); i++) {
				ExcelHelper.insertRow(users.get(i), worksheet, workbook, cellDate, i);
			}
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			System.out.println("Created a new excel file.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void createHeaders(HSSFRow row1, HSSFWorkbook workbook) {
		HSSFCell cellA1 = row1.createCell((short) 0);
		cellA1.setCellValue("Date");
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		cellA1.setCellStyle(cellStyle);

		HSSFCell cellB1 = row1.createCell((short) 1);
		cellB1.setCellValue("Firstname");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellB1.setCellStyle(cellStyle);

		HSSFCell cellC1 = row1.createCell((short) 2);
		cellC1.setCellValue("Surname");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellC1.setCellStyle(cellStyle);

		HSSFCell cellD1 = row1.createCell((short) 3);
		cellD1.setCellValue("Location");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellD1.setCellStyle(cellStyle);

		HSSFCell cellE1 = row1.createCell((short) 4);
		cellE1.setCellValue("Mobile");
		cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellE1.setCellStyle(cellStyle);

		HSSFCell cellF1 = row1.createCell((short) 5);
		cellF1.setCellValue("Email");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellF1.setCellStyle(cellStyle);

		HSSFCell cellG1 = row1.createCell((short) 6);
		cellG1.setCellValue("Domain");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellG1.setCellStyle(cellStyle);

		HSSFCell cellH1 = row1.createCell((short) 7);
		cellH1.setCellValue("Source");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellH1.setCellStyle(cellStyle);

		HSSFCell cellJ1 = row1.createCell((short) 8);
		cellJ1.setCellValue("Campaign");
		cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellJ1.setCellStyle(cellStyle);
	}

	public static void insertRow(User user, HSSFSheet worksheet, HSSFWorkbook workbook, String cellDate, int step) {
		HSSFRow row = worksheet.createRow((short) step + 1);

		HSSFCell cellA1 = row.createCell((short) 0);
		cellA1.setCellValue(cellDate);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		cellA1.setCellStyle(cellStyle);

		HSSFCell cellB1 = row.createCell((short) 1);
		cellB1.setCellValue(user.getFirstname().trim());
		HSSFCellStyle cellBStyle = workbook.createCellStyle();
		cellBStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellBStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellB1.setCellStyle(cellBStyle);

		HSSFCell cellC1 = row.createCell((short) 2);
		cellC1.setCellValue(user.getSurname().trim());
		HSSFCellStyle cellCStyle = workbook.createCellStyle();
		cellCStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellCStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellC1.setCellStyle(cellCStyle);

		HSSFCell cellD1 = row.createCell((short) 3);
		cellD1.setCellValue(user.getHometown().trim());
		HSSFCellStyle cellDStyle = workbook.createCellStyle();
		cellDStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellDStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellD1.setCellStyle(cellDStyle);

		HSSFCell cellE1 = row.createCell((short) 4);
		cellE1.setCellValue("'" + user.getTelephone().trim());
		HSSFCellStyle cellEStyle = workbook.createCellStyle();
		cellEStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellEStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellE1.setCellStyle(cellEStyle);

		HSSFCell cellF1 = row.createCell((short) 5);
		cellF1.setCellValue(user.getEmail().trim());
		HSSFCellStyle cellFStyle = workbook.createCellStyle();
		cellFStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellFStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellF1.setCellStyle(cellFStyle);

		HSSFCell cellG1 = row.createCell((short) 6);
		cellG1.setCellValue(user.getCourse().trim());
		HSSFCellStyle cellGStyle = workbook.createCellStyle();
		cellGStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellGStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellG1.setCellStyle(cellGStyle);

		HSSFCell cellH1 = row.createCell((short) 7);
		cellH1.setCellValue(user.getSource().trim());
		HSSFCellStyle cellHStyle = workbook.createCellStyle();
		cellHStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellHStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellH1.setCellStyle(cellHStyle);

		HSSFCell cellJ1 = row.createCell((short) 8);
		cellJ1.setCellValue(user.getOptin().trim().equals("true") ? "true" : "false");
		HSSFCellStyle cellJStyle = workbook.createCellStyle();
		cellJStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellJStyle.setFillPattern(HSSFCellStyle.ALIGN_LEFT);
		cellJ1.setCellStyle(cellJStyle);
	}
}
