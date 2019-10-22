package poi;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class XWPFSample {

	public static void main(String[] args) throws Exception {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph header = doc.createParagraph();
		header.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = header.createRun();
		run.setText("�T���v������");
		
		doc.write(new FileOutputStream("sample1.docx"));
	}

}
