package com.ykx.baselibs.https;

import com.ykx.baselibs.commons.Constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*********************************************************************************
 * Project Name  : YKX
 * Package       : com.ykx.apps
 * <p>
 * <p>
 * Copyright  优课学技术部  Corporation 2017 All Rights Reserved
 * <p>
 * <p>
 * <Pre>
 * TODO  http基础访问接口
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/7.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class BaseHttp {

    private static String token;
    private static String qnToken;//公有token
    private static String qnPrivateToken;//私有token

    public static void setToken(String tokenMsg){
        token=tokenMsg;
    }

    public static String getToken(){
        return token;
    }

    public static String getQnToken() {
        return qnToken;
    }

    public static void setQnToken(String qnToken) {
        BaseHttp.qnToken = qnToken;
    }

    public static String getQnPrivateToken() {
        return qnPrivateToken;
    }

    public static void setQnPrivateToken(String qnPrivateToken) {
        BaseHttp.qnPrivateToken = qnPrivateToken;
    }

    /**
     *
     * @param requestMethod  RequestMethod.POST
     * @param servername
     * @param param
     * @param httpCallBack
     * @param <T>
     */
    public <T> void doTast(int requestMethod,String servername, Map<String, String> param, HttpCallBack<T> httpCallBack){

        HashMap<String, String> headers = new HashMap<>();
        if (token!=null) {
            headers.put("X-AUTHTOKEN", token);
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("order","id");
        params.put("now_time",String.valueOf(System.currentTimeMillis()));

        if (requestMethod==RequestMethod.GET){
            params.putAll(param);
        }

        int index=0;
        for (Map.Entry<String,String> entry:params.entrySet()){
            if (index==0) {
                servername = servername+"?";
            }else{
                servername = servername+"&";
            }
            servername=servername +entry.getKey()+"="+entry.getValue();
            index++;
        }

        HttpUtil.getInstance().request(requestMethod,Constant.BASE_URL+servername,param,headers,httpCallBack);

    }

    /**
     *
     * @param requestMethod  RequestMethod.POST
     * @param servername
     * @param param
     * @param httpCallBack
     * @param <T>
     */
    public <T> void doJSONTast(int requestMethod, String servername, Map<String, Object> param, HttpCallBack<T> httpCallBack){

        HashMap<String, String> headers = new HashMap<>();
        if (token!=null) {
            headers.put("X-AUTHTOKEN", token);
        }
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("order","id");
        params.put("now_time",String.valueOf(System.currentTimeMillis()));

        int index=0;
        for (Map.Entry<String,String> entry:params.entrySet()){
            if (index==0) {
                servername = servername+"?";
            }else{
                servername = servername+"&";
            }
            servername=servername +entry.getKey()+"="+entry.getValue();
            index++;
        }

        HttpUtil.getInstance().jsonRequest(requestMethod,Constant.BASE_URL+servername,param,headers,httpCallBack);

    }

}
