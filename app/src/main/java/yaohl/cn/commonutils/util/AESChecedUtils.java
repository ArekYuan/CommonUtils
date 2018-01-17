package yaohl.cn.commonutils.util;

import android.content.Context;


import com.yaohl.retrofitlib.utils.StringUtil;

import yaohl.cn.commonutils.alert.MToast;
import yaohl.cn.commonutils.sharepref.SharePref;

/**
 * Created by asus on 2016/11/8.
 */

public class AESChecedUtils
{
    /**
     * tag
     */
    private static final String TAG = AESChecedUtils.class.getSimpleName();

    /**
     * 检查输入的资金密码是否正确
     *
     * @param mContext
     *
     * @return
     */
    public static boolean isCheckAES(Context mContext, String captialPwd)
    {
        boolean b;

        String moneyPwd = new SharePref(mContext).getMoneyPassWord();
        String publicKey = new SharePref(mContext).getPublicKey();
        if (!StringUtil.isEmpty(moneyPwd))
        {
            String password1 = AES256Utils.encrypt(publicKey, captialPwd);
            CMLog.e(TAG, "---->" + password1);
            String password = password1.substring(0, password1.length() - 1);
            if (StringUtil.isEquals(moneyPwd, password))
            {
                b = true;
            }
            else
            {
                MToast.showMsg(mContext, "资金密码不正确");
                b = false;
            }
        }
        else
        {
            b = false;
        }
        return b;
    }


}
