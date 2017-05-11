package com.ykx.organization.pages.home.operates.brandmanager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.views.CustomEditText;
import com.ykx.baselibs.views.SubmitStateView;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrandModelEmailActivity extends BaseActivity {

    private SubmitStateView submitStateView;
    private CustomEditText customEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_model_email);
        initUI();
    }

    private void initUI(){
        customEditText= (CustomEditText) findViewById(R.id.email_view);
        submitStateView= (SubmitStateView) findViewById(R.id.send_email_view);

        customEditText.getEditText().setHint(getResString(R.string.brandmanager_activity_modle_email_input_hint));
        customEditText.setLeftImageView(BitmapUtils.getDrawable(this,R.drawable.svg_email));
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.brandmanager_activity_modle_email_submit),null);
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.brandmanager_activity_modle_email_title);
    }

    protected boolean isBackButton() {
        return false;
    }

    @Override
    protected RightResourceInterface leftResource() {

        return new RightResourceInterface() {
            @Override
            public String viewTitle() {
                return null;
            }

            @Override
            public Drawable viewIcon() {
                return BitmapUtils.getDrawable(BrandModelEmailActivity.this, R.drawable.svg_close);
            }
        };
    }

    @Override
    protected void setLeftView(LinearLayout leftView) {
        leftView.setPadding(0,-DensityUtil.dip2px(this,8),0,0);
    }

    @Override
    protected void callBack() {
        finish();
    }

    public void sendEmailAction(View view){

        String email=customEditText.getEditText().getText().toString();
        if (email.length()<=0) {
            toastMessage(getResString(R.string.brandmanager_activity_modle_email_input_hint));
            return;
        }
        if(checkEmaile(email)){

            submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);
            new OperateServers().brandSendEmail(email, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String data) {
                    submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.brandmanager_activity_modle_email_send_success_hint));
                    finish();
                }

                @Override
                public void onFail(String msg) {
                    submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,null,getResString(R.string.brandmanager_activity_modle_email_send_fail_hint));
                    submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.brandmanager_activity_modle_email_submit),null);
                }
            });

        }else{
            toastMessage(getResString(R.string.brandmanager_activity_modle_email_exception_hint));
            return;
        }

    }

    /**
     * 正则表达式校验邮箱
     * @param emaile 待匹配的邮箱
     * @return 匹配成功返回true 否则返回false;
     */
    private boolean checkEmaile(String emaile){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配
        return m.matches();
    }

}
