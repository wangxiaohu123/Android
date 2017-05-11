package com.ykx.organization.pages.usercenter.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.organization.R;
import com.ykx.organization.servers.CommonServers;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.vo.VersionInfo;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

public class SettingMainActivity extends BaseActivity {

    private TextView versionView;

    private VersionInfo versionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);


        initUI();
        getVersionInfo();
    }

    private void initUI() {
        versionView= (TextView) findViewById(R.id.new_version_view);
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.persion_center_info_jg_sz);
    }

    private void getVersionInfo(){

        new CommonServers().getVersionInfo(new HttpCallBack<VersionInfo>() {
            @Override
            public void onSuccess(VersionInfo data) {
                versionInfo=data;
                if (data!=null){
                    try {
                        String versionname=data.getNumber().replace("v","");
                        boolean isnew=isNewVersion(getNowVersionAction(),versionname);
//                        isnew=true;
                        if (isnew){
                            versionView.setVisibility(View.VISIBLE);
                        }else{
                            versionView.setVisibility(View.GONE);
                        }
                    }catch (Exception e){}
                }
            }
            @Override
            public void onFail(String msg) {}
        });
    }


    public void changePWAction(View view){

        Intent intent=new Intent(this,GetPhoneCodeActivity.class);

        startActivity(intent);
    }

    public void aboutUSAction(View view){

        startActivity(new Intent(this,AboutUsActivity.class));

    }

    private String getNowVersionAction(){
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            int currentVersionCode = info.versionCode; // 版本号
            Log.d("Check Version",currentVersionCode + " " + appVersionName);
            return appVersionName.substring(0,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0";
    }


    public void checkVersionAction(View view){

        if (versionView.getVisibility()==View.VISIBLE){
            showVersionInfoDialog();
        }
    }

    private boolean isNewVersion(String nowVersionInfo,String lastVersion){

        try {
            String[] nvis = nowVersionInfo.split("\\.");
            String[] lvis = lastVersion.split("\\.");
            if (nvis.length == lvis.length) {
                for (int i = 0; i < lvis.length; i++) {
                    String lvi = lvis[i];
                    String nvi = nvis[i];
                    if (Double.valueOf(lvi) > Double.valueOf(nvi)) {
                        return true;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void showVersionInfoDialog(){
        View dialogview= LayoutInflater.from(SettingMainActivity.this).inflate(R.layout.view_user_center_setting_version_check,null);
        TextView infoview= (TextView) dialogview.findViewById(R.id.app_info_view);
        TextView titleview= (TextView) dialogview.findViewById(R.id.app_version_info_view);
        if (versionInfo!=null){
            infoview.setText(versionInfo.getRemark());
            String versionmsg=String.format(getResources().getString(R.string.sys_version_info_defailt_title),versionInfo.getNumber());
            titleview.setText(versionmsg);
        }
        final MaterialDialog mMaterialDialog = new MaterialDialog(SettingMainActivity.this);
        mMaterialDialog.setContentView(dialogview);
        dialogview.findViewById(R.id.close_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        dialogview.findViewById(R.id.true_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path="https://pro-app-qn.fir.im/a8ebef2a905ab769bd1f82be7a839047c28c7c7c.apk?attname=organization-debug.apk_1.0.0-beta-build-170428040951.apk&e=1493713588&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:2LTaIldfB54C0saVnvAKTVqI0GM=";
                if (versionInfo!=null){
                    path=versionInfo.getLink();
                }
                Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(path));
                startActivity(i);
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.setBackground(BitmapUtils.getDrawable(this,R.color.theme_transparent_style));
        mMaterialDialog.setCanceledOnTouchOutside(true);
        mMaterialDialog.show();
    }

    public void logoutAction(View view){
        new UserServers().logout(new HttpCallBack<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                PreManager.getInstance().saveData(PreManager.PASSWORD,"");
                Intent intent=new Intent("com.ykx.organization.pages.LoginActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
}
