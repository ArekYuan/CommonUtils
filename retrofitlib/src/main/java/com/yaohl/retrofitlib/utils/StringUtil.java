package com.yaohl.retrofitlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.yaohl.retrofitlib.log.YLog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文 件 名: StringUtil.java 版 权: 描 述: <String工具类> 版 本： <版本号> 创 建 人: 创建时间: 2015年10月14日
 */
@SuppressWarnings("ALL")
public class StringUtil {
    /**
     * 预防点击两次
     */
    private static final int MIN_CLICK_DELAY_TIME = 1000;

    private static long lastClickTime = 0;
    /**
     * 字母，数字集合
     */
    private static final String TAG = "StringUtil";
    private static Toast mToast = null;

    /**
     * 字母，数字集合
     */
    private static final byte[] CHARS = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9'};

    /**
     * <判断字符串是或否为空> <功能详细描述>
     *
     * @param str 字符
     * @return true 空 false 非空 返 回 类 型：boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 ? true : false;
    }

    /**
     * <判断字符串是否有空格字符,是否有实际内容> <功能详细描述>
     *
     * @param str 字符串
     * @return true 有，false 没有 返 回 类 型：boolean
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * <判断字符串是否相等> <功能详细描述>
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return true相等 false 不相等 返 回 类 型：boolean
     */
    public static boolean isEquals(String str1, String str2) {
        if (str1 == str2) {
            return true;
        }
        if (str1 != null) {
            return str1.equals(str2);
        } else {
            return false;
        }
    }

    /**
     * <判断字符串是否相等> <功能详细描述>
     *
     * @return true相等 false 不相等 返 回 类 型：boolean
     */
    public static String getHandSetInfo() {
        String handSetInfo =
                "手机型号:" + Build.MODEL + ",SDK版本:" + Build.VERSION.SDK_INT + ",系统RELEASE版本:"
                        + Build.VERSION.RELEASE;
        // handSetInfo: 手机型号:Redmi Note 2,SDK版本:21,系统版本:5.0.2
        YLog.d("handSetInfo: " + handSetInfo);
        return handSetInfo;

    }

    /**
     * <是否是数字> <功能详细描述>
     *
     * @param str 字符串
     * @return true是 flase不是 返 回 类 型：boolean
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * <获取字节长度> <功能详细描述>
     *
     * @param str 字符串
     * @return 字节长度 返 回 类 型：boolean
     */
    public static int getLengthByByte(String str) {
        int length = 0;
        if (str == null || str.length() == 0) {
            return length;
        }

        for (int i = 0; i < str.length(); i++) {
            int ascii = Character.codePointAt(str, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }

    /**
     * <还原字符串中特殊字符> <功能详细描述>
     *
     * @param strData strData
     * @return 返 回 类 型：String
     */
    public static String decodeString(String strData) {
        if (strData == null) {
            return "";
        }
        return strData.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&apos;", "'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&");
    }

    /**
     * <获取用户手机IMEI> <功能详细描述>
     *
     * @param context context
     * @return 返 回 类 型：String
     */
    public static String getPhoneImei(Context context) {
        TelephonyManager mTelephonyMgr;

        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return mTelephonyMgr.getDeviceId();
    }

    /**
     * <获取版本名称> <功能详细描述>
     *
     * @param context context
     * @return 返 回 类 型：String
     */
    public static String getVersion(Context context) {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * <获取版本号> <功能详细描述>
     *
     * @param context context
     * @return 当前应用的版本号 ViersionCode 返 回 类 型：String
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * <获取手机型号> <功能详细描述>
     *
     * @return 当前应用的版本号 ViersionCode 返 回 类 型：String
     */
    public static String getModel() {
        String version = "";
        version = Build.MODEL;
        return version;
    }

    /**
     * <获取手机操作系统版本> <功能详细描述>
     *
     * @return 操作系统版本 返 回 类 型：String
     */
    public static String getRelease() {
        String version = "";
        version = Build.VERSION.RELEASE;
        return version;
    }

    /**
     * <保留两位有效数字> <功能详细描述>
     *
     * @param num num
     * @return 返 回 类 型：String
     */
    public static String retained2SignFig(String num) {
        return new BigDecimal(num).setScale(2, RoundingMode.HALF_UP).toString();
    }

    /**
     * <减法运算并保留两位有效数字> <功能详细描述>
     *
     * @param subt1 subt1
     * @param subt2 subt2
     * @return 返 回 类 型：String
     */
    public static String subtract(String subt1, String subt2) {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <加法运算并保留两位有效数字> <功能详细描述>
     *
     * @param addend1 addend1
     * @param addend2 addend2
     * @return 返 回 类 型：String
     */
    public static String add(String addend1, String addend2) {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <加法运算 整数> <功能详细描述>
     *
     * @param addend1 addend1
     * @param addend2 addend2
     * @return 返 回 类 型：String
     */
    public static String addInt(String addend1, String addend2) {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        return result.toString();
    }

    /**
     * <乘法运算并保留两位有效数字> <功能详细描述>
     *
     * @param factor1 factor1
     * @param factor2 factor2
     * @return 返 回 类 型：String
     */
    public static String multiply(String factor1, String factor2) {
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     *
     * @param divisor1 divisor1
     * @param divisor2 divisor2
     * @return 返 回 类 型：String
     */
    public static String divide(String divisor1, String divisor2) {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     *
     * @param divisor1 divisor1
     * @param divisor2 divisor2
     * @return 返 回 类 型：String
     */
    public static String dividePoint1(String divisor1, String divisor2) {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 1, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <如果距离超过1000米则转换成1km> <功能详细描述>
     *
     * @param m 米
     * @return 返 回 类 型：String
     */
    public static String convertToKm(String m) {
        if (m.length() < 4) {
            return m;
        } else {
            return dividePoint1(m, "1000");
        }
    }

    /**
     * <生成6位数的随机数> <功能详细描述>
     *
     * @return 返 回 类 型：int
     */
    public static String getRandomNum() {
        Random rdm = new Random();
        return String.valueOf(rdm.nextInt(900000) + 100000);
    }

    /**
     * <判断手机号码> <功能详细描述>
     *
     * @param phoneNumber phoneNumber
     * @return 返回类型:boolean
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        // 移动
        String mobile = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
        // 联通
        String unicom = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";
        // 电信
        String telecom = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

        Pattern mobileP = Pattern.compile(mobile);
        Matcher mobileM = mobileP.matcher(phoneNumber);

        Pattern unicomP = Pattern.compile(unicom);
        Matcher unicomM = unicomP.matcher(phoneNumber);

        Pattern telecomP = Pattern.compile(telecom);
        Matcher telecomM = telecomP.matcher(phoneNumber);

        return mobileM.matches() | unicomM.matches() | telecomM.matches();

    }

    /**
     * <判断邮箱> <功能详细描述>
     *
     * @param email
     * @return 返回类型:boolean
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();

    }

    /**
     * <判断手机号码---来自服务端 孙月军提供>
     *
     * @param phoneNumber phoneNumber
     * @return 返回类型:boolean
     */
    @SuppressWarnings("WrongConstant")
    public static boolean isPhoneNumber(String phoneNumber) {
        String reg = "^1[34578]{1}\\d{9}$";
        boolean ignoreCase = true;
        if (StringUtil.isEmpty(phoneNumber)) {
            return false;
        }
        Pattern pattern = null;
        if (ignoreCase) {
            pattern = Pattern.compile(reg, 2);
        } else {
            pattern = Pattern.compile(reg);
        }
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    /**
     * <半角转换为全角> <即将所有的数字、字母及标点全部转为全角字符，<br/>
     * 使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。<br/>
     * 半角转为全角的代码如下，只需调用即可
     *
     * @param input
     * @return 返回类型:String
     */
    public static String toDBC(String input) {
        if (isEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 获取二维码解析数据 <根据URL得到参数，URL类型：http://www.baidu.com?accountCid=" + accountCid + "&accountType=" + accountType
     * https://www.baidu.com?accountCid=" + accountCid + "&accountType=" + accountType> <功能详细描述>
     *
     * @param https
     * @return 返回类型:Map<String,Object>
     */
    public static Map<String, String> getParams(String https) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtil.isEmpty(https) || (!https.startsWith("http://") && !https.startsWith("https://"))) {
            return map;
        }

        String params = https.substring(https.indexOf("?") + 1);
        String[] paramsKeys = params.split("&");
        for (int i = 0; i < paramsKeys.length; i++) {
            String[] keys = paramsKeys[i].split("=");
            if (keys.length >= 2) {
                map.put(keys[0], keys[1]);
            }
        }
        return map;
    }

    /**
     * get window x,y
     *
     * @param context
     * @return dm DisplayMetrics
     */
    public static DisplayMetrics getWindowXY(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void hideSoftInput(Context context) {
        Activity activity = (Activity) context;
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) activity.getSystemService(context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    activity.getCurrentFocus()
                            .getWindowToken(),
                    0
            );
        }
    }

    /**
     * 文件的大小显示的方法
     *
     * @param length
     * @return
     */
    public static String convertFileSize(long length) {
        int sub_index = 0;
        String show = "";
        if (length >= 1073741824) {
            sub_index = (String.valueOf((float) length / 1073741824)).indexOf(".");
            show = ((float) length / 1073741824 + "000").substring(0, sub_index + 3) + "GB";
        } else if (length >= 1048576) {
            sub_index = (String.valueOf((float) length / 1048576)).indexOf(".");
            show = ((float) length / 1048576 + "000").substring(0, sub_index + 3) + "MB";
        } else if (length >= 1024) {
            sub_index = (String.valueOf((float) length / 1024)).indexOf(".");
            show = ((float) length / 1024 + "000").substring(0, sub_index + 3) + "KB";
        } else if (length < 1024) {
            show = String.valueOf(length) + "B";
        }

        return show;
    }

    /**
     * 预防两次点击
     *
     * @return
     */
    public static boolean isCheckedTwiceClick() {
        boolean b;
        long currentTimes = System.currentTimeMillis();
        if (currentTimes - lastClickTime > MIN_CLICK_DELAY_TIME) {
            b = true;
        } else {
            b = false;
        }
        lastClickTime = currentTimes;
        return b;

    }

    /**
     * 保留小数点后两位
     *
     * @return
     */
/*    public static String saveTwoNum(long num)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format((num / 100));
    }*/
    public static String saveTwoNumDouble(Double num) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format((num / 100));
    }

    public static String saveTwoNumPrice(Double num) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format((num));
    }

    /**
     * double 数据保留小数点后两位数字
     *
     * @param num
     * @return
     */
    public static String twoDeciamal(double num) {
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(2);

        return ddf1.format(num);

    }

    public static String twoEnd(float num) {
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(2);

        return ddf1.format(num);
    }

    /**
     * 根据用户名的不同长度，来进行替换 ，达到保密效果
     *
     * @param str 用户名
     * @return 替换后的用户名
     */
    public static String userNameReplaceWithStar(String str, int index_one, int index) {
        String subOne = "";
        String sub = "";
        try {
            subOne = str.substring(0, index_one);
            sub = str.substring(index_one, str.length() - index);
            int firstLen = str.length() - index - index_one;
            sub = str.substring(str.length() - index, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < firstLen; i++) {
                sb = sb.append("*");
            }
//            sub += sb.toString();
            sub = subOne + sb.append(sub).toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sub;

    }

    /**
     * 保留银行卡后4位数字
     *
     * @param str
     * @param index
     * @return
     */
    public static String bankIdNameSpaceWith(String str, int index) {
        String sub = "";
        try {
//            sub = str.substring(0, str.length() - index);
            int firstLen = str.length() - index;
            sub = str.substring(firstLen, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < firstLen; i++) {
                sb = sb.append("*");
            }
//            sub += sb.toString();
            sub = sb.append(sub).toString();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return spaceForBankIdStr(sub);
    }

    /**
     * 银行卡 每4位空格
     *
     * @param num
     * @return
     */
    public static String spaceForBankIdStr(String num) {
        String regex = "(.{4})";
        return num.replaceAll(regex, "$1 ");
    }


    /**
     * 数量的操作
     * 以百万和亿为单位进行区分
     *
     * @param num
     * @return
     */
    public static String numDeal(double num, int ratio) {
        String a = "";
        String amout = "";
        if (ratio == 2) {
            a = "#0.00";
        } else if (ratio == 3) {
            a = "#0.000";
        } else if (ratio == 6) {
            a = "#0.000000";
        }
        DecimalFormat df = new DecimalFormat(a);
        num = num / 1000;
        if (num < 100000) {
            amout = df.format(num);
        } else if (num < 1000000) {
            amout = df.format(num / 100000) + "十万";
        } else if (num < 100000000) {
            amout = df.format(num / 1000000) + "百万";
        } else if (num >= 100000000) {
            amout = df.format(num / 100000000) + "亿";
        }

        return amout;

    }

    /**
     * 密码强度判断
     *
     * @param password
     *
     * @return
     */
//    public static boolean checkpassword(String password)
//    {
//        Pattern p = Pattern.compile("^[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
//        Matcher m = p.matcher(password);
//        return m.matches();
//    }

    /**
     * 密码判断长度判断
     *
     * @param password
     * @return
     */
    public static boolean checkpassword(String password) {
        boolean b = false;
        int len;
        if (!StringUtil.isEmpty(password)) {
            len = password.length();
        } else {
            len = 0;
        }
        if (len < 6) {
            b = false;
        } else if (len >= 6) {
            b = true;
        }
        return b;
    }

    /**
     * 判断 软键盘 是否隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param mContext
     * @param token
     */
    public static void hideSoftInput(Context mContext, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断时间是否在24小时内
     *
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static boolean judgmentDate(String date1, String date2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();

        if (cha < 0) {
            return false;
        }

        double result = cha * 1.0 / (1000 * 60 * 60);

        if (result <= 24) {
            return true;
        } else {
            return false;
        }
    }
}
