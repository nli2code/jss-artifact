package me.ly.tools.excel.imp.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.ly.tools.excel.imp.entity.*;
import me.ly.tools.excel.imp.exception.ImportRecordException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel读取工具类
 *
 * @author Created by LiYao on 2017-05-11 11:10.
 */
public class ExcelReadUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelReadUtil.class);

    private static final Map<String, Workbook> WORKBOOK_MAP = new ConcurrentHashMap<>();

    public static Workbook createWorkbook(String fileName, InputStream inputStream) throws Exception {
        if (StringUtils.isBlank(fileName) || inputStream == null) {
            return null;
        }
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            WORKBOOK_MAP.put(fileName, workbook);
            return workbook;
        } catch (Exception e) {
            logger.error("展开【{}】文件失败。检查上传文件是否为Excel文件", fileName, e);
            throw new ImportRecordException("无法读取。请检查是否为Excel文件");
        } finally {
            inputStream.close();
        }
    }

    public static Workbook getWorkbook(String fileName) throws Exception {
        OutputStream out = null;
        InputStream input = null;
        Workbook workbook = WORKBOOK_MAP.get(fileName);
        File excelFile = FileUtil.createTempFile(fileName);
        try {
            if (workbook != null) {
                out = new FileOutputStream(excelFile);
                workbook.write(out);
                WORKBOOK_MAP.remove(fileName);
            }
            input = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(input);
            return workbook;
        } finally {
            FileUtil.closeResource(input, out);
        }
    }

    public static void readExcel(Workbook wb, Excel excelEntity) throws Exception {
        if (wb == null || excelEntity == null) {
            return;
        }

        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

        List<Sheet> sheetList = excelEntity.getAllSheet();
        for (Sheet sheet : sheetList) {
            org.apache.poi.ss.usermodel.Sheet poiSheet = wb.getSheet(sheet.getName());
            int startRow = sheet.getReadStartRow();
            int lastRow = poiSheet.getPhysicalNumberOfRows();
            int startCol = sheet.getReadStartCol();
            int lastCol = sheet.colSize() - startCol + 1;
            // 校验上传文件与模版是否对应
            Row titleRow = sheet.getRow(startRow - 1);
            org.apache.poi.ss.usermodel.Row poiTitleRow = poiSheet.getRow(startRow - 2);
            for (int j = startCol; j <= lastCol; j++) {
                Cell cell = titleRow.getCell(j);
                org.apache.poi.ss.usermodel.Cell poiCell = poiTitleRow.getCell(j - 1);
                String poiValue = getCellValue(poiCell, evaluator);
                if (!poiValue.equals(cell.getName())) {
                    throw new ImportRecordException("上传文件与模版不匹配");
                }
            }

            org.apache.poi.ss.usermodel.Row poiRow;
            org.apache.poi.ss.usermodel.Cell poiCell;
            boolean isBlankRow = true;

            for (int i = startRow; i <= lastRow; i++) {
                if (i != startRow && isBlankRow) {
                    break;
                }
                isBlankRow = nextRowIsBlank(evaluator, poiSheet.getRow(i), startCol, lastCol);

                poiRow = poiSheet.getRow(i - 1);
                Row dataRow = sheet.createRow(i);

                for (int j = startCol; j <= lastCol; j++) {
                    Column dataColumn = sheet.getColumn(j);
                    Cell dataCell = dataRow.createCell(j);
                    if (dataColumn == null || dataCell == null) {
                        continue;
                    }
                    poiCell = poiRow.getCell(j - 1);
                    // 清除原有数据行的颜色、批注，以免在生成错误文件的时候产生混淆
                    if (poiCell != null) {
                        if (poiCell.getCellStyle() != null) {
                            poiCell.setCellStyle(null);
                        }
                        if (poiCell.getCellComment() != null) {
                            poiCell.setCellComment(null);
                        }
                    }

                    dataCell.setName(dataColumn.getName());
                    dataCell.setCode(dataColumn.getCode());
                    dataCell.setErrMsg(null);
                    String value;
                    try {
                        value = getCellValue(poiCell, evaluator);
                    } catch (Exception e) {
                        dataCell.setErrMsg("解析该单元格失败，请检查！");
                        if ("not implemented yet".equals(e.getMessage())) {
                            dataCell.setErrMsg("不支持该公式，请修改！");
                        }
                        logger.error("读取单元格异常。Excel：{},Sheet：{},row：{},col：{}。Exception：", excelEntity.getName(), sheet.getName(), i, j, e);
                        continue;
                    }
                    dataCell.setValue(value);

                    dataCell.setDataType(dataColumn.getDataType());
                    dataCell.setFormat(dataColumn.getFormat());
                    dataCell.setRequired(dataColumn.isRequired());
                    dataCell.setAllowSkip(dataColumn.isAllowSkip());
                    dataCell.setValueList(dataColumn.getValueList());
                }

            }

        }
    }

    private static boolean nextRowIsBlank(FormulaEvaluator evaluator, org.apache.poi.ss.usermodel.Row poiRow, int startCol, int lastCol) {
        if (poiRow == null) {
            return true;
        }
        for (int j = startCol; j <= lastCol; j++) {
            org.apache.poi.ss.usermodel.Cell poiCell = poiRow.getCell(j - 1);
            try {
                String value = getCellValue(poiCell, evaluator);
                if (StringUtils.isNotBlank(value)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

        }
        return true;
    }

    private static final DecimalFormat DF = new DecimalFormat("#.#########");

    @SuppressWarnings("WeakerAccess")
    public static String getCellValue(org.apache.poi.ss.usermodel.Cell poiCell, FormulaEvaluator evaluator) {
        if (poiCell == null) {
            return "";
        }
        switch (poiCell.getCellType()) {

        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
            return poiCell.getBooleanCellValue() + "";
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(poiCell)) {
                Date tmpDate = poiCell.getDateCellValue();
                return DateFormatUtils.format(tmpDate, "yyyy-MM-dd HH:mm:ss");
            }
            double d = poiCell.getNumericCellValue();
            return DF.format(d);
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
            return StringUtils.chomp(StringUtils.defaultString(poiCell.getStringCellValue(), ""));
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
            return "";
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
            CellValue cellValue = evaluator.evaluate(poiCell);
            return getCellFormulaValue(cellValue);
        }
        return "";
    }

    private static String getCellFormulaValue(CellValue cellValue) {
        if (cellValue == null) {
            return "";
        }

        switch (cellValue.getCellType()) {

        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
            return cellValue.getBooleanValue() + "";
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
            double d = cellValue.getNumberValue();
            return DF.format(d);
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
            return StringUtils.chomp(StringUtils.defaultString(cellValue.getStringValue(), ""));
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
            return "";
        case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
            return "";

        }
        return "";
    }

}
