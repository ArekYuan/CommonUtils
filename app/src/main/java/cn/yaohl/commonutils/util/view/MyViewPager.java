package cn.yaohl.commonutils.util.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by asus on 2016/10/24.
 */

public class MyViewPager extends ViewPager
{
    private boolean enabled;

    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.enabled = false;
    }

    //触摸没有反应就可以了
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (this.enabled)
        {
            return super.onTouchEvent(event);
        }
        return false;
    }


    @Override
    public void setOnTouchListener(OnTouchListener l)
    {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (this.enabled)
        {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
}
