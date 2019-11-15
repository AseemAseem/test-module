package com.example.mybatis.common.enm;

public enum ResultCode {
	
	FAIL									(0,"fail"),
	OK										(1,"success"),
	UNKNOWN									(500,"未知错误");
	
	/**
	 * http状态码
	 */
	private int code;
	/**
	 * http状态码对应的消息
	 */
	private String message;

	ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
	
	/**
	 * 根据代码获取消息
	 * 
	 * @param code
	 * @return
	 */
	public static ResultCode getMsg(int code) {
		for (ResultCode resultCode : values()) {
			if (resultCode.code == code) {
				return resultCode;
			}
		}
		return null;
	}

}
