package com.example.mybatis.service;

import com.example.mybatis.common.base.BaseImportLogDTO;
import com.example.mybatis.common.dto.StatisticFiterDTO;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IExcelService {
    void exportDiseaseClassConstituteReport(StatisticFiterDTO statisticFiterDTO, HttpServletResponse response);

    /**
     * 从excel导入科室测试
     */
    BaseImportLogDTO importDept(HttpServletRequest request, MultipartFile file, HttpServletResponse response);
}
