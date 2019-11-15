package com.example.mybatis.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


public class PageUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    //分页
    public static Page buildPage(HttpServletRequest request) {
        String isPage = request.getParameter("isPage");
        int current = 1;
        int size = 10;
        if (StringUtils.isNotBlank(isPage) && "false".equals(isPage)) {
            current = RowBounds.NO_ROW_OFFSET;
            size = RowBounds.NO_ROW_LIMIT;
        } else {
            String page = request.getParameter("page");
            String limit = request.getParameter("pageSize");
            current = StringUtils.isNotBlank(page) ? Integer.valueOf(page) : 1;
            size = StringUtils.isNotBlank(limit) ? Integer.valueOf(limit) : 10;
        }
        return new Page(current, size);
    }

    //分页
    public static Page buildPage(String isPage, Integer page, Integer size) {
        Integer current = 1;
        Integer pageSize = 10;
        if (StringUtils.isNotBlank(isPage) && "false".equals(isPage)) {
            current = RowBounds.NO_ROW_OFFSET;
            pageSize = RowBounds.NO_ROW_LIMIT;
        } else {
            current = (page != null && page != 0) ? page : 1;
            pageSize = (size != null && size != 0) ? size : 10;
        }
        return new Page(current, pageSize);
    }
}
