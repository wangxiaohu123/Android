package com.ykx.organization.pages;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.DemoHelper;
import com.ykx.baselibs.https.BaseHttp;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.pages.home.InfoMessageFragment;
import com.ykx.organization.pages.home.OperateFragment;
import com.ykx.organization.pages.home.TeachingFragment;
import com.ykx.organization.pages.home.WorkbenchFragment;
import com.ykx.organization.pages.usercenter.PersonCenterView;
import com.ykx.organization.servers.CommonServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.TokenVO;
import com.ykx.organization.storage.vo.UserInfoVO;

import me.drakeet.materialdialog.MaterialDialog;

public class HomeActivity extends BaseActivity {

    private int selectedIndex;


    private LinearLayout contentView;

    private WorkbenchFragment workbenchFragment;
    private InfoMessageFragment infoMessageFragment;
    private TeachingFragment teachingFragment;
    private OperateFragment operateFragment;

    private PersonCenterView personCenterView;
    private boolean isOpernUCFlag=false;

    private ImageView gztImageView,llImageView,jxImageView,yyImageView;
    private TextView gztTextView,llTextView,jxTextView,yyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        contentView = (LinearLayout) findViewById(R.id.home_activity_content);

        initUI();
        getFileUploadToken();

        showExceptionDialogFromIntent(getIntent());
    }

    private void initUI() {

        gztImageView= (ImageView) findViewById(R.id.home_nav_gzt_imageview);
        llImageView= (ImageView) findViewById(R.id.home_nav_ll_imageview);
        jxImageView= (ImageView) findViewById(R.id.home_nav_jx_imageview);
        yyImageView= (ImageView) findViewById(R.id.home_nav_yy_imageview);

        gztTextView= (TextView) findViewById(R.id.home_nav_gzt_textview);
        llTextView= (TextView) findViewById(R.id.home_nav_ll_textview);
        jxTextView= (TextView) findViewById(R.id.home_nav_jx_textview);
        yyTextView= (TextView) findViewById(R.id.home_nav_yy_textview);

//        ImageView backView = (ImageView) actionBar.getCustomView().findViewById(com.ykx.baselibs.R.id.action_bar_default_back);
//        backView.setPadding(0, -DensityUtil.dip2px(this, 10), 0, 0);

        workbenchFragment=new WorkbenchFragment();
        infoMessageFragment=new InfoMessageFragment();
        teachingFragment=new TeachingFragment();
        operateFragment=new OperateFragment();

        personCenterView=new PersonCenterView(this,new PersonCenterView.CallBackListener(){

            @Override
            public void callBack() {
                closeUserCenter();
            }
        });
        changeFragment(workbenchFragment, 1);
    }


    public void refresh(){
        UserInfoVO userInfoVO= MMCacheUtils.getUserInfoVO();
        if (userInfoVO!=null) {
            workbenchFragment.refreshWithRole(userInfoVO.getPower());
            infoMessageFragment.refreshWithRole(userInfoVO.getPower());
            teachingFragment.refreshWithRole(userInfoVO.getPower());
            operateFragment.refreshWithRole(userInfoVO.getPower());
        }
    }


    private void closeUserCenter(){
        TranslateAnimation tAnim = new TranslateAnimation(0, -DensityUtil.dip2px(HomeActivity.this,300), 0, 0);//横向位移
        /**
         accelerate_decelerate_interpolator   加速-减速 动画插入器
         accelerate_interpolator               加速-动画插入器
         decelerate_interpolator               减速- 动画插入器
         */
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator() );
        tAnim.setDuration(300);
        personCenterView.getPersonContentView().startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                removeViewIntoBaseContentView(personCenterView);
                personCenterView.setTag(null);
                isOpernUCFlag=false;
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


        AlphaAnimation aAnim=new AlphaAnimation(1,0);
        aAnim.setDuration(300);
        aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        personCenterView.getbgView().startAnimation(aAnim);
    }

    protected void callBack() {
        super.callBack();
        if (personCenterView.getTag()==null){
            TranslateAnimation tAnim = new TranslateAnimation(-DensityUtil.dip2px(this,300), 0, 0, 0);//横向位移
            tAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            tAnim.setDuration(300);
            personCenterView.getPersonContentView().startAnimation(tAnim);

            addViewIntoBaseContentView(personCenterView);
            personCenterView.setTag(this);
            tAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    isOpernUCFlag=true;
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            AlphaAnimation aAnim=new AlphaAnimation(0,1);
            aAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            aAnim.setDuration(300);
            personCenterView.getbgView().startAnimation(aAnim);

        }else{
        }
    }


    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.home_title);
    }

    protected boolean isBackButton() {
        return false;
    }

    @Override
    protected RightResourceInterface leftResource() {

        return new RightResourceInterface() {
            @Override
            public String viewTitle() {
                return null;
            }

            @Override
            public Drawable viewIcon() {
                return BitmapUtils.getDrawable(HomeActivity.this, R.drawable.svg_home_nav_pc);
            }
        };
    }

    @Override
    protected RightResourceInterface rightResource() {
        return new RightResourceInterface() {
            @Override
            public String viewTitle() {
                return null;
            }

            @Override
            public Drawable viewIcon() {
                actionBar.getCustomView().findViewById(com.ykx.baselibs.R.id.action_bar_default_right).setVisibility(View.VISIBLE);
                return BitmapUtils.getDrawable(HomeActivity.this, R.drawable.svg_home_add);
            }
        };
    }

    @Override
    protected void rightClieck() {
        super.rightClieck();
    }

    public void changeContentViewAction(View view){
        switch (view.getId()){
            case R.id.item_gzt:

                changeFragment(workbenchFragment,1);
                break;
            case R.id.item_ll:

                changeFragment(infoMessageFragment,2);
                break;
            case R.id.item_jx:

                changeFragment(teachingFragment,3);
                break;
            case R.id.item_yy:

                changeFragment(operateFragment,4);
                break;

            default:
                break;
        }
    }

    private void resetSelectedImage(){
        gztImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_gzt));
        llImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_ll));
        jxImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_jx));
        yyImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_yy));

        setTextColor(R.color.default_second_text_color,gztTextView);
        setTextColor(R.color.default_second_text_color,llTextView);
        setTextColor(R.color.default_second_text_color,jxTextView);
        setTextColor(R.color.default_second_text_color,yyTextView);

    }

    private void changeFragment(Fragment newFragment, int newSelectedIndex) {
        TextView titleView = (TextView) actionBar.getCustomView().findViewById(R.id.action_bar_default_title);

        if (selectedIndex == newSelectedIndex) {
            return;
        } else {
            resetSelectedImage();
            if (newSelectedIndex == 1) {
                gztImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_gzt_selected));
                setTextColor(R.color.theme_main_background_color,gztTextView);
                titleView.setText(getResources().getString(R.string.home_nav_item_gzt));
                workbenchFragment.refreshWithRole(MMCacheUtils.getUserInfoVO().getPower());
            } else if (newSelectedIndex == 2) {
                llImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_ll_selected));
                setTextColor(R.color.theme_main_background_color,llTextView);
                titleView.setText(getResources().getString(R.string.home_nav_item_ll));
            } else if (newSelectedIndex == 3) {
                jxImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_jx_selected));
                setTextColor(R.color.theme_main_background_color,jxTextView);
                titleView.setText(getResources().getString(R.string.home_nav_item_jx));
            } else if (newSelectedIndex == 4) {
                yyImageView.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_home_yy_selected));
                setTextColor(R.color.theme_main_background_color,yyTextView);
                titleView.setText(getResources().getString(R.string.home_nav_item_yy));
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (selectedIndex != 0) {
                if (selectedIndex < newSelectedIndex) {
                    transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_left_exit);
                } else {
                    transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit);
                }
            }
            contentView.removeAllViews();
            transaction.replace(R.id.home_activity_content, newFragment);
            transaction.commit();
            this.selectedIndex = newSelectedIndex;
        }
    }


    private void getFileUploadToken() {
        new CommonServers().upToken(new HttpCallBack<TokenVO>() {
            @Override
            public void onSuccess(TokenVO data) {
                BaseHttp.setQnToken(data.getToken());
            }

            @Override
            public void onFail(String msg) {

            }
        });
        new CommonServers().upPrivateToken(new HttpCallBack<TokenVO>() {
            @Override
            public void onSuccess(TokenVO data) {
                BaseHttp.setQnPrivateToken(data.getToken());
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isOpernUCFlag){
                closeUserCenter();
                return true;
            }
            showDiagout(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDiagout(final BaseActivity context) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        mMaterialDialog.setTitle(context.getResources().getString(R.string.default_exit_window_title));
        mMaterialDialog.setMessage(context.getResources().getString(R.string.default_exit_window_content));
        mMaterialDialog.setPositiveButton(context.getResources().getString(R.string.default_exit_window_yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                context.finish();
//                System.exit(0);
            }
        });
        mMaterialDialog.setNegativeButton(context.getResources().getString(R.string.default_exit_window_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }


    /**
     * 环信推送监听
     */
    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow =  false;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }
    }

    private int getExceptionMessageId(String exceptionType) {
        if(exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return com.hyphenate.chatuidemo.R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return com.hyphenate.chatuidemo.R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return com.hyphenate.chatuidemo.R.string.user_forbidden;
        }
        return com.hyphenate.chatuidemo.R.string.Network_error;
    }
    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false,null);
        String st = getResources().getString(com.hyphenate.chatuidemo.R.string.Logoff_notification);
        if (!HomeActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(HomeActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(com.hyphenate.chatuidemo.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
            } catch (Exception e) {
            }
        }
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }


}
