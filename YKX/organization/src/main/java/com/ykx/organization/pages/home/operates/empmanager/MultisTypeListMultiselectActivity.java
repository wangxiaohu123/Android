package com.ykx.organization.pages.home.operates.empmanager;

import android.os.Bundle;

import com.ykx.organization.pages.bases.BaseTypeListMultiselectActivity;

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
 * Created by 2017/4/13.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class MultisTypeListMultiselectActivity extends BaseTypeListMultiselectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isMultiselected() {
        return true;
    }
}
