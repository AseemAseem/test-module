/**
 * FileName: ReportData
 * Author:   liujh
 * Date:     2018/6/28 22:50
 * Description: The Data Filled In Report
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.mybatis.common.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportData implements Serializable {
	private static final long serialVersionUID = 1L;
    private Map param = new HashMap();
    private List<Map<String,?>> list = new ArrayList<Map<String,?>>();
    private String title = "";

    public Map getParam() {
        return param;
    }

    public void setParam(Map param) {
        this.param = param;
    }

    public List<Map<String, ?>> getList() {
        return list;
    }

    public void setList(List<Map<String, ?>> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
