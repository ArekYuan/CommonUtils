package yaohl.cn.commonutils.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * 文 件 名: FileUtil.java
 */
public class FileUtil
{
    /**
     * TAG
     */
    public static final String TAG = "FileUtil";

    /**
     * 错误日志后缀
     */
    public static final String ERROR_LOG_SUFFIX = ".log";

    private static final boolean DEBUG = false;

    /**
     * <获取文件夹下所有文件和文件夹的大小> <功能详细描述>
     *
     * @param file File实例
     *
     * @return 单位为M
     *
     * @throws Exception 返 回 类 型：long
     */
    public static long getFolderSize(java.io.File file)
            throws Exception
    {
        long size = 0;
        java.io.File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++)
        {
            if (fileList[i].isDirectory())
            {
                size = size + getFolderSize(fileList[i]);
            }
            else
            {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    /**
     * <删除目录（文件夹）下的文件> <功能详细描述>
     *
     * @param path 被删除目录的文件路径 返 回 类 型：void
     */
    public static void deleteDirectory(String path)
    {
        File dirFile = new File(path);
        File[] files = dirFile.listFiles();
        if (files != null && files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                // 删除子文件
                if (files[i].isFile())
                {
                    files[i].delete();
                }
                // 删除子目录
                else
                {
                    deleteDirectory(files[i].getAbsolutePath());
                }
            }
        }
    }

    /**
     * <删除文件夹下的文件> <功能详细描述>
     *
     * @param file 被删除的文件或文件夾 返 回 类 型：void
     */
    public void deleteFile(File file)
    {
        File[] files = file.listFiles();
        if (files != null && files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                // 删除子文件
                if (files[i].isFile())
                {
                    files[i].delete();
                }
                // 删除子目录
                else
                {
                    deleteDirectory(files[i].getAbsolutePath());
                }
            }
        }
    }

    /**
     * <通过提供的文件名在默认路径下创建文件> <如果存在，先删除后创建>
     *
     * @param filePath filePath
     *
     * @return 返 回 类 型：File
     */
    public static File createFile(String filePath)
    {
        if (TextUtils.isEmpty(filePath))
        {
            Log.e(TAG, "in method createFile, filePath is null");
            return null;
        }
        File file = createFolder(filePath);
        try
        {
            file.createNewFile();
        }
        catch (Exception e)
        {
            Log.e(TAG, "create file fail, filePath = " + filePath);
        }
        return file;
    }

    /**
     * <如果存在文件先删除，然后创建> <功能详细描述>
     *
     * @param filePath filePath
     *
     * @return 返 回 类 型：File
     */
    private static File createFolder(String filePath)
    {
        String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File folder = getFileByPath(folderPath);
        folder.mkdirs();
        File file = getFileByPath(filePath);

        if (file.exists())
        {
            file.delete();
        }
        return file;
    }

    /**
     * <创建新文件，返回目录路径> <功能详细描述>
     *
     * @param path 路径
     *
     * @return 返 回 类 型：String
     */
    public static String createNewFile(String path)
    {
        File dir = new File(path);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        return path;
    }

    /**
     * <根据文件路径创建文件> <功能详细描述>
     *
     * @param filePath filePath
     *
     * @return 返 回 类 型：File
     */
    public static File getFileByPath(String filePath)
    {
        filePath = filePath.replaceAll("\\\\", "/");
        boolean isSdcard = false;
        int subIndex = 0;
        if (filePath.indexOf("/sdcard") == 0)
        {
            isSdcard = true;
            subIndex = 7;
        }
        else if (filePath.indexOf("/mnt/sdcard") == 0)
        {
            isSdcard = true;
            subIndex = 11;
        }

        if (isSdcard)
        {
            if (isExistSdcard())
            {
                // 获取SDCard目录,2.2的时候为:/mnt/sdcard
                // 2.1的时候为�?sdcard，所以使用静态方法得到路径会好一点�?
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = filePath.substring(subIndex);
                return new File(sdCardDir, fileName);
            }
            else if (isEmulator())
            {
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = filePath.substring(subIndex);
                return new File(sdCardDir, fileName);
            }
            return null;
        }
        else
        {
            return new File(filePath);
        }
    }

    /**
     * <判断是否存在sd卡> <功能详细描述>
     *
     * @return 返 回 类 型：boolean
     */
    private static boolean isExistSdcard()
    {
        if (!isEmulator())
        {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
        return true;
    }

    /**
     * <判断是否是模拟器> <功能详细描述>
     *
     * @return 返 回 类 型：boolean
     */
    private static boolean isEmulator()
    {
        return android.os.Build.MODEL.equals("sdk");
    }

    /**
     * <复制文件> <功能详细描述>
     *
     * @param inputStream inputStream
     * @param targetFile  targetFile
     *
     * @throws IOException IOException 返 回 类 型：void
     */
    public static void copyFile(InputStream inputStream, File targetFile)
            throws IOException
    {
        BufferedOutputStream outBuff = null;
        try
        {
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inputStream.read(b)) != -1)
            {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally
        {
            // 关闭流
            if (inputStream != null)
            {
                inputStream.close();
            }
            if (outBuff != null)
            {
                outBuff.close();
            }
        }
    }

    /**
     * <文件是否已存在> <功能详细描述>
     *
     * @param file 文件
     *
     * @return 返 回 类 型：boolean
     */
    public static boolean isFileExit(File file)
    {
        if (file.exists())
        {
            return true;
        }
        return false;
    }

    /**
     * <判断指定目录是否有文件存在> <功能详细描述>
     *
     * @param path     path
     * @param fileName 文件名
     *
     * @return 返 回 类 型：File
     */
    public static File getFiles(String path, String fileName)
    {
        File f = new File(path);
        File[] files = f.listFiles();
        if (files == null)
        {
            return null;
        }

        if (null != fileName && !"".equals(fileName))
        {
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                if (fileName.equals(file.getName()))
                {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * <根据文件路径获取文件名> <功能详细描述>
     *
     * @param path path
     *
     * @return 返 回 类 型：String
     */
    public static String getFileName(String path)
    {
        if (path != null && !"".equals(path.trim()))
        {
            return path.substring(path.lastIndexOf("/"));
        }

        return "";
    }

    /**
     * <从asset中读取文件> <功能详细描述>
     *
     * @param context  context
     * @param fileName fileName
     *
     * @return 返 回 类 型：String
     */
    public static String getFromAssets(Context context, String fileName)
    {
        String result = "";
        try
        {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null)
            {
                result += line;
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <获取sdcard的目录> <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getSDPath(Context context)
    {
        // 判断sdcard是否存在
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            // 获取根目录
            File sdDir = Environment.getExternalStorageDirectory();
            return sdDir.getPath();
        }
        return "/data/data/" + context.getPackageName();
    }

    /**
     * <获取保存数据库的目录> <功能详细描述>
     *
     * @param context context
     *
     * @return 返 回 类 型：String
     */
    public static String getDatabasePath(Context context)
    {
        return "/data/data/" + context.getPackageName();
    }

    /**
     * 获取内外缓存的总大小
     *
     * @param context
     *
     * @return cacheSizeString(B/KB/MB/GB...)
     */
    public static String getCacheSize(Context context)
    {
        long cacheSize = 0;
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO))
        {
            cacheSize =
                    getDirSize(context.getFilesDir()) + getDirSize(context.getCacheDir())
                            + getDirSize(context.getExternalCacheDir());
        }
        else
        {
            cacheSize = getDirSize(context.getFilesDir()) + getDirSize(context.getCacheDir());
        }
        return getFriendlySize(cacheSize, true);
    }

    /**
     * * 将字节数转换为友好的字符 *
     * <p>
     * 这个方法实在是太牛X了
     * </p>
     *
     * @param bytes 字节数 * @param si true则返回KB格式(1000进制)， 否则返回KiB格式(1024进制)
     *
     * @return 友好的字符
     *
     * @see <a
     * href="http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java">来源</a>
     */
    public static String getFriendlySize(long bytes, boolean si)
    {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
        {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        // String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static long getDirSize(File dirFile)
    {
        long dirSize = 0;
        if (dirFile != null && dirFile.isDirectory())
        {
            File[] files = dirFile.listFiles();
            for (File file : files)
            {
                if (file.isFile())
                {
                    dirSize += file.length();
                }
                else if (file.isDirectory())
                {
                    dirSize += file.length();
                    dirSize += getDirSize(file);
                }
            }
        }
        return dirSize;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     *
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode)
    {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     *
     * @return
     */
    public static String FormetFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0)
        {
            return wrongSize;
        }
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     *
     * @return
     */
    public static int clearCacheFolder(File dir, long curTime)
    {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory())
        {
            try
            {
                for (File child : dir.listFiles())
                {
                    if (child.isDirectory())
                    {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime)
                    {
                        if (child.delete())
                        {
                            deletedFiles++;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath
     *
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath)
    {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    /**
     * 通过意图 跳转本地 选择视频 获取视频数据
     *
     * @return
     */
    public static Intent createGetContentIntent()
    {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // intent.setType("*/*");
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    /**
     * 将 意图带过来的 uri 转化成 路径
     *
     * @param context
     * @param uri
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getVideoPath(final Context context, final Uri uri)
    {
        if (null == uri)
        {
            return null;
        }
        if (isMediaDocument(uri))
        {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type))
            {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }
            else if ("video".equals(type))
            {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            }
            else if ("audio".equals(type))
            {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{split[1]};

            return getDataColumn(context, contentUri, selection, selectionArgs);
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
    {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try
        {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst())
            {
                if (DEBUG)
                {
                    DatabaseUtils.dumpCursor(cursor);
                }

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri)
    {
        return "szzc.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 获取字符串编码
     *
     * @param str
     *
     * @return
     */
    public static String getEncoding(String str)
    {

        String encode1 = "ISO-8859-1";
        try
        {
            if (str.equals(new String(str.getBytes(encode1), encode1)))
            {
                String s = encode1;
                return s;
            }
        }
        catch (Exception exception)
        {
        }
        String encode2 = "GB2312";
        try
        {
            if (str.equals(new String(str.getBytes(encode2), encode2)))
            {
                String s1 = encode2;
                return s1;
            }
        }
        catch (Exception exception1)
        {
        }
        String encode3 = "GBK";
        try
        {
            if (str.equals(new String(str.getBytes(encode3), encode3)))
            {
                String s2 = encode3;
                return s2;
            }
        }
        catch (Exception exception2)
        {
        }
        String encode4 = "UTF-8";
        try
        {
            if (str.equals(new String(str.getBytes(encode4), encode4)))
            {
                String s3 = encode4;
                return s3;
            }
        }
        catch (Exception exception3)
        {
        }
        return "";
    }
}
