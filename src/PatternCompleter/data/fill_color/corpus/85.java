package com.xyy.saas.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xyy.saas.constant.ExcelName;

/**
 * Created by zhengding on 2018/4/24.
 */
public class CreateExcelInitialization {

    public static XSSFWorkbook createExcel(String[] headers, String title, String sheetName, List<String[]> dataList, String fileName) throws Exception{
        XSSFWorkbook work = new XSSFWorkbook();

        //创建工作表
        Sheet sheet = work.createSheet(sheetName);

        //显示标题
        Row title_row = sheet.createRow(0);
        title_row.setHeight((short)(40*20));

        Row header_row = sheet.createRow(0);
//        header_row.setHeight((short)(20*24));

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,0));

        for(int i=0;i<headers.length;i++){
            //设置列宽   基数为256
            sheet.setColumnWidth(i, 30*256);
            Cell cell = header_row.createCell(i);
            String head = headers[i];
            if(fileName.equals(ExcelName.SHANGPINZILIAO_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式
                if(head.equals("商品名称") || head.equals("产地") || head.equals("商品条码")
                        || head.equals("处方分类") || head.equals("ABC分类") || head.equals("架位")
                        || head.equals("库存上限") || head.equals("库存下限") || head.equals("标准库ID")){
                    colourStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }else{
                    colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }
            }
            if(fileName.equals(ExcelName.SHANGPINJIECUN_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式
                if(head.equals("最后进货单位编号") || head.equals("最后进货单位名称")){
                    colourStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }else{
                    colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }
            }
            if(fileName.equals(ExcelName.GONGYINGSHANGZILIAO_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式

                colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cell.setCellStyle(colourStyle);

            }
            if(fileName.equals(ExcelName.GONGYINSHANGGYUE_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式

                colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cell.setCellStyle(colourStyle);

            }
            if(fileName.equals(ExcelName.HUIYUANZILIAO_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式
                if(head.equals("年龄") || head.equals("身份证号") || head.equals("地址") || head.equals("QQ")
                        || head.equals("微信") || head.equals("激活时间") || head.equals("失效日期")){
                    colourStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }else{
                    colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }
            }
            if(fileName.equals(ExcelName.BIAOZHUNKU_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式

                colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cell.setCellStyle(colourStyle);

            }
            if(fileName.equals(ExcelName.LISHICAIGOUJILU_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式
                if(head.equals("日期") || head.equals("单据编号")){
                    colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }else{
                    colourStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);

                }
            }
            if(fileName.equals(ExcelName.LISHIXIAOSHOUJILU_TEMPLET)){
                CellStyle colourStyle = work.createCellStyle();
                colourStyle.setAlignment(colourStyle.ALIGN_CENTER); //水平方向上的对其方式
                colourStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直方向上的对其方式
                if(head.equals("小票号") || head.equals("时间")){
                    colourStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }else{
                    colourStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                    colourStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    cell.setCellStyle(colourStyle);
                }

            }
            //应用样式到  单元格上
            cell.setCellValue(head);
        }

        //插入需导出的数据
        for(int i=0;i<dataList.size();i++){
            Row row = sheet.createRow(i+1);
            row.setHeight((short)(20*20)); //设置行高  基数为20
            String[] data1 = dataList.get(i);
            for(int j=0;j<data1.length;j++){
                Cell cell = row.createCell(j);
                cell.setCellValue(data1[j]);
            }
        }
        return work;
    }

    public static void writeExcel(HttpServletResponse response, String fileName, String headers[], String title, List<String[]> dataList, String sheetName) throws IOException {
        OutputStream out = null;
        try {
            XSSFWorkbook work = createExcel( headers, title, sheetName, dataList,fileName);
            out = response.getOutputStream();
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName , "UTF-8"))));
            work.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("输出流错误");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
