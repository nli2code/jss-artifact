// Copyright 2014 MCWorkshop Inc. All rights reserved.
// $Id$
package com.mcworkshop.common.web.report;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @author $Author$
 * 
 */
public class ReportUtil {

    public static void setBorder(int border, CellRangeAddress region, Sheet sheet,
            Workbook workbook) {
        RegionUtil.setBorderTop((short)border, region, sheet, workbook);
        RegionUtil.setBorderBottom((short)border, region, sheet, workbook);
        RegionUtil.setBorderLeft((short)border, region, sheet, workbook);
        RegionUtil.setBorderRight((short)border, region, sheet, workbook);
    }

}
