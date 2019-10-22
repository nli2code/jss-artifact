package org.zap.framework.common.excel.jxls;


import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.jxls.common.CellData;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiUtil;
import org.jxls.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Cell data wrapper for POI cell
 * @author Leonid Vysochyn
 *         Date: 1/23/12
 */
public class PoiCellData extends CellData {
    static Logger logger = LoggerFactory.getLogger(PoiCellData.class);

    RichTextString richTextString;
    private CellStyle cellStyle;
    private Hyperlink hyperlink;
    private Comment comment;
    private String commentAuthor;
    private Cell srcCell;

    public PoiCellData(CellRef cellRef) {
        super(cellRef);
    }

    public PoiCellData(CellRef cellRef, Cell srcCell) {
        super(cellRef);
        this.srcCell = srcCell;
    }

    public static PoiCellData createCellData(CellRef cellRef, Cell srcCell){
        PoiCellData cellData = new PoiCellData(cellRef, srcCell);
        cellData.readCell(srcCell);
        cellData.updateFormulaValue();
        return cellData;
    }

    public void readCell(Cell srcCell){
        readCellGeneralInfo(srcCell);
        readCellContents(srcCell);
        readCellStyle(srcCell);
    }

    private void readCellGeneralInfo(Cell srcCell) {
        hyperlink = srcCell.getHyperlink();
        try {
            comment = srcCell.getCellComment();
        } catch (Exception e) {
            logger.error("Failed to read cell comment at " + new CellReference(srcCell).formatAsString(), e);
            return;
        }
        if(comment != null ){
            commentAuthor = comment.getAuthor();
        }
        if( comment != null && comment.getString() != null ){
            setCellComment( comment.getString().getString() );
        }
    }


    private void readCellContents(Cell srcCell) {
        switch( srcCell.getCellType() ){
            case Cell.CELL_TYPE_STRING:
                richTextString = srcCell.getRichStringCellValue();
                cellValue = richTextString.getString();
                cellType = CellType.STRING;

                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = srcCell.getBooleanCellValue();
                cellType = CellType.BOOLEAN;
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(srcCell)) {
                    cellValue = srcCell.getDateCellValue();
                    cellType = CellType.DATE;
                } else {
                    cellValue = srcCell.getNumericCellValue();
                    cellType = CellType.NUMBER;
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                formula = srcCell.getCellFormula();
                cellValue = formula;
                cellType = CellType.FORMULA;
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = srcCell.getErrorCellValue();
                cellType = CellType.ERROR;
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = null;
                cellType = CellType.BLANK;
                break;
        }
        evaluationResult = cellValue;
    }

    private void readCellStyle(Cell srcCell) {
        cellStyle = srcCell.getCellStyle();
    }

    public void writeToCell(Cell cell, Context context, MyPoiTransformer transformer){
        evaluate(context);
        if( evaluationResult != null && evaluationResult instanceof WritableCellValue){
        	cell.setCellStyle(cellStyle);
            ((WritableCellValue)evaluationResult).writeToCell(cell, context);
        }else{
            updateCellGeneralInfo(cell);
            updateCellContents(cell);
            updateCellStyle(cell, cellStyle);
        }

        //解决中文列宽自适应问题，格子最小宽度
//        int columnWidth = cell.getSheet().getColumnWidth(cell.getColumnIndex());
//        cell.getSheet().setColumnWidth(cell.getColumnIndex(), Math.max(columnWidth, String.valueOf(evaluationResult).getBytes().length * 2 * 256));
    }


    private void updateCellGeneralInfo(Cell cell) {
        cell.setCellType(getPoiCellType(targetCellType) );
        if( hyperlink != null ){
            cell.setHyperlink( hyperlink );
        }
        if(comment != null && getCellComment()!=null&&!PoiUtil.isJxComment(getCellComment())){
            PoiUtils.setCellComment(cell, getCellComment(), commentAuthor, null);
        }
    }

    static int getPoiCellType(CellType cellType){
        if( cellType == null ){
            return Cell.CELL_TYPE_BLANK;
        }
        switch (cellType){
            case STRING:
                return Cell.CELL_TYPE_STRING;
            case BOOLEAN:
                return Cell.CELL_TYPE_BOOLEAN;
            case NUMBER:
            case DATE:
                return Cell.CELL_TYPE_NUMERIC;
            case FORMULA:
                return Cell.CELL_TYPE_FORMULA;
            case ERROR:
                return Cell.CELL_TYPE_ERROR;
            case BLANK:
                return Cell.CELL_TYPE_BLANK;
            default:
                return Cell.CELL_TYPE_BLANK;
        }
    }

    private void updateCellContents(Cell cell) {
        switch( targetCellType ){
            case STRING:
                if( evaluationResult instanceof byte[]){

                }else{
                    cell.setCellValue((String) evaluationResult);
                }
                break;
            case BOOLEAN:
                cell.setCellValue( (Boolean)evaluationResult );
                break;
            case DATE:
                cell.setCellValue((Date)evaluationResult);
                break;
            case NUMBER:
                    cell.setCellValue(((Number) evaluationResult).doubleValue());
                break;
            case FORMULA:
                try{
                    if( Util.formulaContainsJointedCellRef((String) evaluationResult) ){
                        cell.setCellValue((String)evaluationResult);
                    }else{
                        cell.setCellFormula((String) evaluationResult);
                    }
                }catch(FormulaParseException e){
                    String formulaString = "";
                    try{
                        formulaString = evaluationResult.toString();
                        logger.error("Failed to set cell formula " + formulaString + " for cell " + this.toString(), e);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(formulaString);
                    }catch(Exception ex){
                        logger.warn("Failed to convert formula to string for cell " + this.toString());
                    }
                }
                break;
            case ERROR:
                cell.setCellErrorValue((Byte) evaluationResult);
                break;
        }
    }

    private void updateCellStyle(Cell cell, CellStyle cellStyle) {
        cell.setCellStyle(cellStyle);
    }
    
    
}
