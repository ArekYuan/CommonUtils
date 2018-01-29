package cn.yaohl.MayorOnline.util;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;


import com.yaohl.retrofitlib.log.YLog;

import java.util.List;

/**
 *
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CommonUtils {

    public static int dip(Context context, int pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue,
                context.getResources().getDisplayMetrics());
    }

    public static int px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static ValueAnimator createHeightAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        // animator.setDuration(DURATION);
        return animator;
    }

    /**
     * 收缩动画
     */
    @SuppressLint("NewApi")
    public static void animateCollapsing(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    public static ValueAnimator createTopMarginAnimatorForLl(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                ((LinearLayout.LayoutParams) layoutParams).setMargins(0, value, 0, 0);
                // layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
//		animator.setDuration(500000);
        animator.setDuration(500);
        return animator;
    }

    /**
     * 扩展动画
     */
    public static boolean animateExpanding(final View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return false;
        }
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
        animator.start();
        return true;
    }

    public static boolean animateExpanding(final View view, Handler handler) {
        if (view.getVisibility() == View.VISIBLE) {
            return false;
        }
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());

        animator.start();
        handler.sendMessageDelayed(new Message(), animator.getDuration() + 80);
        return true;
    }

    public static float getTextWidth(Context Context, String text, int textSize, TextPaint paint) {
        float scaledDensity = Context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }

    /**
     * 将汉字转化为拼音
     *
     * @param inputString
     * @return
     */
//    public static String getPingYin(String inputString) {
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
//
//        char[] input = inputString.trim().toCharArray();
//        String output = "";
//
//        try {
//            for (char curchar : input) {
//                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
//                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
//                    output += temp[0];
//                } else
//                    output += java.lang.Character.toString(curchar);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        return output;
//    }


    /**
     * 隐藏 软键盘
     *
     * @param event     touch event
     * @param mActivity who's pager
     */
    public static void hideSoft(MotionEvent event, Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mActivity.getCurrentFocus() != null) {
                if (mActivity.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param className 某个界面名称(包名+类名)
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            YLog.d(className + "....." + cpn.getClassName());
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断app是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals(getPackageName(context));
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
