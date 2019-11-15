package com.example.mybatis.service.impl;

import com.example.mybatis.common.base.BaseImportLogDTO;
import com.example.mybatis.common.base.BaseImportLogDetailDTO;
import com.example.mybatis.common.constant.Constant;
import com.example.mybatis.common.dto.StatisticFiterDTO;
import com.example.mybatis.common.report.ReportData;
import com.example.mybatis.common.utils.ExcelUtils;
import com.example.mybatis.common.utils.ReportUtils;
import com.example.mybatis.dto.DeptInfoImportDTO;
import com.example.mybatis.dto.DeptInfoImportLogDTO;
import com.example.mybatis.service.IExcelService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;

@Service
public class ExcelServiceImpl implements IExcelService {


    private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Override
    public void exportDiseaseClassConstituteReport(StatisticFiterDTO statisticFiterDTO, HttpServletResponse response) {
        try {
            String path = ResourceUtils.getFile("classpath:report/DiseaseClassStatistic.jrxml").getPath();
            ReportData reportData = setReportDataForTest();//设置测试数据
            //设置excel中的部分属性  如统计的日期、疾病名称、统计地区、病例分类
            setCDCExcelParameter(reportData, statisticFiterDTO, "疾病分类统计表");
            if (StringUtils.isNotBlank(path)) {
                ReportUtils.exportToExcelWithResponse("疾病分类统计表.xlsx", path, response, reportData);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public BaseImportLogDTO importDept(HttpServletRequest request, MultipartFile file, HttpServletResponse response) {
        ExcelImportResult<DeptInfoImportDTO> result = ExcelUtils.getExcelImportResult(file, DeptInfoImportDTO.class, Constant.TITLE_ROWS);
        //获取dto列表
        List<DeptInfoImportDTO> resultList = result.getList();
        //获取工作簿
        Workbook workbook = result.getWorkbook();
        //校验科室数据
        BaseImportLogDTO baseImportLogDTO = checkDeptInfoList(resultList);
        //导出结果excel
        Long attachmentId = wirteImportLog(response, workbook, baseImportLogDTO.getLogDetailDTOS(), Constant.TITLE_ROWS, 1);
        baseImportLogDTO.setAttachmentId(attachmentId);
        return baseImportLogDTO;
    }

    private void setCDCExcelParameter(ReportData reportData, StatisticFiterDTO statisticFiterDTO, String titleName) {
        //日期
        String reportTime = String.format("%s 到 %s", statisticFiterDTO.getStartDate(), statisticFiterDTO.getEndDate());
        //疾病名称
        String dName = StringUtils.isBlank(statisticFiterDTO.getDiseaseName()) ? "全部" : statisticFiterDTO.getDiseaseName();
        //统计地区
        String rArea = "统计地区";
        //病例分类，如果有多个用逗号隔开
        String dType = "病例分类";
        //标题
        String title = "标题";
        Map<String, Object> params = new HashMap<>();
        params.put("reportTime", reportTime);
        params.put("dName", dName);
        params.put("rArea", rArea);
        params.put("dType", dType);
        params.put("title", title);
        reportData.setParam(params);
    }

    private ReportData setReportDataForTest() {
        ReportData result = new ReportData();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("diseaseName", "感冒");
        row1.put("diseaseNums", "1");
        result.getList().add(row1);
        return result;
    }

    private BaseImportLogDTO checkDeptInfoList(List<DeptInfoImportDTO> resultList) {
        Set<Integer> noSet = new HashSet<>();
        Set<String> deptNameSet = new HashSet<>();
        List<BaseImportLogDetailDTO> deptInfoImportLogDTOS = new ArrayList<>();
        BaseImportLogDTO result = new BaseImportLogDTO();

        //记录条数
        int totalCount = resultList.size();
        int successCount = 0;
        int failCount = 0;
//        validate.isNotEmpty(currentUser, "当前用户不能为空");
//        validate.isNotEmpty(currentUser.getOrgId(), "当前登录用户机构id为空");
        for (DeptInfoImportDTO deptInfoImportDTO : resultList) {
            //记录日志
            DeptInfoImportLogDTO logDTO = new DeptInfoImportLogDTO();
            logDTO.setNo(deptInfoImportDTO.getNo());
            //初始成功
            logDTO.setSuccess(true);
            //校验参数非空
            checkNotNull(deptInfoImportDTO, logDTO);
            //表格校验no是否重复
            boolean successNo = noSet.add(deptInfoImportDTO.getNo());
            if (!successNo) {
                StringBuilder logMessage = new StringBuilder(logDTO.getFailMessage());
                logDTO.setSuccess(false);
                logMessage.append("工作簿中科室序号重复;");
                logDTO.setFailMessage(logMessage.toString());
            }
            //表格校验科室名称是否重复
            boolean successDeptName = deptNameSet.add(deptInfoImportDTO.getDeptName());
            if (!successDeptName) {
                StringBuilder logMessage = new StringBuilder(logDTO.getFailMessage());
                logDTO.setSuccess(false);
                logMessage.append("工作簿中存在重复的科室名称;");
                logDTO.setFailMessage(logMessage.toString());
            }
            if (logDTO.getSuccess()) {
                //记录成功数
                successCount++;
            } else {
                //记录失败数
                failCount++;
            }
            deptInfoImportLogDTOS.add(logDTO);
        }
        result.setTotalCount(totalCount);
        result.setSuccessCount(successCount);
        result.setFailCount(failCount);
        result.setLogDetailDTOS(deptInfoImportLogDTOS);
        return result;
    }

    /**
     * 导出结果excel
     *
     * @param workbook   工作簿
     * @param logDTOList 日志列表
     */
    public Long wirteImportLog(HttpServletResponse response, Workbook workbook, List<BaseImportLogDetailDTO> logDTOList, int skipRowNum, Integer messageCount) {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();

        //错误的样式
        Font errorFont = workbook.createFont();
        errorFont.setFontName("Calibri");
        errorFont.setColor(HSSFColor.RED.index);
        CellStyle errorCellStyle = workbook.createCellStyle();
        errorCellStyle.setFont(errorFont);
        errorCellStyle.setAlignment(ALIGN_CENTER);

        //成功的样式
        Font successFont = workbook.createFont();
        successFont.setFontName("Calibri");
        successFont.setColor(HSSFColor.GREEN.index);
        CellStyle successCellStyle = workbook.createCellStyle();
        successCellStyle.setFont(successFont);
        successCellStyle.setAlignment(ALIGN_CENTER);

        skipRowNum = skipRowNum + 1;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (skipRowNum > 0) {
                skipRowNum--;
                continue;
            }
            short lastCellNum = row.getLastCellNum();
            Cell cell0 = row.getCell(0);
            if (cell0 != null && cell0.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                Double cell0Value = cell0.getNumericCellValue();
                Cell message1 = row.createCell(lastCellNum);
                Cell message2 = row.createCell(lastCellNum + 1);
                for (BaseImportLogDetailDTO baseImportLogDTO : logDTOList) {
                    if (cell0Value.equals(Double.valueOf(baseImportLogDTO.getNo()))) {
                        //成功记录用户名和密码
                        if (baseImportLogDTO.getSuccess() && messageCount.equals(2)) {
                            //设置样式
                            message1.setCellStyle(successCellStyle);
                            message2.setCellStyle(successCellStyle);
                            message1.setCellValue(baseImportLogDTO.getAutoUserName());
                            message2.setCellValue(baseImportLogDTO.getAutoPassword());
                        }
                        if (baseImportLogDTO.getSuccess() && messageCount.equals(1)) {
                            //设置样式
                            message1.setCellStyle(successCellStyle);
                            message1.setCellValue("导入成功");
                        }
                        if (!baseImportLogDTO.getSuccess()) {
                            message1.setCellStyle(errorCellStyle);
                            message1.setCellValue(baseImportLogDTO.getFailMessage());
                        }
                    }
                }
            }
        }
        try {
            String fileName = URLEncoder.encode("导入结果", "utf-8") + ".xlsx";
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private void checkNotNull(DeptInfoImportDTO entity, DeptInfoImportLogDTO logDTO) {
        StringBuilder logMessage = new StringBuilder();
        if (null == entity.getNo()) {
            logDTO.setSuccess(false);
            logMessage.append("序号不能为空;");
        }
        if (StringUtils.isEmpty(entity.getDeptName())) {
            logDTO.setSuccess(false);
            logMessage.append("科室名称不能为空;");
        }
        logDTO.setFailMessage(logMessage.toString());
    }
}
