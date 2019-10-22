/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiobj;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;
import java.io.FileOutputStream;

/**
 *
 * @author Administrator
 */
public class NewSheet {
    public static void main(String args[]) throws Exception
    {
         Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
         Sheet sheet1 = wb.createSheet("new sheet");
         Sheet sheet2 = wb.createSheet("second sheet");
    //注意：sheet表名不能超过31个字符，必须不能包含下列字符:
    // Note that sheet name is Excel must not exceed 31 characters
    // and must not contain any of the any of the following characters:
    // 0x0000
    // 0x0003
    // colon (:)
    // backslash (\)
    // asterisk (*)
    // question mark (?)
    // forward slash (/)
    // opening square bracket ([)
    // closing square bracket (])

    // You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
    // for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
    //你可以使用org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)
    //使用一种安全的方式创建有效的名字，这个应用程序使用空格代替无效的字符
    String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
    Sheet sheet3 = wb.createSheet(safeName);

    FileOutputStream fileOut = new FileOutputStream("D://java源程序/workbook.xls");
    wb.write(fileOut);
    fileOut.close();
    }
    
}
