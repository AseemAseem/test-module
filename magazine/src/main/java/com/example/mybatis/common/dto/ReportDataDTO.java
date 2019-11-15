package com.example.mybatis.common.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ReportDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Map<String,?>> list;

    private Map<String,Object> param;

    private String title;

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String, ?>> getList() {
        return list;
    }

    public void setList(List<Map<String, ?>> list) {
        this.list = list;
    }
}
