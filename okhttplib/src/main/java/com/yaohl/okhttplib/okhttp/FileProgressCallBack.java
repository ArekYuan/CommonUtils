package com.yaohl.okhttplib.okhttp;

import java.io.File;

/**
 * Created by Administrator on 2017/4/24.
 */

public interface FileProgressCallBack {
    /**
     * 实时获取下载文件进度
     *
     * @param fileSize
     * @param downloadedSize
     */
    void fileProgressChange(long fileSize, long downloadedSize);

    /**
     * 文件下载结束
     *
     * @param file
     */
    void fileFinishDownLoad(File file);

    /**
     * 文件获取失败
     *
     * @param msg
     */
    void fileProgressFailed(String msg);
}
