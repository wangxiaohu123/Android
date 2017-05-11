package com.ykx.organization.pages.home.operates.brandmanager;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.QNFileUpAndDownManager;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.bases.BasePicActivity;
import com.ykx.organization.pages.home.operates.curriculum.CurriculumInfoDesActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.BrandInfoVO;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BrandInfoActivity extends BasePicActivity {

    private ImageView imageView,showpicimageview;
    private EditText brandNameView;

    private boolean isedit=true;

    private Bitmap bitmap;

    private BrandInfoVO.Brand brand;

    private int teachingFlag=1011;
    private int teamFlag=1021;
    private int honourFlag=1031;
    private int environmentFlag=1041;
    private int operationFlag=1051;

    private String teaching="";
    private String team="";
    private String honour="";
    private String environment="";
    private String operation="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isedit=getIntent().getBooleanExtra("isEdit",true);
        brand= (BrandInfoVO.Brand) getIntent().getSerializableExtra("brand");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_info);
        initUI();
        resetUI();
    }

    private void initUI(){
        imageView= (ImageView) findViewById(R.id.xkz_imageview);
        showpicimageview= (ImageView) findViewById(R.id.showpicimageview);

        brandNameView= (EditText) findViewById(R.id.input_name_edittext);

        if (brand!=null){
            brandNameView.setText(brand.getName());
            BaseApplication.application.getDisplayImageOptions(brand.getLogo_url(),imageView);
            showpicimageview.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

            teaching=brand.getTeaching();
            team=brand.getTeam();
            honour=brand.getHonour();
            environment=brand.getEnvironment();
            operation=brand.getOperation();
        }

        if (!isedit){
            brandNameView.setEnabled(false);

        }else{

        }
    }

    private void resetUI(){
        setUnAbleNull(R.id.name_textview);
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.brandmanager_activity_base_info_title);
    }


    @Override
    protected void setRightView(LinearLayout rightContentView) {

        if (brand!=null){
            boolean isedit = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_brand,RoleConstants.role_edit);
            if (!isedit){
                return;
            }
        }else{
            boolean isadd = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_brand,RoleConstants.role_add);
            if (!isadd){
                return;
            }
        }

        TextView rightview=new TextView(this);
        rightview.setGravity(Gravity.CENTER);
        rightview.setText(getResources().getString(R.string.curriculum_activity_add_title_save));
        rightview.setTextSize(15);
        rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
        rightContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isedit) {
                    submitAction();
                }
            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    private boolean selectPicAndMessageFlag=false;

    public void selectPicAndMessageAction(View view){
        selectPicAndMessageFlag=true;
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        if (view.getId()==R.id.item_view1){
            intent.putExtra("message",teaching);
            startActivityForResult(intent,teachingFlag);
        }else if (view.getId()==R.id.item_view2){
            intent.putExtra("message",team);
            startActivityForResult(intent,teamFlag);
        }else if (view.getId()==R.id.item_view3){
            intent.putExtra("message",honour);
            startActivityForResult(intent,honourFlag);
        }else if (view.getId()==R.id.item_view4){
            intent.putExtra("message",environment);
            startActivityForResult(intent,environmentFlag);
        }else if (view.getId()==R.id.item_view5){
            intent.putExtra("message",operation);
            startActivityForResult(intent,operationFlag);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (selectPicAndMessageFlag) {
                String message = data.getStringExtra("message");
                if (requestCode == teachingFlag) {
                    teaching = message;
                } else if (requestCode == teamFlag) {
                    team = message;
                } else if (requestCode == honourFlag) {
                    honour = message;
                } else if (requestCode == environmentFlag) {
                    environment = message;
                } else if (requestCode == operationFlag) {
                    operation = message;
                }
                selectPicAndMessageFlag=false;
            }
        }

    }

    private void submitAction(){

        final String name=brandNameView.getText().toString();

        if ((name.length()<=0)||(name.length()>12)){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_ppmc_hint));
            return;
        }

        if (bitmap!=null) {
            List<FileVO> files = new ArrayList<>();
            files.add(new FileVO(bitmap, "logo"));

            QNFileUpAndDownManager qnFileUpAndDownManager = new QNFileUpAndDownManager();
            qnFileUpAndDownManager.init();
            qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
                @Override
                public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {
                    if (uploadtag) {
                        String logo = uploadfiles.get("logo");
                        resetBrandInfo(name,logo,teaching,team,honour,environment,operation);
                    }
                }
            });
        }else{
            String logo="";
            if (brand!=null){
                logo=brand.getLogo();
            }
            resetBrandInfo(name,logo,teaching,team,honour,environment,operation);
        }


    }

    private void resetBrandInfo(String name,String logo,String teaching,String team,String honour,String environment,String operation){

        new OperateServers().saveOrUpdateBrandInfo(name, logo, teaching, team, honour, environment, operation, new HttpCallBack<BrandInfoVO.Brand>() {
            @Override
            public void onSuccess(BrandInfoVO.Brand data) {

                Intent intent=new Intent();
                intent.putExtra("brand",data);
                setResult(RESULT_OK,intent);
                finish();

            }

            @Override
            public void onFail(String msg) {

            }
        });

    }


    protected void resetBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
        imageView.setImageBitmap(this.bitmap);
        showpicimageview.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }


    public void takenBZXKZAction(View view){
        if (isedit) {
            showPicDialog(new BasePicActivity.Size(5, 5));
        }
    }

}
