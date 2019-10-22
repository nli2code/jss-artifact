package com.good.comm.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author wuwei
 *
 */
public class DefultBgSetter implements IBgSetter {

    public void setBackground(StringBuilder sb, int r, int c, CellModel cell) {
        sb.append("background-color:");
//        Double x= (Double)cell.getData();
//        if (x > 25 && x < 27 ) {
//            sb.append("#aaffff;");
//        }
//        else if (x > 27 && x < 150 ){
            sb.append("#ffaaff;");
//        }
//        else {
//            sb.append("#eeeeee;");
//        }
    }

    public void setBackground(Workbook wb,Cell hcell, int r, int c, CellModel cell) {
//        Double x= (Double)cell.getData();
//        if (x > 25 && x < 27 ) {
            CellStyle style = hcell.getCellStyle();
            CellStyle nstyle = wb.createCellStyle();
            nstyle.cloneStyleFrom(style);
            hcell.setCellStyle(nstyle);
            hcell.getCellStyle().setFillForegroundColor(IndexedColors.ORANGE.getIndex());
//        }
//        else if (x > 27 && x < 150 ){
//            CellStyle style = hcell.getCellStyle();
//            CellStyle nstyle = wb.createCellStyle();
//            nstyle.cloneStyleFrom(style);
//            hcell.setCellStyle(nstyle);
//            hcell.getCellStyle().setFillForegroundColor(IndexedColors.GOLD.getIndex());
//        }
//        else {
//        	 CellStyle style = hcell.getCellStyle();
//             CellStyle nstyle = wb.createCellStyle();
//             nstyle.cloneStyleFrom(style);
//             hcell.setCellStyle(nstyle);
//             hcell.getCellStyle().setFillForegroundColor(IndexedColors.WHITE.getIndex());
//        }
    }
    
}
