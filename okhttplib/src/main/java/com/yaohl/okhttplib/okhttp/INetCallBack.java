package com.yaohl.okhttplib.okhttp;


public interface INetCallBack
{
    /**
     * <请求成功>
     * <功能详细描述>
     *
     * @param ob 返 回 类 型：void
     */
    void onSuccess(Object ob);

    /**
     * <请求失败>
     * <功能详细描述>
     *
     * @param errorMessage 请求失败message
     *                     返 回 类 型：void
     */
    void onFail(String errorMessage);

}
