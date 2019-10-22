package org.bitbudget.vegetable.office.poi.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bitbudget.vegetable.office.poi.constant.AlignmentType;
import org.bitbudget.vegetable.office.poi.style.bean.ReportInfoStyle;
import org.bitbudget.vegetable.office.poi.utils.history.ExportUtils4;

import junit.framework.TestCase;

public class ExportUtils4Test extends TestCase {

	public void testExport(){
		
		List<List> list = new ArrayList<List>();
		List<Map> subList = new ArrayList<Map>();
		
		ReportInfoStyle vo = new ReportInfoStyle();
		
		//��һ��
		Map<ReportInfoStyle, String> headerMap = new HashMap<ReportInfoStyle, String>();
		vo.setAlignment(AlignmentType.center);
		vo.setBold(true);
		vo.setColNums("A-f");
		headerMap.put(vo, "XX��ѧ����Ϣ��������걨����");
		
		subList.add(headerMap);

		//�ڶ���
		headerMap = new HashMap<ReportInfoStyle, String>();
		vo = new ReportInfoStyle();
		vo.setAlignment(AlignmentType.rigth);
		vo.setColNums("a");
//		vo.setFontSize(Short.parseShort("10"));
		headerMap.put(vo, "��༶");
		
		vo = new ReportInfoStyle();
		vo.setAlignment(AlignmentType.rigth);
		vo.setColNums("c");
		headerMap.put(vo, "");
		vo = new ReportInfoStyle();
		vo.setAlignment(AlignmentType.rigth);
		vo.setColNums("d");
		headerMap.put(vo, "");
		
		
		vo = new ReportInfoStyle();
		vo.setAlignment(AlignmentType.left);
		vo.setColNums("b");
		headerMap.put(vo, "������");
		
		vo = new ReportInfoStyle();
		vo.setAlignment(AlignmentType.left);
		vo.setColNums("e");
		headerMap.put(vo, "�����");
		
		vo = new ReportInfoStyle();
		vo.setAlignment(AlignmentType.left);
		vo.setColNums("f");
		headerMap.put(vo, "2013-3-3");
		
		subList.add(headerMap);
		
		//������--����
		vo = new ReportInfoStyle();
		vo.setColNums("a-f");
		headerMap.put(vo, "");
		subList.add(headerMap);
		
		list.add(subList);
		
		ExportUtils4 util = new ExportUtils4("test hh");
		HSSFWorkbook exportExcel = util.exportExcel(list);
		
		OutputStream io = null;
		try {
			 io = new FileOutputStream("src/org/bitbudget/vegetable/office/poi/test/files/exportUtils4.xls");
			exportExcel.write(io);
			io.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if(io != null){
				try {
					io.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException e){
			e.printStackTrace();
			if(io != null){
				try {
					io.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
