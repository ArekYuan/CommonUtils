package com.yaohl.okhttplib.okhttp;

import org.json.JSONObject;

import java.util.Map;


/**
 * 文 件 名:  OkHttpRequestParams.java
 * 描    述:  <HTTP请求参数封装类>
 * 创 建 人:  袁光跃
 * 创建时间:  2015年10月20日
 */
public class OkHttpRequestParams
{

    /**
     * 文 件 名:  OkHttpRequestParams.java
     * 版    权:
     * 描    述:  <HTTP 请求类型>
     * 版    本： <版本号>
     * 创 建 人:  ygy
     * 创建时间:  2015年10月20日
     */
    public enum HttpRequestMethod
    {
        GET, POST, PUT, DELETE
    }

    /**
     * 请求参数
     */
    private Map<String, String> params;

    /**
     * 请求类型
     */
    private HttpRequestMethod httpRequestMethod;

    /**
     * 请求tag
     */
    private String tag;

    /**
     * json实体对象类
     */
    private JSONObject jsonObj;

    private int timeOut = 10000;

    /**
     * 是否是短信验证码
     */
    private boolean isVerifyCode;

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public JSONObject getJsonObj()
    {
        return jsonObj;
    }

    public void setJsonObj(JSONObject jsonObj)
    {
        this.jsonObj = jsonObj;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    /**
     * <设置请求参数>
     * <功能详细描述>
     *
     * @param params 请求参数
     *
     * @return 返 回 类 型：OkHttpRequestParams
     */
    public OkHttpRequestParams setParams(Map<String, String> params)
    {
        this.params = params;
        return this;
    }

    /**
     * <获得请求方法>
     * <功能详细描述>
     *
     * @return 返 回 类 型：HttpRequestMethod
     */
    public HttpRequestMethod getHttpRequestMethod()
    {
        return httpRequestMethod == null ? HttpRequestMethod.POST : httpRequestMethod;
    }

    /**
     * <设置请求方法>
     * <功能详细描述>
     *
     * @param httpRequestMethod 请求方法名
     *
     * @return 返 回 类 型：OkHttpRequestParams
     */
    public OkHttpRequestParams setHttpRequestMethod(HttpRequestMethod httpRequestMethod)
    {
        this.httpRequestMethod = httpRequestMethod;
        return this;
    }

    public boolean isVerifyCode()
    {
        return isVerifyCode;
    }

    public void setVerifyCode(boolean isVerifyCode)
    {
        this.isVerifyCode = isVerifyCode;
    }

    public int getTimeOut()
    {
        return timeOut;
    }

    public void setTimeOut(int timeOut)
    {
        this.timeOut = timeOut;
    }


}
