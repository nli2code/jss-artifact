package Excel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*
 * �������ڶԱ��������ʽ������
 */
public class Style {
	public static XSSFCellStyle createCellStyle(XSSFWorkbook workbook){
		XSSFFont font=workbook.createFont();
		//�ڶ�Ӧ��workbook���½�����
		font.setFontName("΢���ź�");
		//����΢���ź�
		font.setFontHeightInPoints((short)11);
		//���������С
		XSSFCellStyle style=workbook.createCellStyle();
		//�½�Cell����
		style.setFont(font);
		return style;
	}
}
