package cn.yaohl.MayorOnline.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.yaohl.MayorOnline.R;

/**
 * Created by ygy on 2018/3/27 0027.
 * 弹框
 */

public class PopWindow extends PopupWindow {

    private Context mContext;

    private OnItemClickListener onItemClickListener;
    private LayoutInflater mInflater;
    private View img;
    private int popupWidth;
    private int popupHeight;
    private int width;
    private int height;

    public PopWindow(Context mContext, View v) {
        super(mContext);
        this.mContext = mContext;
        this.img = v;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.pop_item_layout, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new BitmapDrawable());

        int popupWidth = view.getWidth();
        int popupHeight = view.getHeight();
        int width = img.getWidth();
        int height = img.getHeight();
        initView(view);
    }

    private void initView(View v) {
        TextView aboutUsTxt = (TextView) v.findViewById(R.id.aboutUsTxt);
        aboutUsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick();
                }
            }
        });
    }

    public void showPop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //Gravity.BOTTOM设置在view下方
        showAsDropDown(view, (width - popupWidth) / 2, -(popupHeight + height));
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
