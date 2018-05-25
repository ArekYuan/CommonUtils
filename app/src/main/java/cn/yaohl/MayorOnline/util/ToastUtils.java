package cn.yaohl.MayorOnline.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.yaohl.MayorOnline.MayorApplication;

/**
 * ToastUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    private static Toast toast = null; //Toast的对象！
    
    public static void showToast(String id) {
        if (toast == null) {
            toast = Toast.makeText(MayorApplication.getInstance(), id, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 30);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
    
    public static void showToast(Context mContent, String strText, int resId){
    	Toast toastImg;
    	
		toastImg = Toast.makeText(mContent, strText, Toast.LENGTH_SHORT);
		toastImg.setGravity(Gravity.BOTTOM, 0, 30);
	    LinearLayout toastView = (LinearLayout) toastImg.getView();
	    ImageView imageCodeProject = new ImageView(mContent);
	    imageCodeProject.setImageResource(resId);
	    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(CommonUtils.px(mContent,40),CommonUtils.px(mContent,40));
	    ll.setMargins(CommonUtils.px(mContent,20), CommonUtils.px(mContent,10), CommonUtils.px(mContent,20), CommonUtils.px(mContent,10));
	    ll.gravity = Gravity.CENTER_HORIZONTAL;
	    imageCodeProject.setLayoutParams(ll);
	    toastView.addView(imageCodeProject, 0);
    	
    	toastImg.show();
    }
    
}
