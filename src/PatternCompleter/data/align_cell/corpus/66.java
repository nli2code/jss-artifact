package document_generation.LawyersLetter.Paragraphs;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import document_generation.LawyersLetter.LLParagraph;
import document_generation.LawyersLetter.Codes.ParaCode;

public class QuoteBoldParagraph extends LLParagraph{

    public QuoteBoldParagraph(XWPFDocument doc) {

        super(doc);
        this.setParaType(ParaCode.QUOTE);
        XWPFParagraph p = this.getXwpfParagraph();
        p.setIndentFromLeft(650);
        p.setIndentFromRight(550);
        p.setAlignment(ParagraphAlignment.BOTH);
        this.setBold(true);
    }
}
