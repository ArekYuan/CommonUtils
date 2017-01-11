package yaohl.cn.commonutils.newtwork.volley;

/**
 * 文 件 名:  UICallBack.java
 * 版    权:
 * 描    述:  <UI回调接口>
 * 版    本： <版本号>
 * 创 建 人:  姜飞
 * 创建时间:  2015年10月19日
 */
public interface UICallBack
{
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
     * 系统返回json格式内 数据异常
     *
     * @param msg
     */
    void onReqFailed(String msg);

}
