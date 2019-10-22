package ht.xporter.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

class Text implements Style {
	private CellStyle textStyle;

	public Text(PoiCase _case) {
		textStyle = _case.cellStyle();
		textStyle.setAlignment(CellStyle.ALIGN_LEFT);
		Border.fill(textStyle, _case.border());
	}

	public CellStyle get() {
		return textStyle;
	}
}