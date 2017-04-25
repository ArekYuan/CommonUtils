package com.yaohl.volleylib.volley;

/**
 * 文 件 名:  FileProgress.java
 * 版    权:
 * 描    述:  <下载文件进度接口>
 * 版    本： <版本号>
 * 创 建 人:
 * 创建时间:  2015年10月23日
 */
public interface FileProgress
{
    /**
     * <实时获取下载文件进度>
     * <功能详细描述>
     *
     * @param fileSize       文件大小
     * @param downloadedSize 已下载文件大小
     *                       返 回 类 型：void
     */
    void fileProgressChange(String fileSize, String downloadedSize);
}
