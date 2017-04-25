package com.yaohl.okhttplib.okhttp;

/**
 * 文件下载回调
 * <p>
 * Created by Administrator on 2017/4/24.
 */

public interface DownLoadFileCallBack
{
    /**
     * 请求成功
     *
     * @param total
     * @param current
     */
    void onSuccess(long total, long current);

    /**
     * <请求失败>
     * <功能详细描述>
     */
    void onFail(String errorMessage);
}
