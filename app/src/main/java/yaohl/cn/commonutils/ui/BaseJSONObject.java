package yaohl.cn.commonutils.ui;

import android.content.Context;

import com.yaohl.okhttplib.okhttp.GsonHelp;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ygy on 2016/9/21.
 */
public class BaseJSONObject extends JSONObject
{
    public <T> BaseJSONObject(Context mContext, T baseRequest) throws JSONException
    {
        super(GsonHelp.objectToJsonString(baseRequest));
    }
}
