package com.hxci.util;

import com.hxci.pojo.Zdtype;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExportExcelUtil {

    /**
     *  导出模板方法
     * @param list
     * @param type
     * @throws IOException
     */
    public static void export(List<Zdtype> list ,LanguageType type ,  OutputStream out) throws IOException {

        if(!((list ==null) || list.size()<=0)){
            // 第一步,创建一个workbook,对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 第二步,在workbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = workbook.createSheet("sheet1");

            // 第三步,在sheet中添加表头第0行，注意老版本poi对Excel 的行数列数有限制short
            HSSFRow row =sheet.createRow(0);
            row.setHeight((short) 500);
            // 第四步,创建单元格,并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            //居中样式
            hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //创建绘图
            Drawing drawing =  sheet.createDrawingPatriarch();



            HSSFCell hssfCell = null;

            for (int i =0;i<list.size();i++){
                sheet.setColumnWidth(i,200*20);
                sheet.autoSizeColumn(i,true);//自动适应列宽
                //创建批注(设置创建批注的大小范围)
                //int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2
                //前四个参数是批注坐标，后四个参数设置批注大小
                Comment comment= drawing.createCellComment(
                        new HSSFClientAnchor(0, 0, 0, 0, (short)3, (short)3, (short)6, (short)6));
                //批注是否可见
                comment.setVisible(false);
                hssfCell = row.createCell(i);//列索引从0开始

                //需要导出的字段是中文 还是英文
                if(LanguageType.CN.equals(type)){//中文
                    //设置批注内容
                    comment.setString(new HSSFRichTextString(list.get(i).getZddm()));
                    hssfCell.setCellComment(comment);
                    hssfCell.setCellValue(list.get(i).getZdmc());//列名
                }
                if(LanguageType.EN.equals(type)){//英文
                    comment.setString(new HSSFRichTextString(list.get(i).getZdmc()));
                    hssfCell.setCellComment(comment);
                    hssfCell.setCellValue(list.get(i).getZddm());//列名
                }
                hssfCell.setCellStyle(hssfCellStyle);//列居中显示
            }
//            File file = new File("C:\\Users\\xuning\\Desktop\\aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.xls");
//            file.createNewFile();
//            FileOutputStream stream = FileUtils.openOutputStream(file);
            workbook.write(out);
            out.flush();
            out.close();
        }
    }


}
