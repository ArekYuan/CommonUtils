/**
 * <一句话功能简述>
 * <功能详细描述>
 * <p>
 * 创 建 人  8106589
 * 版 本  [版本号, 2016年6月28日]
 * 模 块  [产品/模块版本]
 */
package yaohl.cn.commonutils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import yaohl.cn.commonutils.util.Constant;


public class VerifyBroadcast extends BroadcastReceiver {


    public static final String USERACCOUNTFROM = "userAccount";


    @Override
    public void onReceive(final Context context, Intent intent) {
        //登录过期
        if (TextUtils.equals(Constant.PACKAGE_NAME_INFO, intent.getAction())) {
//            ToastUtils.showToast("登录失效，请重新登录");
//            DialogUtil.showTwoButtonDialog(SuNingApplication.getApplication().currentActivity(),
//                    new DialogCallBack(SuNingApplication.getApplication().currentActivity()) {
//                        @Override
//                        public String dialogTitleText() {
//                            return "注意";
//                        }
//
//                        @Override
//                        public String dialogContextText() {
//                            return "登录失效，请重新登录";
//                        }
//
//                        @Override
//                        public void dialogRightBtnClick() {
//                            Intent loginIntent = new Intent(context, LoginActivity.class);
//                            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                            context.startActivity(loginIntent);
//                            SuNingApplication.getApplication().clearAllActivities();
//                            SuNingApplication.getInstance().logintOut(context);
//                        }
//                    });
        }

    }

}
