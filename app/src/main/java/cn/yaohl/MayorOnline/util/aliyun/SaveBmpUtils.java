package cn.yaohl.MayorOnline.util.aliyun;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lifujun on 2017/11/8.
 */

public class SaveBmpUtils {

    public static boolean saveBmp(Bitmap bitmap, String absFilePath){
        File f = new File(absFilePath);
        try {
            f.createNewFile();
        } catch (IOException e) {
            com.alivc.player.VcPlayerLog.e("lfj1103", "在保存图片时出错：" + e.toString());
            return false;
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            com.alivc.player.VcPlayerLog.e("lfj1103", "在保存图片时出错：" + e.toString());
            return false;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) { com.alivc.player.VcPlayerLog.e("lfj1103", "在保存图片时出错：" + e.toString());
            return false;
        }
        try {
            fOut.close();
        } catch (IOException e) { com.alivc.player.VcPlayerLog.e("lfj1103", "在保存图片时出错：" + e.toString());
            return false;
        }
        return true;
    }
}
