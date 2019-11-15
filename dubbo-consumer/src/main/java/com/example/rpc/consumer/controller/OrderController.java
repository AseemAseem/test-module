package com.example.rpc.consumer.controller;

import com.example.rpc.commoninterface.entity.User;
import com.example.rpc.consumer.common.utils.ExportExcelUtil;
import com.example.rpc.consumer.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.hssf.model.InternalWorkbook.createWorkbook;

@Controller
@Api(tags = "订单")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @ApiOperation(value = "获取用户名测试", response = User.class)
    @GetMapping(value = {"/getUserName"})
    @ResponseBody
    public String getUserName() {
        return orderService.getUserName();
    }

    @ApiOperation(value = "接口平台返回模拟", response = User.class)
    @GetMapping(value = {"/", "/crHcop_getPatientList"})
    @ResponseBody
    public String getPatientList() {
        String str = "{\n" +
                "  \"errorCode\": \"1\",\n" +
                "  \"errorMsg\": \"查询成功\",\n" +
                "  \"hospitalData\": [\n" +
                "    {\n" +
                "      \"birthday\": \"1968-09-10\",\n" +
                "      \"patientCode\": \"00151197\",\n" +
                "      \"contactPhone\": null,\n" +
                "      \"idCard\": \"440225196809103264\",\n" +
                "      \"phone\": null,\n" +
                "      \"nativePlace\": null,\n" +
                "      \"relation\": null,\n" +
                "      \"nation\": \"汉族\",\n" +
                "      \"country\": \"中国\",\n" +
                "      \"allergicHistory\": null,\n" +
                "      \"married\": \"90\",\n" +
                "      \"email\": null,\n" +
                "      \"address\": \"广东省乐昌市\",\n" +
                "      \"contactName\": null,\n" +
                "      \"name\": \"陈丁秀\",\n" +
                "      \"gender\": \"2\",\n" +
                "      \"patientType\": \"2\",\n" +
                "      \"chargeType\": null,\n" +
                "      \"pastHistory\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        return str;
    }

    @ApiOperation(value = "excel操作测试")
    @GetMapping(value = {"/excelTest"})
    @ResponseBody
    public String excelTest(HttpServletResponse response) {
        try {
            String fileName = "Excel导出测试";
            fileName = URLEncoder.encode(fileName, "utf-8") + ".xlsx";
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            String sheetName = "sheetName";
            String headName = "headName";
            String[] rowName = new String[]{"姓名","姓名","姓名","姓名"};
            String[] dataType = new String[]{"String","String","String","String"};

            List<Object[]> dataList = new ArrayList<Object[]>();
            for (int i = 0; i < 5; i++) {
                String name = "小明" + i;
                String[] temp = new String[]{name+"a",name+"b",name+"c",name+"d"};
                dataList.add(temp);
            }
            ExportExcelUtil.createWorkbook(response.getOutputStream(), sheetName, headName, rowName, dataType, dataList);
        } catch (Exception e) {

        }
        return "ok";
    }


}
