package com.ykx.organization.pages.home.operates.brandmanager;

import android.os.Bundle;

import com.ykx.baselibs.commons.Constant;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BaseWebActivity;

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
 * Created by 2017/4/20.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class BrandDetailInfoActivity extends BaseWebActivity {

    private final StringBuffer stringBuffer=new StringBuffer(Constant.WEB_APP_URL+"b-brandMsg?agency_id=");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected String getLoadUrl() {
        return stringBuffer.append(PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,"")).toString();
    }

    @Override
    protected String titleMessage() {
        String brandName=PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,getResString(R.string.activity_webview_title_brand));
        return brandName;
    }
}
