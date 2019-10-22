package mmb.product.autoImport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 
 * @author huangxiulan
 * 
 * <p>
 * create_datetime:2012-04-16
 * </p>
 * 
 * <p>
 * description:使用POI写excel
 * </p>
 */

public class ProductExcelDeal {
	
	
	public  String  excelDeal(List excelList,String fileName,String filePath){
		// 创建新的Excel 工作簿   
		HSSFWorkbook workbook = new HSSFWorkbook();   
		// 设置字体   
		HSSFFont font = workbook.createFont();   
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);   
		font.setFontHeightInPoints((short) 14); 

		// 设置样式   （excel表头的颜色）
		HSSFPalette palette = workbook.getCustomPalette();  //wb HSSFWorkbook对象
		palette.setColorAtIndex((short)9, (byte)253, (byte)233, (byte)217);
		HSSFCellStyle cellStyle = workbook.createCellStyle();   
		cellStyle.setFont(font); 
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);   
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor((short)9);

		//设置  正常显示的 父商品跟子商品的样式
		HSSFFont font1 = workbook.createFont();   
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);   
		font1.setFontHeightInPoints((short)11);  
		HSSFCellStyle cellStyle1 = workbook.createCellStyle();   
		cellStyle1.setFont(font1); 
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER); 

		//设置  错误时的样式
		HSSFCellStyle cellStyle2 = workbook.createCellStyle(); 
		HSSFFont font2 = workbook.createFont(); 
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);   
		font2.setFontHeightInPoints((short)11);  
		font2.setColor(HSSFFont.COLOR_RED);  
		cellStyle2.setFont(font2); 
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); 

		HSSFCellStyle cellStyle3 = workbook.createCellStyle(); 
		cellStyle3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle3.setFillForegroundColor(HSSFColor.RED.index);

		// 在Excel工作簿中建一工作表，其名为缺省值   
		// 如要新建一名为"月报表"的工作表，其语句为：   
		HSSFSheet sheet = workbook.createSheet("批量导入商品表");   
		sheet.setDefaultColumnWidth(20);

		//设置表头   开始
		String[] values=new String[]{"异常","分类","旧分类","品牌","是否是自主品牌","显示顺序","显示名称","型号(款式)","内部名称(主)","内部名称(子)"};
		String[] values1 = new String[]{"采购价","市场价","买卖宝价","商品编号","条码","库存标准(广州)","警戒线(广州)","广州补货所需时间","供应商"};
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0,1,0,0);
		sheet.addMergedRegion(cellRangeAddress);  
		HSSFRow row = sheet.createRow(0);   
		// 在索引0的位置创建单元格（左上端）   
		HSSFCell cell = row.createCell(0);   
				// 定义单元格为字符串类型   
		cell.setCellStyle(cellStyle);   
				// 在单元格中输入一些内容   
		cell.setCellValue("异常");    



		for(int i = 1 ; i < values.length ; i++){
					cellRangeAddress = new CellRangeAddress(0, 1, i, i); 
					sheet.addMergedRegion(cellRangeAddress);    
					cell = row.createCell(i);
					cell.setCellStyle(cellStyle);   
					cell.setCellValue(values[i]);
			}


		cellRangeAddress = new CellRangeAddress(0,0,10,11);
					sheet.addMergedRegion(cellRangeAddress);    
					cell = row.createCell(10);
					cell.setCellStyle(cellStyle);   
					cell.setCellValue("颜色");
					
		cellRangeAddress = new CellRangeAddress(0,0,12,13);
		sheet.addMergedRegion(cellRangeAddress);    
					cell = row.createCell(12);
					cell.setCellStyle(cellStyle);   
					cell.setCellValue("尺寸");
		cellRangeAddress = new CellRangeAddress(0,0,14,15);
		sheet.addMergedRegion(cellRangeAddress);    
					cell = row.createCell(14);
					cell.setCellStyle(cellStyle);   
					cell.setCellValue("补充项");
					
		for(int i = 0 ; i<values1.length ; i++){
					cellRangeAddress = new CellRangeAddress(0, 1, 16+i, 16+i); 
					sheet.addMergedRegion(cellRangeAddress);    
					cell = row.createCell(16+i);
					cell.setCellValue(values1[i]);
					cell.setCellStyle(cellStyle); 
		}	
					
		row = sheet.createRow(1);  
		String[] values2 = new String[]{"选择颜色","手填颜色","选择尺码","手填尺码","选择值","手填值"};
		for(int i = 0 ;i < values2.length ; i++){
					cell = row.createCell(10+i);
					cell.setCellStyle(cellStyle);   
					cell.setCellValue(values2[i]);
		}
			
		//设置批注信息
		HSSFPatriarch p=sheet.createDrawingPatriarch();
		HSSFComment comment = null;
		

		int childCount = 2;
		int parentCount = 2;
		for(int i = 0 ; i < excelList.size(); i ++) {
			ProductInfoItem info = (ProductInfoItem)excelList.get(i);
  			List products = (List)info.getProductList();
			row = sheet.createRow(parentCount); 
			//设置异常：
//			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,0,0);
//			sheet.addMergedRegion(cellRangeAddress);    
//			cell = row.createCell(0);
//			if(info.isStatus() == false){
//				cell.setCellStyle(cellStyle3); 
//				cell.setCellValue("ERROR");	
//			}else{
//				cell.setCellStyle(cellStyle1);   
//			}
  			//设置新分类的值
  			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,1,1);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(1);
			if(info.isNewCatalogStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getNewCatalogTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getNewCatalogTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
			cell.setCellValue(info.getNewCatalogName());
			
			//设置旧分类的值：
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,2,2);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(2);
			if(info.isOldCatalogStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getOldCatalogTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getOldCatalogTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
			cell.setCellValue(info.getOldCatalogName());
			
			//设置品牌的值：
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,3,3);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(3);
			if(info.isBrandStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getBrandTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getBrandTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
		    cell.setCellValue(info.getBrandName());
			
			//设置是否是自主品牌的值、
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,4,4);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(4);
			if(info.isBusinessTypeStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getBusinessTypeTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getBusinessTypeTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
		    cell.setCellValue(info.getIs_for_zizhuName());
			
			//设置显示排序的值：
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,5,5);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(5);
			if(info.isRankStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getRankTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getRankTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
			cell.setCellValue(info.getRankName());
			
			//设置显示名称的值：
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,6,6);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(6);
			if(info.isDisplayNameStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getDisplayNameTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getDisplayNameTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
			cell.setCellValue(info.getDisplayName());
			
			//设置型号的值：
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,7,7);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(7);
			if(info.isModelStatus() == false){
				cell.setCellStyle(cellStyle2); 
					if(info.getModelTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getModelTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
						}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
			cell.setCellValue(info.getModel());
			
			//设置内部名称的值：
			cellRangeAddress = new CellRangeAddress(parentCount,parentCount+products.size()-1,8,8);
			sheet.addMergedRegion(cellRangeAddress);    
			cell = row.createCell(8);
			if(info.isNameStatus() == false){
				cell.setCellStyle(cellStyle2); 
				if(info.getNameTip() != null){
					comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
					comment.setString(new HSSFRichTextString(info.getNameTip()));
					comment.setAuthor("toad");
					cell.setCellComment(comment);
				}
			}else{
				cell.setCellStyle(cellStyle1);   
			}
			cell.setCellValue(info.getName());
			
			parentCount = parentCount + products.size();
			
			
			//显示子商品信息
			for(int k = 0 ; k < products.size();  k ++){
				ProductItem product = (ProductItem)products.get(k);
				if(k != 0){
					row = sheet.createRow(childCount); 
				}
				
				//对每一个有商品设置error：
				cell = row.createCell(0);
				if(info.isStatus() == false){
					cell.setCellStyle(cellStyle3); 
					cell.setCellValue("ERROR");	
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				
				//设置内部名称(子)
				cell = row.createCell(9);
				if(product.isProductNameStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getProductNameTip() != null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getProductNameTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getName());
				
				//设置选择颜色
				cell = row.createCell(10);
				if(product.isChoiceColorStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getChoiceColorTip() != null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getChoiceColorTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getChoiceColor());
				
				//设置手填颜色
				cell = row.createCell(11);
				if(product.isHandleColorStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getHandleColorTip() != null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getHandleColorTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}
				}else{
					cell.setCellStyle(cellStyle1);   
				}  
				cell.setCellValue(product.getHandleColor());
				
				
				//设置选择尺码：
				cell = row.createCell(12);
				if(product.isChoiceSizeStatus() == false){
					cell.setCellStyle(cellStyle2); 
					if(product.getChoiceSizeTip() != null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getChoiceSizeTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}  
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getChoiceSize());
				
				//设置手填尺码：
				cell = row.createCell(13);
				if(product.isHandleSizeStatus() == false){
					cell.setCellStyle(cellStyle2); 
					if(product.getHandleSizeTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getHandleSizeTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}    
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getHandleSize());
				
				//设置选择值：
				cell = row.createCell(14);
				if(product.isChoiceSupplyStatus() == false){
					cell.setCellStyle(cellStyle2); 
					if(product.getChoiceSupplyTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getChoiceSupplyTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}    
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getChoiceSupply());
				
				//设置手填值：
				cell = row.createCell(15);
				if(product.isHandleSupplyStatus() == false){
					cell.setCellStyle(cellStyle2); 
					if(product.getHandleSupplyTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getHandleSupplyTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}   
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getHandleSupply());
				
				//设置采购价：
				cell = row.createCell(16);
				if(product.isPrice3Status() == false){
					cell.setCellStyle(cellStyle2);
					if(product.getPrice3Tip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getPrice3Tip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}     
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getPrice3());
				
				//设置市场价：
				cell = row.createCell(17);
				if(product.isPrice2Status() == false){
					cell.setCellStyle(cellStyle2);  
					if(product.getPrice2Tip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getPrice2Tip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}  
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getPrice2());
				
				//设置mmb价：
				cell = row.createCell(18);
				if(product.isPriceStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getPriceTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getPriceTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					} 
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getPrice());
				
				
				//设置商品编号：
				cell = row.createCell(19);
				cell.setCellStyle(cellStyle1);   
				cell.setCellValue(product.getCode());
				
				//设置条形码：
				cell = row.createCell(20);
				if(product.isBarcodeStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getBarcodeTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getBarcodeTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					} 
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getBarcode());
				
				//设置库存标准：
				cell = row.createCell(21);
				if(product.isStockStandardGdStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getStockStandardGdTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getStockStandardGdTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					} 
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getStockStandardGd());
				
				//设置警戒线：
				cell = row.createCell(22);
				if(product.isStockLineGdStatus() == false){
					cell.setCellStyle(cellStyle2);  
					if(product.getStockLineGdTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getStockLineGdTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}  
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getStockLineGd());
				
				//设置补货时间：
				cell = row.createCell(23);
				if(product.isGdBuhuoTimeStatus() == false){
					cell.setCellStyle(cellStyle2);  
					if(product.getGdBuhuoTimeTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getGdBuhuoTimeTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}   
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getGdBuhuoTime());
				
				//设置供应商：
				cell = row.createCell(24);
				if(product.isProxyNameStatus() == false){
					cell.setCellStyle(cellStyle2);   
					if(product.getProxyNameTip()!= null){
						comment = p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
						comment.setString(new HSSFRichTextString(product.getProxyNameTip()));
						comment.setAuthor("toad");
						cell.setCellComment(comment);
					}   
				}else{
					cell.setCellStyle(cellStyle1);   
				}
				cell.setCellValue(product.getProxyName());
				childCount ++;
			}
		}
		
		String dirPath = filePath+fileName.substring(0,6)+"/";
		try{
			//先创建目录
			File path = new File(dirPath);
	    	if (!path.exists()) {
	    		path.mkdirs();
	    	}
	    	//在创建文件
			File file = new File(dirPath+fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			//向文件中写东西
			FileOutputStream fOut = new FileOutputStream(dirPath+fileName); 
			// 把相应的Excel 工作簿存盘   
			workbook.write(fOut);   
			fOut.flush();   
			// 操作结束，关闭文件   
			fOut.close();   
		}catch(Exception e){
			e.printStackTrace();
		}
		return dirPath+fileName;
	}
}
