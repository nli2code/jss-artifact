/*
 * ActivityInfo
 * Copyright (C) 2009-2013 UNICEF
 * Copyright (C) 2014-2018 BeDataDriven Groep B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.activityinfo.server.report.renderer.excel;

import org.activityinfo.legacy.shared.reports.content.FilterDescription;
import org.activityinfo.legacy.shared.reports.model.ReportElement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.util.List;
import java.util.logging.Logger;

public abstract class BaseExcelRenderer<ElementT extends ReportElement> {

    private static final int MAX_CELL_CONTENT_LENGTH = 32700;
    protected final ElementT element;
    protected final Workbook book;
    protected final Sheet sheet;
    protected final CreationHelper factory;

    private static final Logger LOGGER = Logger.getLogger(BaseExcelRenderer.class.getName());

    protected int rowIndex;

    public BaseExcelRenderer(Workbook book, ElementT element) {

        this.element = element;
        this.book = book;
        this.factory = book.getCreationHelper();
        this.sheet = book.createSheet(composeSheetName());

        /* Create title line */

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(factory.createRichTextString(element.getTitle()));

        /* Create filter descriptors */

        List<FilterDescription> descs = generateFilterDescriptions();

        rowIndex = 2;

        for (FilterDescription desc : descs) {

            Row filterRow = sheet.createRow(rowIndex++);
            Cell filterCell = filterRow.createCell(0);

            String label = desc.joinLabels(", ");
            if (label.length() > MAX_CELL_CONTENT_LENGTH) {
                LOGGER.severe("Huge filter label string: " + label);
                label = label.substring(0, MAX_CELL_CONTENT_LENGTH) + "...";
            }
            filterCell.setCellValue(factory.createRichTextString(label));
        }

        rowIndex++;

        generate();
    }

    public abstract List<FilterDescription> generateFilterDescriptions();

    public String composeSheetName() {
        if (element.getSheetTitle() != null) {
            return WorkbookUtil.createSafeSheetName(element.getSheetTitle());
        } else if (element.getTitle() != null) {
            return WorkbookUtil.createSafeSheetName(element.getTitle());
        } else {
            return WorkbookUtil.createSafeSheetName("Sheet" + (book.getNumberOfSheets() + 1));
        }
    }

    public void generate() {

    }

    protected Font createBaseFont() {
        Font font = book.createFont();

        return font;
    }

}
