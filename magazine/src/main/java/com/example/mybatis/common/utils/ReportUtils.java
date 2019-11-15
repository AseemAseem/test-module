/**
 * FileName: ReportUtils
 * Author:   liujh
 * Date:     2018/6/28 16:01
 * Description: The Util For JarsperRepore
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.mybatis.common.utils;

import com.example.mybatis.common.report.ReportData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.util.CollectionUtils;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class ReportUtils {

    @SuppressWarnings("deprecation")
    public static void exportToExcelWithResponse(String fileName, String filePath, HttpServletResponse response,
                                                 ReportData reportData) throws IOException,
            NoSuchFieldException, IllegalAccessException {
        ServletOutputStream ouputStream = null;
        if (CollectionUtils.isEmpty(reportData.getList())) {
            System.out.println("无数据不支持导出报表。");
            return;
        }
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(filePath);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            java.lang.reflect.Field margin = JRBaseReport.class.getDeclaredField("leftMargin");
            margin.setAccessible(true);
            margin.setInt(jasperReport, 0);
            margin = JRBaseReport.class.getDeclaredField("topMargin");
            margin.setAccessible(true);
            margin.setInt(jasperReport, 0);
            margin = JRBaseReport.class.getDeclaredField("bottomMargin");
            margin.setAccessible(true);
            margin.setInt(jasperReport, 0);
            ouputStream = response.getOutputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportData.getParam(),
                    new JRMapCollectionDataSource(reportData.getList()));
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/vnd.ms-excel");
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            exporter.setParameter(
                    JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                    Boolean.TRUE); // 删除记录最下面的空行
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                    Boolean.FALSE);// 删除多余的ColumnHeader
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                    Boolean.FALSE);// 显示边框
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            ouputStream.flush();
            if (ouputStream != null) {
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
