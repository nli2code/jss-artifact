package vm.helper;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CellStyleStorage {
	private XSSFCellStyle basic;
	private XSSFCellStyle basicRed;
	private XSSFCellStyle basicDarkRed;
	private XSSFCellStyle basicGreen;
	private XSSFCellStyle basicLightGreen;
	
	private XSSFCellStyle basicYellow;
	private XSSFCellStyle basicOrange;
	private XSSFCellStyle basicPurple;
	private XSSFCellStyle basicBlue;
	private XSSFCellStyle basicPink;
	private XSSFCellStyle basicDarkGrey;
	private XSSFCellStyle basicBlack;
	
	
	private CellStyle headerBasic;
	private CellStyle headerGrey;
	private CellStyle headerRotatedBasic;
	private CellStyle headerRotatedGrey;
	private CellStyle headerRotatedRed;

	protected CellStyleStorage(XSSFWorkbook wb){
		setUpStyles(wb);
	}
	
	private void setUpStyles(XSSFWorkbook wb){
		
		//sadly, this repetetive bullshit is necessary
		Font fontBasic = wb.createFont();
		fontBasic.setFontHeightInPoints((short) 11);
		fontBasic.setFontName("Calibri");
		
		basic = wb.createCellStyle();
		basic.setFont(fontBasic);
		basic.setAlignment(CellStyle.ALIGN_LEFT);
		
		basicGreen = wb.createCellStyle();
		basicGreen.setFillForegroundColor(HSSFColor.GREEN.index);
		basicGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicGreen.setFont(fontBasic);
		basicGreen.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicLightGreen = wb.createCellStyle();
		basicLightGreen.setFillForegroundColor(new XSSFColor(new java.awt.Color(143,244,62)));
		basicLightGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicLightGreen.setFont(fontBasic);
		basicLightGreen.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicRed = wb.createCellStyle();
		basicRed.setFillForegroundColor(new XSSFColor(new java.awt.Color(249,104,79)));
		basicRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicRed.setFont(fontBasic);
		basicRed.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicDarkRed = wb.createCellStyle();
		basicDarkRed.setFillForegroundColor(HSSFColor.RED.index);
		basicDarkRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicDarkRed.setFont(fontBasic);
		basicDarkRed.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		basicBlue = wb.createCellStyle();
		basicBlue.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		basicBlue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicBlue.setFont(fontBasic);
		basicBlue.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicOrange = wb.createCellStyle();
		basicOrange.setFillForegroundColor(new XSSFColor(new java.awt.Color(255,69,0)));
		basicOrange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicOrange.setFont(fontBasic);
		basicOrange.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicYellow = wb.createCellStyle();
		basicYellow.setFillForegroundColor(new XSSFColor(new java.awt.Color(255,215,0)));
		basicYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicYellow.setFont(fontBasic);
		basicYellow.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicPurple = wb.createCellStyle();
		basicPurple.setFillForegroundColor(HSSFColor.VIOLET.index);
		basicPurple.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicPurple.setFont(fontBasic);
		basicPurple.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicPink = wb.createCellStyle();
		basicPink.setFillForegroundColor(HSSFColor.PINK.index);
		basicPink.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicPink.setFont(fontBasic);
		basicPink.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicDarkGrey = wb.createCellStyle();
		basicDarkGrey.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		basicDarkGrey.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicDarkGrey.setFont(fontBasic);
		basicDarkGrey.setAlignment(CellStyle.ALIGN_CENTER);
		
		basicBlack = wb.createCellStyle();
		basicBlack.setFillForegroundColor(HSSFColor.BLACK.index);
		basicBlack.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		basicBlack.setFont(fontBasic);
		basicBlack.setAlignment(CellStyle.ALIGN_CENTER);
		
		
		Font fontHeader = wb.createFont();
		fontHeader.setFontHeightInPoints((short) 12);
		fontHeader.setFontName("Calibri");
		fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		
		headerBasic = wb.createCellStyle();
		headerBasic.setBorderBottom(CellStyle.BORDER_THIN);
		headerBasic.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerBasic.setFillForegroundColor(HSSFColor.WHITE.index);
		headerBasic.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerBasic.setFont(fontHeader);
		
		headerGrey = wb.createCellStyle();
		headerGrey.setBorderBottom(CellStyle.BORDER_THIN);
		headerGrey.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerGrey.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerGrey.setFont(fontHeader);
		headerGrey.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		
		headerRotatedGrey = wb.createCellStyle();
		headerRotatedGrey.setBorderBottom(CellStyle.BORDER_THIN);
		headerRotatedGrey.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerRotatedGrey.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerRotatedGrey.setFont(fontHeader);
		headerRotatedGrey.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerRotatedGrey.setRotation((short)90);
		
		headerRotatedBasic = wb.createCellStyle();
		headerRotatedBasic.setBorderBottom(CellStyle.BORDER_THIN);
		headerRotatedBasic.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerRotatedBasic.setFont(fontHeader);
		headerRotatedBasic.setRotation((short)90);
		
		headerRotatedRed = wb.createCellStyle();
		headerRotatedRed.setBorderBottom(CellStyle.BORDER_THIN);
		headerRotatedRed.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerRotatedRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerRotatedRed.setFont(fontHeader);
		headerRotatedRed.setFillForegroundColor(HSSFColor.RED.index);
		headerRotatedRed.setRotation((short)90);
		
	}
	
	protected CellStyle getBasicStyle(){
		return basic;
	}
	
	protected CellStyle getBasicRedStyle(){
		return basicRed;
	}
	
	protected CellStyle getBasicDarkRedStyle(){
		return basicDarkRed;
	}
	
	protected CellStyle getBasicGreenStyle(){
		return basicGreen;
	}
	
	protected CellStyle getBasicLightGreenStyle(){
		return basicLightGreen;
	}
	
	protected CellStyle getBasicYellowStyle(){
		return basicYellow;
	}
	
	protected CellStyle getBasicOrangeStyle(){
		return basicOrange;
	}
	
	protected CellStyle getBasicBlueStyle(){
		return basicBlue;
	}
	
	protected CellStyle getBasicPurpleStyle(){
		return basicPurple;
	}
	
	protected CellStyle getBasicDarkGreyStyle(){
		return basicDarkGrey;
	}
	
	protected CellStyle getBasicBlackStyle(){
		return basicBlack;
	}
	
	protected CellStyle getHeaderBasicStyle(){
		return headerBasic;
	}
	
	protected CellStyle getHeaderGreyStyle(){
		return headerGrey;
	}
	
	protected CellStyle getHeaderRotatedBasicStyle(){
		return headerRotatedBasic;
	}
	
	protected CellStyle getHeaderRotatedSimpleStyle(){
		return headerRotatedGrey;
	}
	
	protected CellStyle getHeaderRotatedRedStyle(){
		return headerRotatedRed;
	}
	
	
}