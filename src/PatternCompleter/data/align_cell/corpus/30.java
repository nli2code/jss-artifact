package polischukovik.msformating;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import polischukovik.domain.Test;
import polischukovik.msformating.interfaces.DocumentTitleComposer;

public class SimpleTitleComposer implements DocumentTitleComposer {

	@Override
	public void addDocumentTite(Test test, XWPFDocument doc) {
		XWPFParagraph p = doc.createParagraph();
		p.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r = p.createRun();
		r.addBreak();
		r.addBreak();
		r.addBreak();
		r.addBreak();
		r.setText(test.getCaption());		
		r.addBreak();		
	}

}
