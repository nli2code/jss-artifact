package ampc.com.gistone.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ampc.com.gistone.database.model.TMeasureExcel;
import ampc.com.gistone.util.JsonUtil;
import ampc.com.gistone.util.LogUtil;
@RestController
@RequestMapping
public class TestController {
	
	
	@RequestMapping("json/readJson")
	public static void readJson(){  
		try {
			File directory = new File("");
			String path= directory.getCanonicalPath()+"\\src\\main\\resources\\check.json";
			System.out.println(path);
			//String map=JsonUtil.readFile(path);
			LinkedHashMap map=JsonUtil.readObjFromJsonFile(path, LinkedHashMap.class);
			System.out.println(map.get("应急系统新_1描述文件"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
	
	
	
	@RequestMapping("log/log4jTest")
	public static void log4jTest(){
		/**
		 * 所有logger中的内容信息 都根据实际情况填写  如：添加任务成功 、添加任务失败、添加任务异常 等
 		 */
		/**
		 * 成功的实例
		 */
		try{
			//执行成功是用该方法记录
			LogUtil.getLogger().info("执行成功");
		}catch(Exception e){
			LogUtil.getLogger().error("异常了",e);
		}
		/**
		 * 警告的实例
		 * 在不影响程序的继续进行时使用该方法记录
		 */
		try{
			int i=1,j=2;
			if((i+j)==2)LogUtil.getLogger().info("执行成功");
			throw new Exception("计算错误");
		}catch(Exception e){
			//出现警告进行记录   在不影响程序的继续进行时使用该方法记录
			LogUtil.getLogger().warn("警告",e);
		}
		/**
		 * 异常的实例
		 * 在影响了结果的显示时使用该方法记录
		 */
		try{
			String[] a=null;
			System.out.println(a[1]);
			LogUtil.getLogger().info("执行成功");
		}catch(Exception e){
			//出现异常进行记录  在影响了结果的显示时使用该方法记录
			LogUtil.getLogger().error("异常了",e);
		}
	}
	
	
	 //得到一个POI的工具类
	 public static CreationHelper factory;
	 public static Workbook wb  = null;  
	 public static ClientAnchor anchor =null;
	 public static LinkedHashMap drawMap;
	@RequestMapping("ex/ex")
	public static void ReadMeasure(){  
		
        try {    
//        	this.workbook = obtainBook(extend, stream).getWorkbook();
//            //设置背景色为黄色
//            this.yellowStyle = workbook.createCellStyle();
//            this.yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//            this.yellowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            this.yellowStyle.setAlignment(CellStyle.ALIGN_CENTER);
//            this.yellowStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//
//            this.factory = workbook.getCreationHelper();
//            //this.anchor = factory.createClientAnchor();
//            this.anchor = new XSSFClientAnchor(0, 0, 0, 0, (short)1, 2, (short)3, 10);
//
//            this.drawMap = new LinkedHashMap<>();
//            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//              Sheet sheet = workbook.getSheetAt(i);
//              this.drawMap.put(sheet.getSheetName(), sheet.createDrawingPatriarch());
//            }
            //自动根据Excel版本创建对应的Workbook
        	String path="C:\\Users\\Mr_Wang\\Desktop\\ceshi.xlsx";
        	InputStream is=null;
            OutputStream os=null;
            File file =new File(path);
            is=new FileInputStream(file);    
            wb = WorkbookFactory.create(is);  
            factory = wb.getCreationHelper();
            anchor = new XSSFClientAnchor(0, 0, 0, 0, (short)1, 2, (short)3, 10);
            drawMap = new LinkedHashMap();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
              Sheet sheet = wb.getSheetAt(i);
              drawMap.put(sheet.getSheetName(), sheet.createDrawingPatriarch());
            }
        	//获得当前页
        	Sheet sheet = wb.getSheetAt(0);  
        	//获得所有行
        	Iterator<Row> rows = sheet.rowIterator(); 
        	//循环所有行
        	while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                String s1=(getCellValue(row.getCell(0)));
                String s2=(getCellValue(row.getCell(1)));
                String s3=(getCellValue(row.getCell(2)));
                String s4=(getCellValue(row.getCell(3)));
                String s5=(getCellValue(row.getCell(4)));
                String s6=(getCellValue(row.getCell(5)));
                if(s1.equals("")||s1==null){
                	CellStyle style = wb.createCellStyle();
					style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					Cell cell=row.getCell(0);
					cell=row.createCell((short) 0);
					cell.setCellStyle(style);
					cell.setCellComment(getComment(sheet, "为空")); 
                }
				if(s2.equals("")||s2==null){
					CellStyle style = wb.createCellStyle();
					style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					Cell cell=row.getCell(1);
					cell=row.createCell((short) 1);
					cell.setCellStyle(style);
					cell.setCellComment(getComment(sheet, "为空"));           	
				}
				if(s3.equals("")||s3==null){
					CellStyle style = wb.createCellStyle();
					style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					Cell cell=row.getCell(2);
					cell=row.createCell((short) 2);
					cell.setCellStyle(style);
					cell.setCellComment(getComment(sheet, "为空"));  
				}
				if(s4.equals("")||s4==null){
					CellStyle style = wb.createCellStyle();
					style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					Cell cell=row.getCell(3);
					cell=row.createCell((short) 3);
					cell.setCellStyle(style);
					cell.setCellComment(getComment(sheet, "为空"));  
				}
				if(s5.equals("")||s5==null){
					CellStyle style = wb.createCellStyle();
					style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					Cell cell=row.getCell(4);
					cell=row.createCell((short) 4);
					cell.setCellStyle(style);
					cell.setCellComment(getComment(sheet, "为空"));  
				}
            } 
        	is.close();
        	//File file = new File("C:\\Users\\Mr_Wang\\Desktop\\test.xlsx");
        	//FileOutputStream out = new FileOutputStream(new File(path));
        	os = new FileOutputStream(file);
        	wb.write(os);
            os.close();
            wb.close();
        } catch (Exception ex) {  
            ex.printStackTrace();
        }  
    }  
	
	/**
	   * 生成标注
	*/
  public static Comment getComment(Sheet sheet, String message) {
    Drawing drawing = (Drawing)drawMap.get(sheet.getSheetName());
    ClientAnchor an = drawing.createAnchor(0, 0, 0, 0, (short) 1, 2, (short) 3, 10);
    Comment comment0 = drawing.createCellComment(an);
    RichTextString str0 = factory.createRichTextString(message);
    comment0.setString(str0);
    comment0.setAuthor("Apache POI");
    return comment0;
  }
	
  /**
   * 获取yellow style
   */
  public static CellStyle createErrorStyle(CellStyle style) {
    //设置背景色为黄色
    CellStyle errorStyle = wb.createCellStyle();
    errorStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    errorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    errorStyle.setAlignment(CellStyle.ALIGN_CENTER);
    errorStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    if (null != style) errorStyle.setDataFormat(style.getDataFormat());
    return errorStyle;
  }
 
  
  
	/**   
	* 获取单元格的值   
	* @param cell   
	* @return   
	*/    
	public static String getCellValue(Cell cell){
		if(cell == null) return "";
		switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
	        case Cell.CELL_TYPE_NUMERIC:
	        	return String.valueOf(cell.getNumericCellValue()).trim();
	        case Cell.CELL_TYPE_STRING:
	        	return cell.getStringCellValue().trim();
	        case Cell.CELL_TYPE_BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue()).trim();
	        case Cell.CELL_TYPE_FORMULA:
	            return cell.getCellFormula().trim();
	        default:
	            return "";
		}
	}
	
	public static Object sssss() throws IOException{
		String path="C:\\Users\\Administrator\\Desktop\\result.json";
		ObjectMapper mapper=new ObjectMapper();
		JsonNode js=mapper.readTree(new File(path));
		return js;
	}
	
	
}
