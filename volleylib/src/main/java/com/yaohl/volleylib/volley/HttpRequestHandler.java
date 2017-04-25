package com.yaohl.volleylib.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import yaohl.cn.commonutils.sharepref.SharePref;

/**
 * 文 件 名: HttpRequestHandler.java
 */
public class HttpRequestHandler extends JsonObjectRequest
{

    private Context context;

    public HttpRequestHandler(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                              Response.ErrorListener errorListener, Context context)
    {
        super(method, url, jsonRequest, listener, errorListener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders()
            throws AuthFailureError
    {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("charset", "utf-8");
        headers.put("Content-Type", "application/json");
        String sessionId = new SharePref(context).getSessionId();
        if (!TextUtils.isEmpty(sessionId))
        {
            headers.put("cookie", sessionId);
        }
        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String jsonString = new String(response.data, "UTF-8");
            return Response.success(
                    new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JSONException je)
        {
            return Response.error(new ParseError(je));
        }

//        try
//        {
//            Map<String, String> headers = response.headers;
//            String cookie = headers.get("Set-Cookie");
//            if (cookie != null)
//            {
//                String sessionId = cookie.substring(0, cookie.indexOf(";"));// 获取sessionID
//                new SharePref(context).saveSessionId(sessionId);// 将sessionid 保存在本地
//            }
//            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            return Response.error(new ParseError(e));
//        }
//        catch (JSONException je)
//        {
//            return Response.error(new ParseError(je));
//        }
    }
}
