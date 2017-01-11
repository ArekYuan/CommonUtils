package yaohl.cn.commonutils.newtwork.volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.request.FileDownloadRequest;
import com.duowan.mobile.netroid.toolbox.FileDownloader;
import com.duowan.mobile.netroid.toolbox.FileDownloader.DownloadController;

import yaohl.cn.commonutils.util.FileSystemManager;
import yaohl.cn.commonutils.util.FileUtil;

/**
 * 文 件 名:  FileDownloaderHelper.java
 * 版    权:
 * 描    述:  <下载文件帮助类>
 * 版    本： <版本号>
 * 创 建 人:  姜飞
 * 创建时间:  2015年10月22日
 */
public class FileDownloaderHelper implements  FileDownLoader
{
    /**
     * 文件下载控制器
     */
    private FileDownloader.DownloadController downloadController;

    /**
     *
     */
    private yaohl.cn.commonutils.newtwork.volley.FileProgress fileProgress;

    /**
     * 文件下载器
     */
    private FileDownloader downloader;

    public FileDownloaderHelper( FileProgress fileProgress)
    {
        downloader = new FileDownloader( NetRequestQueueHandler.getNetroidRequestQueue(), 1)
        {
            @Override
            public FileDownloadRequest buildRequest(String storeFilePath, String url)
            {
                return new FileDownloadRequest(storeFilePath, url)
                {
                    @Override
                    public void prepare()
                    {
                        addHeader("Accept-Encoding", "identity");
                        super.prepare();
                    }
                };
            }
        };
        this.fileProgress = fileProgress;
    }

    /**
     * <down.file是保存的文件名，这个文件只在下载成功后才存在，在下载过程中>
     * <Netroid会在文件路径下创建一个临时文件，命名为：down.file.tmp，下载成功后更名为down.file>
     * 返 回 类 型：void
     *
     * @param url     文件服务器地址（"http://server.com/res/down.file"）
     * @param context ("/sdcard/netroid/down.file")
     */
    public void downLoadFile(String url, final Context context)
    {
        yaohl.cn.commonutils.newtwork.volley.NetRequestQueueHandler.getNetroidRequestQueue().start();
        Log.e("--------------", FileSystemManager.getCacheFilePath(context));
        downloadController = downloader.add(
                FileUtil.createNewFile(FileSystemManager.getCacheFilePath(context)) + "hft_android.apk", url, new Listener<Void>()
                {
                    /**
                     * 如果暂停或放弃了该任务，onSuccess()不会回调
                     */
                    @Override
                    public void onSuccess(Void arg0)
                    {
                        Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                        Log.e("下载成功", "下载成功");
                    }

                    /**
                     * 如果暂停或放弃了该任务，onFinish()不会回调
                     */
                    @Override
                    public void onFinish()
                    {
                        Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                        Log.e("下载完成", "下载完成");
                    }

                    /**
                     * 如果暂停或放弃了该任务，onError()不会回调
                     */
                    @Override
                    public void onError(NetroidError error)
                    {
                        Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                        Log.e("下载失败", "下载失败");
                        error.printStackTrace();
                    }

                    /**
                     *  Listener添加了这个回调方法专门用于获取进度
                     */
                    @Override
                    public void onProgressChange(long fileSize, long downloadedSize)
                    {
                        super.onProgressChange(fileSize, downloadedSize);
                        //                Toast.makeText(context, "下载进度：" + (downloadedSize * 1.0f / fileSize * 100) + "%", Toast.LENGTH_SHORT)
                        //                    .show();
                        //                Message msg = new Message();
                        //                Bundle bundle = new Bundle();
                        //                bundle.putString("fileSize", String.valueOf(fileSize));
                        //                bundle.putString("downloadedSize", String.valueOf(downloadedSize));
                        //                msg.setData(bundle);
                        //                handler.sendMessage(msg);
                        fileProgress.fileProgressChange(String.valueOf(fileSize), String.valueOf(downloadedSize));
                    }
                });
    }

    /**
     * <查看该任务的状态>
     * <功能详细描述>
     * 返 回 类 型：void
     */
    void downLoadStatus()
    {
        switch (downloadController.getStatus())
        {
            case DownloadController.STATUS_WAITING:// 等待中
                Log.e("等待中", "等待中");
                break;

            case DownloadController.STATUS_DOWNLOADING:// 下载中
                Log.e("下载中", "下载中");

                break;

            case DownloadController.STATUS_PAUSE:// 已暂停
                Log.e("已暂停", "已暂停");

                break;

            case DownloadController.STATUS_SUCCESS:// 已成功（标识下载已经正常完成并成功）
                Log.e("已成功", "已成功");

                break;

            case DownloadController.STATUS_DISCARD:// 已取消（放弃）
                Log.e("已取消", "已取消");

                break;

            default:
                break;
        }
    }

    @Override
    public void downLoadPause()
    {
        // 暂停该任务
        if (downloadController != null)
        {
            downloadController.pause();
        }
    }

    @Override
    public void downLoadResume()
    {
        // 继续该任务
        if (downloadController != null)
        {
            downloadController.resume();
        }
    }

    @Override
    public void downLoadDiscard()
    {
        // 放弃(删除)该任务
        if (downloadController != null)
        {
            downloadController.discard();
        }
    }
}
