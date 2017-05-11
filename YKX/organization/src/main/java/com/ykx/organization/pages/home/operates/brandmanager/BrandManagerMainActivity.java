package com.ykx.organization.pages.home.operates.brandmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.BrandInfoVO;

import java.math.BigDecimal;

public class BrandManagerMainActivity extends BaseActivity {

    private final int BRAND_INFO_FLAOG=101;
    private final int AUTO_BRAND_FLAOG=102;

    private ImageView imageView;
    private TextView brandName,brandState;
    private RelativeLayout ydView;
    private LinearLayout passView,unPassView,waitView;
    private TextView unPassResonView;
    private View wrzView;

    private BrandInfoVO brandInfoVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_manager_main);


        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        BrandInfoVO.Branding branding= (BrandInfoVO.Branding) intent.getSerializableExtra("branding");
        if (branding!=null){
            if (brandInfoVO == null) {
                 brandInfoVO = new BrandInfoVO();
            }
            brandInfoVO.setBranding(branding);
            resetUI(brandInfoVO);
        }

    }

    private void initUI(){
        wrzView=findViewById(R.id.wrz_view);
        imageView= (ImageView) findViewById(R.id.left_view);
        brandName= (TextView) findViewById(R.id.brand_name_view);
        brandState= (TextView) findViewById(R.id.brand_state_view);

        ydView= (RelativeLayout) findViewById(R.id.state_view1);
        waitView= (LinearLayout) findViewById(R.id.state_view2);
        unPassView= (LinearLayout) findViewById(R.id.state_view3);
        passView= (LinearLayout) findViewById(R.id.state_view4);
        unPassResonView= (TextView) findViewById(R.id.un_pass_reson_view);
    }

    private void loadInfo(){
        new OperateServers().brandInfo(new HttpCallBack<BrandInfoVO>() {
            @Override
            public void onSuccess(BrandInfoVO data) {
                findViewById(R.id.activity_brand_manager_main).setVisibility(View.VISIBLE);
                resetUI(data);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void resetUI(BrandInfoVO data){
        brandInfoVO=data;
        BrandInfoVO.Branding branding = data.getBranding();
        ydView.setVisibility(View.GONE);
        waitView.setVisibility(View.GONE);
        unPassView.setVisibility(View.GONE);
        passView.setVisibility(View.GONE);
        if (branding.getStatus()==-1){
            ydView.setVisibility(View.VISIBLE);
            wrzView.setVisibility(View.VISIBLE);
        }else {
            wrzView.setVisibility(View.GONE);
            if (branding.getStatus() == 0) {
                waitView.setVisibility(View.VISIBLE);
            } else {
                if (branding.getStatus() == 1) {
                    passView.setVisibility(View.VISIBLE);
                } else if (branding.getStatus() == 2) {
                    unPassView.setVisibility(View.VISIBLE);
                    if ((branding.getReason()!=null)&&(branding.getReason().length()>0)) {
                        unPassResonView.setText(branding.getReason());
                    }else{
                        unPassResonView.setText(getResString(R.string.sys_default_null));
                    }
                }
                brandState.setTextColor(getResources().getColor(R.color.theme_main_background_color));
            }
            String des=getResources().getString(R.string.brandmanager_activity_base_item_pwp)+progressString()+"%";
            brandState.setText(des);
        }

        BrandInfoVO.Brand brand = data.getBrand();
        if (brand!=null){
            brandName.setText(brand.getName());
            BaseApplication.application.getDisplayImageOptions(brand.getLogo_url(),imageView);
        }

    }

    private String progressString(){

        if (brandInfoVO!=null) {
            BrandInfoVO.Brand brand = brandInfoVO.getBrand();
            double i=1.0;
            if (brand.getLogo().length()>0){
                i=i+1;
            }
            if (brand.getTeaching().length()>0){
                i=i+1;
            }
            if (brand.getTeam().length()>0){
                i=i+1;
            }
            if (brand.getHonour().length()>0){
                i=i+1;
            }
            if (brand.getEnvironment().length()>0){
                i=i+1;
            }
            if (brand.getOperation().length()>0){
                i=i+1;
            }
            String progress=String.valueOf((i*100.0)/7);

            BigDecimal bigDecimal = new BigDecimal(progress).setScale(0, BigDecimal.ROUND_HALF_UP);

           return  String.valueOf(bigDecimal.intValue());

        }
        return "0";
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.brandmanager_activity_title);
    }



    public void toEditInfoAction(View view){


        boolean isedit=true;
        Intent intent=new Intent(this,BrandInfoActivity.class);
        if (brandInfoVO!=null){
            intent.putExtra("brand",brandInfoVO.getBrand());
            BrandInfoVO.Branding branding = brandInfoVO.getBranding();
            if (branding!=null){
                if ((branding.getStatus()==0)||(branding.getStatus()==1)){
                    isedit=false;
                }
            }
        }
        intent.putExtra("isEdit",isedit);
        startActivityForResult(intent,BRAND_INFO_FLAOG);

    }


    public void toAutoBrandAction(View view){
        Intent intent=new Intent(this,AutoBrandActivity.class);
        if (brandInfoVO!=null) {
            BrandInfoVO.Branding branding = brandInfoVO.getBranding();
            if (branding.getStatus()==0){
                toastMessage(getResString(R.string.brandmanager_activity_base_info_mamager_unable_manage));
                return;
            }
            intent.putExtra("branding", branding);
        }
        startActivityForResult(intent,AUTO_BRAND_FLAOG);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==BRAND_INFO_FLAOG){
                BrandInfoVO.Brand brand = (BrandInfoVO.Brand) data.getSerializableExtra("brand");

                if (brand!=null) {
                    if (brandInfoVO == null) {
                        brandInfoVO = new BrandInfoVO();
                    }
                    brandInfoVO.getBranding().setName(brand.getName());
                    brandInfoVO.getBranding().setLogo_url(brand.getLogo_url());
//                    brandInfoVO.getBranding().setStatus(brand.getBranding());

                    brandInfoVO.setBrand(brand);
                }
                resetUI(brandInfoVO);
            }else if (requestCode==AUTO_BRAND_FLAOG){

            }
        }
    }
}
