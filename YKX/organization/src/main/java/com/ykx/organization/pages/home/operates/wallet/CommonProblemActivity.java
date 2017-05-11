package com.ykx.organization.pages.home.operates.wallet;

import android.os.Bundle;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;

public class CommonProblemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_problem);
    }


    @Override
    protected String titleMessage() {
        return getResString(R.string.activity_operate_wallet_common_problem_title);
    }
}
