package com.zb.tools.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

public class PoiCreateExcelTest
{
    public static void main(String[] args)
    {
        /** 
         * @see <a href="http://poi.apache.org/hssf/quick-guide.html#NewWorkbook">For more</a>
         */
        // 创建新的Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        HSSFSheet sheet = workbook.createSheet();
        // HSSFSheet sheet = workbook.createSheet("SheetName");

        // 用于格式化单元格的数据
        HSSFDataFormat format = workbook.createDataFormat();

        // 创建新行(row),并将单元格(cell)放入其中. 行号从0开始计算.
        HSSFRow row = sheet.createRow((short) 1);

        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20); // 字体高度
        font.setColor(HSSFFont.COLOR_RED); // 字体颜色
        font.setFontName("黑体"); // 字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
        font.setItalic(true); // 是否使用斜体
        // font.setStrikeout(true); //是否使用划线

        // 设置单元格类型
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
        cellStyle.setWrapText(true);

        // 添加单元格注释
        // 创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("Xuys.");

        // 创建单元格
        HSSFCell cell = row.createCell((short) 1);
        HSSFRichTextString hssfString = new HSSFRichTextString("Hello World!");
        cell.setCellValue(hssfString);// 设置单元格内容
        cell.setCellStyle(cellStyle);// 设置单元格样式
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 指定单元格格式：数值、公式或字符串
        cell.setCellComment(comment);// 添加注释

        // 格式化数据
        row = sheet.createRow((short) 2);
        cell = row.createCell((short) 2);
        cell.setCellValue(11111.25);
        cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format.getFormat("0.0"));
        cell.setCellStyle(cellStyle);

        row = sheet.createRow((short) 3);
        cell = row.createCell((short) 3);
        cell.setCellValue(9736279.073);
        cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format.getFormat("#,##0.0000"));
        cell.setCellStyle(cellStyle);

        sheet.autoSizeColumn((short) 0); // 调整第一列宽度
        sheet.autoSizeColumn((short) 1); // 调整第二列宽度
        sheet.autoSizeColumn((short) 2); // 调整第三列宽度
        sheet.autoSizeColumn((short) 3); // 调整第四列宽度

        try
        {
            FileOutputStream fileOut = new FileOutputStream("/3.xls");
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

}