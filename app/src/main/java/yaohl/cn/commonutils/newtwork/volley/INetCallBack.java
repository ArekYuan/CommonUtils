package yaohl.cn.commonutils.newtwork.volley;

/**
 * 文 件 名:  INetCallBack.java
 * 版    权:
 * 描    述:  <网络请求回调>
 * 版    本： <版本号>
 * 创 建 人:
 * 创建时间:  2015年10月19日
 */
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

    /**
     * <系统错误>
     * <功能详细描述>
     * 返回类型:void
     */
    void onSysFail(int sysCode);

    /**
     * json格式内异常数据
     *
     * @param msg
     */
    void onReqFailed(String msg);
}
