package cn.yaohl.MayorOnline.alipay;

public class AlipayUtil {

		public static String sign(String content,String key) {
			return SignUtils.sign(content, key);
		}
		public static String getSignType() {
			return "sign_type=\"RSA\"";
		}
}
