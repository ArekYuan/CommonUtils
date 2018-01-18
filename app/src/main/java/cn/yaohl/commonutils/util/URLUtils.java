package cn.yaohl.commonutils.util;

/**
 * 接口总汇
 */
public class URLUtils
{
    /**
     * 1 获取K线数据
     */
    public static final String GET_KLINE_DATA = "kline";

    /**
     * 2 获取最新K线数据
     */
    public static final String GET_KLIE_LAST_DATA = "kline/last";


    /**
     * 1 用户注册: /user/signup  请求方式：POST
     */
    public static final String POST_REGISTER_DATA = "user/signup";

    /**
     * 1.1 发送验证码
     */
    public static final String POST_REGISTER_SMS_DATA = "sms";

    /**
     * 2 用户登录: /user/login  请求方式：POST
     */
    public static final String POST_LOGIN_DATA = "user/login";

    /**
     * 11 获取所有市场:  /markets    请求方式:  GET
     */
    public static final String GET_ALL_MARKET = "markets";

    /**
     * 5 找回密码：/user/forget   请求方式：POST
     */
    public static final String POST_FIND_PWD = "user/forget";

    /**
     * 10 下单交易买单和卖单使用同一个接口:/trade/trade  请求方式：POST
     */
    public static final String POST_TRADE = "trade/trade";

    /**
     * 设置或修改资金密码：/user/capital   请求方式：POST
     */
    public static final String POST_SET_CAPITAL = "user/capital  ";

    /**
     * 7 提交充值账单:  /pays/recharge  请求方式：POST
     */
    public static final String POST_PAYS_RECHARGE = "pays/recharge";

    /**
     * 9 用户提现：/user/cash   请求方式：POST
     */
    public static final String POST_PAYS_CASH = "user/cash";

    /**
     * 查询用户人民币充值记录:  /pays     请求方式:  GET
     */
    public static final String GET_PAYS_UID = "pays";

    /**
     * 3 用户设置身份证: /user/set  请求方式：POST
     */
    public static final String POST_ID_SET = "user/set";

    /**
     * 11 撤单： /trade/cancel  请求方式：POST
     */
    public static final String POST_TRADE_CANCEL = "trade/cancel";

    /**
     * 18 绑定银行卡 /address/card  请求方式：POST
     */
    public static final String POST_ADDRESS_CARD = "address/card";

    /**
     * 19 查询用户绑定的银行卡列表  /address/cards  请求方式:GET
     */
    public static final String GET_FIND_ADDRESS_CARDS = "address/cards";

    /**
     * 8 查询用户持仓: /user/position   请求方式: POST
     */
    public static final String POST_USER_POSITION = "user/position";

    /**
     * 13 获得用户的三个币种的充值地址:  /wallets/uid   请求方式:  GET
     */
    public static final String GET_WALLETS_WITHDRAWLS = "wallet/index";

    /**
     * 20 用户解除绑定银行卡或者地址 /address/unbind  请求方式POST
     */
    public static final String POST_UNBIND_BANK = "address/unbind";

    /**
     * 4 单个用户信息:  /users/id      请求方式:  GET
     */
    public static final String GET_PERSONAL_AUTHOR = "users/";

    /**
     * 21 获取深度挂单队列  /kline/depth  请求方式GET
     */
    public static final String GET_KLINE_DEPTH = "kline/depth";

    /**
     * 22 获取委托记录  /trade/trades  请求方式post
     */
    public static final String POST_HISTORY_TRADES = "trade/trades";

    /**
     * 23 获取未成交委托记录  /trade/wait  请求方式post
     */
    public static final String POST_NOT_DEAL_TRADES = "trade/wait";

    /**
     * 22 获取新闻公告以及资讯 /news  请求方式GET
     */
    public static final String GET_NEWS = "news/index";

    /**
     * 付币
     */
    public static final String POST_DO_CASH = "dw/cash";

    /**
     * 提现记录
     */
    public static final String GET_PAY_CASH = "pay/cash";

    /**
     * 版本更新
     */
    public static final String GET_APP_UPDATE = "app/upgrade";

    /**
     * 反馈
     */
    public static final String GET_APP_FEEDBACK = "app/feedback";
}
