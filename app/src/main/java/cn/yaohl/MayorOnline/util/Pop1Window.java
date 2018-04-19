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

public class Pop1Window extends PopupWindow {

    private Context mContext;

    private OnItemClickListener onItemClickListener;
    private LayoutInflater mInflater;
    private View img;
    private int popupWidth;
    private int popupHeight;
    private int width;
    private int height;

    public Pop1Window(Context mContext, View v) {
        super(mContext);
        this.mContext = mContext;
        this.img = v;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.pop_1_item_layout, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new BitmapDrawable());

        popupWidth = view.getWidth();
//        popupHeight = view.getHeight();
        width = img.getWidth();
//        height = img.getHeight();
        initView(view);
    }

    private void initView(View v) {
        TextView videoTxt = (TextView) v.findViewById(R.id.videoTxt);
        TextView liveTxt = (TextView) v.findViewById(R.id.liveTxt);
        videoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onVideoClick();
                }
            }
        });
        liveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onLiveClick();
                }
            }
        });
    }

    public void showPop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //Gravity.BOTTOM设置在view下方
        showAsDropDown(view, -width , -(popupHeight + height));
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onLiveClick();

        void onVideoClick();
    }
}
