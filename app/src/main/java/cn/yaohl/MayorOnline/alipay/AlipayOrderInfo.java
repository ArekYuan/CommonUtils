package cn.yaohl.MayorOnline.alipay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Vincent Chen on 2018/4/23.
 */
public class AlipayOrderInfo {

    private String rsa_public="";
    private String rsa_private="";
    private String seller="";
    private String partner="";
    private String subject="";
    private String body="";
    private String price="";
    private String notify_url="";
    private String serialNum="";


    public void parseDataFromJsonStr(String jsonDataStr) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonDataStr);
            this.rsa_public = jsonObj.optString("pck");
            this.rsa_private = jsonObj.optString("pvk");
            this.seller = jsonObj.optString("sellerAccount");
            this.partner = jsonObj.optString("partnerId");
            this.subject = jsonObj.optString("subject");
            this.body = jsonObj.optString("body");
            this.price = jsonObj.optString("total_fee");
            this.notify_url = jsonObj.optString("notifyUrl");
            this.serialNum = jsonObj.optString("serialNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getAlipayOrderInfo(){
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + partner + "\"";
        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + seller + "\"";
        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + serialNum + "\"";
        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";
        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";
        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";
        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";
        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";
        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"10m\"";
        String sign=AlipayUtil.sign(orderInfo,rsa_private);
        String payInfo="";
        try {
            sign= URLEncoder.encode(sign, "UTF-8");
            payInfo = orderInfo + "&sign=\"" + sign + "\"&" + AlipayUtil.getSignType();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return payInfo;
    }
}
