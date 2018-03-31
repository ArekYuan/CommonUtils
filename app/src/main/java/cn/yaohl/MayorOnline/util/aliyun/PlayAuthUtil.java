package cn.yaohl.MayorOnline.util.aliyun;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.aliyun.vodplayer.utils.HttpClientUtil;

import org.json.JSONObject;

/**
 * Created by pengshuang on 31/08/2017.
 */

public class PlayAuthUtil {

    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPlayAuth(String vid) {

        String strAuth = null;

        try {
            String response = HttpClientUtil.doGet("http://30.27.92.37:9988/?playauth=1&vid=" + vid);
            String jsonAuthinfo = response.replaceAll("u'", "\"").replace("'", "\"");
            JSONObject authinfo = new JSONObject(jsonAuthinfo);
            strAuth = authinfo.get("PlayAuth").toString();

        } catch (Exception e) {
        } finally {
            return strAuth;
        }

    }


    public interface OnAuthResultListener {
        void onSuccess(String authStr);

        void onFail();
    }


    public static AsyncTask getPlayAuth(final String vid, final OnAuthResultListener onAuthResultListener) {
        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                return getPlayAuth(params[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                if (TextUtils.isEmpty(s)) {
                    onAuthResultListener.onFail();
                } else {
                    onAuthResultListener.onSuccess(s);
                }
//                super.onPostExecute(s);
            }
        };
        asyncTask.execute(vid);

        return asyncTask;
    }


}
