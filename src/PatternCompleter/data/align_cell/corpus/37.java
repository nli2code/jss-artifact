package wordtopdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
/**
 * ��ʽ��Word�ı�
 * @author dd
 *
 */
public class WordTemplateUtils {
	// ����Word�����
	private static FileOutputStream fos;

	public static void createWord(File destFile, String content) throws IOException {

		// ��ʼ�������
		fos = new FileOutputStream(destFile);
		XWPFDocument doc = new XWPFDocument();
        //���ı������д���
		String[] contents = content.split("\n");
		for (int i = 0; i < contents.length; i++) {
			// ��������
			XWPFParagraph p1 = doc.createParagraph();
			if (i == 0 || (i == 1 && contents[i].startsWith("��"))) {
				p1.setAlignment(ParagraphAlignment.CENTER);
			} else {
				p1.setAlignment(ParagraphAlignment.LEFT);
			}

			XWPFRun r1 = p1.createRun();

			if (i == 0) {
				r1.setBold(true);
				r1.setFontSize(20);
			} else {
				r1.setFontSize(12);
				r1.setBold(false);
			}

			r1.setText(contents[i]);
			r1.setFontFamily("΢���ź�");
		}
		
		doc.write(fos);
		fos.close();
	}

}
