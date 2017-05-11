package com.ykx.organization.pages.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;

public class AuthenticationActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        initUI();
    }

    private void initUI(){
    }


    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.authentication_home_title);
    }


    /**
     * 办学许可证登记点击事件
     * @param view
     */
    public void clickItemXXAction(View view){

        Intent intent=new Intent(this,AuthenticationRegisterMainActivity.class);
        intent.putExtra("register_type",1);


        startActivity(intent);
    }
    /**
     * 营业执照登记点击事件
     * @param view
     */
    public void clickItemSYAction(View view){


        Intent intent=new Intent(this,AuthenticationRegisterMainActivity.class);
        intent.putExtra("register_type",2);


        startActivity(intent);

    }


}
