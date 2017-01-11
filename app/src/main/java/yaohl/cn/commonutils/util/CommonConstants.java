package yaohl.cn.commonutils.util;

/**
 * CommonConstant.java
 */
public class CommonConstants
{
    /**
     * 服务器ip
     */
    public static String IP = "119.254.210.58:8089/api/web/index.php";

    /**s
     * 正式服务器
     */
    public static String SERVER_IP = "api.szzc.com/api/web/index.php";

    public static String SERVER = "http://" + SERVER_IP + "/";

    /**
     * 服务器返回成功
     */
    public static final int SERVER_SUCCESS_CODE = 2000;

    /***
     * 服务器处理失败
     */
    public static final int SERVER_DEAL_FAILED = 5000;

    /**
     * 服務器數據不存在
     */
    public static final int SERVER_DATA_NOT_EXIST = 4000;

    /**
     * 参数错误
     */
    public static final int SERVER_PARAMS_ERROR = 1000;

    /**
     * 6000 短息验证码错误
     */
    public static final int SERVER_SMS_ERROR = 6000;

    /**
     * 7000 rpc调用错误
     */
    public static final int SERVER_START_ERROR = 7000;

}
