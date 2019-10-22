package trimatrix.reports.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import trimatrix.db.DAOLayer;
import trimatrix.db.SchedulesDetail;
import trimatrix.db.ZonesDefinition;
import trimatrix.reports.Report;
import trimatrix.services.ServiceLayer;
import trimatrix.services.TranslationService;
import trimatrix.ui.ScheduleUI;
import trimatrix.ui.ScheduleUI.ScheduleItem;
import trimatrix.utils.Constants;
import trimatrix.utils.Helper;
import trimatrix.utils.HelperTime;

public class CalendarOverview extends Report {
    private ServiceLayer serviceLayer;
    private DAOLayer daoLayer;
    private String header[] = { Helper.getLiteral("cw"), Helper.getLiteral("monday"), Helper.getLiteral("tuesday"), Helper.getLiteral("wednesday"), Helper.getLiteral("thursday"),
            Helper.getLiteral("friday"), Helper.getLiteral("saturday"), Helper.getLiteral("sunday") };
    private String header2[] = new String[8];
    private String detailHeader[] = {Helper.getLiteral("start"), Helper.getLiteral("duration"), Helper.getLiteral("type"), Helper.getLiteral("description"), Constants.EMPTY, Constants.EMPTY};
    private List<ScheduleItem> scheduleItems;

    private static final int rows = 5 + ScheduleUI.NUMBEROFBLOCKS;
    private static final int hours = ScheduleUI.NUMBEROFBLOCKS / 2;
    private static final int cols = 8;
    private static final int startingHour = ScheduleUI.STARTINGHOUR;
    private Locale locale;

    private String time[] = new String[hours];

    public CalendarOverview(ServiceLayer serviceLayer, DAOLayer daoLayer, Locale locale, Integer weekNumber, String[] day, List<ScheduleItem> scheduleItems) {
        this.serviceLayer = serviceLayer;
        this.daoLayer = daoLayer;
        this.locale = locale;
        header2[0] = weekNumber.toString();
        header2[1] = day[0];
        header2[2] = day[1];
        header2[3] = day[2];
        header2[4] = day[3];
        header2[5] = day[4];
        header2[6] = day[5];
        header2[7] = day[6];
        this.scheduleItems = scheduleItems;
        // build time column
        for (int i = 0; i < hours; i++) {
            time[i] = (startingHour + i) + ":00";
        }

    }

    @Override
    public String getExtension() {
        return "xlsx";
    }

    @Override
    public String getFilename() {
        return "calendar_overview.xlsx";
    }

    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private CreationHelper factory;

    @Override
    public byte[] getContent() {
        Styles.resetStyles(); // style just for one workbook valid!
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int x, y;
            int detailsRow = rows + 2;

            wb = new XSSFWorkbook();
            sheet = wb.createSheet(header2[0]);

            factory = wb.getCreationHelper();

            // build cells matrix
            XSSFCell[][] scheduleMatrix = new XSSFCell[rows][cols+1];
            for (x = 1; x <= rows; x++) {
                XSSFRow row = sheet.createRow(x - 1);
                for (y = 1; y <= cols+1; y++) {	// one additional column for totals
                    XSSFCell cell = row.createCell(y - 1);
                    // generell align centered
                    scheduleMatrix[x - 1][y - 1] = cell;
                }
            }

            // set headers
            for (x = 0; x < cols; x++) {
                scheduleMatrix[0][x].setCellValue(header[x]);
                scheduleMatrix[0][x].setCellStyle(Styles.getHeaderStyle(wb));
                scheduleMatrix[1][x].setCellValue(header2[x]);
                scheduleMatrix[1][x].setCellStyle(Styles.getHeaderStyle(wb));
            }
            // set time
            y = 0;
            for (x = 2; x < 37; x = x + 2) {
                scheduleMatrix[x][0].setCellValue(time[y]);
                scheduleMatrix[x][0].setCellStyle(Styles.getHeaderStyle(wb));
                scheduleMatrix[x+1][0].setCellStyle(Styles.getHeaderStyle(wb));
                y++;
            }

            // set sum rows
            scheduleMatrix[37][0].setCellStyle(Styles.getHeaderStyle(wb));
            scheduleMatrix[38][0].setCellValue(Helper.getLiteral("swim"));
            scheduleMatrix[38][0].setCellStyle(Styles.getHeaderStyle(wb));
            scheduleMatrix[39][0].setCellValue(Helper.getLiteral("bike"));
            scheduleMatrix[39][0].setCellStyle(Styles.getHeaderStyle(wb));
            scheduleMatrix[40][0].setCellValue(Helper.getLiteral("run"));
            scheduleMatrix[40][0].setCellStyle(Styles.getHeaderStyle(wb));

            // set details header
            XSSFRow row = sheet.createRow(detailsRow);
            for (x = 0; x < detailHeader.length; x++) {
                XSSFCell detail = row.createCell(x);
                detail.setCellValue(detailHeader[x]);
                detail.setCellStyle(Styles.getHeaderStyle(wb));
            }
            detailsRow++;

            // set schedule items
            long sumSwim[] = new long[7];
            long sumRun[]  = new long[7];
            long sumBike[] = new long[7];
            long sumSwimTotal = 0l;
            long sumRunTotal = 0l;
            long sumBikeTotal = 0l;
            if (scheduleItems != null) {
                for (ScheduleItem item : scheduleItems) {
                    String background = item.getColor();
                    int start = (item.getStartWeekDay() + 5) % 7;
                    y =  start + 1;
                    x = (int) (item.getStartInMinutes() / 30) + 2;
                    // String type = item.getType();
                    String type = serviceLayer.getTranslationService().getDescription(item.getType(), locale.getLanguage(), TranslationService.TYPE.SCHEDULETYPES);
                    String done = "(X)";
                    if(item.getDone()==null ||item.getDone()==false) done = "( )";
                    scheduleMatrix[x][y].setCellValue(done + " " + type);
                    scheduleMatrix[x][y].setCellStyle(Styles.getStyleForColor(wb, background));
                    int z = (int) (item.getDuration() / 30) - 1;
                    for (int v = 0; v < z; v++) {
                        scheduleMatrix[x + v + 1][y].setCellStyle(Styles.getStyleForColor(wb, background));
                    }
                    // summation
                    if("swim".equals(item.getType())) {
                    	sumSwim[start] += item.getDuration();
                    	sumSwimTotal += item.getDuration();
                    } else if("bike".equals(item.getType())) {
                    	sumBike[start] += item.getDuration();
                    	sumBikeTotal += item.getDuration();
                    } else if("run".equals(item.getType())) {
                    	sumRun[start] += item.getDuration();
                    	sumRunTotal += item.getDuration();
                    }
                    // write details
                    XSSFRow rowDetail = sheet.createRow(detailsRow);
                    XSSFCell detail0 = rowDetail.createCell(0);
                    detail0.setCellValue(Helper.formatDate(item.getStart(), "dd.MM. hh:mm", locale));
                    XSSFCell detail1 = rowDetail.createCell(1);
                    detail1.setCellValue(HelperTime.calculateTime((int)(item.getDuration()*60), true));
                    XSSFCell detail2 = rowDetail.createCell(2);
                    detail2.setCellValue(type);
                    XSSFCell detail3 = rowDetail.createCell(3);
                    detail3.setCellValue(item.getDescription());
                    detailsRow++;
                    // build details (run, swim, bike)
                    if(!Helper.isEmpty(item.getSchedulesDetail())) {
                        List<SchedulesDetail> scheduleDetails = item.getSchedulesDetail();
                        for(SchedulesDetail scheduleDetail : scheduleDetails) {
                            ZonesDefinition zonesDefinition = daoLayer.getZonesDefinitionDAO().findById(scheduleDetail.getZoneId());
                            XSSFRow rowScheduleDetail = sheet.createRow(detailsRow);
                            XSSFCell scheduleDetail1 = rowScheduleDetail.createCell(1);
                            if("swim".equals(item.getType())) {
                                scheduleDetail1.setCellValue(scheduleDetail.getDistance() + "m");
                                scheduleDetail1.setCellComment(createComment(Helper.getLiteral("distance")));
                            } else {
                                scheduleDetail1.setCellValue(scheduleDetail.getDurationTarget());
                                scheduleDetail1.setCellComment(createComment(Helper.getLiteral("duration")));
                            }
                            XSSFCell scheduleDetail2 = rowScheduleDetail.createCell(2);
                            scheduleDetail2.setCellValue(zonesDefinition.getShortcut());
                            scheduleDetail2.setCellStyle(Styles.getStyleForColor(wb, zonesDefinition.getColor()));
                            scheduleDetail2.setCellComment(createComment(Helper.getLiteral("intensity")));
                            XSSFCell scheduleDetail3 = rowScheduleDetail.createCell(3);
                            XSSFCell scheduleDetail4 = rowScheduleDetail.createCell(4);
                            if("bike".equals(item.getType())) {
                                scheduleDetail3.setCellValue(scheduleDetail.getPower() + "/" + scheduleDetail.getCadence());
                                scheduleDetail3.setCellComment(createComment(Helper.getLiteral("power") + " / " + Helper.getLiteral("cadence")));
                            } else if("run".equals(item.getType())) {
                                scheduleDetail3.setCellValue(scheduleDetail.getHrLow() + "-" + scheduleDetail.getHrHigh());
                                scheduleDetail3.setCellComment(createComment(Helper.getLiteral("hr_zone")));
                            } else if("swim".equals(item.getType())) {
                                scheduleDetail3.setCellValue(scheduleDetail.getDescription());
                                scheduleDetail3.setCellComment(createComment(Helper.getLiteral("description")));
                                scheduleDetail4.setCellValue(scheduleDetail.getTimeLow() + "-" + scheduleDetail.getTimeHigh());
                                scheduleDetail4.setCellComment(createComment(Helper.getLiteral("time_zone")));
                            }
                            XSSFCell scheduleDetail5 = rowScheduleDetail.createCell(5);
                            scheduleDetail5.setCellValue(scheduleDetail.getComment());
                            scheduleDetail5.setCellComment(createComment(Helper.getLiteral("comment")));
                            detailsRow++;
                        }
                    }
                    detailsRow++;
                }
            }
            // write summation
            for(int i=0;i<7;i++) {
            	scheduleMatrix[38][i+1].setCellValue(HelperTime.calculateTime((int)(sumSwim[i]), true));
            	scheduleMatrix[38][i+1].setCellStyle(Styles.getHeaderStyle(wb));
            	scheduleMatrix[39][i+1].setCellValue(HelperTime.calculateTime((int)(sumBike[i]), true));
            	scheduleMatrix[39][i+1].setCellStyle(Styles.getHeaderStyle(wb));
            	scheduleMatrix[40][i+1].setCellValue(HelperTime.calculateTime((int)(sumRun[i]), true));
            	scheduleMatrix[40][i+1].setCellStyle(Styles.getHeaderStyle(wb));
            }
            scheduleMatrix[38][cols].setCellValue(HelperTime.calculateTime((int)(sumSwimTotal), true));
            scheduleMatrix[38][cols].setCellStyle(Styles.getHeaderBoldStyle(wb));
            scheduleMatrix[39][cols].setCellValue(HelperTime.calculateTime((int)(sumBikeTotal), true));
            scheduleMatrix[39][cols].setCellStyle(Styles.getHeaderBoldStyle(wb));
            scheduleMatrix[40][cols].setCellValue(HelperTime.calculateTime((int)(sumRunTotal), true));
            scheduleMatrix[40][cols].setCellStyle(Styles.getHeaderBoldStyle(wb));

            // set border
//			for (x = 1; x <= rows; x++) {
//				for (y = 1; y <= cols; y++) {
//					if(x==1 || x==rows) {
//						if(scheduleMatrix[x - 1][y - 1].getCellStyle()==null) scheduleMatrix[x - 1][y - 1].setCellStyle(wb.createCellStyle());
//						if(y==1) {
//							scheduleMatrix[x - 1][y - 1].getCellStyle().setBorderLeft(CellStyle.BORDER_THIN);
//						}
//						if(y==cols) {
//							scheduleMatrix[x - 1][y - 1].getCellStyle().setBorderRight(CellStyle.BORDER_THIN);
//						}
//						if(x==1) {
//							scheduleMatrix[x - 1][y - 1].getCellStyle().setBorderTop(CellStyle.BORDER_THIN);
//						} else {
//							scheduleMatrix[x - 1][y - 1].getCellStyle().setBorderBottom(CellStyle.BORDER_THIN);
//						}
//					}
//					if(y==1) {
//						scheduleMatrix[x - 1][y - 1].getCellStyle().setBorderLeft(CellStyle.BORDER_THIN);
//					}
//					if(y==cols) {
//						scheduleMatrix[x - 1][y - 1].getCellStyle().setBorderRight(CellStyle.BORDER_THIN);
//					}
//				}
//			}

            // finally set column widths, the width is measured in units of
            // 1/256th of a character width
            for (int i = 0; i < cols+1; i++) {
                sheet.autoSizeColumn(i, true);
            }

            // fit to page
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);

            wb.write(out);
            return out.toByteArray();
        } catch (Exception ex) {
        	Logger.getRootLogger().error("Error creating calendar overview!");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        return new byte[0];
    }

    @Override
    public String getContentType() {
        return "application/xls";
    }

    private Comment createComment(String text) {
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = factory.createClientAnchor();
        Comment comment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(text);
        comment.setString(str);
        return comment;
    }

}
