package com.ykx.organization.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.organization.R;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.libs.pays.alipay.PayDemoActivity;
import com.ykx.organization.libs.pays.wx.PayActivity;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.AgenciesVO;
import com.ykx.organization.storage.vo.LoginReturnInfo;

import java.util.List;

public class WelcomeActivity extends BaseActivity {

    private ImageView imageView;

    private static int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initUI();
    }

    private void initUI1(){

        if (index%2!=0) {
            startActivity(new Intent(this, PayActivity.class));
        }else{
            startActivity(new Intent(this,PayDemoActivity.class));
        }
        index++;
    }

    private void initUI(){
        imageView= (ImageView) findViewById(R.id.loading_progressbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final String phone=PreManager.getInstance().getData(PreManager.USER_NAME,"");
                final String password=PreManager.getInstance().getData(PreManager.PASSWORD,"");

                if ((phone!=null)&&(phone.length()>0)&&(password!=null)&&(password.length()>0)) {
                    startLoading();
                    new UserServers().login(phone, password, new HttpCallBack<LoginReturnInfo>() {
                        @Override
                        public void onSuccess(final LoginReturnInfo data) {
                            MMCacheUtils.setLoginReturnInfo(data);
                            BaseHttp.setToken(data.getToken());
                            final String brandid=PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,"");
                            if ((brandid!=null)&&(brandid.length()>0)){
                                callUserBrand(PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,""),brandid,phone,password);
                            }else{
                                List<AgenciesVO> lists=data.getAgencies();
                                if ((lists!=null)&&(lists.size()>1)){
                                    Intent intent = new Intent(WelcomeActivity.this, SelectedBrandActivity.class);
                                    intent.putExtra("phone",phone);
                                    intent.putExtra("password",password);
                                    intent.putExtra("data",data);
                                    startActivity(intent);
                                    BaseApplication.application.clearActivity();
                                    finish();
                                }else{
                                    if ((lists!=null)&&(lists.size()==1)){
                                        AgenciesVO agenciesVO = lists.get(0);
                                        String newbrandid=String.valueOf(agenciesVO.getAgency_id());
                                        callUserBrand(agenciesVO.getBrand_name(),newbrandid,phone,password);
                                    }else{
                                        PreManager.getInstance().setLoginInfo(phone, password,brandid,PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,""));
                                        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        BaseApplication.application.clearActivity();
                                        finish();
                                    }
                                }

                            }

                        }

                        @Override
                        public void onFail(String msg) {
                            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                }else{
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },1500);

    }

    private void callUserBrand(final String brandname,final String brandid,final String phone,final String password){
        new UserServers().handleAgence(brandid, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object message) {

                PreManager.getInstance().setLoginInfo(phone, password,brandid,brandname);
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                BaseApplication.application.clearActivity();
                finish();
            }

            @Override
            public void onFail(String msg) {
                toastMessage(getResString(R.string.login_fail_toast));
                imageView.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected boolean isHideActionBar() {
        return true;
    }


    private void startLoading(){
        imageView.setVisibility(View.VISIBLE);
        Animation operatingAnim = AnimationUtils.loadAnimation(this, com.ykx.baselibs.R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.setAnimation(operatingAnim);
    }
}
