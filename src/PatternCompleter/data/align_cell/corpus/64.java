package document_generation.LawyersLetter.Paragraphs;

import document_generation.LawyersLetter.LLParagraph;
import document_generation.LawyersLetter.Codes.ParaCode;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * Created by Yale Wang
 */
public class QuoteParagraph extends LLParagraph {

    public QuoteParagraph(XWPFDocument doc) {

        super(doc);
        this.setParaType(ParaCode.QUOTE);
        XWPFParagraph p = this.getXwpfParagraph();
        p.setIndentFromLeft(650);
        p.setIndentFromRight(550);
        p.setAlignment(ParagraphAlignment.BOTH);
    }
}
