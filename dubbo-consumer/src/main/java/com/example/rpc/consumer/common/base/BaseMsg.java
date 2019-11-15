package com.example.rpc.consumer.common.base;


import com.example.rpc.consumer.common.enm.ResultCode;
import java.util.HashMap;


public class BaseMsg extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * key:返回消息
     */
    public static final String MSG = "msg";
    /**
     * key:返回代码
     */
    public static final String CODE = "code";
    /**
     * key:返回数据
     */
    public static final String DATA = "data";

    public BaseMsg() {
        put(CODE, ResultCode.OK.getCode());
    }

    /**
     * 返回成功状态
     *
     * @return
     */
    public static BaseMsg success() {
        return customMsg(ResultCode.OK, null, null);
    }
    /**
     * 返回成功消息
     *
     * @param msg
     * @return
     */
    public static BaseMsg successMsg(String msg) {
        return customMsg(ResultCode.OK, msg, null);
    }


    /**
     * 返回失败状态
     *
     * @return
     */
    public static BaseMsg error() {
        return customMsg(ResultCode.FAIL, null, null);
    }

    /**
     * 返回失败消息
     *
     * @param msg
     * @return
     */
    public static BaseMsg errorMsg(String msg) {
        return customMsg(ResultCode.FAIL, msg, null);
    }

    public static BaseMsg errorCodeMsg(int code, String msg) {
        return customMsg(code, msg, null);
    }

    /**
     * 返回成功对象
     *
     * @param obj
     * @return
     */
    public static BaseMsg successData(Object obj) {
        return customMsg(ResultCode.OK, null, obj);
    }

    /**
     * 返回失败对像
     *
     * @param obj
     * @return
     */
    public static BaseMsg errorData(Object obj) {
        return customMsg(ResultCode.FAIL, null, obj);
    }

    /**
     * 返回自定义消息
     *
     * @param code 枚举对象
     * @return
     */
    public static BaseMsg customMsg(ResultCode code) {
        return customMsg(code, null, null);

    }

    /**
     * 返回自定义消息
     *
     * @param code 枚举对象
     * @param msg  重新定义code消息
     * @return
     */
    public static BaseMsg customMsg(ResultCode code, String msg) {
        return customMsg(code, msg, null);

    }

    /**
     * 返回自定义消息
     *
     * @param code 枚举对象
     * @param data 返回对象
     * @return
     */
    public static BaseMsg customMsg(ResultCode code, Object data) {
        return customMsg(code, null, data);
    }

    /**
     * 返回自定义消息
     *
     * @param code 枚举对象
     * @param msg  重新定义枚举对象对应的消息
     * @param data 返回对象
     * @return
     */
    public static BaseMsg customMsg(ResultCode code, String msg, Object data) {
        BaseMsg baseMsg = new BaseMsg();
        baseMsg.put(CODE, code.getCode());
        baseMsg.put(MSG, code.getMessage());
        baseMsg.put(DATA, data);
        if (msg != null) {
            baseMsg.put(MSG, msg);
        }
        return baseMsg;

    }

    public static BaseMsg customMsg(int code, String msg, Object data) {
        BaseMsg baseMsg = new BaseMsg();
        baseMsg.put(CODE, code);
        baseMsg.put(MSG, msg);
        baseMsg.put(DATA, data);
        if (msg != null) {
            baseMsg.put(MSG, msg);
        }
        return baseMsg;

    }

    /**
     * 自定义key-value
     */
    public BaseMsg put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
