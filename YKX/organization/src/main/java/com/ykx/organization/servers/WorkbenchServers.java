package com.ykx.organization.servers;

import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.RequestMethod;
import com.ykx.organization.storage.vo.WorkbenchTJInfo;

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
 * Created by 2017/4/26.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class WorkbenchServers extends BaseHttp {

    /** GET /v1/agency/panel
     * 获取工作台主题数据
     * @param callBack
     */
    public void getWorkbenchMainDatas(HttpCallBack<WorkbenchTJInfo> callBack){
        String serverrul="v1/agency/panel";
        Map<String, String> params=new HashMap<>();

        doTast(RequestMethod.GET,serverrul,params,callBack);
    }

}
