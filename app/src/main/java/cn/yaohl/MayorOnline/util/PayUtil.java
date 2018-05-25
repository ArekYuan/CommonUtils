package cn.yaohl.MayorOnline.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.yaohl.MayorOnline.alipay.PayResult;
import cn.yaohl.MayorOnline.wxapi.WeiChatCustomOrder;

/**
 * Created by Vincent Chen on 2018/4/23.
 */
public class PayUtil {

    public static final String APP_ID="wx4e8a40650ed4748e";
    public static final int ALIPAY_RESULR_FLAG = 0x66;
    private static AlipayResultCallback mCallback;



    public static void startWeiChatPay(Context context,WeiChatCustomOrder order){
        IWXAPI api= WXAPIFactory.createWXAPI(context, APP_ID);
        if(api.isWXAppInstalled()){
            api.sendReq(order.convert2WxPayReq());
        }else{
            Toast.makeText(context,"请先安装微信客户端",Toast.LENGTH_LONG).show();
        }
    }

    public static void startAlipay(final Activity activity, final String payInfo,AlipayResultCallback callback){
        mCallback=callback;
        Runnable payRunnable=new Runnable() {
            @Override
            public void run() {
                PayTask alipay=new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = ALIPAY_RESULR_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
    }


    //支付宝支付结果
    private static Handler handler=new Handler(){
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch(msg.what){
                case ALIPAY_RESULR_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if(mCallback!=null){
                            mCallback.paySuccess();
                        }
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if(mCallback!=null){
                            mCallback.payFail();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public interface AlipayResultCallback{
        void paySuccess();
        void payFail();
    }
}
