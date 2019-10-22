package ht.xporter.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

class Decimal implements Style {
	private CellStyle decimalFormat;

	public Decimal(PoiCase _case) {
		CreationHelper helper = _case.creationHelper();
		decimalFormat = _case.cellStyle();
		decimalFormat.setAlignment(CellStyle.ALIGN_RIGHT);
		decimalFormat.setDataFormat(helper.createDataFormat().getFormat(_case.pattern()));
		Border.fill(decimalFormat, _case.border());
	}

	public CellStyle get() {
		return decimalFormat;
	}
}