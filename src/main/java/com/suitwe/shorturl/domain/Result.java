package com.suitwe.shorturl.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * web接口返回对象
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    public Result() {
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
