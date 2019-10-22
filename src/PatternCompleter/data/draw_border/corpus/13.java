package excelreports;

import org.apache.poi.ss.usermodel.CellStyle;

public interface Stylus {
    static Stylus mediumBorder = new MediumBorderStylus();

    void style(CellStyle style);

    static class MediumBorderStylus implements Stylus {
        @Override
        public void style(CellStyle style) {
            style.setBorderBottom(CellStyle.BORDER_MEDIUM);
            style.setBorderLeft(CellStyle.BORDER_MEDIUM);
            style.setBorderTop(CellStyle.BORDER_MEDIUM);
            style.setBorderRight(CellStyle.BORDER_MEDIUM);
        }
    }
}
