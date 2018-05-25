package cn.yaohl.MayorOnline.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.yaohl.retrofitlib.log.YLog;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vincent Chen on 2018/3/21.
 */
public class PhotoUtil {

    public static String cacheImgName = System.currentTimeMillis() + "_cacheImg.jpg";
    public static String cacheIconImage = System.currentTimeMillis() + "_cacheIcon.jpg";
    public static String dir = Environment.getExternalStorageDirectory().toString()
            + "/wjl/";
    public static final int PHOTO_REQUEST_TAKEPHOTO = 0x11;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 0x22;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 0x33;// 结果
    public static final String PHOTO_FLAG_POSITION = "photo_flag_position";// 标记位
    public static Uri imageUri = null;
    public static Uri uritempFile = null;
    public static int flagPosi = -1;
    private static Activity mContext = null;

    /**
     * 打开相机方法
     */
    public static void startCamearPicCut(Activity context, int flag) {
        flagPosi = flag;
        mContext = context;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 文件夹test
        String path = dir + cacheImgName;
        File tempFile = new File(path);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0以后使用该方法，否则调不起相机
            imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", tempFile);
            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
//            imageUri = Uri.fromFile(tempFile);
            imageUri = getImageContentUri(tempFile);
        }
        openCameraIntent.putExtra("autofocus", true);// 自动对焦
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        try {
            mContext.startActivityForResult(openCameraIntent, PHOTO_REQUEST_TAKEPHOTO);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showToast("打开相机失败!");
        }
    }


    /**
     * 打开相册
     *
     * @param activity
     */
    public static void startImageCaptrue(Activity activity, int flag) {
        flagPosi = flag;
        mContext = activity;
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        try {
            mContext.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showToast("打开相册失败!");
        }
    }

    public static void startPhotoZoom(Uri uri, int wsize, int hsize, Activity activity) {
//        File file=new File(dir+"cut.jpg");//缩略图地址
//        if(file.exists()){
//            file.delete();
//        }

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        File imageFile = new File(file, cacheIconImage);
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
//        String photo_url = CommonUtils.filePath(mContext, uri);
//        uritempFile = getImageContentUri(new File(photo_url));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        uritempFile = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        try {
            mContext.startActivityForResult(intent, PHOTO_REQUEST_CUT);
        } catch (Exception e) {
            YLog.d("file no crop---->" + e.getMessage());
            ToastUtils.showToast("裁剪失败!");
        }
    }

    private static Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return mContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
