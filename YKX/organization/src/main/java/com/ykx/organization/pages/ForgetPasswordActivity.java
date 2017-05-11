package com.ykx.organization.pages;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.views.CustomEditText;
import com.ykx.organization.R;
import com.ykx.organization.servers.CommonServers;

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
 * Created by 2017/3/8.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class ForgetPasswordActivity extends BaseActivity {


    private CustomEditText userNameView;
    private CustomEditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initUI();
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.forget_password_textview_title);
    }

    private void initUI() {
        userNameView= (CustomEditText) findViewById(R.id.login_username);
        passwordView= (CustomEditText) findViewById(R.id.login_password);

        userNameView.setLeftImageView(BitmapUtils.getDrawable(this,R.drawable.svg_phone));

        userNameView.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
        userNameView.setHintText(getResources().getString(R.string.register_edittext_input_phone));

        passwordView.setHintText(getResources().getString(R.string.register_edittext_input_verification_code));
        passwordView.setRightImageView(null);
//        passwordView.getEditText().setEnabled(false);

    }

    public void getPhoneCodeAction(final View view){
        String  phone=userNameView.getEditText().getText().toString();

        if (phone.length()==0){
            toastMessage(getResources().getString(R.string.login_edittext_input_phone_toast_null));
            return;
        }
        if (phone.length()!=11){
            toastMessage(getResources().getString(R.string.login_edittext_input_phone_toast_num_excption));
            return;
        }

        view.setEnabled(false);
        new CommonServers().getPhoneCodeByCfind(phone, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {

                if (view instanceof TextView){
                    final TextView titleview= (TextView) view;
                    titleview.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);

                    new AsyncTask<String,String,String>(){
                        @Override
                        protected String doInBackground(String... params) {
                            int i=0;
                            while (i<60){
                                try {
                                    Thread.sleep(1000);
                                    publishProgress(String.valueOf(60-i));
                                    i++;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onProgressUpdate(String... values) {
                            super.onProgressUpdate(values);
                            String zsrss=String.format(getResources().getString(R.string.phone_code_textview_reset),values[0]);
                            titleview.setText(zsrss);
                            titleview.setTextColor(getResources().getColor(R.color.theme_main_background_un_deep_color));
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            view.setEnabled(true);
                            titleview.setText("");
                            titleview.setGravity(Gravity.CENTER);
                            titleview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
                            titleview.setText(getResString(R.string.register_edittext_get_verification_code));
                        }
                    }.execute();

                }

//                passwordView.getEditText().setText(data);
            }

            @Override
            public void onFail(String msg) {
                view.setEnabled(true);
            }
        });

    }



    public void nextAction(View view){

        String username=userNameView.getEditText().getText().toString();
        String code=passwordView.getEditText().getText().toString();

        if (username.length()==0){
            toastMessage(getResources().getString(R.string.login_edittext_input_phone_toast_null));
            return;
        }
        if (username.length()!=11){
            toastMessage(getResources().getString(R.string.login_edittext_input_phone_toast_num_excption));
            return;
        }

        if (code.length()<=0){
            toastMessage(getResources().getString(R.string.register_edittext_get_verification_code_toast));
            return;
        }

        Intent intent=new Intent(this,SetPasswordActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("code",code);
        intent.putExtra("doTag",2);

        startActivityForResult(intent,1001);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1001){
            if (resultCode==10011){

            }
        }
    }

}
