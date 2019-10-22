package ModelLayer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class Design {
	
	private CellStyle style,style2,design,designUp,designUpLeft,designUpRight,designDown,designDownLeft,designDownRight,designLeft,designRight;
	
	@SuppressWarnings("deprecation")
	public Design(HSSFWorkbook workbook){
		style = workbook.createCellStyle();
	  	style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	  	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	  	style.setAlignment(CellStyle.ALIGN_CENTER);
	  	style.setBorderTop((short) 2);
	  	style.setBorderRight((short) 2);
	  	style.setBorderBottom((short) 2);
	  	style.setBorderLeft((short) 2);
	  	
	  	style2 = workbook.createCellStyle();
	    style2.setBorderBottom((short) 1);
	  	
	  	design = workbook.createCellStyle();
	  	design.setBorderTop((short) 1);
    	design.setBorderRight((short) 1);
    	design.setBorderBottom((short) 1);
    	design.setBorderLeft((short) 1);
    	
    	designUp = workbook.createCellStyle();
    	designUp.setBorderTop((short) 2);
    	designUp.setBorderRight((short) 1);
    	designUp.setBorderBottom((short) 1);
    	designUp.setBorderLeft((short) 1);
    	
    	designUpLeft = workbook.createCellStyle();
    	designUpLeft.setBorderTop((short) 2);
    	designUpLeft.setBorderRight((short) 1);
    	designUpLeft.setBorderBottom((short) 1);
    	designUpLeft.setBorderLeft((short) 2);
    	
    	designUpRight = workbook.createCellStyle();
    	designUpRight.setBorderTop((short) 2);
    	designUpRight.setBorderRight((short) 2);
    	designUpRight.setBorderBottom((short) 1);
    	designUpRight.setBorderLeft((short) 1);
    	
    	designDown = workbook.createCellStyle();
    	designDown.setBorderTop((short) 1);
    	designDown.setBorderRight((short) 1);
    	designDown.setBorderBottom((short) 2);
    	designDown.setBorderLeft((short) 1);
    	
    	designDownLeft = workbook.createCellStyle();
    	designDownLeft.setBorderTop((short) 1);
    	designDownLeft.setBorderRight((short) 1);
    	designDownLeft.setBorderBottom((short) 2);
    	designDownLeft.setBorderLeft((short) 2);
    	
    	designDownRight = workbook.createCellStyle();
    	designDownRight.setBorderTop((short) 1);
    	designDownRight.setBorderRight((short) 2);
    	designDownRight.setBorderBottom((short) 2);
    	designDownRight.setBorderLeft((short) 1);
    	
    	designLeft = workbook.createCellStyle();
    	designLeft.setBorderTop((short) 1);
    	designLeft.setBorderRight((short) 1);
    	designLeft.setBorderBottom((short) 1);
    	designLeft.setBorderLeft((short) 2);
    	
    	designRight = workbook.createCellStyle();
    	designRight.setBorderTop((short) 1);
    	designRight.setBorderRight((short) 2);
    	designRight.setBorderBottom((short) 1);
    	designRight.setBorderLeft((short) 1);
	}
	
	public CellStyle getStyle(){
		return style;
	}
	
	public CellStyle getStyle2(){
		return style2;
	}
	
	public CellStyle getDesign(){
		return design;
	}
	
	public CellStyle getDesignUp(){
		return designUp;
	}
	
	public CellStyle getDesignUpLeft(){
		return designUpLeft;
	}
	
	public CellStyle getDesignUpRight(){
		return designUpRight;
	}
	
	public CellStyle getDesignDown(){
		return designDown;
	}
	
	public CellStyle getDesignDownLeft(){
		return designDownLeft;
	}
	
	public CellStyle getDesignDownRight(){
		return designDownRight;
	}
	
	public CellStyle getDesignLeft(){
		return designLeft;
	}
	
	public CellStyle getDesignRight(){
		return designRight;
	}
}
