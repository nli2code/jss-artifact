package com.apachpoi.sample;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ApplyingBorder {

	public static void main(String[] args) throws Exception {
		XWPFDocument document = new XWPFDocument();
		FileOutputStream out = new FileOutputStream(new File(
				"applyingBorder.docx"));
		XWPFParagraph paragraph = document.createParagraph();

		paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);

		paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);

		paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);

		paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);

		XWPFRun run = paragraph.createRun();

		run.setText("At tutorialspoint.com, we strive hard to "
				+ "provide quality tutorials for self-learning "
				+ "purpose in the domains of Academics, Information "
				+ "Technology, Management and Computer Programming "
				+ "Languages.");
		
		document.write(out);
		out.close();
		System.out.println("applyingborder.docx written successully");
		
		

	}

}
