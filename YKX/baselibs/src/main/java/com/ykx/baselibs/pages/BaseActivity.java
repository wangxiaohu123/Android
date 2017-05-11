package com.ykx.baselibs.pages;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;
import com.ykx.baselibs.R;
import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.utils.DeviceUtils;
import com.ykx.baselibs.views.SelectedButtomView;
import com.ykx.baselibs.vo.TypeVO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * ******************************************************************************
 * <p/>
 * Project Name  : Trace
 * Package       : com.sofnlky.trace.pages
 * <p/>
 * <p/>
 * Copyright  禄康源电子商务部  Corporation 2015 All Rights Reserved
 * <p/>
 * <p/>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 15/11/4 下午3:07.
 * <p/>
 * <p/>
 * ********************************************************************************
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected MyActionBar actionBar;
    protected SystemBarTintManager tintManager;

    protected FrameLayout baseContentView;

    private View loadingView;
    private ImageView loadingImageView;

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public interface RightResourceInterface {
        String viewTitle();

        Drawable viewIcon();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = new MyActionBar(this);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.theme_navigation_background_color));

        createActionBar();

        //设定状态栏的颜色，当版本大于4.4时起作用
        if (isNewApi()) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setTintColor(Color.TRANSPARENT);
        tintManager.setStatusBarTintEnabled(true);
        //此处可以重新指定状态栏颜色
        tintManager.setStatusBarTintResource(R.color.theme_navigation_background_color);

        tintManager.setStatusBarTintColor(Color.parseColor("#00000000"));

        if (DeviceUtils.isPad(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        BaseApplication.application.addActivity(this);
        BaseApplication.application.currentActivity = this;
//        hideBottomUIMenu();
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.application.removeActivity(this);
    }

    private boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            System.out.println("error =" + e.getMessage());
        }
        return false;
    }

    public class MyActionBar extends LinearLayout {

        private boolean showing;
        private View customView;

        public MyActionBar(Context context) {
            super(context);
        }

        public void setShowing(boolean isShowing) {
            showing = isShowing;
        }

        public boolean isShowing() {
            return showing;
        }

        public View getCustomView() {
            return customView;
        }

        public void hide() {
            this.setVisibility(GONE);
        }

        public void setCustomView(int layoutId) {
            removeAllViews();
            customView = LayoutInflater.from(BaseActivity.this).inflate(layoutId, null);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(customView, layoutParams);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }


    public Drawable findDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    protected void showRightView(boolean isShow) {

        ImageView rightView = (ImageView) actionBar.getCustomView().findViewById(R.id.action_bar_default_right);
        if (isShow) {
            rightView.setVisibility(View.VISIBLE);
        } else {
            rightView.setVisibility(View.GONE);
        }
    }

    protected void showRightView(boolean isShow, Drawable drawable) {

        ImageView rightView = (ImageView) actionBar.getCustomView().findViewById(R.id.action_bar_default_right);
        if (isShow) {
            rightView.setImageDrawable(drawable);
            rightView.setVisibility(View.VISIBLE);
        } else {
            rightView.setVisibility(View.GONE);
        }
    }

    private void createActionBar() {
        if (isHideActionBar()) {
            actionBar.hide();
        } else {
            if (userDefaultActionBar()) {
                actionBar.setCustomView(R.layout.actionbar_view);
                View centerView = createActionBarCenterView();
                TextView titleView = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_default_title);
                FrameLayout frameLayoutView = (FrameLayout) actionBar.getCustomView().findViewById(R.id.action_bar_center_view);
                if (centerView != null) {
                    titleView.setVisibility(View.GONE);
                    frameLayoutView.setVisibility(View.VISIBLE);
                    frameLayoutView.addView(centerView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                } else {
                    titleView.setVisibility(View.VISIBLE);
                    frameLayoutView.setVisibility(View.GONE);
                    titleView.setText(titleMessage());
                }
                ImageView backView = (ImageView) actionBar.getCustomView().findViewById(R.id.action_bar_default_back);
//                backView.setGravity(Gravity.CENTER_VERTICAL);
                TextView leftTextView = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_default_back_text);

                LinearLayout leftView = (LinearLayout) actionBar.getCustomView().findViewById(R.id.action_bar_left_view);

                if (!isBackButton()) {
                    RightResourceInterface leftResourceInterface = leftResource();
                    Drawable drawable = leftResourceInterface.viewIcon();
                    leftView.setPadding(0, 0, 0, 0);
                    backView.setImageDrawable(drawable);
                    setLeftView(leftView);
//                    backView.setCompoundDrawables(drawable, null, null, null);
                    String title = leftResourceInterface.viewTitle();
                    if ((title != null) && (title.length() > 0)) {
                        leftTextView.setText(title);
                    } else {
                        leftTextView.setVisibility(View.GONE);
                    }
                    leftView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callBack();
                        }
                    });
                } else {
//                    Drawable drawable=getDrawable(R.drawable.svg_back);
                    backView.setImageDrawable(BitmapUtils.getDrawable(BaseActivity.this, R.drawable.svg_back));
//                    backView.setCompoundDrawables(FontIconDrawable.inflate(BaseActivity.this, R.xml.icon_back), null, null, null);
                    leftView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            callBack();
                        }
                    });
                }

                ImageView rightView = (ImageView) actionBar.getCustomView().findViewById(R.id.action_bar_default_right);
                LinearLayout rightContentView = (LinearLayout) actionBar.findViewById(R.id.right_contentview);
                setRightView(rightContentView);

                RightResourceInterface rightResourceInterface = rightResource();
                Drawable drawable = rightResourceInterface.viewIcon();
                rightView.setImageDrawable(drawable);
                rightView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightClieck();
                    }
                });

            }
        }
    }

    protected void setLeftView(LinearLayout leftView){

    }

    protected void setRightView(LinearLayout rightContentView){
    }

    protected boolean isBackButton() {
        return true;
    }

    protected void callBack() {
    }

    protected void rightClieck() {

    }


    protected String titleMessage() {
        return getResources().getString(R.string.default_activity_title_title);
    }

    protected RightResourceInterface rightResource() {

        return new RightResourceInterface() {
            @Override
            public String viewTitle() {
                return null;
            }

            @Override
            public Drawable viewIcon() {
                return null;
            }
        };

    }

    protected RightResourceInterface leftResource() {

        return new RightResourceInterface() {
            @Override
            public String viewTitle() {
                return null;
            }

            @Override
            public Drawable viewIcon() {
//                return FontIconDrawable.inflate(BaseActivity.this, R.xml.icon_more);

                return BitmapUtils.getDrawable(BaseActivity.this, R.drawable.svg_back);
            }
        };

    }

    /**
     * 自定义ActionBar的中间View。
     *
     * @return view!=null,覆盖title
     */
    protected View createActionBarCenterView() {
        return null;
    }

    /**
     * 是否使用默认定义的ActionBar
     */
    protected boolean userDefaultActionBar() {
        return true;
    }


    private boolean isNewApi() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ? true : false;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);


    }


    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    public void setContentView(View view) {
        baseContentView = new FrameLayout(this);
        LinearLayout contentView = new LinearLayout(this);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        if (isNewApi()) {
            if (!isHideActionBar()) {
                contentView.setFitsSystemWindows(true);
                contentView.setClipToPadding(true);
            }
        }
        if (!isHideActionBar()) {
            contentView.addView(actionBar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(BaseActivity.this, 50)));
            View lineView = new View(this);
            lineView.setBackgroundDrawable(getResources().getDrawable(R.color.default_line_color));
            contentView.addView(lineView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 1)));
        }
        view.setBackgroundDrawable(getPageBackgroudDrawable());
        contentView.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        baseContentView.addView(contentView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        super.setContentView(baseContentView);
    }

    public void addViewIntoBaseContentView(View view) {
        if (view!=null) {
             baseContentView.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
         }
    }

    public void removeViewIntoBaseContentView(View view) {
        if (view!=null) {
            baseContentView.removeView(view);
        }
    }

    protected String getMessage(String message){
        if ((message!=null)&&(message.length()>0)){
        }else{
            message=getResString(R.string.sys_default_null);
        }
        return message;
    }

    protected Drawable getPageBackgroudDrawable() {
        return getResources().getDrawable(R.color.white);
    }

    protected Drawable getSysDrawable(int resId){
       return getResources().getDrawable(resId);
    }

    protected boolean isHideActionBar() {

        return false;
    }

    protected void setTextColor(int color, TextView textView) {
        textView.setTextColor(getResources().getColor(color));
    }


    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    public PopupWindow showPopupWindow(View clickView,View contentView,int bgres) {
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(getResources().getDrawable(bgres));
        popupWindow.showAsDropDown(clickView);

//        int[] location = new int[2];
//        clickView.getLocationOnScreen(location);
//        popupWindow.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0]-popupWindow.getWidth(), location[1]);

        return popupWindow;

    }

    protected String getResString(int stringId){
        return  getResources().getString(stringId);
    }

    public interface SelectedListener{
        void callBack(boolean flag);//是否确认执行
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isCheckBackKey()) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                showDefaultToast(getResString(R.string.sys_input_message_is_save_toast),R.drawable.svg_fail);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean isCheckBackKey(){
        return false;
    }

    /**
     * 显示Toast
     * @param tvString
     * @param resourceId
     */

    public void showDefaultToast(String tvString,int resourceId){
        View layout = LayoutInflater.from(this).inflate(R.layout.default_toast_view,null);
        TextView text = (TextView) layout.findViewById(R.id.toast_message_view);
        ImageView mImageView = (ImageView) layout.findViewById(R.id.toast_imageview);
        mImageView.setImageDrawable(BitmapUtils.getDrawable(this,resourceId));
        text.setText(tvString);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void showLoadingView(){
        if (loadingView==null){
            loadingView=LayoutInflater.from(this).inflate(R.layout.view_submit_loading,null);
            loadingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            loadingImageView= (ImageView) loadingView.findViewById(R.id.submit_image_loading_view);
            addViewIntoBaseContentView(loadingView);
            startLoading(loadingImageView);
        }
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();


        AlphaAnimation aAnim = new AlphaAnimation(0, 1);
        aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        aAnim.setDuration(300);
        loadingView.startAnimation(aAnim);
    }

    private void startLoading(ImageView imageView){
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.setAnimation(operatingAnim);
    }

    public void hindLoadingView(){
        if (loadingView!=null) {
            loadingView.setVisibility(View.GONE);

            AlphaAnimation aAnim = new AlphaAnimation(1,0);
            aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            aAnim.setDuration(300);
            loadingView.startAnimation(aAnim);
        }
    }

    public void resetSubmitState(Button clickButton){

    }

    public void showDeleteDialog(final SelectedListener selectedListener){
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(getResources().getString(R.string.sys_dialog_delete_title));
        mMaterialDialog.setMessage(getResources().getString(R.string.sys_dialog_delete_content));
        mMaterialDialog.setPositiveButton(getResources().getString(R.string.default_exit_window_yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (selectedListener!=null){
                  selectedListener.callBack(true);
               }
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.setNegativeButton(getResources().getString(R.string.default_exit_window_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedListener!=null){
                    selectedListener.callBack(false);
                }
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }


    protected void setUnAbleNull(int viewId){
        TextView textView= (TextView) findViewById(viewId);
        String context=textView.getText().toString();
        context=context+" *";
        SpannableStringBuilder builder = new SpannableStringBuilder(context);

        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, context.length()-1, context.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(builder);
    }


    protected void removeViewAnimation(View contextView,View bgView){
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0, DensityUtil.dip2px(this,400));
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator() );
        tAnim.setDuration(300);
        contextView.startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


        if (bgView!=null) {
            AlphaAnimation aAnim = new AlphaAnimation(1, 0);
            aAnim.setDuration(300);
            aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            bgView.startAnimation(aAnim);
        }
    }

    protected void addViewAnimation(View contextView,View bgView) {
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, DensityUtil.dip2px(this,400), 0);
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        tAnim.setDuration(300);
        contextView.startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        if (bgView!=null) {
            AlphaAnimation aAnim = new AlphaAnimation(0, 1);
            aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            aAnim.setDuration(300);
            bgView.startAnimation(aAnim);
        }
    }

    protected void showOption(int selectedIndex,ArrayList<TypeVO> list, SelectedButtomView.SelectedButtomViewListener selectedButtomViewListener){
        SelectedButtomView.showView(selectedIndex,this,baseContentView,list,selectedButtomViewListener);
    }

    protected void showOptionWithName(String selectedName,ArrayList<TypeVO> list, SelectedButtomView.SelectedButtomViewListener selectedButtomViewListener){
        SelectedButtomView.showViewWithName(selectedName,this,baseContentView,list,selectedButtomViewListener);
    }

}
