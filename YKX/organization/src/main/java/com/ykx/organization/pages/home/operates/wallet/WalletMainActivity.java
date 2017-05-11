package com.ykx.organization.pages.home.operates.wallet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;

public class WalletMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_main);
    }

    @Override
    protected String titleMessage() {
        final String brandid= PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,getResString(R.string.sys_default_null));
        String zsrps=String.format(getResources().getString(R.string.activity_operate_wallet_main_title),brandid);
        return zsrps;
    }


    protected Drawable getPageBackgroudDrawable() {
        return  getSysDrawable(R.color.default_line_color);
    }



    public void toUBDetailAction(View view){

        Intent intent=new Intent(this,UBActivity.class);
        startActivity(intent);

    }

}
