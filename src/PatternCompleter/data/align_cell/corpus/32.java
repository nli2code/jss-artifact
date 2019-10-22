package kkr.ktm.domains.excel.components.catalogstyles.poi;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;

import kkr.ktm.domains.excel.components.catalogstyles.CatalogStyles;
import kkr.ktm.domains.excel.components.exceladapter.TStyle;
import kkr.ktm.domains.excel.components.exceladapter.TWorkbook;
import kkr.ktm.domains.excel.components.exceladapter.poi.TStylePoi;
import kkr.ktm.domains.excel.components.exceladapter.poi.TWorkbookPoi;
import kkr.ktm.domains.excelpoi.style.Style;
import kkr.common.errors.BaseException;

public class CatalogStylesPoi extends CatalogStylesPoiFwk implements CatalogStyles {
	private static final Logger LOG = Logger.getLogger(CatalogStylesPoi.class);

	private Map<String, TStyle> stylesPoi;
	private TWorkbookPoi tWorkbookPoi;

	private void testOpened() {
		testConfigured();
		if (!isOpened()) {
			throw new IllegalStateException("CatalogStyle is not initialized");
		}
	}

	public boolean isOpened() {
		testConfigured();
		return tWorkbookPoi != null;
	}

	public void close() {
		testConfigured();
		if (stylesPoi != null) {
			stylesPoi.clear();
			stylesPoi = null;
		}
		tWorkbookPoi = null;
	}

	public void open(TWorkbook tWorkbook) {
		LOG.trace("BEGIN");
		try {
			testConfigured();

			tWorkbookPoi = (TWorkbookPoi) tWorkbook;

			stylesPoi = new HashMap<String, TStyle>();

			for (Map.Entry<String, Style> entry : styles.entrySet()) {
				String name = entry.getKey();
				Style style = entry.getValue();

				DataFormat dataFormat = tWorkbookPoi.getWorkbook().createDataFormat();
				CellStyle cellStyle = tWorkbookPoi.getWorkbook().createCellStyle();
				Font font = tWorkbookPoi.getWorkbook().createFont();

				modifyStyle(style, cellStyle, font);
				cellStyle.setFont(font);

				TStylePoi tStylePoi = new TStylePoi(cellStyle);

				stylesPoi.put(name, tStylePoi);
			}

			LOG.trace("OK");
		} finally {
			LOG.trace("END");
		}
	}

	private void modifyStyle(Style style, CellStyle cellStyle, Font font) {
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		if (style.getPoiAlignment() != null) {
			cellStyle.setAlignment(style.getPoiAlignment());
		}
		if (style.getPoiBackgroundColor() != null) {
			cellStyle.setFillForegroundColor(style.getPoiBackgroundColor().getIndex());
		}
		if (style.getPoiBoldweight() != null) {
			font.setBoldweight(style.getPoiBoldweight());
		}
		if (style.getPoiForegroundColor() != null) {
			font.setColor(style.getPoiForegroundColor().getIndex());
		}
	}

	public TStyle updateStyle(String styleName, TStyle tStyleFrom) throws BaseException {
		testOpened();

		if (styleName == null) {
			throw new IllegalArgumentException("Name of the style is null: " + styleName);
		}

		CellStyle cellStyle = tWorkbookPoi.getWorkbook().createCellStyle();
		Font font = tWorkbookPoi.getWorkbook().createFont();

		Style style = styles.get(styleName);

		if (style == null) {
			LOG.warn("Style not found: " + styleName);
		}

		TStylePoi tStyleFromPoi = (TStylePoi) tStyleFrom;
		cellStyle.cloneStyleFrom(tStyleFromPoi.getCellStyle());

		modifyStyle(style, cellStyle, font);

		TStylePoi tStylePoi = new TStylePoi(cellStyle);
		return tStylePoi;
	}

	public TStyle createStyle(String styleName) throws BaseException {
		testOpened();

		if (styleName == null) {
			throw new IllegalArgumentException("Name of the style is null: " + styleName);
		}

		TStyle retval = stylesPoi.get(styleName);

		if (retval == null) {
			LOG.warn("Style not found: " + styleName);
		}

		return retval;
	}
}
