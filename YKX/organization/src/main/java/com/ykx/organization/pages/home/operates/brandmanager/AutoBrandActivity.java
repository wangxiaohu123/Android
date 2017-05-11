package com.ykx.organization.pages.home.operates.brandmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykx.baselibs.views.SelectedButtomView;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.libs.views.SamplePicView;
import com.ykx.organization.pages.bases.BasePicActivity;
import com.ykx.organization.storage.vo.BrandInfoVO;

import java.util.ArrayList;

public class AutoBrandActivity extends BasePicActivity {


    private int index=-1;

    private EditText nameView;
    private EditText zchView;

    private ImageView imageView1,showpicimageview1;
    private ImageView imageView2,showpicimageview2;
    private ImageView imageView3,showpicimageview3;
    public static Bitmap bitmap1,bitmap2,bitmap3;

    private BrandInfoVO.Branding branding;

    private TextView brandTypeView;
    private TypeVO brandTypeVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        branding= (BrandInfoVO.Branding) getIntent().getSerializableExtra("branding");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_brand);
        initUI();
        resetUI();
    }

    private void initUI(){
        brandTypeView= (TextView) findViewById(R.id.brand_type_view);
        nameView= (EditText) findViewById(R.id.input_name_edittext);
        zchView= (EditText) findViewById(R.id.ppsbzch_edittext_view);
        imageView1= (ImageView) findViewById(R.id.imageView1);
        showpicimageview1= (ImageView) findViewById(R.id.showpicimageview1);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        showpicimageview2= (ImageView) findViewById(R.id.showpicimageview2);
        imageView3= (ImageView) findViewById(R.id.imageView3);
        showpicimageview3= (ImageView) findViewById(R.id.showpicimageview3);


//        if (branding!=null){
//            imageView1.setVisibility(View.GONE);
//            showpicimageview1.setVisibility(View.VISIBLE);
//            nameView.setText(branding.getName());
//            BaseApplication.application.getDisplayImageOptions(branding.getLogo_url(),showpicimageview1);
//        }
    }

    private void resetUI(){
        setUnAbleNull(R.id.name_textview);
        setUnAbleNull(R.id.ppsbzch_view);
        setUnAbleNull(R.id.ppsbzcz_view);
        setUnAbleNull(R.id.xkz_textview);
    }


    @Override
    protected String titleMessage() {
        return getResString(R.string.brandmanager_activity_base_info_mamager_title);
    }


    protected void resetBitmap(Bitmap bitmap){
        if (index==1){
            showpicimageview1.setImageBitmap(bitmap);
            imageView1.setVisibility(View.GONE);
            showpicimageview1.setVisibility(View.VISIBLE);
            this.bitmap1=bitmap;
        }else if (index==2){
            showpicimageview2.setImageBitmap(bitmap);
            imageView2.setVisibility(View.GONE);
            showpicimageview2.setVisibility(View.VISIBLE);
            this.bitmap2=bitmap;
        }else if (index==3){
            showpicimageview3.setImageBitmap(bitmap);
            imageView3.setVisibility(View.GONE);
            showpicimageview3.setVisibility(View.VISIBLE);
            this.bitmap3=bitmap;
        }
    }

    public void selectedTypeAction(View view){
        ArrayList<TypeVO> datas=new ArrayList<>();
        datas.add(new TypeVO("图形商标",1));
        datas.add(new TypeVO("文字商标",2));
        brandTypeVO=datas.get(0);
        showOption(0,datas, new SelectedButtomView.SelectedButtomViewListener() {
            @Override
            public void callBack(boolean isTrue, TypeVO typevo) {

                if (isTrue) {
                    if (typevo!=null) {
                        brandTypeView.setText(typevo.getName());
                        brandTypeVO=typevo;
                    }
                }else{
                }
            }
        });
    }

    public void sendEmailAction(View view){
        Intent intent=new Intent(this,BrandModelEmailActivity.class);

        startActivity(intent);
    }

    public void takenBZXKZAction(View view) {
        if (view.getId() == R.id.brand_pic1) {
            index=1;
            showPicDialog(new BasePicActivity.Size(5,5));
        } else if (view.getId() == R.id.brand_pic2) {
            showSBZCZAction();
//            index=2;
//            showPicDialog(new BasePicActivity.Size(4,3));
        } else if (view.getId() == R.id.brand_pic3) {
            showPPSQSAction();
//            index=3;
//            showPicDialog(new BasePicActivity.Size(4,3));
        }
    }

    private void showSBZCZAction(){
        int resid=R.mipmap.sample_sbrz;
        SamplePicView.newInstance(this).showView(getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_sbzcz), getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_second), resid, new SamplePicView.TakePicListener() {
            @Override
            public void takePicAction() {
                index=2;
                showPicDialog(new BasePicActivity.Size(4,3));
            }
        });
    }

    private void showPPSQSAction(){

        int resid=R.mipmap.sample_ppsq;
        SamplePicView.newInstance(this).showView(getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_ppsqs), getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_second), resid, new SamplePicView.TakePicListener() {
            @Override
            public void takePicAction() {
                index=3;
                showPicDialog(new BasePicActivity.Size(4,3));
            }
        });
    }

    public void toNextAutoBrandAction(View view){

        String name=nameView.getText().toString();
        String ppzchname=zchView.getText().toString();

        String ppbm="1";
        if (brandTypeVO!=null){
            ppbm=String.valueOf(brandTypeVO.getCode());
        }

        if (name.length()<=0){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_ppmc_hint));
            return;
        }
        if (ppzchname.length()<=0){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_mamager_ppsbzch_hint));
            return;
        }

        if (bitmap2==null){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_mamager_ppsbzcz_toast));
            return;
        }
        if (bitmap3==null){
            toastMessage(getResString(R.string.brandmanager_activity_base_info_item_mamager_gz_toast));
            return;
        }

        Intent intnet=new Intent(this,AutoBrandSubmitActivity.class);
        intnet.putExtra("brandName",name);
        intnet.putExtra("brandNum",ppzchname);
        intnet.putExtra("brandType",ppbm);

        startActivity(intnet);

    }
}
