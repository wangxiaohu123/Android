package com.ykx.baselibs.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.ImageView;

import com.shamanland.fonticon.FontIconTypefaceHolder;
import com.ykx.baselibs.R;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;



/**
 * <p>
 * Description：基类应用Application
 * </p>
 *
 * @author wangxiaohu
 */


public class BaseApplication extends Application {


    public static BaseApplication application;

    public Activity currentActivity;

    private List<Activity> activityList=new ArrayList<>();

    private Handler handler=new Handler();

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        application = this;


        FontIconTypefaceHolder.init(getAssets(), "fontawesome-webfont.ttf");

//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());

        loadFiles();

    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public void clearActivity(){
        for (Activity activity:activityList){
            if (activity!=currentActivity) {
                activity.finish();
            }
        }
    }

    public void clearActivityExcepted(Class<? extends Activity> context){
        for (Activity activity:activityList){
            if (activity.getClass()!=context) {
                activity.finish();
            }
        }
    }

    public boolean isExite(Class<? extends Activity> context){
        for (Activity activity:activityList){
            if (activity.getClass()==context) {
                return true;
            }
        }
        return false;
    }

    private void loadFiles() {

    }

    public void getDisplayImageOptions(String imageUrl, ImageView mImageView){}


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    public void appLoginOut(Activity context) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle(getResources().getString(R.string.default_exit_window_title));
        mMaterialDialog.setMessage(getResources().getString(R.string.default_exit_window_content));
        mMaterialDialog.setPositiveButton(getResources().getString(R.string.default_exit_window_yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitsSystem();
                System.exit(0);
            }
        });
        mMaterialDialog.setNegativeButton(getResources().getString(R.string.default_exit_window_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();

    }

    /**
     * 删除所有activity重新登录
     */
    public void relogin(){

//        currentActivity.startActivity(new Intent(currentActivity,LoginActivity.class));
//        for (Activity activity:activityList){
//            if (!(activity instanceof LoginActivity)){
//                activity.finish();
//            }
//        }
//        activityList.clear();

    }

    public void exitsSystem() {

    }
}
