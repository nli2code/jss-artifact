package ht.xporter.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

class Date implements Style {
	private CellStyle dateStyle;

	public Date(PoiCase _case) {
		CreationHelper helper = _case.creationHelper();
		dateStyle = _case.cellStyle();
		dateStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		if (!"".equals(_case.pattern())) {
			dateStyle.setDataFormat(helper.createDataFormat().getFormat(_case.pattern()));
		}

		Border.fill(dateStyle, _case.border());
	}

	public CellStyle get() {
		return dateStyle;
	}
}