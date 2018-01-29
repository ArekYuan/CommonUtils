package cn.yaohl.MayorOnline.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by asus on 2016/10/20.
 */

public class ScrollRelativeLayout extends RelativeLayout
{
    /**
     * 滑动监听
     */
    private OnScrollListener onScrollListener;

    public ScrollRelativeLayout(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs,
                                int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * scroll in from right
     */
    public void beginScrollFromRight()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.rightMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide right
     */
    public void beginScrollHideRight()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.rightMargin = -getWidth();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }


    /**
     * scroll in from left
     */
    public void beginScrollFromLeft()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.leftMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide left
     */
    public void beginScrollHideLeft()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.leftMargin = -getWidth();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll in from bottom
     */
    public void beginScrollFromBottom()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.bottomMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide bottom
     */
    public void beginScrollHideBottom()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.bottomMargin = -getHeight();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll in from bottom
     */
    public void beginScrollFromTop()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.topMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide top
     */
    public void beginScrollHideTop()
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                // TODO Auto-generated method stub
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                params.topMargin = -getHeight();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }


    @Override
    public void computeScroll()
    {
        // TODO Auto-generated method stub
        super.computeScroll();
        if (onScrollListener != null)
        {
            onScrollListener.computeScroll();
        }
    }

    public OnScrollListener getOnScrollListener()
    {
        return onScrollListener;
    }

    /**
     * 滑动监听
     */
    public void setOnScrollListener(OnScrollListener listener)
    {
        this.onScrollListener = listener;
    }

    /**
     * 滑动监听
     *
     * @author LangK
     */
    public interface OnScrollListener
    {
        public void computeScroll();
    }
}
