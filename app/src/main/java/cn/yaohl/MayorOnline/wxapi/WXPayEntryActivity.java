package cn.yaohl.MayorOnline.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.yaohl.MayorOnline.util.Constant;
import cn.yaohl.MayorOnline.util.PayUtil;
import cn.yaohl.MayorOnline.util.ToastUtils;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, PayUtil.APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(WXPayEntryActivity.this, req.toString(), Toast.LENGTH_LONG);
    }


    @Override
    public void onResp(final BaseResp resp) {
//		Log.i("wxPay", "onResp: >>>"+resp.toString());
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage("微信支付结果"+String.valueOf(resp.errCode));
//			builder.show();
//		}

        if (Constant.WEIWHAT_PAY_TYPE == 0) {//会员的微信支付
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("fromType", 1);
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                intent.putExtra("isSuccess", true);
                intent.putExtra("fromWeiXin", true);
            } else {
                intent.putExtra("isSuccess", false);
            }
            startActivity(intent);
            finish();
        } else {//打赏的微信支付
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
//                Intent intent = new Intent(this, AdmireNewsResultActivity.class);
//                intent.putExtra("admireMoney", Constant.ADMIRE_PAY_MONRY);
//                startActivity(intent);
                ToastUtils.showToast("支付成功");
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

}