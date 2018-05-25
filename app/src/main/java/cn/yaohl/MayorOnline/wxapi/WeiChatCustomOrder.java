package cn.yaohl.MayorOnline.wxapi;

import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vincent Chen on 2018/4/23.
 */
public class WeiChatCustomOrder {
    private String appid="";
    private String partnerid="";
    private String prepayid="";
    private String appPackage="Sign=WXPay";
    private String timeStamp="";
    private String nonceStr="";
    private String wxPayOrderSign="";
    private String extData="app data";


    public void parseDataFromJsonStr(String jsonDataStr) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonDataStr);
            this.appid = jsonObj.optString("appId");
            this.partnerid = jsonObj.optString("partnerId");

            this.prepayid = jsonObj.optString("prepayId");

            this.nonceStr = jsonObj.optString("noncestr");

            this.timeStamp = jsonObj.optString("timeStamp");

            this.wxPayOrderSign = jsonObj.optString("sign");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PayReq convert2WxPayReq() {
        PayReq wxPayReq = new PayReq();
        wxPayReq.appId = this.appid;
        wxPayReq.partnerId = this.partnerid;
        wxPayReq.prepayId = this.prepayid;
        wxPayReq.packageValue = this.appPackage;
        wxPayReq.timeStamp = this.timeStamp;
        wxPayReq.nonceStr = this.nonceStr;
        wxPayReq.sign = this.wxPayOrderSign;
        wxPayReq.extData=this.extData;
        return wxPayReq;
    }
}
