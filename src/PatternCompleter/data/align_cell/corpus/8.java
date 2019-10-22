package sample;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ClientWriter {
    private String name;

    ClientWriter(String name) {
        this.name = name;
    }

    public void createClientParagraph(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run = paragraph.createRun();

        run.setText("INWESTOR");
        run.addBreak();
        run.setText(name);
        run.addBreak();
    }
}
