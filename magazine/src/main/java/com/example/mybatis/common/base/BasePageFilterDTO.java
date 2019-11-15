package com.example.mybatis.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="分页过滤条件类")
public class BasePageFilterDTO<T> implements Serializable {

	private static final long serialVersionUID = 378412468731130052L;

	@ApiModelProperty(value = "是否分页 true-是  false-否，默认分页")
	private String isPage;

	@ApiModelProperty(value = "当前页")
	private Integer page;

	@ApiModelProperty(value = "每页大小")
	private Integer pageSize;

	public String getIsPage() {
		return isPage;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
