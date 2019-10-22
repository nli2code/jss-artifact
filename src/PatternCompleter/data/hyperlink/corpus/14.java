package liu.poi.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;

public class XCreationHelper {

	public static void main(String[] args) {
		
		Workbook wb = new HSSFWorkbook();
		
		CreationHelper creationHelper = wb.getCreationHelper();
		
		
		ClientAnchor clientAnchor = creationHelper.createClientAnchor();
		
		DataFormat dataFormat = creationHelper.createDataFormat();
		
		FormulaEvaluator formulaEvaluator = creationHelper.createFormulaEvaluator();
		
		Hyperlink hyperlink = creationHelper.createHyperlink(Hyperlink.LINK_URL);
		
		RichTextString richTextString = creationHelper.createRichTextString("");
		
	}
}
