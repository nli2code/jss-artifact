package resumeBuilder.storage.sections;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Command extends MajorSection {
	private String command;

	public Command(String command) {
		super();
		this.command = command;
	}

	@Override
	public void addSectionToWordDocument(XWPFDocument document) {
		XWPFParagraph blankLine = document.createParagraph();

		XWPFParagraph skillparagraph = document.createParagraph();
		XWPFRun skillrun = skillparagraph.createRun();
		skillrun.setFontFamily("Times New Roman");
		skillrun.setBold(true);
		skillrun.setFontSize(20);
		skillrun.setText(command);
		skillparagraph.setSpacingBefore(0);
		skillparagraph.setSpacingAfter(0);
		skillparagraph.setAlignment(ParagraphAlignment.LEFT);
	}

}
