package com.project.dml.utils.doc;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class SheetHelper {
    public static void addMergedRegionAndBorder(Sheet sheet, CellRangeAddress range){
        sheet.addMergedRegion(range);
        RegionUtil.setBorderTop(BorderStyle.THIN, range, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, range, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, range, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, range, sheet);
    }
}
