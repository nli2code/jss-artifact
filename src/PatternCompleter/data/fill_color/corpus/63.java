package com.kingmay.mutil.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.kingmay.beans.Age2;
import com.kingmay.beans.Age4;
import com.kingmay.beans.Child;

public class DoExcel {
	public DoExcel(){}
	
	/**

     * ��ȡExcel�����ݣ���һά����洢����һ���и��е�ֵ����ά����洢���Ƕ��ٸ���

     * @param file ��ȡ���ݵ�ԴExcel

     * @param ignoreRows ��ȡ���ݺ��Ե�������������ͷ����Ҫ���� ���Ե�����Ϊ1

     * @return ������Excel�����ݵ�����

     * @throws FileNotFoundException

     * @throws IOException

     */

    public static synchronized String[][] getData(File file, int ignoreRows)

           throws FileNotFoundException, IOException {

       List<String[]> result = new ArrayList<String[]>();

       int rowSize = 0;

       BufferedInputStream in = new BufferedInputStream(new FileInputStream(

              file));

       // ��HSSFWorkbook

       POIFSFileSystem fs = new POIFSFileSystem(in);

       HSSFWorkbook  wb = new HSSFWorkbook(fs);

       HSSFCell cell = null;

       for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

           HSSFSheet st = wb.getSheetAt(sheetIndex);

           // ��һ��Ϊ���⣬��ȡ

           for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

              HSSFRow row = st.getRow(rowIndex);

              if (row == null) {

                  continue;

              }

              int tempRowSize = row.getLastCellNum() + 1;

              if (tempRowSize > rowSize) {

                  rowSize = tempRowSize;

              }

              String[] values = new String[rowSize];

              Arrays.fill(values, "");

              boolean hasValue = false;

              for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

                  String value = "";

                  cell = row.getCell(columnIndex);

                  if (cell != null) {

                     // ע�⣺һ��Ҫ��������������ܻ��������

                     cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                     switch (cell.getCellType()) {

                     case HSSFCell.CELL_TYPE_STRING:

                         value = cell.getStringCellValue();

                         break;

                     case HSSFCell.CELL_TYPE_NUMERIC:

                         if (HSSFDateUtil.isCellDateFormatted(cell)) {

                            Date date = cell.getDateCellValue();

                            if (date != null) {

                                value = new SimpleDateFormat("yyyy-MM-dd")

                                       .format(date);

                            } else {

                                value = "";

                            }

                         } else {

                            value = new DecimalFormat("0").format(cell

                                   .getNumericCellValue());

                         }

                         break;

                     case HSSFCell.CELL_TYPE_FORMULA:

                         // ����ʱ���Ϊ��ʽ���ɵ���������ֵ

                         if (!cell.getStringCellValue().equals("")) {

                            value = cell.getStringCellValue();

                         } else {

                            value = cell.getNumericCellValue() + "";

                         }

                         break;

                     case HSSFCell.CELL_TYPE_BLANK:

                         break;

                     case HSSFCell.CELL_TYPE_ERROR:

                         value = "";

                         break;

                     case HSSFCell.CELL_TYPE_BOOLEAN:

                         value = (cell.getBooleanCellValue() == true ? "Y"

                                : "N");

                         break;

                     default:

                         value = "";

                     }

                  }

                  if (columnIndex == 0 && value.trim().equals("")) {

                     break;

                  }

                  values[columnIndex] = rightTrim(value);

                  hasValue = true;

              }

 

              if (hasValue) {

                  result.add(values);

              }

           }

       }

       in.close();

       String[][] returnArray = new String[result.size()][rowSize];

       for (int i = 0; i < returnArray.length; i++) {

           returnArray[i] = (String[]) result.get(i);

       }

       return returnArray;

    }

   

    /**

     * ȥ���ַ����ұߵĿո�

     * @param str Ҫ������ַ���

     * @return �������ַ���

     */

     public static String rightTrim(String str) {

       if (str == null) {

           return "";

       }

       int length = str.length();

       for (int i = length - 1; i >= 0; i--) {

           if (str.charAt(i) != 0x20) {

              break;

           }

           length--;

       }

       return str.substring(0, length);

    }
	
     /**��ȡ2007 4��
      * */
     public static synchronized void read2007_4(String path , List<Child> clist, List<Age4> alist){
    	XSSFWorkbook xwb;
		try {
			xwb = new XSSFWorkbook(path);
			XSSFSheet sheet = xwb.getSheetAt(0);  
			XSSFRow row;  
			String cell;
			List<List<String>> sl= new ArrayList<List<String>>();
			for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {  
			    row = sheet.getRow(i);  
			    List<String> l = new ArrayList<String>();
			    for (int j = row.getFirstCellNum(); j < 40; j++) {  
			        // ͨ�� row.getCell(j).toString() ��ȡ��Ԫ�����ݣ�
			    	if( row.getCell(i)==null || row.getCell(j) == null){
			    		continue;
			    	}
			    	row.getCell(i).getCellType();
			        cell = row.getCell(j).toString();  
			        l.add(cell);
			    }  
			    sl.add(l);
			}  
			
			for(int i = 0; i<sl.size() ; i++){
				Child c = new Child();
				c.setCname(sl.get(i).get(0));
				c.setCtestname(sl.get(i).get(1));
				c.setCtyear((int)Float.parseFloat(sl.get(i).get(2)));
				c.setCtmonth((int)Float.parseFloat(sl.get(i).get(3)));
				c.setCtday((int)Float.parseFloat(sl.get(i).get(4)));
				c.setCbyear((int)Float.parseFloat(sl.get(i).get(5)));
				c.setCbmonth((int)Float.parseFloat(sl.get(i).get(6)));
				c.setCbday((int)Float.parseFloat(sl.get(i).get(7)));
				c.setCid(setlast(new Date()));
				
				int year,month,day,ctyear,ctmonth,ctday,cbyear,cbmonth,cbday;
				ctyear = c.getCtyear();
				ctmonth = c.getCtmonth();
				ctday = c.getCtday();
				cbyear = c.getCbyear();
				cbmonth = c.getCbmonth();
				cbday = c.getCbday();
				if(ctday < cbday){
					ctmonth--;
					ctday += 30;
					if(ctmonth <= 0){
						ctyear--;
						ctmonth += 12;
					}
				}
				day = ctday - cbday;
				if(ctmonth < cbmonth){
					ctyear--;
					ctmonth += 12;
				}
				month = ctmonth - cbmonth;
				year = ctyear - cbyear;
				
				c.setCyear(year);
				c.setCmonth(month);
				c.setCday(day);
				
				Age4 a = new Age4((int)Float.parseFloat(sl.get(i).get(16)), (int)Float.parseFloat(sl.get(i).get(17)), (int)Float.parseFloat(sl.get(i).get(18)), (int)Float.parseFloat(sl.get(i).get(19)), (int)Float.parseFloat(sl.get(i).get(31)), (int)Float.parseFloat(sl.get(i).get(20)), (int)Float.parseFloat(sl.get(i).get(21)), (int)Float.parseFloat(sl.get(i).get(22)), (int)Float.parseFloat(sl.get(i).get(23)), 
						(int)Float.parseFloat(sl.get(i).get(24)), (int)Float.parseFloat(sl.get(i).get(25)), (int)Float.parseFloat(sl.get(i).get(26)), (int)Float.parseFloat(sl.get(i).get(27)), (int)Float.parseFloat(sl.get(i).get(28)), (int)Float.parseFloat(sl.get(i).get(29)), (int)Float.parseFloat(sl.get(i).get(30)), (int)Float.parseFloat(sl.get(i).get(32))
						,  (int)Float.parseFloat(sl.get(i).get(33)) , (int)Float.parseFloat(sl.get(i).get(34)) , 0, c);
				a.setCid(c.getCid());
				
				clist.add(c);
				alist.add(a);
				
				write_4(clist,alist);
				
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}  
     }
     
     /**��ȡ2007 2��
      * */
     public static synchronized void read2007_2(String path , List<Child> clist, List<Age2> alist){
    	 XSSFWorkbook xwb;
		try {
			xwb = new XSSFWorkbook(path);
			XSSFSheet sheet = xwb.getSheetAt(0);  
			XSSFRow row;  
			String cell;
			List<List<String>> sl= new ArrayList<List<String>>();
			for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {  
			    row = sheet.getRow(i);  
			    List<String> l = new ArrayList<String>();
			    for (int j = row.getFirstCellNum(); j < 30; j++) {  
			        // ͨ�� row.getCell(j).toString() ��ȡ��Ԫ�����ݣ�
			    	if( row.getCell(i)==null || row.getCell(j) == null){
			    		continue;
			    	}
			    	row.getCell(i).getCellType();
			        cell = row.getCell(j).toString();  
			        l.add(cell);
//			        System.out.println(""+cell+"\t");
			    }  
			    sl.add(l);
			}  
			
			for(int i = 0; i<sl.size() ; i++){
				Child c = new Child();
				c.setCname(sl.get(i).get(0));
				c.setCtestname(sl.get(i).get(1));
				c.setCtyear((int)Float.parseFloat(sl.get(i).get(2)));
				c.setCtmonth((int)Float.parseFloat(sl.get(i).get(3)));
				c.setCtday((int)Float.parseFloat(sl.get(i).get(4)));
				c.setCbyear((int)Float.parseFloat(sl.get(i).get(5)));
				c.setCbmonth((int)Float.parseFloat(sl.get(i).get(6)));
				c.setCbday((int)Float.parseFloat(sl.get(i).get(7)));
				c.setCid(setlast(new Date()));
				
				int year,month,day,ctyear,ctmonth,ctday,cbyear,cbmonth,cbday;
				ctyear = c.getCtyear();
				ctmonth = c.getCtmonth();
				ctday = c.getCtday();
				cbyear = c.getCbyear();
				cbmonth = c.getCbmonth();
				cbday = c.getCbday();
				
				if(ctday < cbday){
					ctmonth--;
					ctday += 30;
					if(ctmonth <= 0){
						ctyear--;
						ctmonth += 12;
					}
				}
				day = ctday - cbday;
				if(ctmonth < cbmonth){
					ctyear--;
					ctmonth += 12;
				}
				month = ctmonth - cbmonth;
				year = ctyear - cbyear;
				
				c.setCyear(year);
				c.setCmonth(month);
				c.setCday(day);
				
				Age2 a = new Age2((int)Float.parseFloat(sl.get(i).get(16)), (int)Float.parseFloat(sl.get(i).get(17)), (int)Float.parseFloat(sl.get(i).get(18))
						, (int)Float.parseFloat(sl.get(i).get(19)), (int)Float.parseFloat(sl.get(i).get(20)) , (int)Float.parseFloat(sl.get(i).get(21)), 
						(int)Float.parseFloat(sl.get(i).get(22)), (int)Float.parseFloat(sl.get(i).get(23)), (int)Float.parseFloat(sl.get(i).get(24)),
						(int)Float.parseFloat(sl.get(i).get(25)), (int)Float.parseFloat(sl.get(i).get(26)), c);
				a.setCid(c.getCid());
				
				clist.add(c);
				alist.add(a);
				
				write_2(clist,alist);
				
			}
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}  
    	 
     }
     
     public static String setlast(Date time) {
 		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmm");
 		String last = df.format(time);
 		return last;
 	}
     
     /**д2007��ͷ 4��
      * */
     public static synchronized void write_4(List<Child> clist , List<Age4> alist){
    	XSSFWorkbook xwb;
		xwb = new XSSFWorkbook();
		XSSFSheet sheet = xwb.createSheet();
		dxqdHeadLine4(sheet, xwb , alist , clist);  
		String path = setlast(new Date());
		String filePath=ServletActionContext.getServletContext().getRealPath("download")+"\\"+ path+".xlsx";
		try {
			xwb.write(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("path",path);
	}

	private static void dxqdHeadLine4(XSSFSheet sheet, XSSFWorkbook xwb , List<Age4> alist , List<Child> clist) {
		Row header = sheet.createRow(0);// ��һ��  
		Cell cell = header.getCell(0);
		XSSFCellStyle style = xwb.createCellStyle();
		// //��һ������  
		createCell(xwb, header, 0, "");  
        createCell(xwb, header, 1, "����ҳ");  
        sheet.addMergedRegion(getCellRangeAddress(0, 1, 0, 12));
        
        cell = header.getCell(1);
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 13, ""); 
        cell = header.getCell(13);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 14, "ԭʼ�������������ת����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 14, 0, 34));
        cell = header.getCell(14);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 35, ""); 
        cell = header.getCell(35);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 36, "��������ܺ���ϳɷ���ת����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 36, 0, 59));
        cell = header.getCell(36);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 

        createCell(xwb, header, 60, "");
        cell = header.getCell(60);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 61, "��Ҫ����ҳ 4���ȽϷ���"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 61, 0, 67));
        cell = header.getCell(61);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 68, ""); 
        cell = header.getCell(68);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 69, "ָ��ˮƽ ǿ��������ȷ����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 69, 0, 98));
        cell = header.getCell(69);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 99, ""); 
        cell = header.getCell(99);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 100, "�ֲ���ˮƽ ǿ��������ȷ����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 100, 0, 181));
        cell = header.getCell(100);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 182, ""); 
        cell = header.getCell(182);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 183, "����Ƚϱ�"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 183, 0, 242));
        cell = header.getCell(183);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 243, ""); 
        cell = header.getCell(243);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 244, "��������ҳ ��������ܺ��븨��ָ������ת����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 244, 0, 263));
        cell = header.getCell(244);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 264, ""); 
        cell = header.getCell(264);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 265, "����Ƚϱ�"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 265, 0, 280));
        cell = header.getCell(265);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 281, ""); 
        cell = header.getCell(281);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        header = sheet.createRow(1);// ��2��  
        createCell(xwb, header, 0, ""); 
        
        createCell(xwb, header, 1, "��ͯ����");
        createCell(xwb, header, 2, "��������");
        createCell(xwb, header, 3, "���");
        createCell(xwb, header, 4, "��������");
        createCell(xwb, header, 5, "��������");
        createCell(xwb, header, 6, "��������");
        createCell(xwb, header, 7, "��������");
        createCell(xwb, header, 8, "��������");
        createCell(xwb, header, 9, "��������");
        createCell(xwb, header, 10, "ʵ������");
        createCell(xwb, header, 11, "ʵ������");
        createCell(xwb, header, 12, "ʵ������");
        
        createCell(xwb, header, 13, ""); 
        cell = header.getCell(13);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ1");
        
        createCell(xwb, header, 14, "��ľ �������");
        createCell(xwb, header, 15, "��ʶ �������");
        createCell(xwb, header, 16, "�������� �������");
        createCell(xwb, header, 17, "�ҳ� �������");
        createCell(xwb, header, 18, "ͼƬ���� �������");
        createCell(xwb, header, 19, "��ͬ �������");
        createCell(xwb, header, 20, "ͼƬ���� �������");
        createCell(xwb, header, 21, "���� �������");
        createCell(xwb, header, 22, "�����԰ �������");
        createCell(xwb, header, 23, "ƴͼ �������");
        createCell(xwb, header, 24, "�������� �������");
        createCell(xwb, header, 25, "ָ��ͼƬ �������");
        createCell(xwb, header, 26, "ͼƬ���� �������");
        createCell(xwb, header, 27, "������� �������");
        createCell(xwb, header, 28, "������ �������");
        createCell(xwb, header, 29, "�������VCI ��������ܺ�");
        createCell(xwb, header, 30, "�Ӿ��ռ�VSI ��������ܺ�");
        createCell(xwb, header, 31, "��������FRI ��������ܺ�");
        createCell(xwb, header, 32, "��������WMI ��������ܺ�");
        createCell(xwb, header, 33, "�ӹ��ٶ�PSI ��������ܺ�");
        createCell(xwb, header, 34, "ȫ����FSIQ ��������ܺ�");
        
        createCell(xwb, header, 35, ""); 
        cell = header.getCell(35);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ2");
        
        createCell(xwb, header, 36, "�������VCI �ϳɷ���");
        createCell(xwb, header, 37, "�Ӿ��ռ�VSI �ϳɷ���");
        createCell(xwb, header, 38, "��������FRI �ϳɷ���");
        createCell(xwb, header, 39, "��������WMI �ϳɷ���");
        createCell(xwb, header, 40, "�ӹ��ٶ�FSIQ �ϳɷ���");
        createCell(xwb, header, 41, "ȫ����FSIQ �ϳɷ���");
        
        createCell(xwb, header, 42, "�������VCI �ٷֵȼ�");
        createCell(xwb, header, 43, "�Ӿ��ռ�VSI �ٷֵȼ�");
        createCell(xwb, header, 44, "��������FRI �ٷֵȼ�");
        createCell(xwb, header, 45, "��������WMI �ٷֵȼ�");
        createCell(xwb, header, 46, "�ӹ��ٶ�PSI �ٷֵȼ�");
        createCell(xwb, header, 47, "ȫ����FSIQ �ٷֵȼ�");
        
        createCell(xwb, header, 48, "�������VCI 90%��������");
        createCell(xwb, header, 49, "�Ӿ��ռ�VSI 90%��������");
        createCell(xwb, header, 50, "��������FRI 90%��������");
        createCell(xwb, header, 51, "��������WMI 90%��������");
        createCell(xwb, header, 52, "�ӹ��ٶ�PSI 90%��������");
        createCell(xwb, header, 53, "ȫ����FSIQ 90%��������");
        
        createCell(xwb, header, 54, "�������VCI 95%��������");
        createCell(xwb, header, 55, "�Ӿ��ռ�VSI 95%��������");
        createCell(xwb, header, 56, "��������FRI 95%��������");
        createCell(xwb, header, 57, "��������WMI 95%��������");
        createCell(xwb, header, 58, "�ӹ��ٶ�PSI 95%��������");
        createCell(xwb, header, 59, "ȫ����FSIQ 95%��������");
        
        
        createCell(xwb, header, 60, ""); 
        cell = header.getCell(60);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ3");
        
        createCell(xwb, header, 61, "5��ָ���ܷ�");
        createCell(xwb, header, 62, "5��ָ���ܷ־�ֵ");
        createCell(xwb, header, 63, "������FSIQ �ϳɷ���");
        createCell(xwb, header, 64, "10��ָ���ֲ�����������ܺ�");
        createCell(xwb, header, 65, "10��ָ���ֲ������������ֵ");
        createCell(xwb, header, 66, "6�������̷ֲ�����������ܺ�");
        createCell(xwb, header, 67, "6�������̷ֲ����������ֵ");
        
        
        createCell(xwb, header, 68, ""); 
        cell = header.getCell(68);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ4");
        
        createCell(xwb, header, 69, "�������ָ��-ָ��������ֵ ��������");
        createCell(xwb, header, 70, "�Ӿ��ռ�ָ��-ָ��������ֵ ��������");
        createCell(xwb, header, 71, "��������ָ��-ָ��������ֵ ��������");
        createCell(xwb, header, 72, "��������ָ��-ָ��������ֵ ��������");
        createCell(xwb, header, 73, "�ӹ��ٶ�ָ��-ָ��������ֵ ��������");
        
        createCell(xwb, header, 74, "�������ָ��-ָ��������ֵ �ٽ�ֵ");
        createCell(xwb, header, 75, "�Ӿ��ռ�ָ��-ָ��������ֵ �ٽ�ֵ");
        createCell(xwb, header, 76, "��������ָ��-ָ��������ֵ �ٽ�ֵ");
        createCell(xwb, header, 77, "��������ָ��-ָ��������ֵ �ٽ�ֵ");
        createCell(xwb, header, 78, "�ӹ��ٶ�ָ��-ָ��������ֵ �ٽ�ֵ");
        
        createCell(xwb, header, 79, "�������ָ��-ָ��������ֵ ��ǿ�������");
        createCell(xwb, header, 80, "�Ӿ��ռ�ָ��-ָ��������ֵ ��ǿ�������");
        createCell(xwb, header, 81, "��������ָ��-ָ��������ֵ ��ǿ�������");
        createCell(xwb, header, 82, "��������ָ��-ָ��������ֵ ��ǿ�������");
        createCell(xwb, header, 83, "�ӹ��ٶ�ָ��-ָ��������ֵ ��ǿ�������");
        
        createCell(xwb, header, 84, "�������ָ��-������ ��������");
        createCell(xwb, header, 85, "�Ӿ��ռ�ָ��-������ ��������");
        createCell(xwb, header, 86, "��������ָ��-������ ��������");
        createCell(xwb, header, 87, "��������ָ��-������ ��������");
        createCell(xwb, header, 88, "�ӹ��ٶ�ָ��-������ ��������");
        
        createCell(xwb, header, 89, "�������ָ��-������ �ٽ�ֵ");
        createCell(xwb, header, 90, "�Ӿ��ռ�ָ��-������ �ٽ�ֵ");
        createCell(xwb, header, 91, "��������ָ��-������ �ٽ�ֵ");
        createCell(xwb, header, 92, "��������ָ��-������ �ٽ�ֵ");
        createCell(xwb, header, 93, "�ӹ��ٶ�ָ��-������ �ٽ�ֵ");
        
        createCell(xwb, header, 94, "�������ָ��-������ ��ǿ�������");
        createCell(xwb, header, 95, "�Ӿ��ռ�ָ��-������ ��ǿ�������");
        createCell(xwb, header, 96, "��������ָ��-������ ��ǿ�������");
        createCell(xwb, header, 97, "��������ָ��-������ ��ǿ�������");
        createCell(xwb, header, 98, "�ӹ��ٶ�ָ��-������ ��ǿ�������");
        
        createCell(xwb, header, 99, ""); 
        cell = header.getCell(99);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ5");
        
        createCell(xwb, header, 100, "��ʶ-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 101, "��ͬ-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 102, "��ľ-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 103, "ƴͼ-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 104, "��������-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 105, "ͼƬ����-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 106, "ͼƬ����-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 107, "�����԰-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 108, "�ҳ�-10��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 109, "����-10��ָ���ֲ������������ֵ ��������");
        
        createCell(xwb, header, 110, "��ʶ-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 111, "��ͬ-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 112, "��ľ-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 113, "ƴͼ-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 114, "��������-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 115, "ͼƬ����-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 116, "ͼƬ����-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 117, "�����԰-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 118, "�ҳ�-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 119, "����-10��ָ���ֲ������������ֵ �ٽ�ֵ");
        
        createCell(xwb, header, 120, "��ʶ-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 121, "��ͬ-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 122, "��ľ-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 123, "ƴͼ-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 124, "��������-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 125, "ͼƬ����-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 126, "ͼƬ����-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 127, "�����԰-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 128, "�ҳ�-10��ָ���ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 129, "����-10��ָ���ֲ������������ֵ ��ǿ�������");
        
        createCell(xwb, header, 130, "��ʶ-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 131, "��ͬ-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 132, "��ľ-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 133, "ƴͼ-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 134, "��������-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 135, "ͼƬ����-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 136, "ͼƬ����-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 137, "�����԰-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 138, "�ҳ�-10��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 139, "����-10��ָ���ֲ������������ֵ ������");
        
        createCell(xwb, header, 140, "��������������-����������������10��ָ�������֣�");
        
        createCell(xwb, header, 141, "��ʶ-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 142, "��ͬ-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 143, "��ľ-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 144, "ƴͼ-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 145, "��������-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 146, "ͼƬ����-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 147, "ͼƬ����-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 148, "�����԰-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 149, "�ҳ�-6�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 150, "����-6�������̷ֲ������������ֵ ��������");
        
        createCell(xwb, header, 151, "��ʶ-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 152, "��ͬ-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 153, "��ľ-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 154, "ƴͼ-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 155, "��������-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 156, "ͼƬ����-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 157, "ͼƬ����-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 158, "�����԰-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 159, "�ҳ�-6�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 160, "����-6�������̷ֲ������������ֵ �ٽ�ֵ");
        
        createCell(xwb, header, 161, "��ʶ-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 162, "��ͬ-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 163, "��ľ-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 164, "ƴͼ-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 165, "��������-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 166, "ͼƬ����-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 167, "ͼƬ����-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 168, "�����԰-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 169, "�ҳ�-6�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 170, "����-6�������̷ֲ������������ֵ ��ǿ�������");
        
        createCell(xwb, header, 171, "��ʶ-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 172, "��ͬ-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 173, "��ľ-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 174, "ƴͼ-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 175, "��������-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 176, "ͼƬ����-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 177, "ͼƬ����-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 178, "�����԰-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 179, "�ҳ�-6�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 180, "����-6�������̷ֲ������������ֵ ������");
        
        createCell(xwb, header, 181, "��������������-����������������6�������̷ֲ��飩");
        
        createCell(xwb, header, 182, ""); 
        cell = header.getCell(182);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ6");
        
        createCell(xwb, header, 183, "�������-�Ӿ��ռ� ��������");
        createCell(xwb, header, 184, "�������-�������� ��������");
        createCell(xwb, header, 185, "�������-�������� ��������");
        createCell(xwb, header, 186, "�������-�ӹ��ٶ� ��������");
        createCell(xwb, header, 187, "�Ӿ��ռ�-�������� ��������");
        createCell(xwb, header, 188, "�Ӿ��ռ�-�������� ��������");
        createCell(xwb, header, 189, "�Ӿ��ռ�-�ӹ��ٶ� ��������");
        createCell(xwb, header, 190, "��������-�������� ��������");
        createCell(xwb, header, 191, "��������-�ӹ��ٶ� ��������");
        createCell(xwb, header, 192, "��������-�ӹ��ٶ� ��������");
        
        createCell(xwb, header, 193, "�������-�Ӿ��ռ� �ٽ�ֵ");
        createCell(xwb, header, 194, "�������-�������� �ٽ�ֵ");
        createCell(xwb, header, 195, "�������-�������� �ٽ�ֵ");
        createCell(xwb, header, 196, "�������-�ӹ��ٶ� �ٽ�ֵ");
        createCell(xwb, header, 197, "�Ӿ��ռ�-�������� �ٽ�ֵ");
        createCell(xwb, header, 198, "�Ӿ��ռ�-�������� �ٽ�ֵ");
        createCell(xwb, header, 199, "�Ӿ��ռ�-�ӹ��ٶ� �ٽ�ֵ");
        createCell(xwb, header, 200, "��������-�������� �ٽ�ֵ");
        createCell(xwb, header, 201, "��������-�ӹ��ٶ� �ٽ�ֵ");
        createCell(xwb, header, 202, "��������-�ӹ��ٶ� �ٽ�ֵ");
        
        createCell(xwb, header, 203, "�������-�Ӿ��ռ� �Ƿ������Բ���");
        createCell(xwb, header, 204, "�������-�������� �Ƿ������Բ���");
        createCell(xwb, header, 205, "�������-�������� �Ƿ������Բ���");
        createCell(xwb, header, 206, "�������-�ӹ��ٶ� �Ƿ������Բ���");
        createCell(xwb, header, 207, "�Ӿ��ռ�-�������� �Ƿ������Բ���");
        createCell(xwb, header, 208, "�Ӿ��ռ�-�������� �Ƿ������Բ���");
        createCell(xwb, header, 209, "�Ӿ��ռ�-�ӹ��ٶ� �Ƿ������Բ���");
        createCell(xwb, header, 210, "��������-�������� �Ƿ������Բ���");
        createCell(xwb, header, 211, "��������-�ӹ��ٶ� �Ƿ������Բ���");
        createCell(xwb, header, 212, "��������-�ӹ��ٶ� �Ƿ������Բ���");
        
        createCell(xwb, header, 213, "�������-�Ӿ��ռ� ������");
        createCell(xwb, header, 214, "�������-�������� ������");
        createCell(xwb, header, 215, "�������-�������� ������");
        createCell(xwb, header, 216, "�������-�ӹ��ٶ� ������");
        createCell(xwb, header, 217, "�Ӿ��ռ�-�������� ������");
        createCell(xwb, header, 218, "�Ӿ��ռ�-�������� ������");
        createCell(xwb, header, 219, "�Ӿ��ռ�-�ӹ��ٶ� ������");
        createCell(xwb, header, 220, "��������-�������� ������");
        createCell(xwb, header, 221, "��������-�ӹ��ٶ� ������");
        createCell(xwb, header, 222, "��������-�ӹ��ٶ� ������");
        
        createCell(xwb, header, 223, "��ʶ-��ͬ ��������");
        createCell(xwb, header, 224, "��ľ-ƴͼ ��������");
        createCell(xwb, header, 225, "��������-ͼƬ���� ��������");
        createCell(xwb, header, 226, "ͼƬ����-�����԰ ��������");
        createCell(xwb, header, 227, "�ҳ�-���� ��������");
        
        createCell(xwb, header, 228, "��ʶ-��ͬ �ٽ�ֵ");
        createCell(xwb, header, 229, "��ľ-ƴͼ �ٽ�ֵ");
        createCell(xwb, header, 230, "��������-ͼƬ���� �ٽ�ֵ");
        createCell(xwb, header, 231, "ͼƬ����-�����԰ �ٽ�ֵ");
        createCell(xwb, header, 232, "�ҳ�-���� �ٽ�ֵ");
        
        createCell(xwb, header, 233, "��ʶ-��ͬ �Ƿ������Բ���");
        createCell(xwb, header, 234, "��ľ-ƴͼ �Ƿ������Բ���");
        createCell(xwb, header, 235, "��������-ͼƬ���� �Ƿ������Բ���");
        createCell(xwb, header, 236, "ͼƬ����-�����԰ �Ƿ������Բ���");
        createCell(xwb, header, 237, "�ҳ�-���� �Ƿ������Բ���");
        
        createCell(xwb, header, 238, "��ʶ-��ͬ ������");
        createCell(xwb, header, 239, "��ľ-ƴͼ ������");
        createCell(xwb, header, 240, "��������-ͼƬ���� ������");
        createCell(xwb, header, 241, "ͼƬ����-�����԰ ������");
        createCell(xwb, header, 242, "�ҳ�-���� ������");
        
        
        createCell(xwb, header, 243, ""); 
        cell = header.getCell(243);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ7");
        
        createCell(xwb, header, 244, "���Խ��� ��������ܺ�");
        createCell(xwb, header, 245, "������ ��������ܺ�");
        createCell(xwb, header, 246, "һ������ ��������ܺ�");
        createCell(xwb, header, 247, "��֪Ч�� ��������ܺ�");
        
        createCell(xwb, header, 244, "���Խ��� ��������ܺ�");
        createCell(xwb, header, 245, "������ ��������ܺ�");
        createCell(xwb, header, 246, "һ������ ��������ܺ�");
        createCell(xwb, header, 247, "��֪Ч�� ��������ܺ�");
        
        createCell(xwb, header, 248, "���Խ��� �ϳɷ���");
        createCell(xwb, header, 249, "������ �ϳɷ���");
        createCell(xwb, header, 250, "һ������ �ϳɷ���");
        createCell(xwb, header, 251, "��֪Ч�� �ϳɷ���");
        
        createCell(xwb, header, 252, "���Խ���VAI �ٷֵȼ�");
        createCell(xwb, header, 253, "������NVI �ٷֵȼ�");
        createCell(xwb, header, 254, "һ������GAI �ٷֵȼ�");
        createCell(xwb, header, 255, "��֪Ч��CPI �ٷֵȼ�");
        
        createCell(xwb, header, 256, "���Խ���VAI 90%��������");
        createCell(xwb, header, 257, "������NVI 90%��������");
        createCell(xwb, header, 258, "һ������GAI 90%��������");
        createCell(xwb, header, 259, "��֪Ч��CPI 90%��������");
        
        createCell(xwb, header, 260, "���Խ���VAI 90%��������");
        createCell(xwb, header, 261, "������NVI 90%��������");
        createCell(xwb, header, 262, "һ������GAI 90%��������");
        createCell(xwb, header, 263, "��֪Ч��CPI 90%��������");
        
        
        createCell(xwb, header, 264, ""); 
        cell = header.getCell(264);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ8");
        
        createCell(xwb, header, 265, "һ������-������ ��������");
        createCell(xwb, header, 266, "һ������-��֪Ч�� ��������");
        createCell(xwb, header, 267, "һ������-������ �ٽ�ֵ");
        createCell(xwb, header, 268, "һ������-��֪Ч�� �ٽ�ֵ");
        createCell(xwb, header, 269, "һ������-������ �Ƿ������Բ���");
        createCell(xwb, header, 270, "һ������-��֪Ч�� �Ƿ������Բ���");
        createCell(xwb, header, 271, "һ������-������ ������");
        createCell(xwb, header, 272, "һ������-��֪Ч�� ������");
        
        createCell(xwb, header, 273, "ָ��ͼƬ-ͼƬ���� ��������");
        createCell(xwb, header, 274, "�������-������ ��������");
        createCell(xwb, header, 275, "ָ��ͼƬ-ͼƬ���� �ٽ�ֵ");
        createCell(xwb, header, 276, "�������-������ �ٽ�ֵ");
        createCell(xwb, header, 277, "ָ��ͼƬ-ͼƬ���� �Ƿ������Բ���");
        createCell(xwb, header, 278, "�������-������ �Ƿ������Բ���");
        createCell(xwb, header, 279, "ָ��ͼƬ-ͼƬ���� ������");
        createCell(xwb, header, 280, "�������-������ ������");
        
        createCell(xwb, header, 281, ""); 
        cell = header.getCell(281);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ9");
        
        header = sheet.createRow(2);// ��3��  
        createCell(xwb, header, 0, ""); 
        sheet.addMergedRegion(getCellRangeAddress(2, 0, 2, 3));
        
        createCell(xwb, header, 4, "��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 4, 2, 6));
        cell = header.getCell(4);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 7, "��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 7, 2, 9));
        cell = header.getCell(7);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 10, "ʵ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 10, 2, 12));
        cell = header.getCell(10);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 13, "");
        cell = header.getCell(13);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 14, "15���ֲ����������");
        sheet.addMergedRegion(getCellRangeAddress(2, 14, 2, 28));
        cell = header.getCell(14);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 29, "6����Ҫָ����������ܺ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 29, 2, 34));
        cell = header.getCell(29);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 35, "");
        cell = header.getCell(35);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 36, "6����Ҫָ���ϳɷ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 36, 2, 41));
        cell = header.getCell(36);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 42, "6����Ҫָ���ٷֵȼ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 42, 2, 47));
        cell = header.getCell(42);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 48, "6����Ҫָ���ϳɷ�����90%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 48, 2, 53));
        cell = header.getCell(48);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 54, "6����Ҫָ���ϳɷ�����90%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 54, 2, 59));
        cell = header.getCell(54);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 60, "");
        cell = header.getCell(60);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 61, "ÿ������еıȽϷ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 61, 2, 62));
        cell = header.getCell(61);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 63, "");
        cell = header.getCell(63);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 64, "");
        sheet.addMergedRegion(getCellRangeAddress(2, 64, 2, 65));
        cell = header.getCell(64);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 66, "");
        sheet.addMergedRegion(getCellRangeAddress(2, 66, 2, 67));
        cell = header.getCell(66);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 68, "");
        cell = header.getCell(68);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 69, "5��ָ��������ָ��������ֵ ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 69, 2, 73));
        cell = header.getCell(69);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 74, "5��ָ��������ָ��������ֵ �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 74, 2, 78));
        cell = header.getCell(74);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 79, "�ж�ǿ�������");
        sheet.addMergedRegion(getCellRangeAddress(2, 79, 2, 83));
        cell = header.getCell(79);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 84, "5��ָ�������������� ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 84, 2, 88));
        cell = header.getCell(84);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 89, "5��ָ�������������� �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 89, 2, 93));
        cell = header.getCell(89);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 94, "�ж�ǿ����");
        sheet.addMergedRegion(getCellRangeAddress(2, 94, 2, 98));
        cell = header.getCell(94);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 99, "");
        cell = header.getCell(99);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 100, "10���ֲ����������10��ָ���ֲ���������ֵ ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 100, 2, 109));
        cell = header.getCell(100);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 110, "10���ֲ����������10��ָ���ֲ���������ֵ �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 110, 2, 119));
        cell = header.getCell(110);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 120, "�ж�ǿ�������");
        sheet.addMergedRegion(getCellRangeAddress(2, 120, 2, 129));
        cell = header.getCell(120);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 130, "10���ֲ����������10��ָ���ֲ���������ֵ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 130, 2, 139));
        cell = header.getCell(130);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 140, "��ɢ��");
        cell = header.getCell(140);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 141, "10���ֲ����������6�������̷ֲ���������ֵ ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 141, 2, 150));
        cell = header.getCell(141);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 151, "10���ֲ����������6�������̷ֲ���������ֵ �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 151, 2, 160));
        cell = header.getCell(151);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 161, "�ж�ǿ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 161, 2, 170));
        cell = header.getCell(161);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 171, "10���ֲ����������6�������̷ֲ���������ֵ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 171, 2, 180));
        cell = header.getCell(171);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 181, "��ɢ��");
        cell = header.getCell(181);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 182, "");
        cell = header.getCell(182);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 183, "ָ��ˮƽ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 183, 2, 192));
        cell = header.getCell(183);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 193, "ָ��ˮƽ�ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 193, 2, 202));
        cell = header.getCell(193);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 203, "ָ��ˮƽ �ж��Ƿ������Բ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 203, 2, 212));
        cell = header.getCell(203);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 213, "ָ��ˮƽ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 213, 2, 222));
        cell = header.getCell(213);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 223, "�ֲ���ˮƽ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 223, 2, 227));
        cell = header.getCell(223);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 228, "�ֲ���ˮƽ�ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 228, 2, 232));
        cell = header.getCell(228);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 233, "�ֲ���ˮƽ �ж��Ƿ������Բ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 233, 2, 237));
        cell = header.getCell(233);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 238, "�ֲ���ˮƽ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 238, 2, 242));
        cell = header.getCell(238);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 243, "");
        cell = header.getCell(243);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 244, "4������ָ����������ܺ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 244, 2, 247));
        cell = header.getCell(244);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 248, "4������ָ���ϳɷ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 248, 2, 251));
        cell = header.getCell(248);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 252, "4������ָ���ϳɷ����İٷֵȼ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 252, 2, 255));
        cell = header.getCell(252);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 256, "4������ָ���ϳɷ�����90%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 256, 2, 259));
        cell = header.getCell(256);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 260, "4������ָ���ϳɷ�����95%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 260, 2, 263));
        cell = header.getCell(260);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 264, "");
        cell = header.getCell(264);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 265, "ָ��ˮƽ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 265, 2, 266));
        cell = header.getCell(265);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 267, "ָ��ˮƽ�ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 267, 2, 268));
        cell = header.getCell(267);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 269, "ָ��ˮƽ�ж��Ƿ������Բ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 269, 2, 270));
        cell = header.getCell(269);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 271, "ָ������������");
        sheet.addMergedRegion(getCellRangeAddress(2, 271, 2, 272));
        cell = header.getCell(271);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 273, "�ּ��ˮƽ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 273, 2, 274));
        cell = header.getCell(273);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 275, "�ּ��ˮƽ�ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 275, 2, 276));
        cell = header.getCell(275);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 277, "�ּ��ˮƽ�ж��Ƿ������Բ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 277, 2, 278));
        cell = header.getCell(277);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 279, "�ֲ���ˮƽ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 279, 2, 280));
        cell = header.getCell(279);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 281, "");
        cell = header.getCell(281);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        for(int i = 0 ; i < alist.size() ; i++){
        	header = sheet.createRow(i+3);// ��i��  
            createCell(xwb, header, 0, "");
            
            createCell(xwb, header, 1, ""+clist.get(i).getCname());
            createCell(xwb, header, 2, ""+clist.get(i).getCtestname());
            createCell(xwb, header, 3, ""+clist.get(i).getCid());
            createCell(xwb, header, 4, ""+clist.get(i).getCtyear());
            createCell(xwb, header, 5, ""+clist.get(i).getCtmonth());
            createCell(xwb, header, 6, ""+clist.get(i).getCtday());
            createCell(xwb, header, 7, ""+clist.get(i).getCbyear());
            createCell(xwb, header, 8, ""+clist.get(i).getCbmonth());
            createCell(xwb, header, 9, ""+clist.get(i).getCbday());
            createCell(xwb, header, 10, ""+clist.get(i).getCyear());
            createCell(xwb, header, 11, ""+clist.get(i).getCmonth());
            createCell(xwb, header, 12, ""+clist.get(i).getCday());
            
            createCell(xwb, header, 13, "");
            cell = header.getCell(13);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 14, ""+alist.get(i).getBdl());
            createCell(xwb, header, 15, ""+alist.get(i).getInfl());
            createCell(xwb, header, 16, ""+alist.get(i).getMrl());
            createCell(xwb, header, 17, ""+alist.get(i).getBsl());
            createCell(xwb, header, 18, ""+alist.get(i).getPml());
            createCell(xwb, header, 19, ""+alist.get(i).getSil());
            createCell(xwb, header, 20, ""+alist.get(i).getPcl());
            createCell(xwb, header, 21, ""+alist.get(i).getCal());
            createCell(xwb, header, 22, ""+alist.get(i).getZll());
            createCell(xwb, header, 23, ""+alist.get(i).getOal());
            createCell(xwb, header, 24, ""+alist.get(i).getAcl());
            createCell(xwb, header, 25, ""+alist.get(i).getRvl());
            createCell(xwb, header, 26, ""+alist.get(i).getPnl());
            createCell(xwb, header, 27, ""+alist.get(i).getCarl());
            createCell(xwb, header, 28, ""+alist.get(i).getCasl());
            
            createCell(xwb, header, 29, ""+alist.get(i).getVci());
            createCell(xwb, header, 30, ""+alist.get(i).getVsi());
            createCell(xwb, header, 31, ""+alist.get(i).getFri());
            createCell(xwb, header, 32, ""+alist.get(i).getWmi());
            createCell(xwb, header, 33, ""+alist.get(i).getPsi());
            createCell(xwb, header, 34, ""+alist.get(i).getFsiq());
            
            createCell(xwb, header, 35, "");
            cell = header.getCell(35);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 36, ""+alist.get(i).getVcii());
            createCell(xwb, header, 37, ""+alist.get(i).getVsii());
            createCell(xwb, header, 38, ""+alist.get(i).getFrii());
            createCell(xwb, header, 39, ""+alist.get(i).getWmii());
            createCell(xwb, header, 40, ""+alist.get(i).getPsii());
            createCell(xwb, header, 41, ""+alist.get(i).getFsiqi());
            
            createCell(xwb, header, 42, ""+alist.get(i).getVcib());
            createCell(xwb, header, 43, ""+alist.get(i).getVsib());
            createCell(xwb, header, 44, ""+alist.get(i).getFrib());
            createCell(xwb, header, 45, ""+alist.get(i).getWmib());
            createCell(xwb, header, 46, ""+alist.get(i).getPsib());
            createCell(xwb, header, 47, ""+alist.get(i).getFsiqb());
            
            if(alist.get(i).getSp() == 0){
            	createCell(xwb, header, 48, ""+alist.get(i).getVciz());
                createCell(xwb, header, 49, ""+alist.get(i).getVsiz());
                createCell(xwb, header, 50, ""+alist.get(i).getFriz());
                createCell(xwb, header, 51, ""+alist.get(i).getWmiz());
                createCell(xwb, header, 52, ""+alist.get(i).getPsiz());
                createCell(xwb, header, 53, ""+alist.get(i).getFsiqz());
                
                for(int j = 0 ; j<6 ; j++){
                	 createCell(xwb, header, j+54, "");
                }
            }else{
            	for(int j = 0 ; j<6 ; j++){
               	 createCell(xwb, header, j+48, "");
            	}
            	createCell(xwb, header, 54, ""+alist.get(i).getVciz());
                createCell(xwb, header, 55, ""+alist.get(i).getVsiz());
                createCell(xwb, header, 56, ""+alist.get(i).getFriz());
                createCell(xwb, header, 57, ""+alist.get(i).getWmiz());
                createCell(xwb, header, 58, ""+alist.get(i).getPsiz());
                createCell(xwb, header, 59, ""+alist.get(i).getFsiqz());
            }
            
            createCell(xwb, header, 60, "");
            cell = header.getCell(60);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 61, ""+alist.get(i).getZszf5());
            createCell(xwb, header, 62, ""+alist.get(i).getZszf5jz());
            createCell(xwb, header, 63, ""+alist.get(i).getFsiqi());
            createCell(xwb, header, 64, ""+alist.get(i).getZszf10());
            createCell(xwb, header, 65, ""+alist.get(i).getZszf10jz());
            createCell(xwb, header, 66, ""+alist.get(i).getZszf6());
            createCell(xwb, header, 67, ""+alist.get(i).getZszf6jz());
            
            createCell(xwb, header, 68, "");
            cell = header.getCell(68);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 69, ""+alist.get(i).getVci5zsfscy());
            createCell(xwb, header, 70, ""+alist.get(i).getVsi5zsfscy());
            createCell(xwb, header, 71, ""+alist.get(i).getFri5zsfscy());
            createCell(xwb, header, 72, ""+alist.get(i).getWmi5zsfscy());
            createCell(xwb, header, 73, ""+alist.get(i).getPsi5zsfscy());
            
            createCell(xwb, header, 74, ""+alist.get(i).getVci5zsljz());
            createCell(xwb, header, 75, ""+alist.get(i).getVsi5zsljz());
            createCell(xwb, header, 76, ""+alist.get(i).getFri5zsljz());
            createCell(xwb, header, 77, ""+alist.get(i).getWmi5zsljz());
            createCell(xwb, header, 78, ""+alist.get(i).getPsi5zsljz());
            
            createCell(xwb, header, 79, ""+alist.get(i).getVci5qrx());
            createCell(xwb, header, 80, ""+alist.get(i).getVsi5qrx());
            createCell(xwb, header, 81, ""+alist.get(i).getFri5qrx());
            createCell(xwb, header, 82, ""+alist.get(i).getWmi5qrx());
            createCell(xwb, header, 83, ""+alist.get(i).getPsi5qrx());
            
            createCell(xwb, header, 84, ""+alist.get(i).getVci5zzsfscy());
            createCell(xwb, header, 85, ""+alist.get(i).getVsi5zzsfscy());
            createCell(xwb, header, 86, ""+alist.get(i).getFri5zzsfscy());
            createCell(xwb, header, 87, ""+alist.get(i).getWmi5zzsfscy());
            createCell(xwb, header, 88, ""+alist.get(i).getPsi5zzsfscy());
            
            createCell(xwb, header, 89, ""+alist.get(i).getVci5zzsljz());
            createCell(xwb, header, 90, ""+alist.get(i).getVsi5zzsljz());
            createCell(xwb, header, 91, ""+alist.get(i).getFri5zzsljz());
            createCell(xwb, header, 92, ""+alist.get(i).getWmi5zzsljz());
            createCell(xwb, header, 93, ""+alist.get(i).getPsi5zzsljz());
            
            createCell(xwb, header, 94, ""+alist.get(i).getVci5zzsqrx());
            createCell(xwb, header, 95, ""+alist.get(i).getVsi5zzsqrx());
            createCell(xwb, header, 96, ""+alist.get(i).getFri5zzsqrx());
            createCell(xwb, header, 97, ""+alist.get(i).getWmi5zzsqrx());
            createCell(xwb, header, 98, ""+alist.get(i).getPsi5zzsqrx());
            
            createCell(xwb, header, 99, "");
            cell = header.getCell(99);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 100, ""+alist.get(i).getZszf10infscy());
            createCell(xwb, header, 101, ""+alist.get(i).getZszf10sifscy());
            createCell(xwb, header, 102, ""+alist.get(i).getZszf10bdfscy());
            createCell(xwb, header, 103, ""+alist.get(i).getZszf10oafscy());
            createCell(xwb, header, 104, ""+alist.get(i).getZszf10mrfscy());
            createCell(xwb, header, 105, ""+alist.get(i).getZszf10pcfscy());
            createCell(xwb, header, 106, ""+alist.get(i).getZszf10pmfscy());
            createCell(xwb, header, 107, ""+alist.get(i).getZszf10zlfscy());
            createCell(xwb, header, 108, ""+alist.get(i).getZszf10bdfscy());
            createCell(xwb, header, 109, ""+alist.get(i).getZszf10cafscy());
            
            createCell(xwb, header, 110, ""+alist.get(i).getZszf10inljz());
            createCell(xwb, header, 111, ""+alist.get(i).getZszf10siljz());
            createCell(xwb, header, 112, ""+alist.get(i).getZszf10bdljz());
            createCell(xwb, header, 113, ""+alist.get(i).getZszf10oaljz());
            createCell(xwb, header, 114, ""+alist.get(i).getZszf10mrljz());
            createCell(xwb, header, 115, ""+alist.get(i).getZszf10pcljz());
            createCell(xwb, header, 116, ""+alist.get(i).getZszf10pmljz());
            createCell(xwb, header, 117, ""+alist.get(i).getZszf10zlljz());
            createCell(xwb, header, 118, ""+alist.get(i).getZszf10bdljz());
            createCell(xwb, header, 119, ""+alist.get(i).getZszf10caljz());
            
            createCell(xwb, header, 120, ""+alist.get(i).getZszf10inqrx());
            createCell(xwb, header, 121, ""+alist.get(i).getZszf10siqrx());
            createCell(xwb, header, 122, ""+alist.get(i).getZszf10bdqrx());
            createCell(xwb, header, 123, ""+alist.get(i).getZszf10oaqrx());
            createCell(xwb, header, 124, ""+alist.get(i).getZszf10mrqrx());
            createCell(xwb, header, 125, ""+alist.get(i).getZszf10pcqrx());
            createCell(xwb, header, 126, ""+alist.get(i).getZszf10pmqrx());
            createCell(xwb, header, 127, ""+alist.get(i).getZszf10zlqrx());
            createCell(xwb, header, 128, ""+alist.get(i).getZszf10bdqrx());
            createCell(xwb, header, 129, ""+alist.get(i).getZszf10caqrx());
            
            createCell(xwb, header, 130, ""+alist.get(i).getZszf10injcl());
            createCell(xwb, header, 131, ""+alist.get(i).getZszf10sijcl());
            createCell(xwb, header, 132, ""+alist.get(i).getZszf10bdjcl());
            createCell(xwb, header, 133, ""+alist.get(i).getZszf10oajcl());
            createCell(xwb, header, 134, ""+alist.get(i).getZszf10mrjcl());
            createCell(xwb, header, 135, ""+alist.get(i).getZszf10pcjcl());
            createCell(xwb, header, 136, ""+alist.get(i).getZszf10pmjcl());
            createCell(xwb, header, 137, ""+alist.get(i).getZszf10zljcl());
            createCell(xwb, header, 138, ""+alist.get(i).getZszf10bdjcl());
            createCell(xwb, header, 139, ""+alist.get(i).getZszf10cajcl());
            
            createCell(xwb, header, 140, ""+alist.get(i).getZszf10fsd());
            
            createCell(xwb, header, 141, ""+alist.get(i).getZszf6infscy());
            createCell(xwb, header, 142, ""+alist.get(i).getZszf6sifscy());
            createCell(xwb, header, 143, ""+alist.get(i).getZszf6bdfscy());
            createCell(xwb, header, 144, ""+alist.get(i).getZszf6oafscy());
            createCell(xwb, header, 145, ""+alist.get(i).getZszf6mrfscy());
            createCell(xwb, header, 146, ""+alist.get(i).getZszf6pcfscy());
            createCell(xwb, header, 147, ""+alist.get(i).getZszf6pmfscy());
            createCell(xwb, header, 148, ""+alist.get(i).getZszf6zlfscy());
            createCell(xwb, header, 149, ""+alist.get(i).getZszf6bdfscy());
            createCell(xwb, header, 150, ""+alist.get(i).getZszf6cafscy());
            
            createCell(xwb, header, 151, ""+alist.get(i).getZszf6inljz());
            createCell(xwb, header, 152, ""+alist.get(i).getZszf6siljz());
            createCell(xwb, header, 153, ""+alist.get(i).getZszf6bdljz());
            createCell(xwb, header, 154, ""+alist.get(i).getZszf6oaljz());
            createCell(xwb, header, 155, ""+alist.get(i).getZszf6mrljz());
            createCell(xwb, header, 156, ""+alist.get(i).getZszf6pcljz());
            createCell(xwb, header, 157, ""+alist.get(i).getZszf6pmljz());
            createCell(xwb, header, 158, ""+alist.get(i).getZszf6zlljz());
            createCell(xwb, header, 159, ""+alist.get(i).getZszf6bdljz());
            createCell(xwb, header, 160, ""+alist.get(i).getZszf6caljz());
            
            createCell(xwb, header, 161, ""+alist.get(i).getZszf6inqrx());
            createCell(xwb, header, 162, ""+alist.get(i).getZszf6siqrx());
            createCell(xwb, header, 163, ""+alist.get(i).getZszf6bdqrx());
            createCell(xwb, header, 164, ""+alist.get(i).getZszf6oaqrx());
            createCell(xwb, header, 165, ""+alist.get(i).getZszf6mrqrx());
            createCell(xwb, header, 166, ""+alist.get(i).getZszf6pcqrx());
            createCell(xwb, header, 167, ""+alist.get(i).getZszf6pmqrx());
            createCell(xwb, header, 168, ""+alist.get(i).getZszf6zlqrx());
            createCell(xwb, header, 169, ""+alist.get(i).getZszf6bdqrx());
            createCell(xwb, header, 170, ""+alist.get(i).getZszf6caqrx());
            
            createCell(xwb, header, 171, ""+alist.get(i).getZszf6injcl());
            createCell(xwb, header, 172, ""+alist.get(i).getZszf6sijcl());
            createCell(xwb, header, 173, ""+alist.get(i).getZszf6bdjcl());
            createCell(xwb, header, 174, ""+alist.get(i).getZszf6oajcl());
            createCell(xwb, header, 175, ""+alist.get(i).getZszf6mrjcl());
            createCell(xwb, header, 176, ""+alist.get(i).getZszf6pcjcl());
            createCell(xwb, header, 177, ""+alist.get(i).getZszf6pmjcl());
            createCell(xwb, header, 178, ""+alist.get(i).getZszf6zljcl());
            createCell(xwb, header, 179, ""+alist.get(i).getZszf6bdjcl());
            createCell(xwb, header, 180, ""+alist.get(i).getZszf6cajcl());
            
            createCell(xwb, header, 181, ""+alist.get(i).getZszf6fsd());
            
            createCell(xwb, header, 182, "");
            cell = header.getCell(182);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 183, ""+alist.get(i).getVci_vsi());
            createCell(xwb, header, 184, ""+alist.get(i).getVci_fri());
            createCell(xwb, header, 185, ""+alist.get(i).getVci_wmi());
            createCell(xwb, header, 186, ""+alist.get(i).getVci_psi());
            createCell(xwb, header, 187, ""+alist.get(i).getVsi_fri());
            createCell(xwb, header, 188, ""+alist.get(i).getVsi_wmi());
            createCell(xwb, header, 189, ""+alist.get(i).getVsi_psi());
            createCell(xwb, header, 190, ""+alist.get(i).getFri_wmi());
            createCell(xwb, header, 191, ""+alist.get(i).getFri_psi());
            createCell(xwb, header, 192, ""+alist.get(i).getWmi_psi());
            
            createCell(xwb, header, 193, ""+alist.get(i).getVci_vsiljz());
            createCell(xwb, header, 194, ""+alist.get(i).getVci_friljz());
            createCell(xwb, header, 195, ""+alist.get(i).getVci_wmiljz());
            createCell(xwb, header, 196, ""+alist.get(i).getVci_psiljz());
            createCell(xwb, header, 197, ""+alist.get(i).getVsi_friljz());
            createCell(xwb, header, 198, ""+alist.get(i).getVsi_wmiljz());
            createCell(xwb, header, 199, ""+alist.get(i).getVsi_psiljz());
            createCell(xwb, header, 200, ""+alist.get(i).getFri_wmiljz());
            createCell(xwb, header, 201, ""+alist.get(i).getFri_psiljz());
            createCell(xwb, header, 202, ""+alist.get(i).getWmi_psiljz());
            
            createCell(xwb, header, 203, ""+alist.get(i).getVci_vsiqrx());
            createCell(xwb, header, 204, ""+alist.get(i).getVci_friqrx());
            createCell(xwb, header, 205, ""+alist.get(i).getVci_wmiqrx());
            createCell(xwb, header, 206, ""+alist.get(i).getVci_psiqrx());
            createCell(xwb, header, 207, ""+alist.get(i).getVsi_friqrx());
            createCell(xwb, header, 208, ""+alist.get(i).getVsi_wmiqrx());
            createCell(xwb, header, 209, ""+alist.get(i).getVsi_psiqrx());
            createCell(xwb, header, 210, ""+alist.get(i).getFri_wmiqrx());
            createCell(xwb, header, 211, ""+alist.get(i).getFri_psiqrx());
            createCell(xwb, header, 212, ""+alist.get(i).getWmi_psiqrx());
            
            createCell(xwb, header, 213, ""+alist.get(i).getVci_vsijcl());
            createCell(xwb, header, 214, ""+alist.get(i).getVci_frijcl());
            createCell(xwb, header, 215, ""+alist.get(i).getVci_wmijcl());
            createCell(xwb, header, 216, ""+alist.get(i).getVci_psijcl());
            createCell(xwb, header, 217, ""+alist.get(i).getVsi_frijcl());
            createCell(xwb, header, 218, ""+alist.get(i).getVsi_wmijcl());
            createCell(xwb, header, 219, ""+alist.get(i).getVsi_psijcl());
            createCell(xwb, header, 220, ""+alist.get(i).getFri_wmijcl());
            createCell(xwb, header, 221, ""+alist.get(i).getFri_psijcl());
            createCell(xwb, header, 222, ""+alist.get(i).getWmi_psijcl());
            
            createCell(xwb, header, 223, ""+alist.get(i).getIn_si());
            createCell(xwb, header, 224, ""+alist.get(i).getBd_oa());
            createCell(xwb, header, 225, ""+alist.get(i).getMr_pc());
            createCell(xwb, header, 226, ""+alist.get(i).getPm_zl());
            createCell(xwb, header, 227, ""+alist.get(i).getBs_ca());
            
            createCell(xwb, header, 228, ""+alist.get(i).getIn_siljz());
            createCell(xwb, header, 229, ""+alist.get(i).getBd_oaljz());
            createCell(xwb, header, 230, ""+alist.get(i).getMr_pcljz());
            createCell(xwb, header, 231, ""+alist.get(i).getPm_zlljz());
            createCell(xwb, header, 232, ""+alist.get(i).getBs_caljz());
            
            createCell(xwb, header, 233, ""+alist.get(i).getIn_sicy());
            createCell(xwb, header, 234, ""+alist.get(i).getBd_oacy());
            createCell(xwb, header, 235, ""+alist.get(i).getMr_pccy());
            createCell(xwb, header, 236, ""+alist.get(i).getPm_zlcy());
            createCell(xwb, header, 237, ""+alist.get(i).getBs_cacy());
            
            createCell(xwb, header, 238, ""+alist.get(i).getIn_sijcl());
            createCell(xwb, header, 239, ""+alist.get(i).getBd_oajcl());
            createCell(xwb, header, 240, ""+alist.get(i).getMr_pcjcl());
            createCell(xwb, header, 241, ""+alist.get(i).getPm_zljcl());
            createCell(xwb, header, 242, ""+alist.get(i).getBs_cajcl());
            
            createCell(xwb, header, 243, "");
            cell = header.getCell(243);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 244, ""+alist.get(i).getVai());
            createCell(xwb, header, 245, ""+alist.get(i).getNvi());
            createCell(xwb, header, 246, ""+alist.get(i).getGai());
            createCell(xwb, header, 247, ""+alist.get(i).getCpi());
            
            createCell(xwb, header, 248, ""+alist.get(i).getVaii());
            createCell(xwb, header, 249, ""+alist.get(i).getNvii());
            createCell(xwb, header, 250, ""+alist.get(i).getGaii());
            createCell(xwb, header, 251, ""+alist.get(i).getCpii());
            
            createCell(xwb, header, 252, ""+alist.get(i).getVaib());
            createCell(xwb, header, 253, ""+alist.get(i).getNvib());
            createCell(xwb, header, 254, ""+alist.get(i).getGaib());
            createCell(xwb, header, 255, ""+alist.get(i).getCpib());
            
            if(alist.get(i).getSp() == 0){
            	createCell(xwb, header, 256, ""+alist.get(i).getVaiz());
                createCell(xwb, header, 257, ""+alist.get(i).getNviz());
                createCell(xwb, header, 258, ""+alist.get(i).getGaiz());
                createCell(xwb, header, 259, ""+alist.get(i).getCpiz());
                
                for(int k = 0 ; k < 4 ; k++){
                	createCell(xwb, header, 260+k, "");
                }
            }else{
            	for(int k = 0 ; k < 4 ; k++){
                	createCell(xwb, header, 256+k, "");
                }
            	
            	createCell(xwb, header, 260, ""+alist.get(i).getVaiz());
                createCell(xwb, header, 261, ""+alist.get(i).getNviz());
                createCell(xwb, header, 262, ""+alist.get(i).getGaiz());
                createCell(xwb, header, 263, ""+alist.get(i).getCpiz());
            }
            
            createCell(xwb, header, 264, "");
            cell = header.getCell(264);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 265, ""+alist.get(i).getGai_fsiq());
            createCell(xwb, header, 266, ""+alist.get(i).getGai_cpi());
            
            createCell(xwb, header, 267, ""+alist.get(i).getGai_fsiqljz());
            createCell(xwb, header, 268, ""+alist.get(i).getGai_cpiljz());
            
            createCell(xwb, header, 269, ""+alist.get(i).getGai_fsiqcy());
            createCell(xwb, header, 270, ""+alist.get(i).getGai_cpicy());
            
            createCell(xwb, header, 271, ""+alist.get(i).getGai_fsiqjcl());
            createCell(xwb, header, 272, ""+alist.get(i).getGai_cpijcl());
            
            createCell(xwb, header, 273, ""+alist.get(i).getRv_pn());
            createCell(xwb, header, 274, ""+alist.get(i).getCar_cas());
            
            createCell(xwb, header, 275, ""+alist.get(i).getRv_pnljz());
            createCell(xwb, header, 276, ""+alist.get(i).getCar_casljz());
            
            createCell(xwb, header, 277, ""+alist.get(i).getRv_pncy());
            createCell(xwb, header, 278, ""+alist.get(i).getCar_cascy());
            
            createCell(xwb, header, 279, ""+alist.get(i).getRv_pnjcl());
            createCell(xwb, header, 280, ""+alist.get(i).getCar_casjcl());
            
            createCell(xwb, header, 281, "");
            cell = header.getCell(281);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            
        }
        
	}
	
	/**д2007��ͷ 2��
     * */
    public static synchronized void write_2(List<Child> clist , List<Age2> alist){
    	XSSFWorkbook xwb;
		xwb = new XSSFWorkbook();
		XSSFSheet sheet = xwb.createSheet();
		dxqdHeadLine2(sheet, xwb , alist , clist);  
		String path = setlast(new Date());
		String filePath=ServletActionContext.getServletContext().getRealPath("download")+"\\"+ path+".xlsx";
		try {
			xwb.write(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("path",path);
    }

    private static void dxqdHeadLine2(XSSFSheet sheet, XSSFWorkbook xwb , List<Age2> alist , List<Child> clist) {
		Row header = sheet.createRow(0);// ��һ��  
		Cell cell = header.getCell(0);
		XSSFCellStyle style = xwb.createCellStyle();
		// //��һ������  
		createCell(xwb, header, 0, "");  
        createCell(xwb, header, 1, "����ҳ");  
        sheet.addMergedRegion(getCellRangeAddress(0, 1, 0, 12));
        
        cell = header.getCell(1);
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 13, ""); 
        cell = header.getCell(13);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 14, "ԭʼ�������������ת����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 14, 0, 24));
        cell = header.getCell(14);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 25, ""); 
        cell = header.getCell(25);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 26, "��������ܺ���ϳɷ���ת����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 26, 0, 41));
        cell = header.getCell(26);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 

        createCell(xwb, header, 42, "");
        cell = header.getCell(42);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 43, "��Ҫ����ҳ 4���ȽϷ���"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 43, 0, 49));
        cell = header.getCell(43);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 50, ""); 
        cell = header.getCell(50);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 51, "ָ��ˮƽ ǿ��������ȷ����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 51, 0, 74));
        cell = header.getCell(51);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 75, ""); 
        cell = header.getCell(75);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 76, "�ֲ���ˮƽ ǿ��������ȷ����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 76, 0, 125));
        cell = header.getCell(76);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 126, ""); 
        cell = header.getCell(126);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 127, "����Ƚϱ�"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 127, 0, 150));
        cell = header.getCell(127);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 151, ""); 
        cell = header.getCell(151);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 152, "��������ҳ ��������ܺ��븨��ָ������ת����"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 152, 0, 166));
        cell = header.getCell(152);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 167, ""); 
        cell = header.getCell(167);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 168, "����Ƚϱ�"); 
        sheet.addMergedRegion(getCellRangeAddress(0, 168, 0, 175));
        cell = header.getCell(168);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 176, ""); 
        cell = header.getCell(176);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        header = sheet.createRow(1);// ��2��  
        createCell(xwb, header, 0, ""); 
        
        createCell(xwb, header, 1, "��ͯ����");
        createCell(xwb, header, 2, "��������");
        createCell(xwb, header, 3, "���");
        createCell(xwb, header, 4, "��������");
        createCell(xwb, header, 5, "��������");
        createCell(xwb, header, 6, "��������");
        createCell(xwb, header, 7, "��������");
        createCell(xwb, header, 8, "��������");
        createCell(xwb, header, 9, "��������");
        createCell(xwb, header, 10, "ʵ������");
        createCell(xwb, header, 11, "ʵ������");
        createCell(xwb, header, 12, "ʵ������");
        
        createCell(xwb, header, 13, ""); 
        cell = header.getCell(13);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ1");
        
        createCell(xwb, header, 14, "ָ��ͼƬ �������");
        createCell(xwb, header, 15, "��ľ �������");
        createCell(xwb, header, 16, "ͼƬ���� �������");
        createCell(xwb, header, 17, "��ʶ �������");
        createCell(xwb, header, 18, "ƴͼ �������");
        createCell(xwb, header, 19, "�����԰ �������");
        createCell(xwb, header, 20, "ͼƬ���� �������");
        createCell(xwb, header, 21, "�������VCI ��������ܺ�");
        createCell(xwb, header, 22, "�Ӿ��ռ�VSI ��������ܺ�");
        createCell(xwb, header, 23, "��������WMI ��������ܺ�");
        createCell(xwb, header, 24, "ȫ����FSIQ ��������ܺ�");
        
        createCell(xwb, header, 25, ""); 
        cell = header.getCell(25);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ2");
        
        createCell(xwb, header, 26, "�������VCI �ϳɷ���");
        createCell(xwb, header, 27, "�Ӿ��ռ�VSI �ϳɷ���");
        createCell(xwb, header, 28, "��������WMI �ϳɷ���");
        createCell(xwb, header, 29, "�ӹ��ٶ�FSIQ �ϳɷ���");
        
        createCell(xwb, header, 30, "�������VCI �ٷֵȼ�");
        createCell(xwb, header, 31, "�Ӿ��ռ�VSI �ٷֵȼ�");
        createCell(xwb, header, 32, "��������WMI �ٷֵȼ�");
        createCell(xwb, header, 33, "ȫ����FSIQ �ٷֵȼ�");
        
        createCell(xwb, header, 34, "�������VCI 90%��������");
        createCell(xwb, header, 35, "�Ӿ��ռ�VSI 90%��������");
        createCell(xwb, header, 36, "��������WMI 90%��������");
        createCell(xwb, header, 37, "ȫ����FSIQ 90%��������");
        
        createCell(xwb, header, 38, "�������VCI 95%��������");
        createCell(xwb, header, 39, "�Ӿ��ռ�VSI 95%��������");
        createCell(xwb, header, 40, "��������WMI 95%��������");
        createCell(xwb, header, 41, "ȫ����FSIQ 95%��������");
        
        
        createCell(xwb, header, 42, ""); 
        cell = header.getCell(42);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ3");
        
        createCell(xwb, header, 43, "3��ָ���ܷ�");
        createCell(xwb, header, 44, "3��ָ���ܷ־�ֵ");
        createCell(xwb, header, 45, "������FSIQ �ϳɷ���");
        createCell(xwb, header, 46, "6��ָ���ֲ�����������ܺ�");
        createCell(xwb, header, 47, "6��ָ���ֲ������������ֵ");
        createCell(xwb, header, 48, "5�������̷ֲ�����������ܺ�");
        createCell(xwb, header, 49, "5�������̷ֲ����������ֵ");
        
        
        createCell(xwb, header, 50, ""); 
        cell = header.getCell(50);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ4");
        
        createCell(xwb, header, 51, "�������ָ��-ָ��������ֵ ��������");
        createCell(xwb, header, 52, "�Ӿ��ռ�ָ��-ָ��������ֵ ��������");
        createCell(xwb, header, 53, "��������ָ��-ָ��������ֵ ��������");
        
        createCell(xwb, header, 54, "�������ָ��-ָ��������ֵ �ٽ�ֵ");
        createCell(xwb, header, 55, "�Ӿ��ռ�ָ��-ָ��������ֵ �ٽ�ֵ");
        createCell(xwb, header, 56, "��������ָ��-ָ��������ֵ �ٽ�ֵ");
        
        createCell(xwb, header, 57, "�������ָ��-ָ��������ֵ ��ǿ�������");
        createCell(xwb, header, 58, "�Ӿ��ռ�ָ��-ָ��������ֵ ��ǿ�������");
        createCell(xwb, header, 59, "��������ָ��-ָ��������ֵ ��ǿ�������");
        
        createCell(xwb, header, 60, "�������ָ��-ָ��������ֵ ������");
        createCell(xwb, header, 61, "�Ӿ��ռ�ָ��-ָ��������ֵ ������");
        createCell(xwb, header, 62, "��������ָ��-ָ��������ֵ ������");
        
        createCell(xwb, header, 63, "�������ָ��-������ ��������");
        createCell(xwb, header, 64, "�Ӿ��ռ�ָ��-������ ��������");
        createCell(xwb, header, 65, "��������ָ��-������ ��������");
        
        createCell(xwb, header, 66, "�������ָ��-������ �ٽ�ֵ");
        createCell(xwb, header, 67, "�Ӿ��ռ�ָ��-������ �ٽ�ֵ");
        createCell(xwb, header, 68, "��������ָ��-������ �ٽ�ֵ");
        
        createCell(xwb, header, 69, "�������ָ��-������ ��ǿ�������");
        createCell(xwb, header, 70, "�Ӿ��ռ�ָ��-������ ��ǿ�������");
        createCell(xwb, header, 71, "��������ָ��-������ ��ǿ�������");
        
        createCell(xwb, header, 72, "�������ָ��-������ ������");
        createCell(xwb, header, 73, "�Ӿ��ռ�ָ��-������ ������");
        createCell(xwb, header, 74, "��������ָ��-������ ������");
        
        createCell(xwb, header, 75, ""); 
        cell = header.getCell(75);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ5");
        
        createCell(xwb, header, 76, "ָ��ͼƬ-6��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 77, "��ʶ-6��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 78, "��ľ-6��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 79, "ƴͼ-6��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 80, "ͼƬ����-6��ָ���ֲ������������ֵ ��������");
        createCell(xwb, header, 81, "�����԰-6��ָ���ֲ������������ֵ ��������");
        
        createCell(xwb, header, 82, "ָ��ͼƬ-6��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 83, "��ʶ-6��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 84, "��ľ-6��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 85, "ƴͼ-6��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 86, "ͼƬ����-6��ָ���ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 87, "�����԰-6��ָ���ֲ������������ֵ �ٽ�ֵ");
        
        createCell(xwb, header, 88, "ָ��ͼƬ-6��ָ���ֲ������������ֵ �ж�ǿ�������");
        createCell(xwb, header, 89, "��ʶ-6��ָ���ֲ������������ֵ �ж�ǿ�������");
        createCell(xwb, header, 90, "��ľ-6��ָ���ֲ������������ֵ �ж�ǿ�������");
        createCell(xwb, header, 91, "ƴͼ-6��ָ���ֲ������������ֵ �ж�ǿ�������");
        createCell(xwb, header, 92, "ͼƬ����-6��ָ���ֲ������������ֵ �ж�ǿ�������");
        createCell(xwb, header, 93, "�����԰-6��ָ���ֲ������������ֵ �ж�ǿ�������");
        
        createCell(xwb, header, 94, "ָ��ͼƬ-6��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 95, "��ʶ-6��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 96, "��ľ-6��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 97, "ƴͼ-6��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 98, "ͼƬ����-6��ָ���ֲ������������ֵ ������");
        createCell(xwb, header, 99, "�����԰-6��ָ���ֲ������������ֵ ������");
        
        createCell(xwb, header, 100, "��������������-����������������6��ָ�������֣�");
        
        createCell(xwb, header, 101, "ָ��ͼƬ-5�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 102, "��ʶ-5�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 103, "��ľ-5�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 104, "ƴͼ-5�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 105, "ͼƬ����-5�������̷ֲ������������ֵ ��������");
        createCell(xwb, header, 106, "�����԰-5�������̷ֲ������������ֵ ��������");
        
        createCell(xwb, header, 107, "ָ��ͼƬ-5�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 108, "��ʶ-5�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 109, "��ľ-5�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 110, "ƴͼ-5�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 111, "ͼƬ����-5�������̷ֲ������������ֵ �ٽ�ֵ");
        createCell(xwb, header, 112, "�����԰-5�������̷ֲ������������ֵ �ٽ�ֵ");
        
        createCell(xwb, header, 113, "ָ��ͼƬ-5�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 114, "��ʶ-5�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 115, "��ľ-5�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 116, "ƴͼ-5�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 117, "ͼƬ����-5�������̷ֲ������������ֵ ��ǿ�������");
        createCell(xwb, header, 118, "�����԰-5�������̷ֲ������������ֵ ��ǿ�������");
        
        createCell(xwb, header, 119, "ָ��ͼƬ-5�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 120, "��ʶ-5�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 121, "��ľ-5�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 122, "ƴͼ-5�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 123, "ͼƬ����-5�������̷ֲ������������ֵ ������");
        createCell(xwb, header, 124, "�����԰-5�������̷ֲ������������ֵ ������");
       
        createCell(xwb, header, 125, "��������������-����������������5�������̷ֲ��飩");
        
        createCell(xwb, header, 126, ""); 
        cell = header.getCell(126);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ6");
        
        createCell(xwb, header, 127, "�������-�Ӿ��ռ� ��������");
        createCell(xwb, header, 128, "�������-�������� ��������");
        createCell(xwb, header, 129, "�Ӿ��ռ�-�������� ��������");
        
        createCell(xwb, header, 130, "�������-�Ӿ��ռ� �ٽ�ֵ");
        createCell(xwb, header, 131, "�������-�������� �ٽ�ֵ");
        createCell(xwb, header, 132, "�Ӿ��ռ�-�������� �ٽ�ֵ");
        
        createCell(xwb, header, 133, "�������-�Ӿ��ռ� �Ƿ������Բ���");
        createCell(xwb, header, 134, "�������-�������� �Ƿ������Բ���");
        createCell(xwb, header, 135, "�Ӿ��ռ�-�������� �Ƿ������Բ���");
        
        createCell(xwb, header, 136, "�������-�Ӿ��ռ� ������");
        createCell(xwb, header, 137, "�������-�������� ������");
        createCell(xwb, header, 138, "�Ӿ��ռ�-�������� ������");
        
        createCell(xwb, header, 139, "ָ��ͼƬ-��ʶ ��������");
        createCell(xwb, header, 140, "��ľ-ƴͼ ��������");
        createCell(xwb, header, 141, "ͼƬ����-�����԰ ��������");
        
        createCell(xwb, header, 142, "ָ��ͼƬ-��ʶ �ٽ�ֵ");
        createCell(xwb, header, 143, "��ľ-ƴͼ �ٽ�ֵ");
        createCell(xwb, header, 144, "ͼƬ����-�����԰ �ٽ�ֵ");
        
        createCell(xwb, header, 145, "ָ��ͼƬ-��ʶ �Ƿ������Բ���");
        createCell(xwb, header, 146, "��ľ-ƴͼ �Ƿ������Բ���");
        createCell(xwb, header, 147, "ͼƬ����-�����԰ �Ƿ������Բ���");
        
        createCell(xwb, header, 148, "ָ��ͼƬ-��ʶ ������");
        createCell(xwb, header, 149, "��ľ-ƴͼ ������");
        createCell(xwb, header, 150, "ͼƬ����-�����԰ ������");
        
        createCell(xwb, header, 151, ""); 
        cell = header.getCell(151);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ7");
        
        createCell(xwb, header, 152, "���Խ��� ��������ܺ�");
        createCell(xwb, header, 153, "������ ��������ܺ�");
        createCell(xwb, header, 154, "һ������ ��������ܺ�");
        
        createCell(xwb, header, 155, "���Խ��� �ϳɷ���");
        createCell(xwb, header, 156, "������ �ϳɷ���");
        createCell(xwb, header, 157, "һ������ �ϳɷ���");
        
        createCell(xwb, header, 158, "���Խ���VAI �ٷֵȼ�");
        createCell(xwb, header, 159, "������NVI �ٷֵȼ�");
        createCell(xwb, header, 160, "һ������GAI �ٷֵȼ�");
        
        createCell(xwb, header, 161, "���Խ���VAI 90%��������");
        createCell(xwb, header, 162, "������NVI 90%��������");
        createCell(xwb, header, 163, "һ������GAI 90%��������");
        
        createCell(xwb, header, 164, "���Խ���VAI 90%��������");
        createCell(xwb, header, 165, "������NVI 90%��������");
        createCell(xwb, header, 166, "һ������GAI 90%��������");
        
        createCell(xwb, header, 167, ""); 
        cell = header.getCell(167);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ8");
        
        createCell(xwb, header, 168, "һ������-������ ��������");
        createCell(xwb, header, 169, "һ������-������ �ٽ�ֵ");
        createCell(xwb, header, 170, "һ������-������ �Ƿ������Բ���");
        createCell(xwb, header, 171, "һ������-������ ������");
        
        createCell(xwb, header, 172, "ָ��ͼƬ-ͼƬ���� ��������");
        createCell(xwb, header, 173, "ָ��ͼƬ-ͼƬ���� �ٽ�ֵ");
        createCell(xwb, header, 174, "ָ��ͼƬ-ͼƬ���� �Ƿ������Բ���");
        createCell(xwb, header, 175, "ָ��ͼƬ-ͼƬ���� ������");
        
        createCell(xwb, header, 176, ""); 
        cell = header.getCell(176);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        cell.setCellValue("ȷ����Ϣ9");
        
        header = sheet.createRow(2);// ��3��  
        createCell(xwb, header, 0, ""); 
        sheet.addMergedRegion(getCellRangeAddress(2, 0, 2, 3));
        
        createCell(xwb, header, 4, "��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 4, 2, 6));
        cell = header.getCell(4);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 7, "��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 7, 2, 9));
        cell = header.getCell(7);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 10, "ʵ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 10, 2, 12));
        cell = header.getCell(10);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 13, "");
        cell = header.getCell(13);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style); 
        
        createCell(xwb, header, 14, "7���ֲ����������");
        sheet.addMergedRegion(getCellRangeAddress(2, 14, 2, 20));
        cell = header.getCell(14);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 21, "4����Ҫָ����������ܺ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 21, 2, 24));
        cell = header.getCell(21);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 25, "");
        cell = header.getCell(25);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 26, "4����Ҫָ���ϳɷ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 26, 2, 29));
        cell = header.getCell(26);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 30, "4����Ҫָ���ٷֵȼ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 30, 2, 33));
        cell = header.getCell(30);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 34, "4����Ҫָ���ϳɷ�����90%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 34, 2, 37));
        cell = header.getCell(34);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 38, "4����Ҫָ���ϳɷ�����90%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 38, 2, 41));
        cell = header.getCell(38);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 42, "");
        cell = header.getCell(42);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 43, "ÿ������еıȽϷ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 43, 2, 44));
        cell = header.getCell(43);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 45, "");
        cell = header.getCell(45);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 46, "");
        sheet.addMergedRegion(getCellRangeAddress(2, 46, 2, 47));
        cell = header.getCell(46);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 48, "");
        sheet.addMergedRegion(getCellRangeAddress(2, 48, 2, 49));
        cell = header.getCell(48);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 50, "");
        cell = header.getCell(50);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 51, "3��ָ��������ָ��������ֵ ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 51, 2, 53));
        cell = header.getCell(51);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 54, "3��ָ��������ָ��������ֵ �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 54, 2, 56));
        cell = header.getCell(54);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 57, "�ж�ǿ�������");
        sheet.addMergedRegion(getCellRangeAddress(2, 57, 2, 59));
        cell = header.getCell(57);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 60, "3��ָ��������ָ��������ֵ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 60, 2, 62));
        cell = header.getCell(60);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 63, "3��ָ�������������� ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 63, 2, 65));
        cell = header.getCell(63);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 66, "3��ָ�������������� �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 66, 2, 68));
        cell = header.getCell(66);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 69, "�ж�ǿ����");
        sheet.addMergedRegion(getCellRangeAddress(2, 69, 2, 71));
        cell = header.getCell(69);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 72, "�ж�ǿ����");
        sheet.addMergedRegion(getCellRangeAddress(2, 72, 2, 74));
        cell = header.getCell(72);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 75, "");
        cell = header.getCell(75);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 76, "6���ֲ����������10��ָ���ֲ���������ֵ ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 76, 2, 81));
        cell = header.getCell(76);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 82, "6���ֲ����������10��ָ���ֲ���������ֵ �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 82, 2, 87));
        cell = header.getCell(82);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 88, "�ж�ǿ�������");
        sheet.addMergedRegion(getCellRangeAddress(2, 88, 2, 93));
        cell = header.getCell(88);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 94, "6���ֲ����������6��ָ���ֲ���������ֵ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 94, 2, 99));
        cell = header.getCell(94);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 100, "��ɢ��");
        cell = header.getCell(100);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 101, "6���ֲ����������5�������̷ֲ���������ֵ ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 101, 2, 106));
        cell = header.getCell(101);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 107, "6���ֲ����������5�������̷ֲ���������ֵ �ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 107, 2, 112));
        cell = header.getCell(107);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 113, "�ж�ǿ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 113, 2, 118));
        cell = header.getCell(113);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 119, "6���ֲ����������5�������̷ֲ���������ֵ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 119, 2, 124));
        cell = header.getCell(119);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 125, "��ɢ��");
        cell = header.getCell(125);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 126, "");
        cell = header.getCell(126);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 127, "ָ��ˮƽ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 127, 2, 129));
        cell = header.getCell(127);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 130, "ָ��ˮƽ�ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 130, 2, 132));
        cell = header.getCell(130);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 133, "ָ��ˮƽ �ж��Ƿ������Բ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 133, 2, 135));
        cell = header.getCell(133);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 136, "ָ��ˮƽ ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 136, 2, 138));
        cell = header.getCell(136);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.ROSE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 139, "�ֲ���ˮƽ��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 139, 2, 141));
        cell = header.getCell(139);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 142, "�ֲ���ˮƽ�ٽ�ֵ");
        sheet.addMergedRegion(getCellRangeAddress(2, 142, 2, 144));
        cell = header.getCell(142);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 145, "�ֲ���ˮƽ �ж��Ƿ������Բ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 145, 2, 147));
        cell = header.getCell(145);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 148, "�ֲ���ˮƽ������");
        sheet.addMergedRegion(getCellRangeAddress(2, 148, 2, 150));
        cell = header.getCell(148);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 151 , "");
        cell = header.getCell(151);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 152, "3������ָ����������ܺ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 152, 2, 154));
        cell = header.getCell(152);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 155, "3������ָ���ϳɷ���");
        sheet.addMergedRegion(getCellRangeAddress(2, 155, 2, 157));
        cell = header.getCell(155);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 158, "3������ָ���ϳɷ����İٷֵȼ�");
        sheet.addMergedRegion(getCellRangeAddress(2, 158, 2, 160));
        cell = header.getCell(158);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 161, "3������ָ���ϳɷ�����90%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 161, 2, 163));
        cell = header.getCell(161);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 164, "4������ָ���ϳɷ�����95%��������");
        sheet.addMergedRegion(getCellRangeAddress(2, 164, 2, 166));
        cell = header.getCell(164);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 167, "");
        cell = header.getCell(167);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 168, "ָ��ˮƽ��������");
        cell = header.getCell(168);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 169, "ָ��ˮƽ�ٽ�ֵ");
        cell = header.getCell(169);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 170, "ָ��ˮƽ�ж��Ƿ������Բ���");
        cell = header.getCell(170);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 171, "ָ������������");
        cell = header.getCell(171);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 172, "�ּ��ˮƽ��������");
        cell = header.getCell(172);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.TAN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 173, "�ּ��ˮƽ�ٽ�ֵ");
        cell = header.getCell(173);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 174, "�ּ��ˮƽ�ж��Ƿ������Բ���");
        cell = header.getCell(174);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LAVENDER.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 175, "�ֲ���ˮƽ������");
        cell = header.getCell(175);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cell.setCellStyle(style);
        
        createCell(xwb, header, 176, "");
        cell = header.getCell(176);
        style = xwb.createCellStyle();
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cell.setCellStyle(style);
        
        for(int i = 0 ; i < alist.size() ; i++){
        	header = sheet.createRow(i+3);// ��i��  
            createCell(xwb, header, 0, "");
            
            createCell(xwb, header, 1, ""+clist.get(i).getCname());
            createCell(xwb, header, 2, ""+clist.get(i).getCtestname());
            createCell(xwb, header, 3, ""+clist.get(i).getCid());
            createCell(xwb, header, 4, ""+clist.get(i).getCtyear());
            createCell(xwb, header, 5, ""+clist.get(i).getCtmonth());
            createCell(xwb, header, 6, ""+clist.get(i).getCtday());
            createCell(xwb, header, 7, ""+clist.get(i).getCbyear());
            createCell(xwb, header, 8, ""+clist.get(i).getCbmonth());
            createCell(xwb, header, 9, ""+clist.get(i).getCbday());
            createCell(xwb, header, 10, ""+clist.get(i).getCyear());
            createCell(xwb, header, 11, ""+clist.get(i).getCmonth());
            createCell(xwb, header, 12, ""+clist.get(i).getCday());
            
            createCell(xwb, header, 13, "");
            cell = header.getCell(13);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 14, ""+alist.get(i).getRvl());
            createCell(xwb, header, 15, ""+alist.get(i).getBdl());
            createCell(xwb, header, 16, ""+alist.get(i).getPml());
            createCell(xwb, header, 17, ""+alist.get(i).getInfl());
            createCell(xwb, header, 18, ""+alist.get(i).getOal());
            createCell(xwb, header, 19, ""+alist.get(i).getZll());
            createCell(xwb, header, 20, ""+alist.get(i).getPnl());
            
            createCell(xwb, header, 21, ""+alist.get(i).getVci());
            createCell(xwb, header, 22, ""+alist.get(i).getVsi());
            createCell(xwb, header, 23, ""+alist.get(i).getWmi());
            createCell(xwb, header, 24, ""+alist.get(i).getFsiq());
            
            createCell(xwb, header, 25, "");
            cell = header.getCell(25);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 26, ""+alist.get(i).getVcii());
            createCell(xwb, header, 27, ""+alist.get(i).getVsii());
            createCell(xwb, header, 28, ""+alist.get(i).getWmii());
            createCell(xwb, header, 29, ""+alist.get(i).getFsiqi());
           
            createCell(xwb, header, 30, ""+alist.get(i).getVcib());
            createCell(xwb, header, 31, ""+alist.get(i).getVsib());
            createCell(xwb, header, 32, ""+alist.get(i).getWmib());
            createCell(xwb, header, 33, ""+alist.get(i).getFsiqb());
            
            if(alist.get(i).getSp() == 0){
            	createCell(xwb, header, 34, ""+alist.get(i).getVciz());
                createCell(xwb, header, 35, ""+alist.get(i).getVsiz());
                createCell(xwb, header, 36, ""+alist.get(i).getWmiz());
                createCell(xwb, header, 37, ""+alist.get(i).getFsiqz());
                
                for(int j = 0 ; j<4 ; j++){
                	 createCell(xwb, header, j+38, "");
                }
            }else{
            	for(int j = 0 ; j<4 ; j++){
               	 createCell(xwb, header, j+34, "");
            	}
            	createCell(xwb, header, 38, ""+alist.get(i).getVciz());
                createCell(xwb, header, 39, ""+alist.get(i).getVsiz());
                createCell(xwb, header, 40, ""+alist.get(i).getWmiz());
                createCell(xwb, header, 41, ""+alist.get(i).getFsiqz());
            }
            
            createCell(xwb, header, 42, "");
            cell = header.getCell(42);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 43, ""+alist.get(i).getZszf3());
            createCell(xwb, header, 44, ""+alist.get(i).getZsfs3jz());
            createCell(xwb, header, 45, ""+alist.get(i).getFsiqi());
            createCell(xwb, header, 46, ""+alist.get(i).getZsfs6());
            createCell(xwb, header, 47, ""+alist.get(i).getZsfs6jz());
            createCell(xwb, header, 48, ""+alist.get(i).getZsfs5());
            createCell(xwb, header, 49, ""+alist.get(i).getZsfs5jz());
            
            createCell(xwb, header, 50, "");
            cell = header.getCell(50);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 51, ""+alist.get(i).getVcizsfscy());
            createCell(xwb, header, 52, ""+alist.get(i).getVsizsfscy());
            createCell(xwb, header, 53, ""+alist.get(i).getWmizsfscy());
            
            createCell(xwb, header, 54, ""+alist.get(i).getVcizsfsljz());
            createCell(xwb, header, 55, ""+alist.get(i).getVsizsfsljz());
            createCell(xwb, header, 56, ""+alist.get(i).getWmizsfsljz());
            
            createCell(xwb, header, 57, ""+alist.get(i).getVcizsfsqrx());
            createCell(xwb, header, 58, ""+alist.get(i).getVsizsfsqrx());
            createCell(xwb, header, 59, ""+alist.get(i).getWmizsfsqrx());
            
            createCell(xwb, header, 60, ""+alist.get(i).getVcizsfsjcl());
            createCell(xwb, header, 61, ""+alist.get(i).getVsizsfsjcl());
            createCell(xwb, header, 62, ""+alist.get(i).getWmizsfsjcl());
            
            createCell(xwb, header, 63, ""+alist.get(i).getVcizzsfscy());
            createCell(xwb, header, 64, ""+alist.get(i).getVsizzsfscy());
            createCell(xwb, header, 65, ""+alist.get(i).getWmizzsfscy());
            
            createCell(xwb, header, 66, ""+alist.get(i).getVcizzsljz());
            createCell(xwb, header, 67, ""+alist.get(i).getVsizzsljz());
            createCell(xwb, header, 68, ""+alist.get(i).getWmizzsljz());
            
            createCell(xwb, header, 69, ""+alist.get(i).getVcizzsqrx());
            createCell(xwb, header, 70, ""+alist.get(i).getVsizzsqrx());
            createCell(xwb, header, 71, ""+alist.get(i).getWmizzsqrx());
            
            createCell(xwb, header, 72, ""+alist.get(i).getVcizzsjcl());
            createCell(xwb, header, 73, ""+alist.get(i).getVsizzsjcl());
            createCell(xwb, header, 74, ""+alist.get(i).getWmizzsjcl());
            
            createCell(xwb, header, 75, "");
            cell = header.getCell(75);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 76, ""+alist.get(i).getZsfs6rvfscy());
            createCell(xwb, header, 77, ""+alist.get(i).getZsfs6infscy());
            createCell(xwb, header, 78, ""+alist.get(i).getZsfs6bdfscy());
            createCell(xwb, header, 79, ""+alist.get(i).getZsfs6oafscy());
            createCell(xwb, header, 80, ""+alist.get(i).getZsfs6pmfscy());
            createCell(xwb, header, 81, ""+alist.get(i).getZsfs6zlfscy());
            
            createCell(xwb, header, 82, ""+alist.get(i).getZsfs6rvljz());
            createCell(xwb, header, 83, ""+alist.get(i).getZsfs6inljz());
            createCell(xwb, header, 84, ""+alist.get(i).getZsfs6bdljz());
            createCell(xwb, header, 85, ""+alist.get(i).getZsfs6oaljz());
            createCell(xwb, header, 86, ""+alist.get(i).getZsfs6pmljz());
            createCell(xwb, header, 87, ""+alist.get(i).getZsfs6zlljz());
            
            createCell(xwb, header, 88, ""+alist.get(i).getZsfs6rvqrx());
            createCell(xwb, header, 89, ""+alist.get(i).getZsfs6inqrx());
            createCell(xwb, header, 90, ""+alist.get(i).getZsfs6bdqrx());
            createCell(xwb, header, 91, ""+alist.get(i).getZsfs6oaqrx());
            createCell(xwb, header, 92, ""+alist.get(i).getZsfs6pmqrx());
            createCell(xwb, header, 93, ""+alist.get(i).getZsfs6zlqrx());
            
            createCell(xwb, header, 94, ""+alist.get(i).getZsfs6rvjcl());
            createCell(xwb, header, 95, ""+alist.get(i).getZsfs6injcl());
            createCell(xwb, header, 96, ""+alist.get(i).getZsfs6bdjcl());
            createCell(xwb, header, 97, ""+alist.get(i).getZsfs6oajcl());
            createCell(xwb, header, 98, ""+alist.get(i).getZsfs6pmjcl());
            createCell(xwb, header, 99, ""+alist.get(i).getZsfs6zljcl());
            
            createCell(xwb, header, 100, ""+alist.get(i).getZsfs6fsd());
            
            createCell(xwb, header, 101, ""+alist.get(i).getZsfs5rvfscy());
            createCell(xwb, header, 102, ""+alist.get(i).getZsfs5infscy());
            createCell(xwb, header, 103, ""+alist.get(i).getZsfs5bdfscy());
            createCell(xwb, header, 104, ""+alist.get(i).getZsfs5oafscy());
            createCell(xwb, header, 105, ""+alist.get(i).getZsfs5pmfscy());
            createCell(xwb, header, 106, ""+alist.get(i).getZsfs5zlfscy());
            
            createCell(xwb, header, 107, ""+alist.get(i).getZsfs5rvljz());
            createCell(xwb, header, 108, ""+alist.get(i).getZsfs5inljz());
            createCell(xwb, header, 109, ""+alist.get(i).getZsfs5bdljz());
            createCell(xwb, header, 110, ""+alist.get(i).getZsfs5oaljz());
            createCell(xwb, header, 111, ""+alist.get(i).getZsfs5pmljz());
            createCell(xwb, header, 112, ""+alist.get(i).getZsfs5zlljz());
            
            createCell(xwb, header, 113, ""+alist.get(i).getZsfs5rvqrx());
            createCell(xwb, header, 114, ""+alist.get(i).getZsfs5inqrx());
            createCell(xwb, header, 115, ""+alist.get(i).getZsfs5bdqrx());
            createCell(xwb, header, 116, ""+alist.get(i).getZsfs5oaqrx());
            createCell(xwb, header, 117, ""+alist.get(i).getZsfs5pmqrx());
            createCell(xwb, header, 118, ""+alist.get(i).getZsfs5zlqrx());
            
            createCell(xwb, header, 119, ""+alist.get(i).getZsfs5rvjcl());
            createCell(xwb, header, 120, ""+alist.get(i).getZsfs5injcl());
            createCell(xwb, header, 121, ""+alist.get(i).getZsfs5bdjcl());
            createCell(xwb, header, 122, ""+alist.get(i).getZsfs5oajcl());
            createCell(xwb, header, 123, ""+alist.get(i).getZsfs5pmjcl());
            createCell(xwb, header, 124, ""+alist.get(i).getZsfs5zljcl());
            
            createCell(xwb, header, 125, ""+alist.get(i).getZsfs5fsd());
            
            createCell(xwb, header, 126, "");
            cell = header.getCell(126);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 127, ""+alist.get(i).getVci_vsi());
            createCell(xwb, header, 128, ""+alist.get(i).getVci_wmi());
            createCell(xwb, header, 129, ""+alist.get(i).getVsi_wmi());
            
            createCell(xwb, header, 130, ""+alist.get(i).getVci_vsiljz());
            createCell(xwb, header, 131, ""+alist.get(i).getVci_wmiljz());
            createCell(xwb, header, 132, ""+alist.get(i).getVsi_wmiljz());
            
            createCell(xwb, header, 133, ""+alist.get(i).getVci_vsiqrx());
            createCell(xwb, header, 134, ""+alist.get(i).getVci_wmiqrx());
            createCell(xwb, header, 135, ""+alist.get(i).getVsi_wmiqrx());
            
            createCell(xwb, header, 136, ""+alist.get(i).getVci_vsijcl());
            createCell(xwb, header, 137, ""+alist.get(i).getVci_wmijcl());
            createCell(xwb, header, 138, ""+alist.get(i).getVsi_wmijcl());
            
            createCell(xwb, header, 139, ""+alist.get(i).getRv_in());
            createCell(xwb, header, 140, ""+alist.get(i).getBd_oa());
            createCell(xwb, header, 141, ""+alist.get(i).getPm_zl());
            
            createCell(xwb, header, 142, ""+alist.get(i).getRv_inljz());
            createCell(xwb, header, 143, ""+alist.get(i).getBd_oaljz());
            createCell(xwb, header, 144, ""+alist.get(i).getPm_zlljz());
            
            createCell(xwb, header, 145, ""+alist.get(i).getRv_inqrx());
            createCell(xwb, header, 146, ""+alist.get(i).getBd_oaqrx());
            createCell(xwb, header, 147, ""+alist.get(i).getPm_zlqrx());
            
            createCell(xwb, header, 148, ""+alist.get(i).getRv_injcl());
            createCell(xwb, header, 149, ""+alist.get(i).getBd_oajcl());
            createCell(xwb, header, 150, ""+alist.get(i).getPm_zljcl());
            
            createCell(xwb, header, 151, "");
            cell = header.getCell(151);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 152, ""+alist.get(i).getVai());
            createCell(xwb, header, 153, ""+alist.get(i).getNvi());
            createCell(xwb, header, 154, ""+alist.get(i).getGai());
            
            createCell(xwb, header, 155, ""+alist.get(i).getVaii());
            createCell(xwb, header, 156, ""+alist.get(i).getNvii());
            createCell(xwb, header, 157, ""+alist.get(i).getGaii());
            
            createCell(xwb, header, 158, ""+alist.get(i).getVaib());
            createCell(xwb, header, 159, ""+alist.get(i).getNvib());
            createCell(xwb, header, 160, ""+alist.get(i).getGaib());
            
            if(alist.get(i).getSp() == 0){
            	createCell(xwb, header, 161, ""+alist.get(i).getVaizxqj());
                createCell(xwb, header, 162, ""+alist.get(i).getNvizxqj());
                createCell(xwb, header, 163, ""+alist.get(i).getGaizxqj());
                
                for(int k = 0 ; k < 3 ; k++){
                	createCell(xwb, header, 164+k, "");
                }
            }else{
            	for(int k = 0 ; k < 3 ; k++){
                	createCell(xwb, header, 161+k, "");
                }
            	
            	createCell(xwb, header, 164, ""+alist.get(i).getVaizxqj());
                createCell(xwb, header, 165, ""+alist.get(i).getNvizxqj());
                createCell(xwb, header, 166, ""+alist.get(i).getGaizxqj());
            }
            
            createCell(xwb, header, 167, "");
            cell = header.getCell(167);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            createCell(xwb, header, 168, ""+alist.get(i).getGai_fsiq());
            
            createCell(xwb, header, 169, ""+alist.get(i).getGai_fsiqljz());
            
            createCell(xwb, header, 170, ""+alist.get(i).getGai_fsiqcy());
            
            createCell(xwb, header, 171, ""+alist.get(i).getGai_fsiqjcl());
            
            createCell(xwb, header, 172, ""+alist.get(i).getRv_pn());
            
            createCell(xwb, header, 173, ""+alist.get(i).getRv_pnljz());
            
            createCell(xwb, header, 174, ""+alist.get(i).getRv_pncy());
            
            createCell(xwb, header, 175, ""+alist.get(i).getRv_pnjcl());
            
            createCell(xwb, header, 176, "");
            cell = header.getCell(176);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
            
        }
        
	}
    
    /**�����ͷ
     * */
    public static synchronized void write_Yuan(List<Child> clist , List<Object> alist){
    	XSSFWorkbook xwb;
		xwb = new XSSFWorkbook();
		XSSFSheet sheet = xwb.createSheet();
		dxqdHeadYuan(sheet, xwb , alist , clist);  
		String path = setlast(new Date());
		String filePath=ServletActionContext.getServletContext().getRealPath("download")+"\\"+ path+".xlsx";
		File f = new File(ServletActionContext.getServletContext().getRealPath("download"));
		if(!f.exists()){
			f.mkdir();
		}
		try {
			xwb.write(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("path",path);
    }
    
    private static void dxqdHeadYuan(XSSFSheet sheet, XSSFWorkbook xwb , List<Object> alist , List<Child> clist) {
    	List<Age2> l_2 = new ArrayList<Age2>();
    	List<Age4> l_4 = new ArrayList<Age4>();
    	List<Child> c_2 = new ArrayList<Child>();
    	List<Child> c_4 = new ArrayList<Child>();
    	
    	for(int i = 0 ; i < alist.size() ; i++){
    		if(alist.get(i) instanceof Age2){
    			l_2.add((Age2)alist.get(i));
    			c_2.add(clist.get(i));
    		}else if(alist.get(i) instanceof Age4){
    			l_4.add((Age4)alist.get(i));
    			c_4.add(clist.get(i));
    		}
    	}
    	
    	System.out.println("list size : list2"+l_2.size() +" list4"+l_4.size());
    	
    	int row = 0;
    	if(l_2.size() > 0){
    		write2scorc(sheet, xwb, l_2 , c_2);
    		row = l_2.size()+4;
    	}
    	
    	if(l_4.size() > 0){
    		write4scorc(sheet, xwb, l_4, row , c_4);
    	}
    	
//    	for(int i = 0 ; i < clist.size() ; i++){
//    		Row header = sheet.createRow(i);
//    		Cell cell = header.getCell(0);
//    		XSSFCellStyle style = xwb.createCellStyle();
//    		// //��i������  
//    		createCell(xwb, header, 0, "");  
//            createCell(xwb, header, 1, ""+clist.get(i).getCname());  
//            createCell(xwb, header, 2, ""+clist.get(i).getCtestname());  
//            createCell(xwb, header, 3, ""+clist.get(i).getCtyear());  
//            createCell(xwb, header, 4, ""+clist.get(i).getCtmonth());  
//            createCell(xwb, header, 5, ""+clist.get(i).getCtday());  
//            createCell(xwb, header, 6, ""+clist.get(i).getCbyear());  
//            createCell(xwb, header, 7, ""+clist.get(i).getCbmonth());  
//            createCell(xwb, header, 8, ""+clist.get(i).getCbday()); 
//            createCell(xwb, header, 9, ""+clist.get(i).getCsex());  
//            createCell(xwb, header, 10, ""+clist.get(i).getChand());  
//            createCell(xwb, header, 11, ""+clist.get(i).getCclass()); 
//            createCell(xwb, header, 12, ""+clist.get(i).getCculture()); 
//            createCell(xwb, header, 13, ""+clist.get(i).getCptel());  
//            createCell(xwb, header, 14, ""+clist.get(i).getCpemail());  
//            createCell(xwb, header, 15, ""+clist.get(i).getCpadd());  
//            createCell(xwb, header, 16, ""+clist.get(i).getCstm());  
//            createCell(xwb, header, 17, "");
//            
//            cell = header.getCell(17);
//            style = xwb.createCellStyle();
//            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//            cell.setCellStyle(style); 
//            if(alist.get(i) instanceof Age2){
//            	Age2 a = (Age2) alist.get(i);
//            	createCell(xwb, header, 18, ""+a.getRv());  
//            	createCell(xwb, header, 19, ""+a.getBd());  
//            	createCell(xwb, header, 20, ""+a.getPm());  
//            	createCell(xwb, header, 21, ""+a.getInf());  
//            	createCell(xwb, header, 22, ""+a.getOa());  
//            	createCell(xwb, header, 23, ""+a.getZl());  
//            	createCell(xwb, header, 24, ""+a.getPn());  
//            	
//            	createCell(xwb, header, 25, "");
//                 
//                cell = header.getCell(25);
//                style = xwb.createCellStyle();
//                style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//                style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//                cell.setCellStyle(style);
//            	
//            	createCell(xwb, header, 26, ""+a.getSp());  
//            	createCell(xwb, header, 27, ""+a.getSsp());  
//            	createCell(xwb, header, 28, ""+a.getBjjc());  
//            	createCell(xwb, header, 29, ""+a.getJsff());  
//            
//            	createCell(xwb, header, 30, "");
//            	cell = header.getCell(30);
//                style = xwb.createCellStyle();
//                style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//                style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//                cell.setCellStyle(style);
//            }else if(alist.get(i) instanceof Age4){
//            	Age4 a = (Age4) alist.get(i);
//            	createCell(xwb, header, 18, ""+a.getBd());  
//            	createCell(xwb, header, 19, ""+a.getInf());  
//            	createCell(xwb, header, 20, ""+a.getMr());  
//            	createCell(xwb, header, 21, ""+a.getBs());  
//            	createCell(xwb, header, 22, ""+a.getPm());  
//            	createCell(xwb, header, 23, ""+a.getSi());  
//            	createCell(xwb, header, 24, ""+a.getPc());  
//            	createCell(xwb, header, 25, ""+a.getCa());  
//            	createCell(xwb, header, 26, ""+a.getZl());  
//            	createCell(xwb, header, 27, ""+a.getOa());  
//            	createCell(xwb, header, 28, ""+a.getAc());
//            	createCell(xwb, header, 29, ""+a.getRv());  
//            	createCell(xwb, header, 30, ""+a.getPn());  
//            	createCell(xwb, header, 31, ""+a.getCar());  
//            	createCell(xwb, header, 32, ""+a.getCas());  
//            	
//            	createCell(xwb, header, 33, "");
//                 
//                cell = header.getCell(33);
//                style = xwb.createCellStyle();
//                style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//                style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//                cell.setCellStyle(style);
//            	
//            	createCell(xwb, header, 34, ""+a.getSp());  
//            	createCell(xwb, header, 35, ""+a.getSsp());  
//            	createCell(xwb, header, 36, ""+a.getBjjc());  
//            	createCell(xwb, header, 37, ""+a.getJsff());  
//            
//            	createCell(xwb, header, 38, "");
//            	cell = header.getCell(38);
//                style = xwb.createCellStyle();
//                style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
//                style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
//                cell.setCellStyle(style);
//            }
//    	}
    }
    
    private static void write2scorc(XSSFSheet sheet, XSSFWorkbook xwb,List<Age2> l , List<Child> clist){
    	Row header = sheet.createRow(0);
		Cell cell = header.getCell(0);
		XSSFCellStyle style = xwb.createCellStyle();
		
		createCell(xwb, header, 0, ""); 
		
		createCell(xwb, header, 1, "�Ƿֲ���ҳ"); 
		sheet.addMergedRegion(getCellRangeAddress(0, 1, 0, 16));
		cell = header.getCell(1);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 17, " "); 
		cell = header.getCell(17);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 18, "���ķֲ���ԭʼ����"); 
		sheet.addMergedRegion(getCellRangeAddress(0, 18, 0, 24));
		cell = header.getCell(18);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 25, " "); 
		cell = header.getCell(25);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 26, "����ҳѡ��"); 
		sheet.addMergedRegion(getCellRangeAddress(0, 26, 0, 29));
		cell = header.getCell(26);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 30, " "); 
		cell = header.getCell(30);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		//��һ��
		header = sheet.createRow(1);
		cell = header.getCell(0);
		style = xwb.createCellStyle();
		
		createCell(xwb, header, 0, ""); 
		createCell(xwb, header, 1, "��ͯ����");
		createCell(xwb, header, 2, "��������");
		createCell(xwb, header, 3, "�������� ��");
		createCell(xwb, header, 4, "�������� ��");
		createCell(xwb, header, 5, "�������� ��");
		createCell(xwb, header, 6, "�������� ��");
		createCell(xwb, header, 7, "�������� ��");
		createCell(xwb, header, 8, "�������� ��");
		createCell(xwb, header, 9, "�Ա�");
		createCell(xwb, header, 10, "����ϰ��");
		createCell(xwb, header, 11, "�����꼶");
		createCell(xwb, header, 12, "�ҳ������̶�");
		createCell(xwb, header, 13, "�绰����");
		createCell(xwb, header, 14, "��������");
		createCell(xwb, header, 15, "��ϵ��ַ");
		createCell(xwb, header, 16, "��ע����");
		
		createCell(xwb, header, 17, "ȷ����Ϣ1"); 
		cell = header.getCell(17);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 18, "ָ��ͼƬ ԭʼ����"); 
		createCell(xwb, header, 19, "��ľ ԭʼ����"); 
		createCell(xwb, header, 20, "ͼƬ���� ԭʼ����"); 
		createCell(xwb, header, 21, "��ʶ ԭʼ����"); 
		createCell(xwb, header, 22, "ƴͼ ԭʼ����"); 
		createCell(xwb, header, 23, "�����԰ ԭʼ����"); 
		createCell(xwb, header, 24, "ͼƬ���� ԭʼ����"); 
		
		createCell(xwb, header, 25, "ȷ����Ϣ2"); 
		cell = header.getCell(25);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 26, "��������"); 
		createCell(xwb, header, 27, "ͳ��������ˮƽ��ǿ��/�������Ƚϣ�"); 
		createCell(xwb, header, 28, "�Ƚϻ���"); 
		createCell(xwb, header, 29, "������Ч/ȱʧ��ԭʼ����ʱѡ�õļ��㷽��"); 
		
		createCell(xwb, header, 30, "ȷ����Ϣ3"); 
		cell = header.getCell(30);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		//��һ��
		header = sheet.createRow(2);
		cell = header.getCell(0);
		style = xwb.createCellStyle();
		
		createCell(xwb, header, 0, ""); 
		createCell(xwb, header, 1, ""); 
		createCell(xwb, header, 2, ""); 
		
		createCell(xwb, header, 3, "��������"); 
		sheet.addMergedRegion(getCellRangeAddress(2, 3, 2, 5));
		cell = header.getCell(3);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.TAN.index);
		cell.setCellStyle(style);
		
		createCell(xwb, header, 6, "��������"); 
		sheet.addMergedRegion(getCellRangeAddress(2, 6, 2, 8));
		cell = header.getCell(6);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cell.setCellStyle(style);
		
		createCell(xwb, header, 9, ""); 
		createCell(xwb, header, 10, ""); 
		createCell(xwb, header, 11, ""); 
		createCell(xwb, header, 12, ""); 
		createCell(xwb, header, 13, ""); 
		createCell(xwb, header, 14, ""); 
		createCell(xwb, header, 15, ""); 
		createCell(xwb, header, 16, ""); 
		
		createCell(xwb, header, 17, ""); 
		cell = header.getCell(17);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 18, ""); 
		createCell(xwb, header, 19, ""); 
		createCell(xwb, header, 20, ""); 
		createCell(xwb, header, 21, ""); 
		createCell(xwb, header, 22, ""); 
		createCell(xwb, header, 23, ""); 
		createCell(xwb, header, 24, ""); 
		
		createCell(xwb, header, 25, ""); 
		cell = header.getCell(25);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 26, "90%/95%"); 
		createCell(xwb, header, 27, "0.01/0.05/0.10/0.15"); 
		createCell(xwb, header, 28, "ȫ����/��������ˮƽ"); 
		createCell(xwb, header, 29, "����ֲ���/�������㷨"); 
		
		createCell(xwb, header, 30, ""); 
		cell = header.getCell(30);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		for(int i = 0 ; i < l.size(); i++){
			header = sheet.createRow(i+3);
    		cell = header.getCell(0);
    		style = xwb.createCellStyle();
    		// //��i������  
    		createCell(xwb, header, 0, "");  
            createCell(xwb, header, 1, ""+clist.get(i).getCname());  
            createCell(xwb, header, 2, ""+clist.get(i).getCtestname());  
            createCell(xwb, header, 3, ""+clist.get(i).getCtyear());  
            createCell(xwb, header, 4, ""+clist.get(i).getCtmonth());  
            createCell(xwb, header, 5, ""+clist.get(i).getCtday());  
            createCell(xwb, header, 6, ""+clist.get(i).getCbyear());  
            createCell(xwb, header, 7, ""+clist.get(i).getCbmonth());  
            createCell(xwb, header, 8, ""+clist.get(i).getCbday()); 
            createCell(xwb, header, 9, ""+clist.get(i).getCsex());  
            createCell(xwb, header, 10, ""+clist.get(i).getChand());  
            createCell(xwb, header, 11, ""+clist.get(i).getCclass()); 
            createCell(xwb, header, 12, ""+clist.get(i).getCculture()); 
            createCell(xwb, header, 13, ""+clist.get(i).getCptel());  
            createCell(xwb, header, 14, ""+clist.get(i).getCpemail());  
            createCell(xwb, header, 15, ""+clist.get(i).getCpadd());  
            createCell(xwb, header, 16, ""+clist.get(i).getCstm());  
            createCell(xwb, header, 17, "");
            
            cell = header.getCell(17);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style); 
            
            Age2 a = (Age2) l.get(i);
        	createCell(xwb, header, 18, ""+a.getRv());  
        	createCell(xwb, header, 19, ""+a.getBd());  
        	createCell(xwb, header, 20, ""+a.getPm());  
        	createCell(xwb, header, 21, ""+a.getInf());  
        	createCell(xwb, header, 22, ""+a.getOa());  
        	createCell(xwb, header, 23, ""+a.getZl());  
        	createCell(xwb, header, 24, ""+a.getPn());  
        	
        	createCell(xwb, header, 25, "");
             
            cell = header.getCell(25);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
        	
        	createCell(xwb, header, 26, ""+a.getSp());  
        	createCell(xwb, header, 27, ""+a.getSsp());  
        	createCell(xwb, header, 28, ""+a.getBjjc());  
        	createCell(xwb, header, 29, ""+a.getJsff());  
        
        	createCell(xwb, header, 30, "");
        	cell = header.getCell(30);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
            
		}
		
    }
    
    private static void write4scorc(XSSFSheet sheet, XSSFWorkbook xwb,List<Age4> l ,int row  , List<Child> clist){
    	Row header = sheet.createRow(row);
		Cell cell = header.getCell(0);
		XSSFCellStyle style = xwb.createCellStyle();
//		System.out.println("write4 score");
		createCell(xwb, header, 0, ""); 
		
		createCell(xwb, header, 1, "�Ƿֲ���ҳ"); 
		sheet.addMergedRegion(getCellRangeAddress(row, 1, row, 16));
		cell = header.getCell(1);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 17, " "); 
		cell = header.getCell(17);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 18, "���ķֲ���ԭʼ����"); 
		sheet.addMergedRegion(getCellRangeAddress(row, 18, row, 32));
		cell = header.getCell(18);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 33, " "); 
		cell = header.getCell(33);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 34, "����ҳѡ��"); 
		sheet.addMergedRegion(getCellRangeAddress(row, 34, row, 37));
		cell = header.getCell(34);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 38, " "); 
		cell = header.getCell(38);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		//��һ��
		row++;
		header = sheet.createRow(row);
		cell = header.getCell(0);
		style = xwb.createCellStyle();
		
		createCell(xwb, header, 0, ""); 
		createCell(xwb, header, 1, "��ͯ����"); 
		createCell(xwb, header, 2, "��������"); 
		createCell(xwb, header, 3, "�������� ��"); 
		createCell(xwb, header, 4, "�������� ��"); 
		createCell(xwb, header, 5, "�������� ��"); 
		createCell(xwb, header, 6, "�������� ��"); 
		createCell(xwb, header, 7, "�������� ��"); 
		createCell(xwb, header, 8, "�������� ��"); 
		createCell(xwb, header, 9, "�Ա�"); 
		createCell(xwb, header, 10, "����ϰ��"); 
		createCell(xwb, header, 11, "�����꼶"); 
		createCell(xwb, header, 12, "�ҳ������̶�"); 
		createCell(xwb, header, 13, "�绰����"); 
		createCell(xwb, header, 14, "��������"); 
		createCell(xwb, header, 15, "��ϵ��ַ"); 
		createCell(xwb, header, 16, "��ע����"); 
		
		createCell(xwb, header, 17, "ȷ����Ϣ1"); 
		cell = header.getCell(17);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 18, "��ľ ԭʼ����"); 
		createCell(xwb, header, 19, "��ʶ ԭʼ����"); 
		createCell(xwb, header, 20, "�������� ԭʼ����"); 
		createCell(xwb, header, 21, "�ҳ� ԭʼ����"); 
		createCell(xwb, header, 22, "ͼƬ���� ԭʼ����"); 
		createCell(xwb, header, 23, "��ͬ ԭʼ����"); 
		createCell(xwb, header, 24, "ͼ������ ԭʼ����"); 
		createCell(xwb, header, 25, "���� ԭʼ����"); 
		createCell(xwb, header, 26, "�����԰ ԭʼ����"); 
		createCell(xwb, header, 27, "ƴͼ ԭʼ����"); 
		createCell(xwb, header, 28, "�������� ԭʼ����"); 
		createCell(xwb, header, 29, "ָ��ͼƬ ԭʼ����"); 
		createCell(xwb, header, 30, "ͼƬ���� ԭʼ����"); 
		createCell(xwb, header, 31, "������� ԭʼ����"); 
		createCell(xwb, header, 32, "������ ԭʼ����"); 
		
		createCell(xwb, header, 33, "ȷ����Ϣ2"); 
		cell = header.getCell(33);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 34, "��������"); 
		createCell(xwb, header, 35, "ͳ��������ˮƽ��ǿ��/�������Ƚϣ�"); 
		createCell(xwb, header, 36, "�Ƚϻ���"); 
		createCell(xwb, header, 37, "������Ч/ȱʧ��ԭʼ����ʱѡ�õļ��㷽��"); 
		
		createCell(xwb, header, 38, "ȷ����Ϣ3"); 
		cell = header.getCell(38);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		row++;
		header = sheet.createRow(row);
		cell = header.getCell(0);
		style = xwb.createCellStyle();
		
		createCell(xwb, header, 0, ""); 
		createCell(xwb, header, 1, ""); 
		createCell(xwb, header, 2, ""); 
		createCell(xwb, header, 3, "��������"); 
		sheet.addMergedRegion(getCellRangeAddress(row, 3, row, 5));
		cell = header.getCell(3);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.TAN.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 6, "��������"); 
		sheet.addMergedRegion(getCellRangeAddress(row, 6, row, 8));
		cell = header.getCell(6);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 9, ""); 
		createCell(xwb, header, 10, ""); 
		createCell(xwb, header, 11, ""); 
		createCell(xwb, header, 12, ""); 
		createCell(xwb, header, 13, ""); 
		createCell(xwb, header, 14, ""); 
		createCell(xwb, header, 15, ""); 
		createCell(xwb, header, 16, ""); 

		createCell(xwb, header, 17, " "); 
		cell = header.getCell(17);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 18, ""); 
		createCell(xwb, header, 19, ""); 
		createCell(xwb, header, 20, ""); 
		createCell(xwb, header, 21, ""); 
		createCell(xwb, header, 22, ""); 
		createCell(xwb, header, 23, ""); 
		createCell(xwb, header, 24, ""); 
		createCell(xwb, header, 25, ""); 
		createCell(xwb, header, 26, ""); 
		createCell(xwb, header, 27, ""); 
		createCell(xwb, header, 28, ""); 
		createCell(xwb, header, 29, ""); 
		createCell(xwb, header, 30, ""); 
		createCell(xwb, header, 31, ""); 
		createCell(xwb, header, 32, ""); 
		
		createCell(xwb, header, 33, " "); 
		cell = header.getCell(33);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		createCell(xwb, header, 34, "90%/95%"); 
		createCell(xwb, header, 35, "0.01/0.05/0.10/0.15"); 
		createCell(xwb, header, 36, "ȫ����/��������ˮƽ"); 
		createCell(xwb, header, 37, "����ֲ���/�������㷨"); 
		
		createCell(xwb, header, 38, " "); 
		cell = header.getCell(38);
		style = xwb.createCellStyle();
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cell.setCellStyle(style); 
		
		row++;

		for(int i = 0 ; i < l.size() ; i++){
			header = sheet.createRow(i+row);
			cell = header.getCell(0);
			style = xwb.createCellStyle();
			
			createCell(xwb, header, 0, "");  
	        createCell(xwb, header, 1, ""+clist.get(i).getCname());  
	        createCell(xwb, header, 2, ""+clist.get(i).getCtestname());  
	        createCell(xwb, header, 3, ""+clist.get(i).getCtyear());  
	        createCell(xwb, header, 4, ""+clist.get(i).getCtmonth());  
	        createCell(xwb, header, 5, ""+clist.get(i).getCtday());  
	        createCell(xwb, header, 6, ""+clist.get(i).getCbyear());  
	        createCell(xwb, header, 7, ""+clist.get(i).getCbmonth());  
	        createCell(xwb, header, 8, ""+clist.get(i).getCbday()); 
	        createCell(xwb, header, 9, ""+clist.get(i).getCsex());  
	        createCell(xwb, header, 10, ""+clist.get(i).getChand());  
	        createCell(xwb, header, 11, ""+clist.get(i).getCclass()); 
	        createCell(xwb, header, 12, ""+clist.get(i).getCculture()); 
	        createCell(xwb, header, 13, ""+clist.get(i).getCptel());  
	        createCell(xwb, header, 14, ""+clist.get(i).getCpemail());  
	        createCell(xwb, header, 15, ""+clist.get(i).getCpadd());  
	        createCell(xwb, header, 16, ""+clist.get(i).getCstm());  
	        createCell(xwb, header, 17, "");
	         
	        cell = header.getCell(17);
	        style = xwb.createCellStyle();
	        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
	        cell.setCellStyle(style); 
	        
	        Age4 a = (Age4) l.get(i);
        	createCell(xwb, header, 18, ""+a.getBd());  
        	createCell(xwb, header, 19, ""+a.getInf());  
        	createCell(xwb, header, 20, ""+a.getMr());  
        	createCell(xwb, header, 21, ""+a.getBs());  
        	createCell(xwb, header, 22, ""+a.getPm());  
        	createCell(xwb, header, 23, ""+a.getSi());  
        	createCell(xwb, header, 24, ""+a.getPc());  
        	createCell(xwb, header, 25, ""+a.getCa());  
        	createCell(xwb, header, 26, ""+a.getZl());  
        	createCell(xwb, header, 27, ""+a.getOa());  
        	createCell(xwb, header, 28, ""+a.getAc());
        	createCell(xwb, header, 29, ""+a.getRv());  
        	createCell(xwb, header, 30, ""+a.getPn());  
        	createCell(xwb, header, 31, ""+a.getCar());  
        	createCell(xwb, header, 32, ""+a.getCas());  
        	
        	createCell(xwb, header, 33, "");
             
            cell = header.getCell(33);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
        	
        	createCell(xwb, header, 34, ""+a.getSp());  
        	createCell(xwb, header, 35, ""+a.getSsp());  
        	createCell(xwb, header, 36, ""+a.getBjjc());  
        	createCell(xwb, header, 37, ""+a.getJsff());  
        
        	createCell(xwb, header, 38, "");
        	cell = header.getCell(38);
            style = xwb.createCellStyle();
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cell.setCellStyle(style);
		}
		
		
		
    }
    
	private static void createCell(XSSFWorkbook xwb, Row header, int column,
			String text) {
		Cell cell=header.createCell(column);  
        cell.setCellValue(text);  
	}  
	
	public static CellRangeAddress getCellRangeAddress(int firstRow,  
            int firstCol, int lastRow, int lastCol) {  
        return new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);  
    }
     
}
