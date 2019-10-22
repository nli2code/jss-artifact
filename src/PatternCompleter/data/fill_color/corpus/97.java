package com.ask.inv.google.analytics.report;

import java.awt.Color;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Style {
	public static XSSFCellStyle headerDateStyle;
	public static XSSFCellStyle headerStyle;
	public static XSSFCellStyle headerLineStyle;
	public static XSSFCellStyle headerLineFont16Style;
	public static XSSFCellStyle headerBorderMediumStyle;
	public static XSSFCellStyle headerBorderMediumFont14Style;
	public static XSSFCellStyle headerBorderMediumFont14Style2;
	public static XSSFCellStyle detailLineStyle;
	public static XSSFCellStyle detailBorderMediumStyle;
	public static XSSFCellStyle numberStyle;
	public static XSSFCellStyle numberBoldStyle;
	public static XSSFCellStyle numberLineStyle;
	public static XSSFCellStyle numberBorderMediumStyle;
	public static XSSFCellStyle headerLineBrStyle;
	public static XSSFCellStyle headerDateBrStyle;
	public static XSSFCellStyle headerBorderMediumBrStyle;
	public static XSSFCellStyle blankBrStyle;
	public static XSSFCellStyle headerBorderMediumFont14BrStyle;
	public static XSSFCellStyle headerBorderMediumFont14BrStyle2;
	public static XSSFCellStyle numberBrStyle;
	public static XSSFCellStyle numberBoldBrStyle;
	public static XSSFCellStyle numberLineBrStyle;
	public static XSSFCellStyle numberBorderMediumBrStyle;
	public static XSSFCellStyle detailLineBrStyle;
	public static XSSFCellStyle headerLineBlStyle;
	public static XSSFCellStyle headerDateBlStyle;
	public static XSSFCellStyle headerBorderMediumBlStyle;
	public static XSSFCellStyle blankBlStyle;
	public static XSSFCellStyle headerBorderMediumFont14BlStyle;
	public static XSSFCellStyle headerBorderMediumFont14BlStyle2;
	public static XSSFCellStyle numberBlStyle;
	public static XSSFCellStyle numberBoldBlStyle;
	public static XSSFCellStyle numberLineBlStyle;
	public static XSSFCellStyle numberBorderMediumBlStyle;
	public static XSSFCellStyle detailLineBlStyle;
	public static XSSFCellStyle headerBorderMediumBrnStyle;
	public static XSSFCellStyle blankBrnStyle;
	public static short percentFormat;
	public static short numberFormat;
	
	public static void init(XSSFWorkbook xwb) {
		XSSFCreationHelper helper= xwb.getCreationHelper();
		Font headerFont = xwb.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.BLACK.index);
		headerFont.setUnderline(Font.U_NONE);
		
		Font header16Font = xwb.createFont();
		header16Font.setFontHeightInPoints((short)16);
		
		Font header14Font = xwb.createFont();
		header14Font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		header14Font.setFontHeightInPoints((short)14);
		
		Font header14FontWhiteColor = xwb.createFont();
		header14FontWhiteColor.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		header14FontWhiteColor.setColor(IndexedColors.WHITE.index);
		header14FontWhiteColor.setFontHeightInPoints((short)14);
		
		headerDateStyle= xwb.createCellStyle();
		headerDateStyle.setFont(headerFont);
		headerDateStyle.setDataFormat(helper.createDataFormat().getFormat("dd-mmm"));
		headerDateStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		
		headerStyle= xwb.createCellStyle();
		headerStyle.setFont(headerFont);
		
		headerLineStyle= xwb.createCellStyle();
		headerLineStyle.setFont(headerFont);
		headerLineStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		headerLineFont16Style= xwb.createCellStyle();
		headerLineFont16Style.setFont(header16Font);
		headerLineFont16Style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		
		headerBorderMediumStyle= xwb.createCellStyle();
		headerBorderMediumStyle.setFont(headerFont);
		headerBorderMediumStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

		headerBorderMediumFont14Style= xwb.createCellStyle();
		headerBorderMediumFont14Style.setFont(header14Font);
		headerBorderMediumFont14Style.setFillForegroundColor(new XSSFColor(new Color(172, 185, 202)));
		headerBorderMediumFont14Style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumFont14Style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		
		headerBorderMediumFont14Style2= xwb.createCellStyle();
		headerBorderMediumFont14Style2.setFont(header14FontWhiteColor);
		headerBorderMediumFont14Style2.setFillForegroundColor(new XSSFColor(new Color(51, 63, 79)));
		headerBorderMediumFont14Style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumFont14Style2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		
		detailLineStyle= xwb.createCellStyle();
		detailLineStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		detailBorderMediumStyle= xwb.createCellStyle();
		detailBorderMediumStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		
		numberFormat = helper.createDataFormat().getFormat("#,##0");
		numberStyle= xwb.createCellStyle();
		numberStyle.setDataFormat(numberFormat);
		
		numberBoldStyle= xwb.createCellStyle();
		numberBoldStyle.setFont(headerFont);
		numberBoldStyle.setDataFormat(numberFormat);
		
		numberLineStyle= xwb.createCellStyle();
		numberLineStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		numberLineStyle.setDataFormat(numberFormat);
		
		numberBorderMediumStyle= xwb.createCellStyle();
		numberBorderMediumStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		numberBorderMediumStyle.setDataFormat(numberFormat);
		
		XSSFColor color = new XSSFColor(new Color(221, 235, 247));
		// BorderRight YTD used
		headerLineBrStyle= xwb.createCellStyle();
		headerLineBrStyle.setFont(headerFont);
		headerLineBrStyle.setFillForegroundColor(color);
		headerLineBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerLineBrStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerLineBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		headerDateBrStyle= xwb.createCellStyle();
		headerDateBrStyle.setFont(headerFont);
		headerDateBrStyle.setFillForegroundColor(color);
		headerDateBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerDateBrStyle.setDataFormat(helper.createDataFormat().getFormat("dd-mmm"));
		headerDateBrStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerDateBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		headerBorderMediumBrStyle= xwb.createCellStyle();
		headerBorderMediumBrStyle.setFont(headerFont);
		headerBorderMediumBrStyle.setFillForegroundColor(color);
		headerBorderMediumBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumBrStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerBorderMediumBrStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		blankBrStyle= xwb.createCellStyle();
		blankBrStyle.setFillForegroundColor(color);
		blankBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		blankBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		headerBorderMediumFont14BrStyle= xwb.createCellStyle();
		headerBorderMediumFont14BrStyle.setFont(header14Font);
		headerBorderMediumFont14BrStyle.setFillForegroundColor(new XSSFColor(new Color(172, 185, 202)));
		headerBorderMediumFont14BrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumFont14BrStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumFont14BrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		headerBorderMediumFont14BrStyle2= xwb.createCellStyle();
		headerBorderMediumFont14BrStyle2.setFont(header14FontWhiteColor);
		headerBorderMediumFont14BrStyle2.setFillForegroundColor(new XSSFColor(new Color(51, 63, 79)));
		headerBorderMediumFont14BrStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumFont14BrStyle2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumFont14BrStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		numberBrStyle= xwb.createCellStyle();
		numberBrStyle.setDataFormat(numberFormat);
		numberBrStyle.setFillForegroundColor(color);
		numberBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		numberBoldBrStyle= xwb.createCellStyle();
		numberBoldBrStyle.setFont(headerFont);
		numberBoldBrStyle.setFillForegroundColor(color);
		numberBoldBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberBoldBrStyle.setDataFormat(numberFormat);
		numberBoldBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		numberLineBrStyle= xwb.createCellStyle();
		numberLineBrStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		numberLineBrStyle.setDataFormat(numberFormat);
		numberLineBrStyle.setFillForegroundColor(color);
		numberLineBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberLineBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		numberBorderMediumBrStyle= xwb.createCellStyle();
		numberBorderMediumBrStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		numberBorderMediumBrStyle.setDataFormat(numberFormat);
		numberBorderMediumBrStyle.setFillForegroundColor(color);
		numberBorderMediumBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberBorderMediumBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		detailLineBrStyle= xwb.createCellStyle();
		detailLineBrStyle.setFillForegroundColor(color);
		detailLineBrStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		detailLineBrStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		detailLineBrStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		// BorderLeft MTD used
		headerLineBlStyle= xwb.createCellStyle();
		headerLineBlStyle.setFont(headerFont);
		headerLineBlStyle.setFillForegroundColor(color);
		headerLineBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerLineBlStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerLineBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		headerDateBlStyle= xwb.createCellStyle();
		headerDateBlStyle.setFont(headerFont);
		headerDateBlStyle.setFillForegroundColor(color);
		headerDateBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerDateBlStyle.setDataFormat(helper.createDataFormat().getFormat("dd-mmm"));
		headerDateBlStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerDateBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		headerBorderMediumBlStyle= xwb.createCellStyle();
		headerBorderMediumBlStyle.setFont(headerFont);
		headerBorderMediumBlStyle.setFillForegroundColor(color);
		headerBorderMediumBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumBlStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		blankBlStyle= xwb.createCellStyle();
		blankBlStyle.setFillForegroundColor(color);
		blankBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		blankBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		headerBorderMediumFont14BlStyle= xwb.createCellStyle();
		headerBorderMediumFont14BlStyle.setFont(header14Font);
		headerBorderMediumFont14BlStyle.setFillForegroundColor(new XSSFColor(new Color(172, 185, 202)));
		headerBorderMediumFont14BlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumFont14BlStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumFont14BlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		headerBorderMediumFont14BlStyle2= xwb.createCellStyle();
		headerBorderMediumFont14BlStyle2.setFont(header14FontWhiteColor);
		headerBorderMediumFont14BlStyle2.setFillForegroundColor(new XSSFColor(new Color(51, 63, 79)));
		headerBorderMediumFont14BlStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerBorderMediumFont14BlStyle2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumFont14BlStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		numberBlStyle= xwb.createCellStyle();
		numberBlStyle.setDataFormat(numberFormat);
		numberBlStyle.setFillForegroundColor(color);
		numberBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		numberBoldBlStyle= xwb.createCellStyle();
		numberBoldBlStyle.setFont(headerFont);
		numberBoldBlStyle.setFillForegroundColor(color);
		numberBoldBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberBoldBlStyle.setDataFormat(numberFormat);
		numberBoldBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		numberLineBlStyle= xwb.createCellStyle();
		numberLineBlStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		numberLineBlStyle.setDataFormat(numberFormat);
		numberLineBlStyle.setFillForegroundColor(color);
		numberLineBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberLineBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		numberBorderMediumBlStyle= xwb.createCellStyle();
		numberBorderMediumBlStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		numberBorderMediumBlStyle.setDataFormat(numberFormat);
		numberBorderMediumBlStyle.setFillForegroundColor(color);
		numberBorderMediumBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberBorderMediumBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		detailLineBlStyle= xwb.createCellStyle();
		detailLineBlStyle.setFillForegroundColor(color);
		detailLineBlStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		detailLineBlStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		detailLineBlStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		
		// BorderRight notes used
		headerBorderMediumBrnStyle= xwb.createCellStyle();
		headerBorderMediumBrnStyle.setFont(headerFont);
		headerBorderMediumBrnStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		headerBorderMediumBrnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerBorderMediumBrnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		blankBrnStyle= xwb.createCellStyle();
		blankBrnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		percentFormat = helper.createDataFormat().getFormat("0%");
	}
}
