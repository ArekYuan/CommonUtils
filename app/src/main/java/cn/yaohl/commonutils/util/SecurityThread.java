package cn.yaohl.commonutils.util;

import android.os.Handler;
import android.widget.Button;

/**
 * 文 件 名: SecurityThread.java 版 权: com 描 述: <两次验证码获取时间间隔控制>
 */
public class SecurityThread extends Thread
{
    /**
     * 要传入的控件
     */
    private Button textView;

    /**
     * 延时时间
     */
    public int lagTime = 60;

    /**
     * 还没点击时的控件字体颜色
     */
    int enalbeColor;

    /**
     * 点击后的控件字体颜色
     */
    int disenalbeColor;

    /**
     *
     */
    Handler handler = new Handler();

    public SecurityThread(Button textView, int normalColor, int disenalbeColor)
    {
        this.textView = textView;
        this.enalbeColor = normalColor;
        this.disenalbeColor = disenalbeColor;
    }

    @Override
    public void run()
    {
        try
        {
            while (lagTime > 0)
            {
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textView.setEnabled(false);
                        // String smsTimeString = "重新获取\n( " + lagTime + " )";
                        String smsTimeString = lagTime + "秒";
                        textView.setText(smsTimeString);
                        textView.setTextColor(disenalbeColor);
                    }
                });
                Thread.sleep(1000);
                lagTime--;

            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                textView.setEnabled(true);
                textView.setText("发送");
                textView.setTextColor(enalbeColor);
            }
        });
    }
}
