package com.ykx.organization.pages.usercenter.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.views.CustomEditText;
import com.ykx.baselibs.views.SubmitStateView;
import com.ykx.organization.R;
import com.ykx.organization.servers.UserServers;

public class CommintPWActivity extends BaseActivity {

    private CustomEditText changePWView;
    private SubmitStateView submitStateView;

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        code=getIntent().getStringExtra("code");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commint_pw);

        initUI();
    }

    private void initUI() {

        changePWView= (CustomEditText) findViewById(R.id.change_password);
        changePWView.setLeftImageView(BitmapUtils.getDrawable(this,R.drawable.svg_password));
        changePWView.setHintText(getResources().getString(R.string.login_edittext_input_password));
        changePWView.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        submitStateView= (SubmitStateView) findViewById(R.id.submit_view);

    }


    @Override
    protected void onResume() {
        super.onResume();

        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.persion_center_info_jg_setting_change_pw_set_do),null);
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.persion_center_info_jg_setting_change_pw_set_title);
    }

    public void changePWAction(View view){

        final String pw=changePWView.getEditText().getText().toString();

        if (pw.length()<6||pw.length()>16){
            toastMessage(getResources().getString(R.string.login_edittext_input_pw_toast_num_excption));
            return;
        }
        if (pw.length()==0){
            toastMessage(getResources().getString(R.string.login_edittext_input_pw_toast_null));
            return;
        }

        submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);
        new UserServers().updatePassword(code, pw, new HttpCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                PreManager.getInstance().setLoginInfo(PreManager.getInstance().getData(PreManager.USER_NAME,""),pw,PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,""),PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,""));
                Intent intent=new Intent("com.ykx.organization.pages.HomeActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.persion_center_info_jg_setting_change_pw_set_do)+getResString(R.string.sys_toast_success));

            }

            @Override
            public void onFail(String msg) {
                submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.persion_center_info_jg_setting_change_pw_set_do),getResString(R.string.persion_center_info_jg_setting_change_pw_set_do)+getResString(R.string.sys_toast_fail));

            }
        });


    }

}
