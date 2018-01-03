package com.yaohl.okhttplib.okhttp;


public interface INetCallBack {
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
    void onFail(String code,String errorMessage);

    /**
     * 获取cookie 成功
     *
     * @param cookie
     */
//    void onCookieSuccess(String cookie);

    /**
     * 服务器 令牌失效
     *
     * @param code
     * @param msg
     */
//    void onLoginInvalid(String code, String msg);

}
