package cn.yaohl.commonutils.util.view;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.yaohl.retrofitlib.log.YLog;


/**
 * 用于解决 ScrollView嵌套listView的冲突问题
 * <p>
 * Created by asus on 2016/11/1.
 */

public class LinearLayoutForListView extends LinearLayout
{
    /**
     * 适配器
     */
    private BaseAdapter adapter;

    /**
     * 点击事件
     */
    private OnClickListener onClickListener = null;

    public void bindLinearLayout()
    {
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++)
        {
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(this.onClickListener);
            addView(v, i);
        }
        YLog.v("countTAG--->" + count);
    }

    public LinearLayoutForListView(Context context)
    {
        super(context);
    }
}
