package com.ykx.organization.pages.usercenter.setting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.views.CustomEditText;
import com.ykx.organization.R;
import com.ykx.organization.servers.CommonServers;


public class GetPhoneCodeActivity extends BaseActivity {

    private CustomEditText phoneCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_code);

        initUI();
    }

    private void initUI() {
        phoneCodeView= (CustomEditText) findViewById(R.id.change_phone_code);

        phoneCodeView.setHintText(getResources().getString(R.string.register_edittext_input_verification_code));
        phoneCodeView.setRightImageView(null);
//        phoneCodeView.getEditText().setEnabled(false);

    }

    public void getPhoneCodeAction(final View view){
        new CommonServers().getPhoneCodeByPWDfind(new HttpCallBack<String>() {
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
//                phoneCodeView.getEditText().setText(data);
            }

            @Override
            public void onFail(String msg) {
                view.setEnabled(true);
            }
        });
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.persion_center_info_jg_setting_change_pw_title);
    }

    public void nextPWAction(View view){

        String code=phoneCodeView.getEditText().getText().toString();
        if (code.length()>0) {

            Intent intent = new Intent(this, CommintPWActivity.class);
            intent.putExtra("code", code);

            startActivity(intent);
        }else{
            toastMessage(getResources().getString(R.string.register_edittext_get_verification_code));
        }
    }
}
