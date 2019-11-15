package com.example.mybatis.web;

import com.example.mybatis.common.base.BaseImportLogDTO;
import com.example.mybatis.common.base.BaseImportLogDetailDTO;
import com.example.mybatis.common.base.BaseMsg;
import com.example.mybatis.common.dto.StatisticFiterDTO;
import com.example.mybatis.common.validator.Validate;
import com.example.mybatis.service.IExcelService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "Excel报表")
@Controller
public class ExcelController {

    @Autowired
    IExcelService excelService;

    @ApiOperation(value = "疾病分类统计表")
    @PostMapping("/exportDiseaseClassConstituteReport")
    @ResponseBody
    public void exportDiseaseClassConstituteReport(@RequestBody StatisticFiterDTO statisticFiterDTO, HttpServletResponse response) throws Exception {
        excelService.exportDiseaseClassConstituteReport(statisticFiterDTO, response);
        return;
    }


    @ApiOperation(value = "导入科室（科室模板在excelImportResult目录下）")
    @PostMapping("/importDept")
    @ResponseBody
    public void importDept(HttpServletRequest request,
                           @RequestParam @ApiParam(name = "file", value = "导入的模版") MultipartFile file, HttpServletResponse response) {
        BaseImportLogDTO baseImportLogDTO = excelService.importDept(request, file, response);
        List<BaseImportLogDetailDTO> logDetailDTOS = baseImportLogDTO.getLogDetailDTOS();
        return;
    }
}
