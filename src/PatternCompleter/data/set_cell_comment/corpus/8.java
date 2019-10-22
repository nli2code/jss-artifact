package com.edu.util;

import org.apache.poi.ss.usermodel.*;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.io.IOException;  
import java.io.FileOutputStream;  
  
/** 
 * Demonstrates how to work with excel cell comments. 
 * <p> 
 * Excel comment is a kind of a text shape, 
 * so inserting a comment is very similar to placing a text box in a worksheet 
 * </p> 
 * 
 * @author Yegor Kozlov 
 */  
public class CellComments {  
    public static void main(String[] args) throws IOException {  
        //1.创建一个工作簿对象  
        XSSFWorkbook wb = new XSSFWorkbook();  
  
        //2.得到一个POI的工具类  
        CreationHelper factory = wb.getCreationHelper();  
  
        //3. 创建一个工作表  
        XSSFSheet sheet = wb.createSheet();  
          
        //4.得到一个换图的对象  
        Drawing drawing = sheet.createDrawingPatriarch();  
        //5. ClientAnchor是附属在WorkSheet上的一个对象，  其固定在一个单元格的左上角和右下角.  
        ClientAnchor anchor = factory.createClientAnchor();  
          
        //6. 创建一个单元格（2A单元格）  
        Cell cell0 = sheet.createRow(1).createCell(0);  
        //6.1. 对这个单元格设置值  
        cell0.setCellValue("Test");  
        //6.2. 对这个单元格加上注解  
        Comment comment0 = drawing.createCellComment(anchor);  
        RichTextString str0 = factory.createRichTextString("Hello, World!");  
        comment0.setString(str0);  
        comment0.setAuthor("Apache POI");  
        cell0.setCellComment(comment0);  
          
        //7. 创建一个单元格（4F单元格）  
        Cell cell1 = sheet.createRow(3).createCell(5);  
        //7.1. 对这个单元格设置值  
        cell1.setCellValue("F4");  
        //7.2. 对这个单元格加上注解  
        Comment comment1 = drawing.createCellComment(anchor);  
        RichTextString str1 = factory.createRichTextString("Hello, World!");  
        comment1.setString(str1);  
        comment1.setAuthor("Apache POI");  
        cell1.setCellComment(comment1);  
  
        //8. 创建一个单元格（4F单元格）  
        Cell cell2 = sheet.createRow(2).createCell(2);  
        cell2.setCellValue("C3");  
  
        Comment comment2 = drawing.createCellComment(anchor);  
        RichTextString str2 = factory.createRichTextString("XSSF can set cell comments");  
        //9。为注解设置字体  
        Font font = wb.createFont();  
        font.setFontName("Arial");  
        font.setFontHeightInPoints((short)14);  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  
        font.setColor(IndexedColors.RED.getIndex());  
        str2.applyFont(font);  
  
        comment2.setString(str2);  
        comment2.setAuthor("Apache POI");  
        comment2.setColumn(2);  
        comment2.setRow(2);  
        //10. 保存成Excel文件  
        String fname = "comments.xlsx";  
        FileOutputStream out = new FileOutputStream(fname);  
        wb.write(out);  
        out.close();  
  
    }  
}  
