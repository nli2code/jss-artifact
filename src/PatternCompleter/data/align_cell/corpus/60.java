package com.kiouri;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Utils {

	public static void addcr(XWPFRun run, int count){
		for (int i = 0; i < count; i++){
			run.addCarriageReturn();
		}		
	}
	
	public static void header0(XWPFDocument document, String text){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragrapRun = paragraph.createRun();
        paragrapRun.setBold(true);
        paragrapRun.setFontSize(14);
        paragrapRun.setText(text);
        paragrapRun.addCarriageReturn();        
	}
	
	public static void header1(XWPFDocument document, String text){
	    XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragrapRun = paragraph.createRun();
        paragrapRun.setBold(true);
        paragrapRun.setText(text);
	}

	public static void plain(XWPFDocument document, String text){
		plainCursiv(document,false, text);
	}
	
	public static void plainCursiv(XWPFDocument document, boolean isItalic, String text){
	    XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun paragrapRun = paragraph.createRun();
        if (isItalic){
        	paragrapRun.setItalic(true);
        }
        paragraph.setIndentationFirstLine(10);
        paragrapRun.setText(text);
	}


	public static void addPageBreak(XWPFDocument document){
	    XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragrapRun = paragraph.createRun();
	    paragrapRun.addBreak(BreakType.PAGE);
	}    
}
