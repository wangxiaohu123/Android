package com.ykx.organization.servers;

import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.RequestMethod;
import com.ykx.organization.storage.vo.TokenVO;
import com.ykx.organization.storage.vo.VersionInfo;

import java.util.HashMap;
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

public class CommonServers extends BaseHttp {


    public void getVersionInfo(HttpCallBack<VersionInfo> callBack){

        String serverrul="v1/agency/version";
        Map<String, String> params=new HashMap<>();
        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端或C端用户获取图片上传Token  共有
     * @param callBack
     */
    public void upToken(HttpCallBack<TokenVO> callBack){

        String serverrul="v1/com/uptoken/public";

        Map<String, String> params=new HashMap<>();
        params.put("X-AUTHTOKEN",BaseHttp.getToken());

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端或C端用户获取图片上传Token  私有
     * @param callBack
     */
    public void upPrivateToken(HttpCallBack<TokenVO> callBack){

        String serverrul="v1/com/uptoken/private";

        Map<String, String> params=new HashMap<>();
        params.put("X-AUTHTOKEN",BaseHttp.getToken());

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     *
     breg B端注册
     bfind B端找回密码
     creg C端注册
     cfind C端找回密码
     * @param callBack
     */
    public void getPhoneCodeByCrge(String phone,HttpCallBack<String> callBack){

        String serverrul="v1/com/code";

        Map<String, String> params=new HashMap<>();
        params.put("type","breg");
        params.put("phone",phone);

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     *
     breg B端注册
     bfind B端找回密码
     creg C端注册
     cfind C端找回密码
     * @param callBack
     */
    public void getPhoneCodeByCfind(String phone,HttpCallBack<String> callBack){

        String serverrul="v1/com/code";

        Map<String, String> params=new HashMap<>();
        params.put("type","bfind");
        params.put("phone",phone);

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

    /**
     * B端-用户修改密码-验证码
     * @param callBack
     */
    public void getPhoneCodeByPWDfind(HttpCallBack<String> callBack){

        String serverrul="v1/agency/code";

        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }


}
