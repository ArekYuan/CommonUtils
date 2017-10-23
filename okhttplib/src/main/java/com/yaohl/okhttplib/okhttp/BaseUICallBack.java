package com.yaohl.okhttplib.okhttp;

/**
 * 文 件 名:  UICallBack.java
 * 描    述:  <UI回调接口>
 * 创 建 人:  袁光跃
 * 创建时间:  2015年10月19日
 */
public interface BaseUICallBack {
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
}
