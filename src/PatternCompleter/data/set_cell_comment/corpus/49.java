package wondermilk.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import wondermilk.Constants;
import wondermilk.entity.DeliveryOrder;
import wondermilk.entity.DeliveryOrderDetail;
import wondermilk.utils.IdGenerator;
import wondermilk.utils.WeekUtils;

public class ExcelWriterImpl implements IExcelWriter {

	public void write(List<DeliveryOrder> deliveryOrders, Date generateDate)
			throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
				Constants.TEMPLATE_FILE_PATH));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFPatriarch p = sheet.createDrawingPatriarch();
		int vernier = Constants.TEMPLATE_FILE_IGNORE_LINE;

		for (Iterator<DeliveryOrder> it = deliveryOrders.iterator(); it
				.hasNext();) {
			DeliveryOrder deliveryOrder = it.next();
			HSSFRow row = sheet.createRow(vernier);

			row.createCell((short) 0).setCellValue(
					deliveryOrder.getSalesOrderNo());
			String deliveryOrderNo = IdGenerator.getInstance().genDaySequenceId(generateDate);
			deliveryOrderNo = "DO-"+deliveryOrderNo;
			row.createCell((short) 1).setCellValue(deliveryOrderNo);
			row.createCell((short) 2).setCellValue(
					deliveryOrder.getCustomer().getAddress());
			row.createCell((short) 3).setCellValue(
					deliveryOrder.getCustomer().getContactPerson());
			row.createCell((short) 4).setCellValue(
					deliveryOrder.getCustomer().getTelephone());
			// 白
			HSSFCell cellWhite = row.createCell((short) 5);
			cellWhite.setCellValue(findQuantity(deliveryOrder,
					Constants.PRODUCT_NO_WHITE));
			cellWhite.setCellComment(getCellComment(p, String
					.valueOf(findUnitPrice(deliveryOrder,
							Constants.PRODUCT_NO_WHITE))));
			// 黑
			HSSFCell cellBlack = row.createCell((short) 6);
			cellBlack.setCellValue(findQuantity(deliveryOrder,
					Constants.PRODUCT_NO_BLACK));
			cellBlack.setCellComment(getCellComment(p, String
					.valueOf(findUnitPrice(deliveryOrder,
							Constants.PRODUCT_NO_BLACK))));
			// 绿
			HSSFCell cellGreen = row.createCell((short) 7);
			cellGreen.setCellValue(findQuantity(deliveryOrder,
					Constants.PRODUCT_NO_GREEN));
			cellGreen.setCellComment(getCellComment(p, String
					.valueOf(findUnitPrice(deliveryOrder,
							Constants.PRODUCT_NO_GREEN))));
			// 灰色
			HSSFCell cellGrey = row.createCell((short) 8);
			cellGrey.setCellValue(findQuantity(deliveryOrder,
					Constants.PRODUCT_NO_GREY));
			cellGrey.setCellComment(getCellComment(p, String
					.valueOf(findUnitPrice(deliveryOrder,
							Constants.PRODUCT_NO_GREY))));
			// 布丁
			HSSFCell cellBuDing = row.createCell((short) 9);
			cellBuDing.setCellValue(findQuantity(deliveryOrder,
			Constants.PRODUCT_NO_BUDING));
			cellBuDing.setCellComment(getCellComment(p, String.valueOf(findUnitPrice(deliveryOrder,
										Constants.PRODUCT_NO_BUDING))));

			row.createCell((short) 10).setCellValue(
					deliveryOrder.getTotalMoney());
			
			row.createCell((short) 11).setCellValue(
					deliveryOrder.getPayType());
			
			row.createCell((short) 12).setCellValue(
					deliveryOrder.getDistributor());
			
			row.createCell((short) 13).setCellValue(
					deliveryOrder.getRemarks());
//			row.createCell((short) 11).setCellValue(deliveryOrder.getMilkman());

			vernier++;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(generateDate) + "("
				+ WeekUtils.chineseDayForWeek(generateDate) + ")";
		FileOutputStream fileOut = new FileOutputStream(
				Constants.TARGET_FILE_PATH + "送货单" + dateStr + ".xls");
		wb.setSheetName(0, "送货单" + dateStr);

		HSSFCell titleCell = sheet.getRow(0).getCell(0);
		String title = titleCell.getStringCellValue();
		title = title + dateStr;
		titleCell.setCellValue(title);
		wb.write(fileOut);
		fileOut.close();

	}

	private int findQuantity(DeliveryOrder deliveryOrder, String productNo) {
		int quantity = 0;
		for (DeliveryOrderDetail deliveryOrderDetail : deliveryOrder
				.getDetails()) {
			String pNo = deliveryOrderDetail.getProduct().getProductNo();
			if (pNo.equals(productNo)) {
				quantity = deliveryOrderDetail.getQuantity();
			}
		}
		return quantity;
	}

	private float findUnitPrice(DeliveryOrder deliveryOrder, String productNo) {
		float unitPrice = 0;
		for (DeliveryOrderDetail deliveryOrderDetail : deliveryOrder
				.getDetails()) {
			String pNo = deliveryOrderDetail.getProduct().getProductNo();
			if (pNo.equals(productNo)) {
				unitPrice = deliveryOrderDetail.getUnitPrice();
			}
		}
		return unitPrice;
	}

	private HSSFComment getCellComment(HSSFPatriarch p, String content) {
		HSSFComment comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0,
				(short) 3, 3, (short) 5, 6));

		comment.setString(new HSSFRichTextString(content));
		return comment;

	}

}
