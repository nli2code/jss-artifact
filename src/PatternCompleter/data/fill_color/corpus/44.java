package de.top100golfcourses.panel.da;


import com.vaadin.addon.tableexport.ExcelExport;
import com.vaadin.ui.Grid;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

import de.top100golfcourses.panel.entity.RankedCourse;

public final class RankingsExporter extends ExcelExport {

    public RankingsExporter(Grid<RankedCourse> grid, String title) {
        super(grid, "Ranking");
        setReportTitle(title);
        setDisplayTotals(false);
        setRowHeaders(false);
        HSSFWorkbook wb = (HSSFWorkbook)getWorkbook();
        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.GOLD.getIndex(),
                (byte) 255, // RGB red (0-255)
                (byte) 215, // RGB green
                (byte) 0    // RGB blue
        );
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex(),
                (byte) 192, // RGB red (0-255)
                (byte) 192, // RGB green
                (byte) 192  // RGB blue
        );
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.MAROON.getIndex(),
                (byte) 222, // RGB red (0-255)
                (byte) 124, // RGB green
                (byte) 0   // RGB blue
        );
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.BRIGHT_GREEN.getIndex(),
                (byte) 0,   // RGB red (0-255)
                (byte) 199, // RGB green
                (byte) 0    // RGB blue
        );
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex(),
                (byte) 126, // RGB red (0-255)
                (byte) 83,  // RGB green
                (byte) 41   // RGB blue
        );
    }

    @Override
    protected CellStyle getCellStyle(final Object propId, final Object rootItemId, final int row, final int col, final boolean totalsRow) {
        CellStyle style = super.getCellStyle(propId, rootItemId, row, col, totalsRow);
        RankedCourse course = (RankedCourse)rootItemId;
        if (propId.equals(("bucket"))) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            System.out.println(course);
            switch (course.getBucket()) {
                case 1:
                    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
                    break;
                case 2:
                    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
                    break;
                case 3:
                    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.MAROON.getIndex());
                    break;
                case 4:
                    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BRIGHT_GREEN.getIndex());
                    break;
                case 5:
                    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
                    break;
                default:
            }
        }
        else style.setFillPattern(FillPatternType.NO_FILL);
        return style;
    }

}
