package yaohl.cn.commonutils.newtwork.okhttp;

/**
 * okHttp service
 * <p>
 * Created by Administrator on 2017/4/19.
 */

public class OkHttpService
{

    /**
     * 访问网络单子例对象
     */
    private static OkHttpService CONNECTSERVICESINGLE = null;

    /**
     * <返回访问网络单子例对象> <功能详细描述>
     *
     * @return 返 回 类 型：ConnectSersvice
     */
    public static OkHttpService getInstance()
    {
        if (CONNECTSERVICESINGLE == null)
        {
            CONNECTSERVICESINGLE = new OkHttpService();
        }
        return CONNECTSERVICESINGLE;
    }
}
