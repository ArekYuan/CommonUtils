package com.yaohl.volleylib.volley;

import android.content.Context;

/**
 * 文 件 名:  FileDownLoader.java
 * 版    权:
 * 描    述:  <下载文件操作功能接口>
 * 版    本： <版本号>
 * 创 建 人:
 * 创建时间:  2015年10月22日
 */
public interface FileDownLoader
{
    /**
     * <暂停该任务>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    void downLoadPause();

    /**
     * <继续该任务>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    void downLoadResume();

    /**
     * <放弃该任务>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    void downLoadDiscard();

    /**
     * <下载文件方法>
     * <功能详细描述>
     *
     * @param url     文件下载地址
     * @param context context
     *                返 回 类 型：void
     */
    void downLoadFile(String url, Context context);
}
