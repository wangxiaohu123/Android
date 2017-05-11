package com.ykx.organization.servers;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.RequestMethod;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.AuthenticationInfo;
import com.ykx.organization.storage.vo.EmpVO;
import com.ykx.organization.storage.vo.LoginReturnInfo;
import com.ykx.organization.storage.vo.SMAutoInfoVO;
import com.ykx.organization.storage.vo.UserInfoVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class UserServers extends BaseHttp {


    /**
     * B端-用户登录
     * @param username
     * @param password
     * @param callBack
     */
    public void login(String username,String password,final HttpCallBack<LoginReturnInfo> callBack){

        final String serverrul="v1/agency/login";

        final Map<String, String> params=new HashMap<>();
        params.put("phone",username);
        params.put("passwd",password);

        doTast(RequestMethod.POST,serverrul,params,callBack);

//        EMClient.getInstance().login(username,password,new EMCallBack() {//回调
//            @Override
//            public void onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//                Log.d("main", "登录聊天服务器成功！");
//
//                doTast(RequestMethod.POST,serverrul,params,callBack);
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//
//            }
//
//            @Override
//            public void onError(int code, final String message) {
//                Log.d("main", "登录聊天服务器失败！message="+message);
//                BaseApplication.application.getHandler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBack.onFail(message);
//                    }
//                });
//
//            }
//        });

    }

    /**
     * B端-用户登录-选择使用品牌
     * @param agencyId
     * @param callBack
     */
    public void handleAgence(String agencyId,final HttpCallBack<Object> callBack){

        String serverrul="v1/agency/handle/"+agencyId;

        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET, serverrul, params, new HttpCallBack<UserInfoVO>() {

            public void onSuccess(UserInfoVO data){

                final UserInfoVO.Easemob easemob = data.getEasemob();
                if(easemob!=null){

                    if (EMClient.getInstance().isLoggedInBefore()){
                        EMClient.getInstance().logout(true, new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                Log.e("EMClient", "登出聊天服务器成功！");
                                hxloing(easemob);
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                            }
                            @Override
                            public void onError(int code, final String message) {
                                Log.e("EMClient", "登出聊天服务器失败！message="+message);
                            }
                        });

                    }else{
                        hxloing(easemob);
                    }
                }


                MMCacheUtils.setUserInfoVO(data);
                if (callBack!=null) {
                    callBack.onSuccess(data);
                }
            }

            public void onFail(String msg){
                if (callBack!=null) {
                    callBack.onFail(msg);
                }
            }
        });
    }

    private void hxloing(UserInfoVO.Easemob easemob){
        EMClient.getInstance().login(easemob.getUsername(),easemob.getPassword(),new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("EMClient", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, final String message) {
                Log.e("EMClient", "登录聊天服务器失败！message="+message);
                BaseApplication.application.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

            }
        });
    }

    /**
     * B端-用户登出
     * @param callBack
     */
    public void logout(final HttpCallBack<ArrayList<String>> callBack){

        final String serverrul="v1/agency/logout";

        final Map<String, String> params=new HashMap<>();

//        doTast(RequestMethod.GET,serverrul,params,callBack);

        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.e("EMClient", "登出聊天服务器成功！");
                doTast(RequestMethod.GET,serverrul,params,callBack);
            }

            @Override
            public void onProgress(int progress, String status) {
            }
            @Override
            public void onError(int code, final String message) {
                Log.e("EMClient", "登出聊天服务器失败！message="+message);
                BaseApplication.application.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack!=null) {
                            callBack.onFail(message);
                        }
                    }
                });
            }
        });

    }

    /**
     * B端-用户修改密码
     * @param code
     * @param password
     * @param callBack
     */
    public void updatePassword(String code,String password,HttpCallBack<String> callBack){

        String serverrul="v1/agency/pwd";

        Map<String, String> params=new HashMap<>();
        params.put("code",code);
        params.put("passwd",password);

        doTast(RequestMethod.PATCH,serverrul,params,callBack);
    }

    /**
     * B端-用户找回密码
     * @param phone
     * @param code
     * @param password
     * @param callBack
     */
    public void findPassword(String phone,String code,String password,HttpCallBack<LoginReturnInfo> callBack){

        String serverrul="v1/agency/find";

        Map<String, String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        params.put("passwd",password);

        doTast(RequestMethod.PATCH,serverrul,params,callBack);
    }

    /**
     * B端-用户注册
     * @param phone
     * @param code
     * @param password
     * @param callBack
     */
    public void register(String phone,String code,String password,HttpCallBack<LoginReturnInfo> callBack){

        String serverrul="v1/agency/reg";

        Map<String, String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        params.put("passwd",password);

        doTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /** POST /v1/agency/operation/real/add
     * B端-申请实名登记
     * @param type
     * @param name
     * @param areaCode
     * @param linkman
     * @param linkphone
     * @param linktel
     * @param callBack
     */
    public void apply(String type, String name, String areaCode, String linkman, String linkphone, String linktel, LinkedHashMap<String, String> uploadfiles, HttpCallBack<AuthenticationInfo> callBack){
//        POST /v1/agency/cert
//        String serverrul="v1/agency/apply";
        String serverrul="v1/agency/operation/real/add";

        Map<String, String> params=new HashMap<>();
        params.put("type",type);
        params.put("name",name);
        params.put("adcode",areaCode);
        params.put("linkman",linkman);
        params.put("phone",linkphone);
        params.put("tel",linktel);

        for (Map.Entry<String,String> entrys:uploadfiles.entrySet()){
            params.put(entrys.getKey(),entrys.getValue());
        }

        doTast(RequestMethod.POST,serverrul,params,callBack);
    }

    /** GET /v1/agency/operation/real/view
     * B端-实名登记-登记信息
     * @param callBack
     */
    public void apply(HttpCallBack<SMAutoInfoVO> callBack){

        String serverrul="v1/agency/operation/real/view";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }


    /**
     * 获取用户信息
     * @param callBack
     */
    public void getUserInfo(HttpCallBack<EmpVO> callBack){
        String serverrul="v1/agency/me";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * 修改用户信息
     * @param avatar
     * @param summary
     * @param resume
     * @param honor
     * @param callBack
     */
    public void editUserInfo(String avatar,String summary,String resume,String honor,HttpCallBack<EmpVO> callBack){
        String serverrul="v1/agency/me";
        Map<String, String> params=new HashMap<>();
        params.put("avatar",avatar);
        params.put("summary",summary);
        params.put("resume",resume);
        params.put("honor",honor);

        doTast(RequestMethod.PUT,serverrul,params,callBack);
    }


}
