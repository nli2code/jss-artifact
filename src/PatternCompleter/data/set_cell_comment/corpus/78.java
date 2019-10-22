package com.vaa25.gitsum;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.PullRequest;

/**
 * Created by vaa25 on 05.09.16.
 */
public final class Xlsx {

    private final Workbook workbook;
    private final Sheet sheet;

    public Xlsx() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();
    }

    private void createUser(
        String user, Map<PullRequest, Duration> durationByPullrequest, Map<Issue, Duration> durationByIssue,
        Map<LocalDate, Duration> issue) {
        final Row row =sheet.createRow(sheet.getLastRowNum() + 1);
        final Cell name = row.createCell(0, Cell.CELL_TYPE_STRING);
        name.setCellValue(user);
        for (Map.Entry<LocalDate, Duration> entry : issue.entrySet()) {
            final LocalDate date = entry.getKey();
            final Duration duration = entry.getValue();
            final int day = date.getDayOfMonth();
            final Cell cell = row.createCell(day, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(duration.toMinutes()/60.0);

            CreationHelper factory = sheet.getWorkbook().getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            // When the comment box is visible, have it show in a 1x3 space
            ClientAnchor anchor = factory.createClientAnchor();
            anchor.setCol1(cell.getColumnIndex());
            anchor.setCol2(cell.getColumnIndex()+1);
            anchor.setRow1(row.getRowNum());
            anchor.setRow2(row.getRowNum()+3);

            // Create the comment and set the text+author
            Comment comment = drawing.createCellComment(anchor);
            RichTextString str = factory.createRichTextString("Hello, World!");
            final Hyperlink hyperlink = factory.createHyperlink(Hyperlink.LINK_URL);

            comment.setString(str);
            comment.setAuthor("Apache POI");

            // Assign the comment to the cell
            cell.setCellComment(comment);

//            EscherContainerRecord container = new EscherContainerRecord();
//            NoteRecord record = new NoteRecord();
//            record.setColumn(cell.getColumnIndex());
//            record.setRow(row.getRowNum());
//            record.setFlags(NoteRecord.NOTE_VISIBLE);
//            TextObjectRecord text = new TextObjectRecord();
//            HSSFRichTextString string = new HSSFRichTextString("text");
//            text.setStr(string);
//            ObjRecord objRecord = new ObjRecord();
//            final SubRecord subRecord = SubRecord.createSubRecord()
//            objRecord.addSubRecord(subRecord);
//            Comment comment = new HSSFComment(container, null, record, text)
//            cell.setCellComment();
        }
    }

//    private Hyperlink hyperlink(Issue issue, Duration duration){
//
//    }

    private void createComment(){
        Workbook workbook = new HSSFWorkbook(); //or new HSSFWorkbook();

        CreationHelper factory = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet();

        Row row   = sheet.createRow(3);
        Cell cell = row.createCell(5);
        cell.setCellValue("F4");

        Drawing drawing = sheet.createDrawingPatriarch();

        // When the comment box is visible, have it show in a 1x3 space
        ClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(cell.getColumnIndex());
        anchor.setCol2(cell.getColumnIndex()+1);
        anchor.setRow1(row.getRowNum());
        anchor.setRow2(row.getRowNum()+3);

        // Create the comment and set the text+author
        Comment comment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString("Hello, World!");
        comment.setString(str);
        comment.setAuthor("Apache POI");

        // Assign the comment to the cell
        cell.setCellComment(comment);


    }

    private void saveWorkbook() throws IOException {
        String fname = "comment-xssf.xls";
        if(workbook instanceof HSSFWorkbook) fname += "x";
        FileOutputStream out = new FileOutputStream(fname);
        workbook.write(out);
        out.close();
    }

    private void createDays(final Map<LocalDate, Duration> issue) {
        final Row row =sheet.createRow(sheet.getLastRowNum() + 1);
        for (Map.Entry<LocalDate, Duration> entry : issue.entrySet()) {
            final LocalDate date = entry.getKey();
            final int day = date.getDayOfMonth();
            final Cell cell = row.createCell(day, Cell.CELL_TYPE_STRING);
            cell.setCellValue(date.toString());
        }
    }

    private Date date(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
