package document_generation.LawyersLetter.Paragraphs;

import document_generation.LawyersLetter.LLParagraph;
import document_generation.LawyersLetter.Codes.ParaCode;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Created by Yale Wang
 */
public class HeaderParagraph extends LLParagraph {

    public HeaderParagraph(XWPFDocument doc){
        super(doc);
        this.setParaType(ParaCode.HEAD);
        this.getXwpfParagraph().setAlignment(ParagraphAlignment.LEFT);
        this.setBold(true);
    }
}
