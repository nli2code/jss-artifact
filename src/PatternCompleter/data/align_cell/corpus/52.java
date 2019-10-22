package test;



import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import java.io.FileOutputStream;


public class CreateXL {

	/** Excel 

	 �ļ�Ҫ��ŵ�λ�ã��ٶ���

	 D

	 ����

	

	 */

	public static String outputFile = "c:\\test.xls";

	private void cteateCell(HSSFWorkbook wb, HSSFRow row, short col, String val) {
		
		HSSFCell cell = row.createCell(col); 
		cell.setCellValue(val); 
		HSSFCellStyle cellstyle = wb.createCellStyle(); 
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); 
		cell.setCellStyle(cellstyle);
	}	
	
	
	public static void main(String argv[]) {
		
		// �����µ�Excel ������
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		// ��������

		HSSFFont font = workbook.createFont(); 

		// font.setColor(HSSFFont.COLOR_RED); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		font.setFontHeightInPoints((short) 14); 
		// HSSFFont font2 = workbook.createFont(); 
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		// font.setFontHeightInPoints((short)14); 
		// ������ʽ

		HSSFCellStyle cellStyle = workbook.createCellStyle(); 
		cellStyle.setFont(font); 
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		// HSSFCellStyle cellStyle2= workbook.createCellStyle(); 
		// cellStyle.setFont(font2); 
		// cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
		// ��Excel�������н�һ����������Ϊȱʡֵ
		// ��Ҫ�½�һ��Ϊ"�±���"�Ĺ����������Ϊ��

		HSSFSheet sheet = workbook.createSheet("�±���"); 
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0,11); 

		sheet.addMergedRegion(cellRangeAddress);

		//��һ��
		// ������0��λ�ô����У���˵��У�
		HSSFRow row = sheet.createRow(0); 

		// ������0��λ�ô�����Ԫ�����϶ˣ�
		HSSFCell cell = row.createCell(0); 
		// ���嵥Ԫ��Ϊ�ַ�������

		cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
		cell.setCellStyle(cellStyle); 
		// �ڵ�Ԫ��������һЩ����
		cell.setCellValue(new HSSFRichTextString("�����ڿ����Ƽ���չ���޹�˾С���Ž�ά���±���")); 

		
		
	}
}  

