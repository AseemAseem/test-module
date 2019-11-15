package com.example.mybatis.dto;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.util.HashSet;

@ExcelTarget("DeptInfoImportDTO")
public class DeptInfoImportDTO {

    @Excel(name = "*序号")
    private Integer no;

    @Excel(name = "*科室名称")
    private String deptName;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public static void main(String[] s ){
        DeptInfoImportDTO dto = new DeptInfoImportDTO();
        int i = dto.hashCode();
        System.out.println(i);
        HashSet<Object> objects = new HashSet<>();

    }
}
