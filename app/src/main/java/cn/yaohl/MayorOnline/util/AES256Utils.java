package cn.yaohl.MayorOnline.util;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by asus on 2016/11/4.
 */

public class AES256Utils
{
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * 加密
     *
     * @param key  公钥
     * @param data 数据
     *
     * @return
     */
    public static String encrypt(String key, String data)
    {
        try
        {
            final Key keySpec = createKey(key);
            final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            final byte[] encoded = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeToString(encoded, Base64.DEFAULT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     *
     * @param key  公钥
     * @param data 数据
     *
     * @return
     */
    public static String decrypt(String key, String data)
    {
        try
        {
            final Key keySpec = createKey(key);
            final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            final byte[] bytes = Base64.decode(data.getBytes("UTF-8"), Base64.DEFAULT);
            return new String(cipher.doFinal(bytes));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static Key createKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] keyByte = digest.digest(key.getBytes("UTF-8"));
        return new SecretKeySpec(keyByte, KEY_ALGORITHM);
    }


}
