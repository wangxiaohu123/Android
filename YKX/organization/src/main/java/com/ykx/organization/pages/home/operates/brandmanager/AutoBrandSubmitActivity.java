package com.ykx.organization.pages.home.operates.brandmanager;

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
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.BrandInfoVO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AutoBrandSubmitActivity extends BaseActivity {

    private String brandName;
    private String brandNum;
    private String brandType;

    private SubmitStateView submitStateView;

    private EditText yylxrView,llsjView,zjdhView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        brandName=getIntent().getStringExtra("brandName");
        brandNum=getIntent().getStringExtra("brandNum");
        brandType=getIntent().getStringExtra("brandType");
        if (brandType==null){
            brandType="";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_brand_submit);

        initUI();
        resetUI();
    }

    private void initUI(){
        yylxrView= (EditText) findViewById(R.id.persion_name_edittext);
        llsjView= (EditText) findViewById(R.id.persion_telphone_edittext);
        zjdhView= (EditText) findViewById(R.id.persion_phone_edittext);
        submitStateView=(SubmitStateView)findViewById(R.id.persion_submitview);

        boolean isadd = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_brand,RoleConstants.role_add);
        if (!isadd){
            submitStateView.setVisibility(View.GONE);
        }
    }

    private void resetUI(){

        setUnAbleNull(R.id.ywlxt_view);
        setUnAbleNull(R.id.lxsj_view);

    }


    @Override
    protected void onResume() {
        super.onResume();

        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.authentication_register_next_bxxk_tjdj),null);
    }


    @Override
    protected String titleMessage() {
        return getResString(R.string.brandmanager_activity_base_info_mamager_dj_title);
    }


    public void doAction(View view){

        final String lxrName=yylxrView.getText().toString();
        final String lxrPhone=llsjView.getText().toString();
        final String lxrtel=zjdhView.getText().toString();

        if (lxrName.length()<=0){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_mamager_ywlxr_hint));
            return;
        }
        if (lxrPhone.length()<=0){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_mamager_lxsj_hint));
            return;
        }


        submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);

        if (AutoBrandActivity.bitmap1!=null) {
            List<FileVO> filespublic=new ArrayList<>();
            filespublic.add(new FileVO(AutoBrandActivity.bitmap1, "logo"));
            QNFileUpAndDownManager qnFileUpAndDownManager= new QNFileUpAndDownManager();
            qnFileUpAndDownManager.init();
            qnFileUpAndDownManager.upfiles(filespublic, new QNFileUpAndDownManager.FileCallBack() {
                @Override
                public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {
                    if (uploadtag) {
                        String logo=uploadfiles.get("logo");
                        submitData(lxrName,lxrPhone,lxrtel,logo);
                    }else{
                        submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.authentication_register_next_bxxk_tjdj),getResString(R.string.authentication_register_next_bxxk_tjdj)+getResString(R.string.sys_toast_fail));
                    }
                }
            });

        }else{
            submitData(lxrName,lxrPhone,lxrtel,"");
        }



    }


    private void submitData(final String lxrName,final String lxrPhone,final String lxrtel,final String logo){
        List<FileVO> files=new ArrayList<>();
        files.add(new FileVO(AutoBrandActivity.bitmap2,"certificate"));
        files.add(new FileVO(AutoBrandActivity.bitmap3,"attorney"));
        QNFileUpAndDownManager qnFileUpAndDownManager= new QNFileUpAndDownManager();
        qnFileUpAndDownManager.init(QNFileUpAndDownManager.TokenType.PRIVATE);
        qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
            @Override
            public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {
                if (uploadtag) {
                    String certificate=uploadfiles.get("certificate");
                    String attorney=uploadfiles.get("attorney");

                    new OperateServers().saveOrUpdateBrandAutoInfo(brandName, logo, brandType, brandNum, certificate, attorney, lxrName, lxrPhone, lxrtel, new HttpCallBack<BrandInfoVO.Branding>() {
                        @Override
                        public void onSuccess(BrandInfoVO.Branding data) {

                            Intent intent=new Intent("com.ykx.organization.pages.home.operates.brandmanager.BrandManagerMainActivity");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            data.setStatus(0);
                            data.setIs_apply(false);
                            intent.putExtra("branding",data);
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
