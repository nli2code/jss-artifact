package com.idsmanager.xsifter.service.business.member;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idsmanager.xsifter.domain.security.SecurityUtils;

public class MyModelExportor {

	private static final Logger LOG = LoggerFactory
			.getLogger(MyModelExportor.class);

	public void export(HttpServletResponse response) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("第1页");

		// 设置列的日期格式
		createDateFormat(workbook, sheet);

		// 性别下拉
		createSelect(sheet, 5, 5, new String[] { "男", "女" });
		// 学历
		createSelect(sheet, 8, 8, new String[] { "小学", "初中", "高中", "大专", "本科",
				"硕士", "博士" });
		// 政治面貌
		createSelect(sheet, 9, 9, new String[] { "群众", "团员", "党员" });
		// 达成面试意向
		createSelect(sheet, 12, 12, new String[] { "是", "否" });
		// 是否参加面试
		createSelect(sheet, 13, 13, new String[] { "是", "否" });
		// 是否同意入职
		createSelect(sheet, 15, 15, new String[] { "是", "否" });
		// 是否成功入职
		createSelect(sheet, 16, 16, new String[] { "是", "否" });
		// 是否有职位变化
		createSelect(sheet, 20, 20, new String[] { "是", "否" });
		// 是否正常离职
		createSelect(sheet, 21, 21, new String[] { "是", "否" });
		// 离职过程是否正常
		createSelect(sheet, 22, 22, new String[] { "是", "否" });
		// 离职后状态是否正常
		createSelect(sheet, 23, 23, new String[] { "是", "否" });

		HSSFRow row0 = sheet.createRow(0);
		createCell(sheet, row0);
		LOG.debug("{}| Download the member import Excel Template",
				SecurityUtils.username());
		writeWorkBook(workbook, response);

	}

	private void createDateFormat(HSSFWorkbook workbook, HSSFSheet sheet) {

		CellStyle style = workbook.createCellStyle();
		HSSFDataFormat dataFormat = workbook.createDataFormat();
		style.setDataFormat(dataFormat.getFormat("yyyy/m/d"));
		//List<String> list = HSSFDataFormat.getBuiltinFormats();
		sheet.setDefaultColumnStyle(6, style);
		sheet.setDefaultColumnStyle(10, style);
		sheet.setDefaultColumnStyle(14, style);
		sheet.setDefaultColumnStyle(17, style);
		sheet.setDefaultColumnStyle(18, style);

	}

	private void createSelect(HSSFSheet sheet, int firstCol, int lastCol,
			String[] list) {
		int lastRowIndex = SpreadsheetVersion.EXCEL97.getLastRowIndex();
		CellRangeAddressList addressList = new CellRangeAddressList(1,
				lastRowIndex, firstCol, lastCol);
		DVConstraint constraint = DVConstraint
				.createExplicitListConstraint(list);
		HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
				constraint);
		sheet.addValidationData(dataValidation);
	}

	private void createCell(HSSFSheet sheet, HSSFRow row0) {
		for (int i = 0; i < 24; i++) {
			sheet.setColumnWidth(i, (short) (40 * 150));
			HSSFCell cell0 = row0.createCell(i);
			setCellValue(cell0, i, sheet);
		}

	}

	private void setCellValue(HSSFCell cell, int i, HSSFSheet sheet) {

		switch (i) {
		case 0:
			cell.setCellValue("中文姓名");
			break;
		case 1:
			cell.setCellValue("英文姓名");
			break;
		case 2:
			cell.setCellValue("手机号");
			break;
		case 3:
			cell.setCellValue("身份证号");
			break;
		case 4:
			cell.setCellValue("邮箱");
			break;
		case 5:
			cell.setCellValue("性别");
			break;
		case 6:
			cell.setCellValue("出生日期");
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFComment comment = patriarch
					.createCellComment(new HSSFClientAnchor(7, 0, 8, 0,
							(short) 3, 3, (short) 5, 6));
			comment.setString(new HSSFRichTextString(
					"格式：年/月/日      如：2016/3/24"));
			comment.setAuthor("筛子网");
			cell.setCellComment(comment);
			break;
		case 7:
			cell.setCellValue("毕业院校");
			break;
		case 8:
			cell.setCellValue("学历");
			break;
		case 9:
			cell.setCellValue("政治面貌");
			break;
		case 10:
			cell.setCellValue("电话邀约时间");
			HSSFPatriarch patriarch1 = sheet.createDrawingPatriarch();
			HSSFComment comment1 = patriarch1
					.createCellComment(new HSSFClientAnchor(11, 0, 12, 0,
							(short) 3, 3, (short) 5, 6));
			comment1.setString(new HSSFRichTextString(
					"格式：年/月/日      如：2016/3/24"));
			comment1.setAuthor("筛子网");
			cell.setCellComment(comment1);
			break;
		case 11:
			cell.setCellValue("面试职位");
			break;
		case 12:
			cell.setCellValue("达成面试意向");
			break;
		case 13:
			cell.setCellValue("是否参加面试");
			break;
		case 14:
			cell.setCellValue("面试时间");
			HSSFPatriarch patriarch2 = sheet.createDrawingPatriarch();
			HSSFComment comment2 = patriarch2
					.createCellComment(new HSSFClientAnchor(14, 0, 15, 0,
							(short) 3, 3, (short) 5, 6));
			comment2.setString(new HSSFRichTextString(
					"格式：年/月/日      如：2016/3/24"));
			comment2.setAuthor("筛子网");
			cell.setCellComment(comment2);
			break;
		case 15:
			cell.setCellValue("是否同意入职");
			break;

		case 16:
			cell.setCellValue("是否成功入职");
			break;

		case 17:
			cell.setCellValue("入职时间");
			HSSFPatriarch patriarch3 = sheet.createDrawingPatriarch();
			HSSFComment comment3 = patriarch3
					.createCellComment(new HSSFClientAnchor(17, 0, 18, 0,
							(short) 3, 3, (short) 5, 6));
			comment3.setString(new HSSFRichTextString(
					"格式：年/月/日      如：2016/3/24"));
			comment3.setAuthor("筛子网");
			cell.setCellComment(comment3);
			break;
		case 18:
			cell.setCellValue("离职时间");
			HSSFPatriarch patriarch4 = sheet.createDrawingPatriarch();
			HSSFComment comment4 = patriarch4
					.createCellComment(new HSSFClientAnchor(18, 0, 19, 0,
							(short) 3, 3, (short) 5, 6));
			comment4.setString(new HSSFRichTextString(
					"格式：年/月/日      如：2016/3/24"));
			comment4.setAuthor("筛子网");
			cell.setCellComment(comment4);
			break;
		case 19:
			cell.setCellValue("离职时工作职位");
			break;
		case 20:
			cell.setCellValue("是否有职位变化");
			break;
		case 21:
			cell.setCellValue("是否正常离职");
			break;
		case 22:
			cell.setCellValue("离职过程是否正常");
			break;
		case 23:
			cell.setCellValue("离职后状态是否正常");
			break;

		default:
			break;
		}

	}

	private static void writeWorkBook(HSSFWorkbook workbook,
			HttpServletResponse response) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("member_template.xls").getBytes(), "iso-8859-1"));

		ServletOutputStream out = response.getOutputStream();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {

			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);

			byte[] buff = new byte[2048];
			int bytesRead;

			// read data in stream
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}

	}

}
