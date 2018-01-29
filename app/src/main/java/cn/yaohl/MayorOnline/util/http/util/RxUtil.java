package cn.yaohl.MayorOnline.util.http.util;


import com.yaohl.retrofitlib.log.YLog;

import cn.yaohl.MayorOnline.ui.BaseResp;
import cn.yaohl.MayorOnline.util.http.ApiException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RxUtil {

    /**
     * 统一线程处理
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 返回结果统一处理
     */
    public static <T extends BaseResp> Observable.Transformer<T, T> parseResponseResult() {   //compose判断结果
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<T, Observable<T>>() {
                    @Override
                    public Observable<T> call(T responseResult) {
                        YLog.d("responseResult:" + responseResult.code + "|" + responseResult.msg);
                        switch (responseResult.code) {
                            case "0"://成功
                                return createData(responseResult);
                            case "101":
//                ToastUtils.showToast("登录超时，请重新登录", Toast.LENGTH_SHORT);
                                //Token超时
//                ExitPresenter exitPresenter = new ExitPresenter(CyyApplication.getContext());
//                exitPresenter.startToExit();
                                break;
                            default:
                                return Observable.error(new ApiException(responseResult.msg));
                        }
                        return Observable.error(new ApiException("服务器连接失败"));
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     */
    public static <T extends BaseResp> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
