package com.ykx.organization.pages.usercenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.QNFileUpAndDownManager;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BasePicActivity;
import com.ykx.organization.pages.home.operates.curriculum.CurriculumInfoDesActivity;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.EmpVO;
import com.ykx.organization.storage.vo.ItemVO;
import com.ykx.organization.storage.vo.UserInfoVO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EditUserInfoActivity extends BasePicActivity {

    private TextView nameView,phoneView,genderView,zwView,ssxqView,skkmView,jjview,llview,ryview;

    private ImageView camerImageView,photoImageView;

    private int jjFlag=1011;
    private int llFlag=1021;
    private int ryFlag=1031;

    private String jjInfo="";
    private String llInfo="";
    private String ryInfo="";

    private EmpVO loginUserInfo;
    private Bitmap headerBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        initUI();
        loadUserInfo();
    }

    private void initUI(){
        nameView= (TextView) findViewById(R.id.name_editview);
        phoneView= (TextView) findViewById(R.id.phone_edittext);
        genderView= (TextView) findViewById(R.id.gender_view);
        zwView= (TextView) findViewById(R.id.zw_view);
        ssxqView= (TextView) findViewById(R.id.ssxq_view);
        skkmView= (TextView) findViewById(R.id.skkm_view);

        camerImageView= (ImageView) findViewById(R.id.xkz_imageview);
        photoImageView= (ImageView) findViewById(R.id.showpicimageview);

        jjview= (TextView) findViewById(R.id.jj_view);
        llview= (TextView) findViewById(R.id.ll_view);
        ryview= (TextView) findViewById(R.id.ry_view);


    }

    private void loadUserInfo(){
        new UserServers().getUserInfo(new HttpCallBack<EmpVO>() {
            @Override
            public void onSuccess(EmpVO data) {
                loginUserInfo=data;
                resetUI();
            }

            @Override
            public void onFail(String msg) {}
        });
    }

    private void resetUI() {
        if (loginUserInfo!=null){
            nameView.setText(loginUserInfo.getName());
            phoneView.setText(loginUserInfo.getPhone());
            genderView.setText(getSex(loginUserInfo.getSex()));
            BaseApplication.application.getDisplayImageOptions(loginUserInfo.getAvatar_url(),camerImageView);
            camerImageView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);

            zwView.setText(getItemInfo(loginUserInfo.getPositions()));
            ssxqView.setText(getCampusInfo(loginUserInfo.getCampuses()));
            skkmView.setText(getTypeInfo(loginUserInfo.getCategories()));


            jjInfo=loginUserInfo.getSummary();
            llInfo=loginUserInfo.getResume();
            ryInfo=loginUserInfo.getHonor();
        }
    }

    public String getSex(String sex) {
        if ("1".equals(sex)){
            return "男";
        }else{
            return "女";
        }
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.persion_center_info_edit_userinfo);
    }


    public void selectedJJAction(View view){
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",jjInfo);
        startActivityForResult(intent,jjFlag);
    }

    public void selectedLLAction(View view){
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",llInfo);
        startActivityForResult(intent,llFlag);
    }
    public void selectedRYAction(View view){
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",ryInfo);
        startActivityForResult(intent,ryFlag);

    }

    public void takenBZXKZAction(View view){

        showPicDialog(new Size(5,5));
    }

    protected void resetBitmap(Bitmap bitmap){
        photoImageView.setVisibility(View.GONE);
        camerImageView.setImageBitmap(bitmap);
        if (headerBitmap!=null){
            if (!headerBitmap.isRecycled()){
                headerBitmap.recycle();
            }
        }
        headerBitmap=bitmap;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String message=data.getStringExtra("message");
            if (requestCode==jjFlag){
                jjInfo=message;
                jjview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            }else if (requestCode==llFlag){
                llInfo=message;
                llview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            }else if (requestCode==ryFlag){
                ryInfo=message;
                ryview.setText(getResString(R.string.emp_manager_activity_add_edit_text));
            }
        }

    }

    public void editUserInfoAction(View view){

        if (loginUserInfo!=null) {

            if (jjInfo.equals(loginUserInfo.getSummary())&&(llInfo.equals(loginUserInfo.getResume()))&&(ryInfo.equals(loginUserInfo.getHonor()))&&(headerBitmap==null)){

                return;
            }

            showLoadingView();
            if (headerBitmap != null) {
                final String key = "user_header";
                List<FileVO> files = new ArrayList<>();
                files.add(new FileVO(headerBitmap, key));

                QNFileUpAndDownManager qnFileUpAndDownManager = new QNFileUpAndDownManager();
                qnFileUpAndDownManager.init();
                qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
                    @Override
                    public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {

                        if (uploadtag) {
                            String keyName = uploadfiles.get(key);
                            callEdit(keyName);
                        } else {
                            hindLoadingView();
                        }
                    }
                });
            } else {
                callEdit(loginUserInfo.getAvatar());
            }
        }
    }

    private void callEdit(String avatar){
        String summary=jjInfo;
        String resume=llInfo;
        String honor=ryInfo;
        new UserServers().editUserInfo(avatar,summary,resume,honor,new HttpCallBack<EmpVO>() {
            @Override
            public void onSuccess(EmpVO data) {

                if (data!=null){

                    UserInfoVO userInfoVO = MMCacheUtils.getUserInfoVO();
                    if (userInfoVO!=null){
                        userInfoVO.setAvatar(data.getAvatar_url());
                    }
                    BaseApplication.application.getDisplayImageOptions(data.getAvatar_url(),PersonCenterView.userHeaderImageView);
                }
                hindLoadingView();
                finish();
            }
            @Override
            public void onFail(String msg) {
                hindLoadingView();
            }
        });
    }


    private String getItemInfo(List<ItemVO> typeVOs){
        if ((typeVOs!=null)&&(typeVOs.size()>0)){

            StringBuffer stringBuffer=new StringBuffer("");
            for (ItemVO typeVO:typeVOs){
                if (stringBuffer.length()>0){
                    stringBuffer.append(",").append(typeVO.getName());
                }else {
                    stringBuffer.append(typeVO.getName());
                }
            }
            return stringBuffer.toString();
        }
        return getResString(R.string.emp_manager_activity_add_hint1);
    }

    private String getTypeInfo(List<TypeVO> typeVOs){
        if ((typeVOs!=null)&&(typeVOs.size()>0)){

            StringBuffer stringBuffer=new StringBuffer("");
            for (TypeVO typeVO:typeVOs){
                if (stringBuffer.length()>0){
                    stringBuffer.append(",").append(typeVO.getName());
                }else {
                    stringBuffer.append(typeVO.getName());
                }
            }
            return stringBuffer.toString();
        }
        return getResString(R.string.emp_manager_activity_add_hint1);
    }


    private String getCampusInfo(List<CampusVO> typeVOs){

        StringBuffer stringBuffer=new StringBuffer("");
        if ((typeVOs!=null)&&(typeVOs.size()>0)){

            for (CampusVO campusVO1:typeVOs){
                if (stringBuffer.length()>0){
                    stringBuffer.append(",").append(campusVO1.getName());
                }else {
                    stringBuffer.append(campusVO1.getName());
                }
            }
            return stringBuffer.toString();
        }else{
            return getResString(R.string.emp_manager_activity_add_hint1);
        }

    }

}
