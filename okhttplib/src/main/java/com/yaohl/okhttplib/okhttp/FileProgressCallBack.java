package com.yaohl.okhttplib.okhttp;

import java.io.File;

/**
 * 作者： 袁光跃 on 2017/4/24 15:27
 * 邮箱：813665242@qq.com
 * 描述：下载文件进度
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
