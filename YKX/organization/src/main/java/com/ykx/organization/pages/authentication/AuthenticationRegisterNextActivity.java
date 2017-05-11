package com.ykx.organization.pages.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.QNFileUpAndDownManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.views.SubmitStateView;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.AuthenticationInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AuthenticationRegisterNextActivity extends BaseActivity {

    private int tag=0;

//    private Bitmap xkzBitmap;
//    private Bitmap sfzzmBitmap;
//    private Bitmap sfzfmBitmap;

    private String inputname;
    private String address;

    private EditText personNameView;
    private EditText personTelPhoneView;
    private EditText personPhoneView;
    private SubmitStateView submitStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tag=getIntent().getIntExtra("register_type",0);//1->办学许可登记,2->营业执照登记

        inputname=getIntent().getStringExtra("inputname");
        address=getIntent().getStringExtra("address");
//        xkzBitmap=getIntent().getParcelableExtra("xkzBitmap");
//        sfzzmBitmap=getIntent().getParcelableExtra("sfzzmBitmap");
//        sfzfmBitmap=getIntent().getParcelableExtra("sfzfmBitmap");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_register_next);

        initUI();

        resetUI();
    }

    private void resetUI(){

        setUnAbleNull(R.id.ywlxt_view);
        setUnAbleNull(R.id.lxsj_view);
//        setUnAbleNull(R.id.zjdh_view);
    }

    public void initUI(){

        personNameView= (EditText) findViewById(R.id.persion_name_edittext);
        personTelPhoneView= (EditText) findViewById(R.id.persion_telphone_edittext);
        personPhoneView= (EditText) findViewById(R.id.persion_phone_edittext);
        submitStateView= (SubmitStateView) findViewById(R.id.persion_submitview);

        boolean isadd = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_real,RoleConstants.role_add);
        if (!isadd){
            submitStateView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.authentication_register_next_bxxk_tjdj),null);
    }

    @Override
    protected String titleMessage() {
        if (tag==1){
            return getResources().getString(R.string.authentication_register_main_bxxk_title);
        }else if(tag==2){
            return getResources().getString(R.string.authentication_register_main_yyzz_title);

        }
        return super.titleMessage();
    }

    public void doAction(View view){

        final String username=personNameView.getText().toString();
        final String telphone=personTelPhoneView.getText().toString();
        final String phone=personPhoneView.getText().toString();

        if (username.length()<=0){
            toastMessage(getResources().getString(R.string.authentication_register_next_bxxk_ywlxr_hint));
            return;
        }
        if (telphone.length()<=0){
            toastMessage(getResources().getString(R.string.authentication_register_next_bxxk_lxsj_hint));
            return;
        }
//        if (phone.length()<=0){
//            toastMessage(getResources().getString(R.string.authentication_register_next_bxxk_zjdh_hint));
//            return;
//        }

        List<FileVO> files=new ArrayList<>();
        files.add(new FileVO(AuthenticationRegisterMainActivity.xkzBitmap,"charter"));
        files.add(new FileVO(AuthenticationRegisterMainActivity.sfzzmBitmap,"front"));
        files.add(new FileVO(AuthenticationRegisterMainActivity.sfzfmBitmap,"back"));

        submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);
        QNFileUpAndDownManager qnFileUpAndDownManager= new QNFileUpAndDownManager();
        qnFileUpAndDownManager.init(QNFileUpAndDownManager.TokenType.PRIVATE);
        qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
            @Override
            public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {
                 if (uploadtag){
                     new UserServers().apply(String.valueOf(tag), inputname, address, username, telphone, phone,uploadfiles, new HttpCallBack<AuthenticationInfo>() {
                         @Override
                         public void onSuccess(AuthenticationInfo data) {


                             Intent intent=new Intent("com.ykx.organization.pages.HomeActivity");
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             startActivity(intent);
                             submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.authentication_register_next_bxxk_tjdj)+getResString(R.string.sys_toast_success));
                         }

                         @Override
                         public void onFail(String msg) {
                             submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.authentication_register_next_bxxk_tjdj),getResString(R.string.authentication_register_next_bxxk_tjdj)+getResString(R.string.sys_toast_fail));

                         }
                     });
                 }else{
                     submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.authentication_register_next_bxxk_tjdj),getResString(R.string.authentication_register_next_bxxk_tjdj)+getResString(R.string.sys_toast_fail));

                 }
            }
        });


    }

}
