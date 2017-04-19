package yaohl.cn.commonutils.newtwork.okhttp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import yaohl.cn.commonutils.util.CMLog;

/**
 * 文 件 名: GsonHelp.java
 */
public class GsonHelp
{
    /**
     * gson builder对象
     */
    private static GsonBuilder BUILDER;

    /**
     * gson对象
     */
    private static Gson GSON;

    static
    {
        BUILDER = new GsonBuilder();
        // 不转换没有 @Expose 注解的字段
        // BUILDER.excludeFieldsWithoutExposeAnnotation();
        GSON = BUILDER.create();
    }

    /**
     * <对象转换成json字符串> <功能详细描述>
     *
     * @param obj obj
     *
     * @return 返 回 类 型：String
     */
    public static String objectToJsonString(Object obj)
    {
        try
        {
            return GSON.toJson(obj);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * <把json string 转化成类对象> <功能详细描述>
     *
     * @param str json字符串
     * @param cls 对象
     *
     * @return 返 回 类 型：Object
     */
    public static Object jsonStringToObject(String str, Class<?> cls, Context context)
            throws Exception
    {
        try
        {
            return GSON.fromJson(str, cls);
        }
        catch (Exception e)
        {
            CMLog.e("jsonStringToObject", "解析错误" + cls + "\n" + e.getMessage(), context);
            e.printStackTrace();
            return "";
        }
    }

    /**
     * <把json string 转化成类对象> <功能详细描述>
     *
     * @param str json字符串
     * @param cls 对象
     *
     * @return 返 回 类 型：Object
     */
    public static Object jsonStringToObject(String str, Class<?> cls)
            throws Exception
    {
        str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        return GSON.fromJson(str, cls);
    }
}
