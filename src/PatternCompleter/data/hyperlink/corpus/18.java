package helloExcel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**����������*/
public class MakeHyperlink {

	@SuppressWarnings("deprecation")
	public static void makeHyperlink() throws Exception{
		//��������������
		XSSFWorkbook workbook = new XSSFWorkbook();
		//��������ҳ����
	    XSSFSheet spreadsheet = workbook.createSheet("Hyperlinks");
	    XSSFCell cell;
	    //����һ��ͨ�ð�����,����ģʽ,��������,����ָ���Ķ���
	    CreationHelper createHelper = workbook.getCreationHelper();
	    //����һ�����ӵ�Ԫ�����ʽ,�ɹ���������
	    XSSFCellStyle hlinkStyle = workbook.createCellStyle();
	    //����һ�������������ʽ,�ɹ���������
	    XSSFFont hlinkFont = workbook.createFont();
	    //������ʽ�����»���,��������1,��ʾ�����»���
	    hlinkFont.setUnderline(XSSFFont.U_SINGLE);
	    //������ʽ������ɫΪ��ɫ
	    hlinkFont.setColor(HSSFColor.BLUE.index);
	    //Ϊ��Ԫ����ʽ����������ʽ.
	    hlinkStyle.setFont(hlinkFont);
	    //������Ԫ��
	    cell = spreadsheet.createRow(1).createCell((short) 1);
	    //��Ԫ������ֵ
	    cell.setCellValue("������");
	    //���������Ӷ���.������������	1.��ҳ��ʽ	2.�ĵ�·��	3.EMAIL·�� 4.�ļ�·��
	    XSSFHyperlink link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);
	    //������ҳ��ʽ,���õ�ַΪһ����ҳ
	    link.setAddress("http://www.baidu.com/" );
	    //����link���뵽��Ԫ����
	    cell.setHyperlink(link);
	    //���õ�Ԫ����ʽ
	    cell.setCellStyle(hlinkStyle);
	    /*����ָ���ļ���ַ�ĵ�Ԫ��*/
	    cell = spreadsheet.createRow(2).createCell((short) 1);
	    cell.setCellValue("File Link");
	    link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_FILE);
	    link.setAddress("��Ԫ����ʽ.xlsx");
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlinkStyle);
	    /*����ָ��EMAIL��ַ�ĵ�Ԫ��*/
	    cell = spreadsheet.createRow(3).createCell((short) 1);
	    cell.setCellValue("Email Link");
	    link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
	    link.setAddress("mailto:1434501783@qq.com?"+"subject=Hyperlink");
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlinkStyle);
	    FileOutputStream out = new FileOutputStream(
	    new File("������.xlsx"));
	    workbook.write(out);
	    out.close();
	    workbook.close();
	    System.out.println("������.xlsx �����ɹ�.....");
	}
	
	public static void main(String[] args) throws Exception{
		makeHyperlink();
	}
}
