package com.yaohl.okhttplib.okhttp;

import java.io.File;

/**
 * 文件下载回调
 * <p>
 * Created by 袁光跃 on 2017/4/24.
 */

public interface DownLoadFileCallBack {
    /**
     * 请求成功
     *
     * @param total
     * @param current
     */
    void onSuccess(long total, long current);

    /**
     * 文件下载结束
     *
     * @param file
     */
    void onDownLoadFinish(File file);

    /**
     * <请求失败>
     * <功能详细描述>
     */
    void onFail(String errorMessage);
}
