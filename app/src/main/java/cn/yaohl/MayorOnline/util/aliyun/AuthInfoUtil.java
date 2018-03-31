package cn.yaohl.MayorOnline.util.aliyun;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by pengshuang on 31/08/2017.
 */

public class AuthInfoUtil {

    private String mediaId;
    private String expireTime;
    private String signature;
    private String secret;

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String generateSign() {
        if (null == getMediaId() || null == getExpireTime()) {
            return null;
        }

        Map<String, String> parameters = new TreeMap<>();
        parameters.put("MediaId", getMediaId());
        parameters.put("ExpireTime", getExpireTime());

        try {
            String strToSign = concatQueryString(parameters);
            System.out.println(strToSign + ": secret: " + getSecret());
            return signString(strToSign, getSecret());
        } catch (InvalidKeyException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String concatQueryString(Map<String, String> parameters)
            throws UnsupportedEncodingException {
        if (null == parameters)
            return null;

        StringBuilder urlBuilder = new StringBuilder("");
        for(Map.Entry<String, String> entry : parameters.entrySet()){
            String key = entry.getKey();
            String val = entry.getValue();
            urlBuilder.append(URLEncoder.encode(key,"UTF-8"));
            if (val != null){
                urlBuilder.append("=").append(URLEncoder.encode(val,"UTF-8"));
            }
            urlBuilder.append("&");
        }

        int strIndex = urlBuilder.length();
        if (parameters.size() > 0)
            urlBuilder.deleteCharAt(strIndex - 1);

        return urlBuilder.toString();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    private final static String AGLORITHM_NAME = "HmacSHA1";

    public String signString(String source, String accessSecret)
            throws InvalidKeyException, IllegalStateException {
        try {
            Mac mac = Mac.getInstance(AGLORITHM_NAME);
            mac.init(new SecretKeySpec(
                    URLEncoder.encode(accessSecret, "UTF-8").getBytes(),AGLORITHM_NAME));
            byte[] signData = mac.doFinal(source.getBytes());
            return Base64Helper.encode(signData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HMAC-SHA1 not supported.");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }

    }

    public String getSignerName() {
        return "HMAC-SHA1";
    }

    public String getSignerVersion() {
        return "1.0";
    }
}
