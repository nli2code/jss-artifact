package com.bigcay.excel.excelfiller.element;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.util.WorkbookUtil;

import com.bigcay.excel.excelfiller.ExcelContext;

public class SheetElement extends AbstractElement {
    
    private XSSFSheet sheet;
    
    private int sheetIndex;
    
    public SheetElement(ExcelContext excelContext) {
        super(excelContext);
    }
    
    public SheetElement(XSSFSheet sheet, ExcelContext excelContext) {
        super(excelContext);
        this.sheet = sheet;
        sheetIndex = workbook.getSheetIndex(this.sheet);
    }

    public int getSheetIndex() {
        return sheetIndex;
    }
    
	public SheetElement sheetName(String sheetName) {
		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);
		workbook.setSheetName(sheetIndex, safeSheetName);
		return this;
	}
}
