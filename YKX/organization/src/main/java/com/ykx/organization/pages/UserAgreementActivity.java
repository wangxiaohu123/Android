package com.ykx.organization.pages;

import android.os.Bundle;

import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BaseWebActivity;

public class UserAgreementActivity extends BaseWebActivity {

    private String user_agreement_url="file:///android_asset/yhxy/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.sys_activity_user_agreement_title);
    }

    @Override
    protected String getLoadUrl() {
        return user_agreement_url;
    }
}
