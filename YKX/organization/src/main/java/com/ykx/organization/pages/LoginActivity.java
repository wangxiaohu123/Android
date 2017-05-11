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
import com.ykx.organization.storage.vo.AgenciesVO;
import com.ykx.organization.storage.vo.LoginReturnInfo;

public class LoginActivity extends BaseActivity {

    private CustomEditText userNameView;
    private CustomEditText passwordView;

    private SubmitStateView submitStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {

        userNameView= (CustomEditText) findViewById(R.id.login_username);
        passwordView= (CustomEditText) findViewById(R.id.login_password);
        submitStateView= (SubmitStateView) findViewById(R.id.login_view);

        userNameView.setLeftImageView(BitmapUtils.getDrawable(this,R.drawable.svg_phone));
        passwordView.setLeftImageView(BitmapUtils.getDrawable(this,R.drawable.svg_password));

        userNameView.setHintText(getResources().getString(R.string.login_edittext_input_phone));
        passwordView.setHintText(getResources().getString(R.string.login_edittext_input_password));

        userNameView.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
        passwordView.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        String phone=PreManager.getInstance().getData(PreManager.USER_NAME,"");
        if ((phone!=null)&&(phone.length()>0)){
            userNameView.getEditText().setText(phone);
        }
//
//        userNameView.getEditText().setText("15982848645");
//        passwordView.getEditText().setText("11111111");

//        userNameView.getEditText().setText("13169818574");
//        passwordView.getEditText().setText("123123");

    }

    @Override
    protected void onResume() {
        super.onResume();
        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.login_textview_login),null);
        BaseApplication.application.clearActivityExcepted(this.getClass());
    }

    @Override
    protected boolean isHideActionBar() {
        return true;
    }


    public void loginAction1(View view){

//        //注册失败会抛出HyphenateException
//        final String phone=userNameView.getEditText().getText().toString();
//        final String password=passwordView.getEditText().getText().toString();
//
//        new AsyncTask<String,String,String>(){
//            @Override
//            protected String doInBackground(String... params) {
//                try {
//                    EMClient.getInstance().createAccount(phone, password);//同步方法
//                    Log.d("xx","注册成功");
//                } catch (HyphenateException e) {
//                    Log.d("xx","注册失败");
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//            }
//        }.execute();
    }

    public void loginAction(final View view){

//        Intent intent=new Intent(LoginActivity.this,CampusListActivity.class);
//        startActivity(intent);


//        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
//        startActivity(intent);

//        return;
        final String phone=userNameView.getEditText().getText().toString();
        final String password=passwordView.getEditText().getText().toString();

        if (phone.length()==0){
            toastMessage(getResources().getString(R.string.login_edittext_input_phone_toast_null));
            return;
        }
        if (phone.length()!=11){
            toastMessage(getResources().getString(R.string.login_edittext_input_phone_toast_num_excption));
            return;
        }
        if (password.length()<6||password.length()>16){
            toastMessage(getResources().getString(R.string.login_edittext_input_pw_toast_num_excption));
            return;
        }
        if (password.length()==0){
            toastMessage(getResources().getString(R.string.login_edittext_input_pw_toast_null));
            return;
        }

        if ((phone.length()>0)&&(password.length()>0)) {
            submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);
            new UserServers().login(phone, password, new HttpCallBack<LoginReturnInfo>() {
                @Override
                public void onSuccess(LoginReturnInfo data) {
                    MMCacheUtils.setLoginReturnInfo(data);
                    BaseHttp.setToken(data.getToken());
                    if (data.getAgencies()==null) {
                        PreManager.getInstance().setLoginInfo(phone,password,"","");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        BaseApplication.application.clearActivity();
                        submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.login_success_toast));
                    }else{
                        if (data.getAgencies().size()==1){
                            final AgenciesVO agenciesVO = data.getAgencies().get(0);
                            if (agenciesVO!=null) {
                                new UserServers().handleAgence(String.valueOf(agenciesVO.getAgency_id()), new HttpCallBack<Object>() {
                                    @Override
                                    public void onSuccess(Object message) {
                                        PreManager.getInstance().setLoginInfo(phone,password,String.valueOf(agenciesVO.getAgency_id()),agenciesVO.getBrand_name());
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        BaseApplication.application.clearActivity();
                                        submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.login_success_toast));
                                    }

                                    @Override
                                    public void onFail(String msg) {
                                        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.login_textview_login),null);
                                    }
                                });
                            }else{
                                submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.login_textview_login),null);
                            }
                        }else{
                            Intent intent = new Intent(LoginActivity.this, SelectedBrandActivity.class);
                            intent.putExtra("phone",phone);
                            intent.putExtra("password",password);
                            intent.putExtra("data",data);
                            startActivity(intent);
                            BaseApplication.application.clearActivity();
                            submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.login_success_toast));
                        }
                    }
//                    finish();
                }

                @Override
                public void onFail(String msg) {
                    submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.login_textview_login),null);
                }
            });
        }

    }

    public void forgetPasswordAction(View view){

        Intent intent=new Intent(this,ForgetPasswordActivity.class);

        startActivity(intent);

    }

    public void registerAction(View view){

        Intent intent=new Intent(this,RegisterActivity.class);

        startActivity(intent);

    }
}
