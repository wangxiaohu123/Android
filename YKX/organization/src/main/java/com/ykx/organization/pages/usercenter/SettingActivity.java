package com.ykx.organization.pages.usercenter;

import android.os.Bundle;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected String titleMessage() {


        return getResources().getString(R.string.persion_center_info_jg_sz);
    }
}
