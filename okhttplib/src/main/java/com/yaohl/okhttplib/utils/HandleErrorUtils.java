/**
 * <一句话功能简述>
 * <功能详细描述>
 * <p>
 * 创 建 人  8106589
 * 版 本  [版本号, 2016年7月19日]
 * 模 块  [产品/模块版本]
 */
package com.yaohl.okhttplib.utils;


import android.content.Context;
import android.content.Intent;

import com.yaohl.okhttplib.log.YLog;
import com.yaohl.okhttplib.okhttp.OkUiCallBack;


public class HandleErrorUtils {
    private static HandleErrorUtils handlerErrorUtils;

    public static HandleErrorUtils getInstance() {
        if (null == handlerErrorUtils) {
            handlerErrorUtils = new HandleErrorUtils();
        }
        return handlerErrorUtils;
    }

    /**
     * 发送全局广播 通知页面 弹出提示框
     *
     * @param context
     * @param callBack
     * @param errorCode
     * @param msg
     * @return
     */
    public boolean handle(Context context, OkUiCallBack callBack, String errorCode, String msg) {
        YLog.d("HandlerH5Error: " + errorCode);
//        CMLog.d(fromTag, "HandlerH5Error: " + errorCode);
        if (SeverConstants.SERVER_SUCCESS_CODE.equals(errorCode)) {
            return true;
        } else {
            switch (errorCode) {
                case "101"://登录失效
                    Intent intent = new Intent();
                    if (StringUtil.isEquals(context.getApplicationInfo().packageName, SeverConstants.PACKAGE_NAME_SUNING)) {
                        intent.setAction(SeverConstants.PACKAGE_NAME_SUNING);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Constant.)
                        context.sendBroadcast(intent);
                    }
                    break;
                default:
                    if (null != callBack) {
                        callBack.onFail(errorCode, msg);
                    }
                    break;
            }
        }
        return false;
    }
}
