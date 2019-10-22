package ht.xporter.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

class Number implements Style {
	private CellStyle numberStyle;

	public Number(PoiCase _case) {
		CreationHelper helper = _case.creationHelper();
		numberStyle = _case.cellStyle();
		numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		if (!"".equals(_case.pattern())) {
			numberStyle.setDataFormat(helper.createDataFormat().getFormat(_case.pattern()));
		}

		Border.fill(numberStyle, _case.border());
	}

	public CellStyle get() {
		return numberStyle;
	}
}