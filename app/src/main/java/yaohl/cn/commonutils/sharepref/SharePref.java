package yaohl.cn.commonutils.sharepref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import yaohl.cn.commonutils.util.CMLog;
import yaohl.cn.commonutils.util.Constant;

import static java.lang.Double.parseDouble;

/**
 * Created by asus on 2017/1/11.
 */

public class SharePref
{

    private static final String TAG = "SharePref";

    /**
     * SharedPreferences对象
     */
    private SharedPreferences pref;

    /**
     * editor对象
     */
    private SharedPreferences.Editor editor;

    /**
     * 帐号 手机
     */
    private final String account_mobile = "account_mobile";

    /**
     * session
     */
    private final String sessionId_server = "sessionId_server";

    /**
     * /**
     * 首次使用APP
     */
    public static final String ISFRIST = "IS_FRIST";

    /**
     * id
     */
    public static final String Id = "ID";

    /**
     * 是否打开手势
     */
    public static final String IS_OPEN_GESTURE = "is_open_gesture";

    /**
     * 手势密码
     */
    public static final String GESTURE_PASSWORD = "gesture_password";

    /**
     * 刷新频率
     */
    public static final String SETTING_REFRESH = "setting_refresh";

    /**
     * 市场 名称
     */
    public static final String TRADE_MARKET_NAME = "trade_market_name";

    /**
     * 公钥
     */
    public static final String PUBLIC_KEY = "public_key";

    /**
     *
     */
    public static final String AES_CODE = "aes_code";

    /**
     * 资金密码
     */
    public static final String CAPTIAL_PWD = "captial_pwd";

    /**
     * cny 余额
     */
    public static final String CNY_BALANCE = "cny_balance";

    /**
     * 总资产
     */
    public static final String TOTAL_MAONY = "total_money";

    @SuppressLint("CommitPrefEdits")
    public SharePref(Context context)
    {
        pref = context.getSharedPreferences(Constant.SZZC_HFT_INFO, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * 保存余额
     *
     * @param balance
     */
    public void saveCnyBalance(String balance)
    {
        editor.putString(this.CNY_BALANCE, balance);
        editor.commit();
    }

    /**
     * 获取余额
     *
     * @return
     */
    public double getCnyBalance()
    {
        String cnyB = pref.getString(CNY_BALANCE, "0");
        return parseDouble(cnyB);
    }

    /**
     * 保存总资产
     *
     * @param total
     */
    public void saveTotalMoney(String total)
    {
        editor.putString(this.TOTAL_MAONY, total);
        editor.commit();
    }

    /**
     * 获取总资产
     *
     * @return
     */
    public double getTotalMoney()
    {
        String totalM = pref.getString(TOTAL_MAONY, "0");
        return parseDouble(totalM);
    }

    /**
     * 保存 市场 名称
     *
     * @param name
     */
    public void saveTradeMraketName(String name)
    {
        editor.putString(this.TRADE_MARKET_NAME, name);
        editor.commit();
    }

    /**
     * 取出 市场名称
     *
     * @return
     */
    public String getTradeMarketName()
    {
        return pref.getString(this.TRADE_MARKET_NAME, "");
    }

    /**
     * 保存刷新选项
     *
     * @param id
     */
    public void saveRefreshId(int id)
    {
        editor.putInt(SETTING_REFRESH, id);
        editor.commit();
    }

    /**
     * 获取刷新选项
     *
     * @return
     */
    public int getRefreshId()
    {
        return pref.getInt(SETTING_REFRESH, 1);
    }

    /***
     * 保存ID
     *
     * @param id
     */
    public void saveId(int id)
    {
        editor.putInt(Id, id);
        editor.commit();
    }

    /**
     * 取出id
     *
     * @return
     */
    public int getId()
    {
        return pref.getInt(Id, 0);
    }

    /**
     * <首次使用APP> <功能详细描述> 返回类型:void
     */
    public void putIsFrist()
    {
        editor.putBoolean(ISFRIST, false);
        editor.commit();
    }

    /**
     * <首次使用APP> <功能详细描述>
     *
     * @return 返回类型:boolean
     */
    public boolean getIsFrist()
    {
        return pref.getBoolean(ISFRIST, true);
    }

    /**
     * 在设置页面将打开手势密码的状态保存下来，供下次使用
     */
    public void putIsOpenGesture(boolean isOpenGesture)
    {
        editor.putBoolean(IS_OPEN_GESTURE, isOpenGesture);
        editor.commit();
    }

    /**
     * isOpen===true 代表 打开手势校验
     *
     * @return
     */
    public boolean getIsOpenGeture()
    {
        return pref.getBoolean(IS_OPEN_GESTURE, false);
    }


    /**
     * 获取 公钥
     *
     * @return
     */
    public String getPublicKey()
    {
        return pref.getString(PUBLIC_KEY, "16TEST+BYTESjM==");
    }


    /**
     * <清除本地数据> <功能详细描述> 返回类型:void
     */
    public void clearData()
    {
        if (null != editor)
        {
            editor.clear().commit();
            CMLog.d(TAG, "sharePref 清除缓存成功");
        }
    }

    /**
     * 保存用户帐号 手机
     *
     * @param mobile
     */
    public void saveAccount(String mobile)
    {
        editor.putString(this.account_mobile, mobile);
        editor.commit();
    }

    /**
     * 找到账户
     *
     * @return
     */
    public String getAccount()
    {
        return pref.getString(account_mobile, "");
    }

    /**
     * 保存sessionid
     *
     * @param sessionId
     */
    public void saveSessionId(String sessionId)
    {
        editor.putString(sessionId_server, sessionId);
        editor.commit();
    }

    /**
     * 取出sessionID
     *
     * @return
     */
    public String getSessionId()
    {
        return pref.getString(this.sessionId_server, "");
    }

    /**
     * 保存手势密码
     *
     * @param inputCode
     */
    public void saveGesturePwd(String inputCode)
    {
        editor.putString(GESTURE_PASSWORD, inputCode);
        editor.commit();
    }

    /**
     * 获取保存的手势密码
     *
     * @return
     */
    public String getGesturePwd()
    {
        return pref.getString(GESTURE_PASSWORD, "");
    }

    /**
     * 保存AES
     *
     * @param encryptResult
     */
    public void putMoneyPassWord(String encryptResult)
    {
        editor.putString(AES_CODE, encryptResult);
        editor.commit();
    }

    /**
     * @return
     */
    public String getMoneyPassWord()
    {
        return pref.getString(this.AES_CODE, "");
    }

    /**
     * 可用币
     */
    public static final String BUY_LOTS = "buy_lots";

    public void saveBuyLots(String buyLots)
    {
        editor.putString(this.BUY_LOTS, buyLots);
        editor.commit();
    }

    public String getBuyLots()
    {
        return pref.getString(BUY_LOTS, "0");
    }

    public static final String LATEST_PRICE = "latest_price";

    public void saveLatestPrice(String last)
    {
        editor.putString(LATEST_PRICE, last);
        editor.commit();
    }

    public String getLatestPrice()
    {
        return pref.getString(LATEST_PRICE, "0");
    }


    public static final String ISTRUE = "istrue";

    public void saveIsSucrity(int istrue)
    {
        editor.putInt(ISTRUE, istrue);
        editor.commit();
    }

    public int getISTrue()
    {
        return pref.getInt(ISTRUE, 0);
    }

    /**
     * 隐藏资金密码状态
     */
    public static final String HIDE_ASSETS = "hide_assets";

    /**
     * 保存隐藏资金密码状态
     *
     * @param assetsNum
     */
    public void saveHideAssetsMoney(String assetsNum)
    {
        editor.putString(this.HIDE_ASSETS, assetsNum);
        editor.commit();
    }

    /**
     * 获取隐藏资金密码状态
     *
     * @return
     */
    public String getHideAssetsMoney()
    {
        return pref.getString(HIDE_ASSETS, "");
    }

    /**
     * 可用cny
     */
    public static final String AVAILABLE_ASSETS = "available_assets";

    /**
     * 保存可用资产
     *
     * @param assetsNum
     */
    public void saveAvailableAssetsMoney(String assetsNum)
    {
        editor.putString(this.AVAILABLE_ASSETS, assetsNum);
        editor.commit();
    }

    /**
     * 获取可用资产
     *
     * @return
     */
    public String getAvailableAssetsMoney()
    {
        return pref.getString(AVAILABLE_ASSETS, "");
    }

    /**
     * 冻结cny
     */
    public static final String FROZEN_ASSETS = "frozen_assets";

    /**
     * 保存冻结资产
     *
     * @param assetsNum
     */
    public void saveFrozenAssetsMoney(String assetsNum)
    {
        editor.putString(this.FROZEN_ASSETS, assetsNum);
        editor.commit();
    }

    /**
     * 获取冻结资产
     *
     * @return
     */
    public String getFrozenAssetsMoney()
    {
        return pref.getString(FROZEN_ASSETS, "");
    }
}
