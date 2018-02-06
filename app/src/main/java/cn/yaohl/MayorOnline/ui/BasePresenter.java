package cn.yaohl.MayorOnline.ui;

import android.os.Handler;

import cn.yaohl.MayorOnline.MayorApplication;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：袁光跃
 * 日期：2018/2/6
 * 描述：
 * 邮箱：813665242@qq.com
 */

public abstract class BasePresenter {

    private Handler handler = new Handler(MayorApplication.getApplication().getMainLooper());

    protected CompositeSubscription mCompositeSubcription;

    /**
     * 如果当前线程是不是用户界面线程，该方法是添加到事件队列的用户界面线程。
     */
    public final void runOnUiThread(Runnable action) {
        if (handler != null) {
            handler.post(action);
        } else {
            action.run();
        }
    }

    public void addSubscribe(Subscription subscription) {
        if (mCompositeSubcription == null) {
            mCompositeSubcription = new CompositeSubscription();
        }
        mCompositeSubcription.add(subscription);
    }

    public void unSubscribe() {
        if (mCompositeSubcription != null) {
            mCompositeSubcription.unsubscribe();
            mCompositeSubcription = null;
        }
    }

}
