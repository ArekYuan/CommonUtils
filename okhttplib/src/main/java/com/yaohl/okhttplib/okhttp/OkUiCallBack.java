package com.yaohl.okhttplib.okhttp;

/**
 * Created by asus on 2016/11/16.
 */

public interface OkUiCallBack {
    /**
     * <返回页面请求结果>
     * <功能详细描述>
     *
     * @param result 返 回 类 型：void
     */
    void onComplete(Object result);

    /**
     * <返回页面请求失败信息>
     * <功能详细描述>
     *
     * @param errorMessage 返 回 类 型：void
     */
    void onFail(String errorMessage);

    /**
     * <系统处理异常回调>
     * <功能详细描述>
     *
     * @param sysErrorCode 返回类型:void
     */
    void onSysFail(int sysErrorCode, String methodName);

    /**
     * 获取Cookie
     *
     * @param cookie
     */
//    void onCookie(String cookie);
}
