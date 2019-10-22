package document_generation.LawyersLetter.Paragraphs;

import document_generation.LawyersLetter.Codes.ParaCode;
import document_generation.LawyersLetter.LLParagraph;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Created by Yale Wang
 */
public class QATempMsgParagraph extends LLParagraph{

    public QATempMsgParagraph(XWPFDocument doc){
        super(doc);
        this.setParaType(ParaCode.HEAD);
        this.getXwpfParagraph().setAlignment(ParagraphAlignment.CENTER);
        this.setBold(true);
    }
}
