package cn.yaohl.MayorOnline.sharepref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import cn.yaohl.MayorOnline.util.Constant;

/**
 * Created by asus on 2017/1/11.
 */

public class SharePref {
    /**
     * SharedPreferences对象
     */
    private SharedPreferences pref;

    /**
     * editor对象
     */
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharePref(Context context) {
        pref = context.getSharedPreferences(Constant.SZZC_HFT_INFO, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        if (null != editor) {
            editor.clear().commit();
        }
    }

    /**
     * 缓存 String类型的数据
     *
     * @param key
     * @param value
     */
    public void setStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取 String类型的数据
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        String value = pref.getString(key, "");
        return value;
    }

    /**
     * 缓存 int 类型的数据
     *
     * @param key
     * @param value
     */
    public void setIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取 int 类型的数据
     *
     * @param key
     * @return
     */
    public int getIntValue(String key) {
        int value = pref.getInt(key, -1);
        return value;
    }

    /**
     * 缓存 int 类型的数据
     *
     * @param key
     * @param value
     */
    public void setFloatValue(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 获取 int 类型的数据
     *
     * @param key
     * @return
     */
    public float getFloatValue(String key) {
        float value = pref.getFloat(key, 0f);
        return value;
    }

    /**
     * 缓存 int 类型的数据
     *
     * @param key
     * @param value
     */
    public void setBooleanValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取 int 类型的数据
     *
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key, boolean defValue) {
        boolean value = pref.getBoolean(key, defValue);
        return value;
    }
}
