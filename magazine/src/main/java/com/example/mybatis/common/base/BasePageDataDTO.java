package com.example.mybatis.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="分页数据类")
public class BasePageDataDTO<T> implements Serializable {

	private static final long serialVersionUID = 378412468731130052L;
	
	/** 返回代码 */
	@ApiModelProperty(value="代码:1成功，0失败")
	private int code = 1;
	/** 消息 */
	@ApiModelProperty(value="消息")
	private String msg;
	/** 返回数据总数 */
	@ApiModelProperty(value="数据总数")
	private int count;
	/** 返回数据列表 */
	@ApiModelProperty(value="数据")
	private List<T> data;

	public BasePageDataDTO() {
		super();
		data = new ArrayList<T>();
	}

	public BasePageDataDTO(List<T> data,int count) {
		this.data = data;
		this.count = count;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
