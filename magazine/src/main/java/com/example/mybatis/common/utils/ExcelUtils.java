package com.example.mybatis.common.utils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtils {

    public static <T> ExcelImportResult<T> getExcelImportResult(MultipartFile file, Class<?> pojoClass, Integer skipRow) {
        //设置参数，主要是表头行数
        ImportParams params = new ImportParams();
        params.setTitleRows(skipRow);
        //不再使用easy-poi内置校验,而是在service中统一校验，这里只用于获取流
        ExcelImportResult<T> importResult = null;
        try {
            importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), pojoClass, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (importResult == null) {
            System.out.println("获取不到导入结果");
        }
        return importResult;
    }

}
