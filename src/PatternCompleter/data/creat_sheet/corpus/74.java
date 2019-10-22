package com.artisiou.hdr.analysis.tools;

import com.google.common.collect.ImmutableList;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Excel {
    public static Workbook makeWorkbook(ImmutableList<String> sheetsNames) throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(100);

        sheetsNames.forEach(sheetName -> wb.createSheet(WorkbookUtil.createSafeSheetName(sheetName)));

        return wb;
    }

    public static void write(Workbook wb, Path filePath) throws IOException {
        File f = new File(filePath.toString());
        f.createNewFile();
        FileOutputStream fileOut = new FileOutputStream(f);
        wb.write(fileOut);
        fileOut.close();
    }
}
