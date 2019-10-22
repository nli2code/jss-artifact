import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Feuil1 {
	
	protected static void Validation_ROS(String filePath) {
		try {
		FileInputStream file = new FileInputStream(new File(filePath));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet("Mesures");
		int col=0, ligne=0,num=1;
		int decalagel=4,decalagec=6;
		double valeur=0.0, valeur2=0.0, resultat=0.0;
		String buffer;
		for(Row row : sheet) {          
			for(Cell cell : row) {
		        switch (cell.getCellType()) {
		        	case Cell.CELL_TYPE_STRING:
		        		if(cell.getStringCellValue().contains("Bande 900"))
		        		{
		        			HSSFCellStyle style = workbook.createCellStyle();
		        			num =1;
		        			style.setFillForegroundColor(HSSFColor.RED.index);
			        		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        		cell.setCellStyle(style);
			        		col=cell.getColumnIndex();
			        		ligne=cell.getRowIndex();
			        		ligne=ligne+decalagel;
			        		col=col+decalagec;
			        		row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure GSM900");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    }
			    		    else
			    		    {
			    		    	System.out.println("Champs non renseigné sur cette Techno");
			    		    }
			    		    ligne=ligne-1;
			    		    col=col+8;
			    		    num++;
			    		    row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Valeur :"+valeur);
			    		    System.out.println("Mesure GSM900");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    }
			    		    ligne=ligne-1;
			    		    col=col+8;
			    		    num++;
			    		    row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure GSM900");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    }
		        		}
		        		else if(cell.getStringCellValue().contains("Bande 1800"))
		        		{
		        			HSSFCellStyle style = workbook.createCellStyle();
		        			num =1;
		        			style.setFillForegroundColor(HSSFColor.WHITE.index);
			        		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        		cell.setCellStyle(style);
			        		col=cell.getColumnIndex();
			        		ligne=cell.getRowIndex();
			        		ligne=ligne+decalagel;
			        		col=col+decalagec;
			        		row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure GSM1800");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    	ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure GSM1800");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
				    		    ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure GSM1800");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
			    		    }
			    		    else
			    		    {
			    		    	System.out.println("Champs non renseigné sur cette Techno");
			    		    }
		        		}
		        		else if(cell.getStringCellValue().contains("Bande UMTS 900"))
		        		{
		        			HSSFCellStyle style = workbook.createCellStyle();
		        			num=1;
		        			style.setFillForegroundColor(HSSFColor.GREEN.index);
			        		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        		cell.setCellStyle(style);
			        		col=cell.getColumnIndex();
			        		ligne=cell.getRowIndex();
			        		ligne=ligne+decalagel;
			        		col=col+decalagec;
			        		row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure UMTS900");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    	ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure UMTS900");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
				    		    ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure UMTS900");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
			    		    }
			    		    else
			    		    {
			    		    	System.out.println("Champs non renseigné sur cette Techno");
			    		    }
		        		}
		        		else if(cell.getStringCellValue().contains("Bande UMTS 2200"))
		        		{
		        			HSSFCellStyle style = workbook.createCellStyle();
		        			num=1;
		        			style.setFillForegroundColor(HSSFColor.BLUE.index);
			        		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        		cell.setCellStyle(style);
			        		col=cell.getColumnIndex();
			        		ligne=cell.getRowIndex();
			        		ligne=ligne+decalagel;
			        		col=col+decalagec;
			        		row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure UMTS2200");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    	ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure UMTS2200");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
				    		    ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure UMTS2200");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
			    		    }
			    		    else
			    		    {
			    		    	System.out.println("Champs non renseigné sur cette Techno");
			    		    }
		        		}
		        		else if(cell.getStringCellValue().contains("Bande LTE 800"))
		        		{
		        			HSSFCellStyle style = workbook.createCellStyle();
		        			num=1;
		        			style.setFillForegroundColor(HSSFColor.VIOLET.index);
			        		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        		cell.setCellStyle(style);
			        		col=cell.getColumnIndex();
			        		ligne=cell.getRowIndex();
			        		ligne=ligne+decalagel;
			        		col=col+decalagec;
			        		row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure LTE800");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élevé");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    	ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure LTE800");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
				    		    ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure LTE800");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
			    		    }
			    		    else
			    		    {
			    		    	System.out.println("Champs non renseign sur cette Techno");
			    		    }
		        		}
		        		else if(cell.getStringCellValue().contains("Bande LTE 2600"))
		        		{
		        			HSSFCellStyle style = workbook.createCellStyle();
		        			num=1;
		        			style.setFillForegroundColor(HSSFColor.YELLOW.index);
			        		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        		cell.setCellStyle(style);
			        		col=cell.getColumnIndex();
			        		ligne=cell.getRowIndex();
			        		ligne=ligne+decalagel;
			        		col=col+decalagec;
			        		row = sheet.getRow(ligne);
			    		    cell = row.getCell(col);
			    		    switch(cell.getCellType())
			    		    {
			    		    case Cell.CELL_TYPE_NUMERIC:
			    		    	valeur = cell.getNumericCellValue();
			    		    	break;
			    		    case Cell.CELL_TYPE_STRING:
			    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
			    		    	if(!buffer.isEmpty())
			    		    		valeur = Double.valueOf(buffer);
			    		    	else
			    		    		valeur=0;
			    		    	break;
			    		    default:
			    		    	valeur=0;
			    		    	break;
			    		    }
			    		    System.out.println("Mesure LTE2600");
			    		    if(valeur!=0)
			    		    {
			    		    	if(valeur<1.5)
			    		    	{
						    		 style = workbook.createCellStyle();
						    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
						        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	 cell.setCellStyle(style);
			    		    		 ligne=ligne+1;
						    		 row = sheet.getRow(ligne);
						    		 cell = row.getCell(col);
						    		 switch(cell.getCellType())
						    		    {
						    		    case Cell.CELL_TYPE_NUMERIC:
						    		    	valeur2 = cell.getNumericCellValue();
						    		    	break;
						    		    case Cell.CELL_TYPE_STRING:
						    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
						    		    	valeur2 = Double.valueOf(buffer);
						    		    	break;
						    		    }
						    		 if(valeur2<1.5)
						    		 {
						    			 style = workbook.createCellStyle();
						    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
						    			 resultat = valeur2-valeur;
						    			 if(Math.abs(resultat)<0.5)
						    			 {
						    				 System.out.println("Secteur "+num+" OK");
						    			 }
						    			 else
						    			 {
						    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
						    			 }
						    		 }
						    		 else
						    		 {
						    		    System.out.println("ROS voie B trop élevé");
						    		    style = workbook.createCellStyle();
									    style.setFillForegroundColor(HSSFColor.RED.index);
									    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									    cell.setCellStyle(style);
						    		 }
			    		    	}
			    		    	else
			    		    	{
			    		    		System.out.println("ROS voie A trop élev");
			    		    		style = workbook.createCellStyle();
						    		style.setFillForegroundColor(HSSFColor.RED.index);
						        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        	cell.setCellStyle(style);
			    		    	}
			    		    	ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure LTE2600");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
				    		    ligne=ligne-1;
				    		    col=col+8;
				    		    num++;
				    		    row = sheet.getRow(ligne);
				    		    cell = row.getCell(col);
				    		    switch(cell.getCellType())
				    		    {
				    		    case Cell.CELL_TYPE_NUMERIC:
				    		    	valeur = cell.getNumericCellValue();
				    		    	break;
				    		    case Cell.CELL_TYPE_STRING:
				    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
				    		    	if(!buffer.isEmpty())
				    		    		valeur = Double.valueOf(buffer);
				    		    	else
				    		    		valeur=0;
				    		    	break;
				    		    default:
				    		    	valeur=0;
				    		    	break;
				    		    }
				    		    System.out.println("Mesure LTE2600");
				    		    if(valeur!=0)
				    		    {
				    		    	if(valeur<1.5)
				    		    	{
							    		 style = workbook.createCellStyle();
							    		 style.setFillForegroundColor(HSSFColor.GREEN.index);
							        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	 cell.setCellStyle(style);
				    		    		 ligne=ligne+1;
							    		 row = sheet.getRow(ligne);
							    		 cell = row.getCell(col);
							    		 switch(cell.getCellType())
							    		    {
							    		    case Cell.CELL_TYPE_NUMERIC:
							    		    	valeur2 = cell.getNumericCellValue();
							    		    	break;
							    		    case Cell.CELL_TYPE_STRING:
							    		    	buffer=cell.getStringCellValue().replaceAll(",", ".");
							    		    	valeur2 = Double.valueOf(buffer);
							    		    	break;
							    		    }
							    		 if(valeur2<1.5)
							    		 {
							    			 style = workbook.createCellStyle();
							    			 style.setFillForegroundColor(HSSFColor.GREEN.index);
								        	 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								        	 cell.setCellStyle(style);
							    			 resultat = valeur2-valeur;
							    			 if(Math.abs(resultat)<0.5)
							    			 {
							    				 System.out.println("Secteur "+num+" OK");
							    			 }
							    			 else
							    			 {
							    				 System.out.println("Ecart entre voies supérieur à 0.5dB");
							    			 }
							    		 }
							    		 else
							    		 {
							    		    System.out.println("ROS voie B trop élevé");
							    		    style = workbook.createCellStyle();
										    style.setFillForegroundColor(HSSFColor.RED.index);
										    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
										    cell.setCellStyle(style);
							    		 }
				    		    	}
				    		    	else
				    		    	{
				    		    		System.out.println("ROS voie A trop élevé");
				    		    		style = workbook.createCellStyle();
							    		style.setFillForegroundColor(HSSFColor.RED.index);
							        	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							        	cell.setCellStyle(style);
				    		    	}
				    		    }
			    		    }
			    		    else
			    		    {
			    		    	System.out.println("Champs non renseigné sur cette Techno");
			    		    }
		        		}
		        	    break;
		        	default:
		        		break;
		        	}
		        }
		    }
		 file.close();
		 try {
			    FileOutputStream out = new FileOutputStream(new File(filePath));
			    workbook.write(out);
			    out.close();
			    System.out.println("Fichier excel FN5 remplit ...");
			     
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}

