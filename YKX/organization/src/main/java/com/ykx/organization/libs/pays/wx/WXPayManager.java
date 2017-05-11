package com.ykx.organization.libs.pays.wx;

import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ykx.baselibs.pages.BaseActivity;

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
 * Created by 2017/5/11.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class WXPayManager {


    private BaseActivity baseActivity;

    public WXPayManager(BaseActivity baseActivity){
        this.baseActivity=baseActivity;
    }

    public void pay(){
        IWXAPI api = WXAPIFactory.createWXAPI(baseActivity, "wxdc3f72e03aa9168e");
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported){
            PayReq req = new PayReq();
            //req.appId = "wxf8b4f85f3a794e77";
            req.appId			= "wxdc3f72e03aa9168e";
            req.partnerId		= "1463223902";
            req.prepayId		= "wx20170511162353666885";
            req.packageValue    = "Sign=WXPay";
            req.nonceStr		= "i328944eu1j0picrxi0r0jv74kpnwgfd";
            req.timeStamp		= "1494491033";
            req.sign			= "DC51C1C86CBCF1022B8DB2652EFD14E9";
//            req.extData			= "app data"; // optional
            api.sendReq(req);
//            boolean isflag = api.sendReq(req);
//            Toast.makeText(baseActivity,"isflag="+isflag,Toast.LENGTH_LONG).show();
//            Log.d("xx","isflag="+isflag);
        }else{
            Toast.makeText(baseActivity, String.valueOf(isPaySupported), Toast.LENGTH_SHORT).show();
        }

    }

}
