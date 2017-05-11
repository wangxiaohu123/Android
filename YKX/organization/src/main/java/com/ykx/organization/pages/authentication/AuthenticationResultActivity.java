package com.ykx.organization.pages.authentication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.organization.R;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.vo.SMAutoInfoVO;

public class AuthenticationResultActivity extends BaseActivity {

    private View contentView;
    private ImageView stateImageView,logoImageView;

    private TextView titleView,desView;

    private TextView reautoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_result);

        initUI();
        loadData();
    }

    private void initUI(){
        contentView=findViewById(R.id.state_view);
        stateImageView= (ImageView) findViewById(R.id.state_imageview);
        logoImageView= (ImageView) findViewById(R.id.logo_imageview);
        titleView= (TextView) findViewById(R.id.state_title_view);
        desView= (TextView) findViewById(R.id.state_des_vew);
        reautoView= (TextView) findViewById(R.id.reauto_view);

        reautoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AuthenticationResultActivity.this,AuthenticationActivity.class);
                startActivity(intent);
            }
        });

    }


    private void loadData(){
        new UserServers().apply(new HttpCallBack<SMAutoInfoVO>() {
            @Override
            public void onSuccess(SMAutoInfoVO data) {
                if (data==null){
                    return;
                }
                if (data.getStatus()==-1){
                    Intent intent=new Intent(AuthenticationResultActivity.this,AuthenticationActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    contentView.setVisibility(View.VISIBLE);
                    String title="";
                    String des="";
                    Drawable res= BitmapUtils.getDrawable(AuthenticationResultActivity.this,R.drawable.svg_wait);
                    if(data.getStatus()==0){
                        res= BitmapUtils.getDrawable(AuthenticationResultActivity.this,R.drawable.svg_wait);
                        title=getResString(R.string.authentication_register_result_state0_title);
                        des=getResString(R.string.authentication_register_result_state0_des);
                    }else if(data.getStatus()==1){
                        res= BitmapUtils.getDrawable(AuthenticationResultActivity.this,R.drawable.svg_pass);
                        title=getResString(R.string.authentication_register_result_state1_title);
                        des=data.getName();
                        logoImageView.setVisibility(View.VISIBLE);
                        BaseApplication.application.getDisplayImageOptions(data.getCharter_url(),logoImageView);
                    }else if (data.getStatus()==2){
                        res= BitmapUtils.getDrawable(AuthenticationResultActivity.this,R.drawable.svg_brand_btg);
                        title=getResString(R.string.authentication_register_result_state2_title);
                        des=getResString(R.string.authentication_register_result_state2_des)+data.getReason();
                        reautoView.setVisibility(View.VISIBLE);
                    }
                    stateImageView.setImageDrawable(res);
                    titleView.setText(title);
                    desView.setText(des);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.authentication_register_result_info_title);
    }
}
