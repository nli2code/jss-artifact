package com.vxichina.wfmpx.web.controller.$_example.lixiaolong.utilDemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vxichina.wfmpx.utils.ExcelPoiUtils;
import com.vxichina.wfmpx.utils.beans.ExcelPoiCell;
import com.vxichina.wfmpx.utils.beans.ExcelPoiRow;
import com.vxichina.wfmpx.utils.beans.ExcelPoiWorkbookCfg;

/**excel导入读取测试.以及excel文件下载测试
 * 
 * @author xiaolong.li
 *
 */
@Controller
@RequestMapping("/ExcelTestController")
public class ExcelTestController {


    /**日志.
     * 
     */
    private static final  Logger LOG = LoggerFactory.getLogger(ExcelTestController.class);

    /**跳转到主界面.
     * 
     * @param request request
     * @return 主界面
     */
    @RequestMapping("/main")
    public ModelAndView  main2(HttpServletRequest request ) {

        Map<String, Object> params = new HashMap<String, Object>();
        //        params.put("projectId", agentSelectorDto.getProjectId());
        //        params.put("aoid", agentSelectorDto.getAoid());
        //        params.put("originAgentType", agentSelectorDto.getOriginAgentType());
        //        params.put("agentHtmlId", agentSelectorDto.getAgentHtmlId());
        //        params.put("bgroupId", agentSelectorDto.getBgroupId());
        return new ModelAndView("$_example/lixiaolong/excelMain",params);
    }

    /**导入测试.
     * 
     * @param file  导入的文件
     * @param projectId 项目id
     * @param projectName 项目名称
     * @param req request
     * @return  返回状态选项
     * @throws Exception Exception
     * @throws IOException  IOException
     */

    @RequestMapping(value = "/importData", produces = {"application/json;charset=UTF-8"}) 
    @ResponseBody
    public String importData(@RequestParam("upfile")CommonsMultipartFile file,
            String projectId,
            String projectName,
            HttpServletRequest req) {

        Map<String,Object>  resultObjMap ;

        try {
            resultObjMap = ExcelPoiUtils.readRowsFromExcel(file, 0, 2,getHiddenFieldRow(null));
        }  catch (Exception e) {
            LOG.error("文件读取出错，请联系管理员",e);
            return JSON.toJSONString("文件读取出错，请联系管理员").toString();
        }

        if ((boolean)resultObjMap.get(ExcelPoiUtils.KEY_STATUS) == false) {
            LOG.debug("导入出错的消息 =  " + JSON.toJSONString(resultObjMap.get(ExcelPoiUtils.KEY_MSG)));
            return JSON.toJSONString(resultObjMap.get(ExcelPoiUtils.KEY_MSG)).toString(); 
        }  else {
            LOG.debug("导入返回的result =  " + JSON.toJSONString(resultObjMap.get(ExcelPoiUtils.KEY_CONTENT)));
            return JSON.toJSONString(resultObjMap.get(ExcelPoiUtils.KEY_CONTENT)).toString(); 

        }





    }

    /**封装隐藏表头域
     * 
     * @return
     */

    private List<ExcelPoiRow>  getHiddenFieldRow (
            ExcelPoiWorkbookCfg workbookCfg) {

        XSSFCellStyle cellStyleOptional = null;
        if (workbookCfg != null ) {
            cellStyleOptional = ExcelPoiUtils.getCellStyle(workbookCfg.getWorkbook(), 
                    IndexedColors.WHITE.index, IndexedColors.RED.index);
        }
        //封装单元内容
        List<ExcelPoiCell> cellList = new ArrayList<>();
        List<String>  hiddenFiledList = new ArrayList<>();
        hiddenFiledList.add("id");
        hiddenFiledList.add("shiftName");
        hiddenFiledList.add("shiftNo");
        hiddenFiledList.add("shiftBegTime");
        hiddenFiledList.add("shiftEndTime");
        hiddenFiledList.add("workdate");
        for (String field : hiddenFiledList) {
            ExcelPoiCell  cell = new ExcelPoiCell();
            cell.setCellValue(field);
            cell.setCellComment("隐藏关键域,不允许进行任何修改="+field);
            cell.setCellStyle(cellStyleOptional);
            cellList.add(cell);
        }
        //将单元格集合添加到行
        ExcelPoiRow row = new ExcelPoiRow();
        row.setCellList(cellList);
        row.setIsHiddenRow(true);
        List<ExcelPoiRow> rowList = new ArrayList<>();
        rowList.add(row);
        return rowList;
    }



    /**设置渲染显示表头内容
     * 
     * @return
     */
    private List<ExcelPoiRow>  getShowHeaderTitle (ExcelPoiWorkbookCfg workbookCfg ) {

        //样式定义有数量显示必须根据业务情况指定定义
        XSSFCellStyle cellStyleRequired = ExcelPoiUtils.getCellStyle(workbookCfg.getWorkbook(), 
                null, IndexedColors.PINK.index);

        XSSFCellStyle cellStyleOptional = ExcelPoiUtils.getCellStyle(workbookCfg.getWorkbook(), 
                null, IndexedColors.GREY_25_PERCENT.index);

        //封装单元内容
        List<ExcelPoiCell> cellList = new ArrayList<>();
        List<String>  hiddenFiledList = new ArrayList<>();
        hiddenFiledList.add("id编号");
        hiddenFiledList.add("班次名称");
        hiddenFiledList.add("变成编号");
        hiddenFiledList.add("开始时间");
        hiddenFiledList.add("结束时间");
        hiddenFiledList.add("工作日");
        for (String field : hiddenFiledList) {
            ExcelPoiCell  cell = new ExcelPoiCell();
            cell.setCellValue(field);
            cell.setCellComment("必填，批注说明=" + field);
            if (field.equals("id编号") ) {
                cell.setCellStyle(cellStyleRequired);

            } else if (field.equals("班次名称") || field.equals("变成编号")){
                cell.setCellStyle(cellStyleRequired);
                cell.setCellComment("防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "\n<br/>防守打法大事发生的分开来打扫房间克里斯多夫将额外热为了让路口发生的两款发的="
                        + "" + field);
            }  else   {
                cell.setCellStyle(cellStyleOptional);
                cell.setCellComment("选填=" + field);
            }

            cellList.add(cell);
        }
        //将单元格集合添加到行
        ExcelPoiRow row = new ExcelPoiRow();
        row.setCellList(cellList);
        row.setIsHiddenRow(false);
        List<ExcelPoiRow> rowList = new ArrayList<>();
        rowList.add(row);
        return rowList;
    }

    private List<ExcelPoiRow>  getShowContent (ExcelPoiWorkbookCfg workbookCfg ) {

        XSSFCellStyle cellStyleOptional = ExcelPoiUtils.getCellStyle(workbookCfg.getWorkbook(), 
                IndexedColors.GREEN.index, IndexedColors.WHITE.index);

        List<String>  hiddenFiledList = new ArrayList<>();
        hiddenFiledList.add("内容1");
        hiddenFiledList.add("内容1内容1");
        hiddenFiledList.add("内容1编号");
        hiddenFiledList.add("内容1时间");
        hiddenFiledList.add("内容1时间2");
        //
        hiddenFiledList.add("内容1");
        hiddenFiledList.add("内容1内容1");
        hiddenFiledList.add("内容1编号");
        hiddenFiledList.add("内容1时间");
        hiddenFiledList.add("内容1时间2");
        List<ExcelPoiRow> rowList = new ArrayList<>();
        //100000 最大导出记录数
        for(int i =0; i< 1000 ;i++) {
            List<ExcelPoiCell> cellList = new ArrayList<>();
            for (String field : hiddenFiledList) {
                ExcelPoiCell  cell = new ExcelPoiCell();
                cell.setCellValue(field);
                //添加批注与样式后，内存消耗会加大，导出的记录数会下降
                cell.setCellComment("必填，批注说明=" + field+" i = " +i);
                cell.setCellStyle(cellStyleOptional);
                cellList.add(cell);
            }
            //将单元格集合添加到行
            ExcelPoiRow row = new ExcelPoiRow();
            row.setCellList(cellList);
            row.setIsHiddenRow(false);
            rowList.add(row);
        }

        return rowList;
    }

    private List<ExcelPoiRow>  getShowContent2 (ExcelPoiWorkbookCfg workbookCfg ) {

        XSSFCellStyle cellStyleOptional = ExcelPoiUtils.getCellStyle(workbookCfg.getWorkbook(), 
                IndexedColors.GREEN.index, IndexedColors.WHITE.index);

        List<String>  hiddenFiledList = new ArrayList<>();
        hiddenFiledList.add("内容1");
        hiddenFiledList.add("内容1内容1");
        hiddenFiledList.add("内容1编号");
        hiddenFiledList.add("内容1时间");
        hiddenFiledList.add("内容1时间2");
        //
        hiddenFiledList.add("内容1");
        hiddenFiledList.add("内容1内容1");
        hiddenFiledList.add("内容1编号");
        hiddenFiledList.add("内容1时间");
        hiddenFiledList.add("内容1时间2");
        List<ExcelPoiRow> rowList = new ArrayList<>();

        for(int i =0; i< 200000 ;i++) {
            List<ExcelPoiCell> cellList = new ArrayList<>();
            for (String field : hiddenFiledList) {
                ExcelPoiCell  cell = new ExcelPoiCell();
                cell.setCellValue(field);
                //添加批注与样式后，内存消耗会加大，导出的记录数会下降
                //cell.setCellComment("必填，批注说明=" + field+" i = " +i);
                cell.setCellStyle(cellStyleOptional);
                cellList.add(cell);
            }
            //将单元格集合添加到行
            ExcelPoiRow row = new ExcelPoiRow();
            row.setCellList(cellList);
            row.setIsHiddenRow(false);
            rowList.add(row);
        }

        return rowList;
    }

    /**导出测试，样式与批注
     * 
     * @param rsp
     * @throws IOException 
     */
    @RequestMapping("/export")
    public void export (HttpServletResponse response) throws IOException{

        ExcelPoiWorkbookCfg workbookCfg =   ExcelPoiUtils.getXssfWorkbookCfg(null);
        List<ExcelPoiRow>   fieldList = getHiddenFieldRow(workbookCfg);
        ExcelPoiUtils.writeRowsToExcel(workbookCfg, fieldList, 0);
        List<ExcelPoiRow>   titleList = getShowHeaderTitle(workbookCfg);
        ExcelPoiUtils.writeRowsToExcel(workbookCfg,titleList, 1);
        List<ExcelPoiRow>   contentList = getShowContent(workbookCfg);
        ExcelPoiUtils.writeRowsToExcel(workbookCfg,contentList, 2);

        ExcelPoiUtils.downExcel(response, workbookCfg.getWorkbook(), "testImprtExcelFile");


    }


    /**导出测试;超大量数据测试
     * 
     * @param rsp
     * @throws IOException 
     */
    @RequestMapping("/export2")
    public void export2 (HttpServletResponse response) throws IOException{

        ExcelPoiWorkbookCfg workbookCfg =   ExcelPoiUtils.getSxssfWorkbookCfg(null);
        List<ExcelPoiRow>   fieldList = getHiddenFieldRow(workbookCfg);
        ExcelPoiUtils.writeRowsToExcel(workbookCfg, fieldList, 0);
        List<ExcelPoiRow>   titleList = getShowHeaderTitle(workbookCfg);
        ExcelPoiUtils.writeRowsToExcel(workbookCfg,titleList, 1);
        List<ExcelPoiRow>   contentList = getShowContent2(workbookCfg);
        ExcelPoiUtils.writeRowsToExcel(workbookCfg,contentList, 2);

        ExcelPoiUtils.downExcel(response, workbookCfg.getWorkbook(), "testImprtExcelFile");



    }

    //
    //    /**导出测试
    //     * 
    //     * 
    //     * @param rsp
    //     * @throws IOException 
    //     * @throws ParseException 
    //     */
    //    @RequestMapping("/export3")
    //    public void export3 (HttpServletResponse response) throws IOException, ParseException{
    //
    //        System.out.println("导出测试 export3");
    //
    //        String headers[][] = {{"日期","DATE"},{"标题","TIME"},{"其他",null},{"日期","DATE"},{"标题","TIME"},{"其他",null},{"其他",null},{"日期","DATE"},{"标题","TIME"},{"其他",null}} ;
    //
    //        //        File file = new File("D://test/michael/test.xlsx");
    //        //
    //        //        if (file.exists())
    //        //
    //        //            file.delete();
    //        //
    //        //        file.createNewFile();
    //
    //
    //
    //        ExportData exportData = new ExportData("test", headers, "test");
    //
    //
    //
    //        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    //
    //        for (int i = 0; i < 200000; i++) {
    //
    //            ArrayList<String> cellList = new ArrayList<String>();
    //
    //            for (int j = 0; j < 20; j++) {
    //
    //                cellList.add("内容1时间");
    //                //                cellList.add("内容1时间");
    //
    //            }
    //
    //            data.add(cellList);
    //
    //        }
    //
    //        //        OutputStream out = new FileOutputStream(file);
    //        response.setContentType("application/vnd.ms-excel");   
    //        response.setHeader("Content-disposition", "attachment;filename=" + "testexport2" + ".xlsx");    
    //        OutputStream ouputStream = response.getOutputStream(); 
    //        exportData.PoiWriteExcel_To2007_3(data,ouputStream);
    //
    //
    //    }
    //
    //    /**导出测试
    //     * 
    //     * 
    //     * @param rsp
    //     * @throws IOException 
    //     * @throws ParseException 
    //     */
    //    @RequestMapping("/export4")
    //    public void export4 (HttpServletResponse response) throws IOException, ParseException{
    //
    //        System.out.println("导出测试 export3");
    //
    //        String headers[][] = {{"日期","DATE"},{"标题","TIME"},{"其他",null},{"日期","DATE"},{"标题","TIME"},{"其他",null},{"其他",null},{"日期","DATE"},{"标题","TIME"},{"其他",null}} ;
    //
    //        //        File file = new File("D://test/michael/test.xlsx");
    //        //
    //        //        if (file.exists())
    //        //
    //        //            file.delete();
    //        //
    //        //        file.createNewFile();
    //
    //
    //
    //        ExportData exportData = new ExportData("test", headers, "test");
    //
    //
    //
    //        List<ExcelPoiRow>    rowList = new ArrayList<>();
    //
    //        for (int i = 0; i < 200000; i++) {
    //
    //            ExcelPoiRow  row= new ExcelPoiRow();
    //            List<ExcelPoiCell> cellList = new ArrayList<>();
    //            for (int j = 0; j < 20; j++) {
    //                ExcelPoiCell cell = new ExcelPoiCell();
    //                cell.setCellValue("内容1时间");
    //                //                cellList.add("内容1时间");
    //                cellList.add(cell);
    //            }
    //            row.setCellList(cellList);
    //
    //
    //            rowList.add(row);
    //
    //        }
    //
    //        //        OutputStream out = new FileOutputStream(file);
    //        response.setContentType("application/vnd.ms-excel");   
    //        response.setHeader("Content-disposition", "attachment;filename=" + "testexport2" + ".xlsx");    
    //        OutputStream ouputStream = response.getOutputStream(); 
    //        exportData.PoiWriteExcel_To2007_4(rowList,ouputStream);
    //
    //
    //    }
    //

}


