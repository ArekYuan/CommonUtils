package yaohl.cn.commonutils.ui;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import yaohl.cn.commonutils.newtwork.okhttp.GsonHelp;

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
