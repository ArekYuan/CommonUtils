package com.yaohl.okhttplib.okhttp;

/**
 * 作者：袁光跃
 * 日期：2017/12/14
 * 描述：错误返回 类
 * 邮箱：813665242@qq.com
 */

public class ErrorResponse {

    private String code;
    private Object data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
