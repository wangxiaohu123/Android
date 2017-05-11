package com.ykx.organization.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.views.CustomEditText;
import com.ykx.baselibs.views.SubmitStateView;
import com.ykx.organization.R;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.LoginReturnInfo;

public class SetPasswordActivity extends BaseActivity {

    private int doTag;//1:注册账号，2:找回密码

    private CustomEditText passwordView;
    private SubmitStateView submitStateView;

    private String username,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doTag=getIntent().getIntExtra("doTag",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        initUI();
    }

    private void initUI(){
        passwordView= (CustomEditText) findViewById(R.id.reset_password);
        passwordView.setLeftImageView(BitmapUtils.getDrawable(this,R.drawable.svg_password));
        passwordView.setRightImageView(BitmapUtils.getDrawable(this,R.drawable.svg_eye));
        if (doTag==1){
            passwordView.setHintText(getResources().getString(R.string.set_password_edittext_input));
        }else{
            passwordView.setHintText(getResources().getString(R.string.set_password_edittext_new_input));
        }
        passwordView.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        submitStateView= (SubmitStateView) findViewById(R.id.submit_view);

        passwordView.getRightImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag=v.getTag();
                if (tag==null){
                    passwordView.setRightImageView(BitmapUtils.getDrawable(SetPasswordActivity.this,R.drawable.svg_eye_hover));
                    passwordView.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                    v.setTag("");
                }else{
                    passwordView.setRightImageView(BitmapUtils.getDrawable(SetPasswordActivity.this,R.drawable.svg_eye));
                    passwordView.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    v.setTag(null);
                }
                passwordView.getEditText().setSelection(passwordView.getEditText().getText().length());
            }
        });

        username=getIntent().getStringExtra("username");
        code=getIntent().getStringExtra("code");

    }

    @Override
    protected void onResume() {
        super.onResume();
        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.set_password_textview_commint),null);
    }

    @Override
    protected String titleMessage() {
        if (doTag==1){
            return getResources().getString(R.string.set_password_textview_title);
        }else if(doTag==2){
            return getResources().getString(R.string.find_password_textview_title);
        }
        return super.titleMessage();
    }

    public void registerAction(View view){
        final String password=passwordView.getEditText().getText().toString();

        if (password.length()<6||password.length()>16){
            toastMessage(getResources().getString(R.string.login_edittext_input_pw_toast_num_excption));
            return;
        }
        if (password.length()==0){
            toastMessage(getResources().getString(R.string.login_edittext_input_pw_toast_null));
            return;
        }

        submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);

        if (doTag==1){
            new UserServers().register(username, code, password, new HttpCallBack<LoginReturnInfo>() {
                @Override
                public void onSuccess(LoginReturnInfo data) {
                    PreManager.getInstance().setLoginInfo(username,password,"","");
                    MMCacheUtils.setLoginReturnInfo(data);
                    BaseHttp.setToken(data.getToken());
                    Intent intent=new Intent(SetPasswordActivity.this,HomeActivity.class);
                    startActivity(intent);
                    BaseApplication.application.clearActivity();
                    submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,"注册成功");

                }

                @Override
                public void onFail(String msg) {
                    submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.set_password_textview_commint),"注册失败");
                }
            });
        }else if(doTag==2){

            new UserServers().findPassword(username, code, password, new HttpCallBack<LoginReturnInfo>() {
                @Override
                public void onSuccess(LoginReturnInfo data) {
                    PreManager.getInstance().setLoginInfo(username,password,PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,""),PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,""));
                    MMCacheUtils.setLoginReturnInfo(data);
                    BaseHttp.setToken(data.getToken());
                    Intent intent=new Intent(SetPasswordActivity.this,HomeActivity.class);
                    startActivity(intent);
                    BaseApplication.application.clearActivity();
                    submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,"找回密码成功");
                }

                @Override
                public void onFail(String msg) {
                    submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.set_password_textview_commint),"找回密码失败");
                }
            });
        }
    }

}
