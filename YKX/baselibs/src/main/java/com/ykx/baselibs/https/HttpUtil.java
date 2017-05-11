package com.ykx.baselibs.https;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ykx.baselibs.app.BaseApplication;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author wangxiaohu
 */
public class HttpUtil {

    private static HttpUtil mHttpUtil;
    private Gson mGson;
    /**
     * 连接超时时间
     */
    private static final int REQUEST_TIMEOUT_TIME = 60 * 1000;


    private static final String REQUEST_ROLE_UPDATE = "4001";

    /**
     * volley请求队列
     */
    public RequestQueue mRequestQueue;

    private HttpUtil() {
        mGson = new Gson();

        SSLSocketFactory sslSocketFactory = SSLUtils.initSSLSocketFactoryTrustAll();
        HurlStack stack = new HurlStack(null, sslSocketFactory);
        mRequestQueue = Volley.newRequestQueue(BaseApplication.application,stack);
    }

    public static HttpUtil getInstance() {
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil();
                }
            }
        }
        return mHttpUtil;
    }

    private int getRequestMethod(int requestMethod){
        if (requestMethod==Request.Method.GET){
            return Request.Method.GET;
        }else if (requestMethod==Request.Method.POST){
            return Request.Method.POST;
        }else if (requestMethod==Request.Method.DELETE){
            return Request.Method.DELETE;
        }else if (requestMethod==Request.Method.PUT){
            return Request.Method.PUT;
        }else if (requestMethod==Request.Method.PATCH){
            return Request.Method.PATCH;
        }

        return Request.Method.POST;
    }

    private <T> void showMessageWithException(VolleyError error,final HttpCallBack<T> httpCallBack){
        if (httpCallBack == null) {
            return;
        }
        String msg;
        if (error instanceof NetworkError) {
            msg="Network异常";
        } else if (error instanceof ServerError) {
            ServerError serverError= (ServerError) error;
            byte[] serverdatas = serverError.networkResponse.data;
            msg="服务器异常 "+new String(serverdatas);
        } else if (error instanceof AuthFailureError) {
            msg="AuthFailure异常";
            //返回登录界面
            Intent intent=new Intent("com.ykx.organization.pages.LoginActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            BaseApplication.application.startActivity(intent);
        } else if (error instanceof ParseError) {
            msg="Parse异常";
        } else if (error instanceof NoConnectionError) {
            msg="NoConnection异常";
        } else if (error instanceof TimeoutError) {
            msg="Timeout异常";
        } else{
            msg="其它异常情况";
        }
        httpCallBack.onFail(msg);
        showToast(msg);
    }

    private boolean loginOut(String code){
        //返回登录界面
        if (REQUEST_ROLE_UPDATE.equals(code)) {
            Intent intent = new Intent("com.ykx.organization.pages.LoginActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            BaseApplication.application.startActivity(intent);
            return true;
        }
        return false;
    }

    private void showToast(String msg){
        if ((msg!=null)&&(msg.length()>0)) {
            Toast.makeText(BaseApplication.application, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * http请求，返回解析好的实体或者List
     *
     * @param url          http地址
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void request(int requestMethod,String url, final Map<String, String> param,final HashMap<String, String> headers, final HttpCallBack<T> httpCallBack) {

        Log.d(HttpUtil.class.getName(),"url="+url+",request="+new Gson().toJson(param));
        StringRequest stringRequest = new StringRequest(getRequestMethod(requestMethod), url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(HttpUtil.class.getName(),"response="+response);
                        if (httpCallBack == null) {
                            return;
                        }
                        try {
                            Type type = TTypeUtils.getTType(httpCallBack.getClass());

                            HttpResult httpResult = mGson.fromJson(response, HttpResult.class);
                            if (httpResult != null) {
                                if (httpResult.getRet() != 200) {//失败
                                    boolean istrue=loginOut(String.valueOf(httpResult.getRet()));
                                    if (!istrue) {
                                        httpCallBack.onFail(httpResult.getMsg());
                                        showToast(httpResult.getMsg());
                                    }
                                } else {//成功
                                    //获取data对应的json字符串
                                    if (type == String.class) {//泛型是String，返回结果json字符串
                                        String msg = httpResult.getData().toString();
                                        httpCallBack.onSuccess((T) msg);
                                    } else {
                                        String json = mGson.toJson(httpResult.getData());
                                        try {
                                            T t = mGson.fromJson(json, type);
                                            httpCallBack.onSuccess(t);
                                        }catch (Exception e){
                                            T t;
                                            if (type instanceof List){
                                                t = mGson.fromJson("[]", type);
                                            }else{
                                                t = mGson.fromJson("{}", type);
                                            }
                                            httpCallBack.onSuccess(t);
                                        }
                                    }
                                }
                            }
                        }catch (Exception e){
                            httpCallBack.onFail(e.getMessage());
//                            showToast("exception="+e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessageWithException(error,httpCallBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        if (mRequestQueue != null)
            mRequestQueue.add(stringRequest.setTag(url));
    }

    /**
     * http请求，返回全部json
     *
     * @param url          http地址
     * @param param        参数
     * @param httpCallBack 回调
     */
    public void requestForJson(int requestMethod,String url, final Map<String, String> param,final HashMap<String, String> headers, final HttpCallBack<String> httpCallBack) {
        StringRequest stringRequest = new StringRequest(getRequestMethod(requestMethod), url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (httpCallBack != null) {
                            httpCallBack.onSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessageWithException(error,httpCallBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        if (mRequestQueue != null)
            mRequestQueue.add(stringRequest.setTag(url));
    }

    /**
     * http请求,以JSON参数形式提交
     *
     * @param url          http地址
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void jsonRequest(int requestMethod, String url, final Map<String,Object> param, final HashMap<String, String> headers, final HttpCallBack<T> httpCallBack) {

        final Gson gson=new Gson();
        Log.d(HttpUtil.class.getName(),"url="+url+",request="+new Gson().toJson(param));
        JSONObject jsonObject = new JSONObject(param);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(getRequestMethod(requestMethod), url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(HttpUtil.class.getName(),"response="+gson.toJson(response));
                        String result = response.toString();
                        if (httpCallBack == null) {
                            return;
                        }
                        try{
                            Type type = TTypeUtils.getTType(httpCallBack.getClass());

                            HttpResult httpResult = mGson.fromJson(result, HttpResult.class);
                            if (httpResult != null) {
                                if (httpResult.getRet() != 200) {//失败
                                    boolean istrue=loginOut(String.valueOf(httpResult.getRet()));
                                    if (!istrue) {
                                        httpCallBack.onFail(httpResult.getMsg());
                                        showToast(httpResult.getMsg());
                                    }
                                } else {//成功
                                    //获取data对应的json字符串
                                    String json = mGson.toJson(httpResult.getData());
                                    if (type == String.class) {//泛型是String，返回结果json字符串
                                        httpCallBack.onSuccess((T) json);
                                    } else {
                                        T t = mGson.fromJson(json, type);
                                        httpCallBack.onSuccess(t);
                                    }
                                }
                            }
                        }catch (Exception e){
                            httpCallBack.onFail(e.getMessage());
                            showToast(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessageWithException(error,httpCallBack);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        if (mRequestQueue != null)
            mRequestQueue.add(jsonRequest.setTag(url));
    }

    /**
     * http请求,以JSON参数形式提交
     *
     * @param url          http地址
     * @param param        参数
     * @param httpCallBack 回调
     */
    public void jsonRequestForJson(int requestMethod,String url, final Map<String, String> param,final HashMap<String, String> headers, final HttpCallBack<String> httpCallBack) {
        JSONObject jsonObject = new JSONObject(param);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(getRequestMethod(requestMethod), url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (httpCallBack != null) {
                            httpCallBack.onSuccess(response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMessageWithException(error,httpCallBack);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        if (mRequestQueue != null)
            mRequestQueue.add(jsonRequest.setTag(url));
    }
}
