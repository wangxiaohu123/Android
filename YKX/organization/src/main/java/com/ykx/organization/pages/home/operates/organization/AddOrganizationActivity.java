package com.ykx.organization.pages.home.operates.organization;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.QNFileUpAndDownManager;
import com.ykx.baselibs.views.SubmitStateView;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BasePicActivity;
import com.ykx.organization.pages.home.operates.campus.AddCampusActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.LoginReturnInfo;
import com.ykx.organization.storage.vo.OraganizationVO;
import com.ykx.organization.storage.vo.TypesVO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AddOrganizationActivity extends BasePicActivity {


    private int REQUEST_SELECTED_TYPE_TAG=1000;//选择typevo的tag
//    private int REQUEST_LOGO_TAG=1001;//logo 图片的tag

    private ImageView logoImageView;
    private EditText organizationNameView;
    private TextView typeNameView;
    private SubmitStateView submitStateView;

    private Bitmap loginBitmap;
    private TypesVO typevo;
    private ImageView takePicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_organization);

        initUI();
    }

    private void initUI(){
        takePicImageView= (ImageView) findViewById(R.id.take_phone_logo_imageview);
        logoImageView= (ImageView) findViewById(R.id.logo_imageview);
        organizationNameView= (EditText) findViewById(R.id.input_name_edittext);
        typeNameView= (TextView) findViewById(R.id.organization_type_view);
        submitStateView= (SubmitStateView) findViewById(R.id.organizaiton_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.organization_activity_jg_save_and_create_xx),null);
    }

    @Override
    protected String titleMessage() {

        return getResources().getString(R.string.organization_activity_title);
    }

    public void showOrganizationTypeAction(View view){

        Intent intent=new Intent(this,TypeListActivity.class);
        intent.putExtra("action","com.ykx.organization.pages.home.operates.organization.AddOrganizationActivity");

        startActivityForResult(intent,REQUEST_SELECTED_TYPE_TAG);

    }

    public void takenLogoAction(View view){
//        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent1, REQUEST_LOGO_TAG);
        showPicDialog(new Size(5,5));
    }

    protected void resetBitmap(Bitmap bitmap){
        logoImageView.setImageBitmap(bitmap);
        logoImageView.setVisibility(View.VISIBLE);
        loginBitmap=bitmap;
        takePicImageView.setVisibility(View.GONE);
    }
    /**
     * 返回应用时回调方法
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_LOGO_TAG) {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
//                logoImageView.setImageBitmap(bitmap);
//                logoImageView.setVisibility(View.VISIBLE);
//                loginBitmap=bitmap;
//                takePicImageView.setVisibility(View.GONE);
//            }else
            if(requestCode==REQUEST_SELECTED_TYPE_TAG){
                typevo= (TypesVO) data.getSerializableExtra("typeVO");
                if (typevo!=null){
                    typeNameView.setText(typevo.getNames());
                }
            }
        }
    }



    public void commintAction(View view){

//        Intent intent=new Intent(AddOrganizationActivity.this, AddCurriculumActivity.class);
//
//        startActivity(intent);
//        finish();

        final String organizationName=organizationNameView.getText().toString();

        if (organizationName.length()<=0){
            toastMessage(getResources().getString(R.string.organization_activity_jg_jc_hint));
            return;
        }

        if (loginBitmap==null){
            toastMessage(getResources().getString(R.string.organization_activity_jg_logo_toast));
            return;
        }

        if (typeNameView.length()<=0){
            toastMessage(getResources().getString(R.string.organization_activity_jg_lm_toast));
            return;
        }

        // TODO: 2017/3/16 新加机构
        List<FileVO> files=new ArrayList<>();
        files.add(new FileVO(loginBitmap,"logo"));

        submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);


        QNFileUpAndDownManager qnFileUpAndDownManager= new QNFileUpAndDownManager();
        qnFileUpAndDownManager.init();
        qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
            @Override
            public void callback(boolean uploadtag, final LinkedHashMap<String, String> uploadfiles) {
                if (uploadtag){
                    new OperateServers().regiestOrganization(organizationName, typevo.getCodes(), uploadfiles, new HttpCallBack<OraganizationVO>() {
                        @Override
                        public void onSuccess(OraganizationVO data) {

                            if (data!=null){
                                LoginReturnInfo loginReturnInfo = MMCacheUtils.getLoginReturnInfo();
                                loginReturnInfo.setName(data.getName());
                                loginReturnInfo.setLogo_url(data.getLogo_url());
                            }

                            Intent intent=new Intent(AddOrganizationActivity.this, AddCampusActivity.class);

                            startActivity(intent);
                            finish();
                            submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.organization_activity_jg_save_and_create_xx)+getResString(R.string.sys_toast_success));

                        }

                        @Override
                        public void onFail(String msg) {
                            submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.organization_activity_jg_save_and_create_xx),getResString(R.string.organization_activity_jg_save_and_create_xx)+getResString(R.string.sys_toast_fail));

                        }
                    });
                }else{
                    submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.organization_activity_jg_save_and_create_xx),getResString(R.string.organization_activity_jg_save_and_create_xx)+getResString(R.string.sys_toast_fail));
                }
            }
        });

    }

}
