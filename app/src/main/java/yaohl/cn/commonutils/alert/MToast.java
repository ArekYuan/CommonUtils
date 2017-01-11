package yaohl.cn.commonutils.alert;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yaohl.cn.commonutils.R;


/**
 * 自定义toast
 */
public class MToast
{
    private static String text = "";
    private static View mView;
    private static LayoutInflater mInflater;
    private static Toast mToast;

    /**
     * showmsg
     *
     * @param mContext
     * @param text     文字
     */
    public static void showMsg(Context mContext, String text)
    {
        showMsg(mContext, text, Toast.LENGTH_SHORT);
    }

    /**
     * showmsg
     *
     * @param mContext
     * @param resId    资源ID
     */
    public static void showMsg(Context mContext, int resId)
    {
        showMsg(mContext, resId, Toast.LENGTH_SHORT);
    }

    /**
     * @param mContext
     * @param resId
     * @param lengthShort
     */
    public static void showMsg(Context mContext, int resId, int lengthShort)
    {
        mInflater = LayoutInflater.from(mContext);
        mView = mInflater.inflate(R.layout.toast_layout_bg, null);
        initText(mView, mContext.getResources().getString(resId));
        mToast = new Toast(mContext);
        mToast.setView(mView);
        mToast.setDuration(lengthShort);
        mToast.show();
    }

    /**
     * 显示文本内容
     *
     * @param mContext
     * @param text
     * @param lengthShort
     */
    public static void showMsg(Context mContext, String text, int lengthShort)
    {
        mInflater = LayoutInflater.from(mContext);
        mView = mInflater.inflate(R.layout.toast_layout_bg, null);
        initText(mView, text);
        mToast = new Toast(mContext);
        mToast.setView(mView);
        mToast.setDuration(lengthShort);
        mToast.show();
    }

    private static void initText(View mView, String text)
    {
        ImageView iconIv = (ImageView) mView.findViewById(R.id.iconIv);
        TextView textView = (TextView) mView.findViewById(R.id.toastTxt);
        textView.setText(text);
    }
}
