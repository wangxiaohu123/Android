package com.ykx.organization.libs.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;

/*********************************************************************************
 * Project Name  : YKX
 * Package       : com.ykx.apps
 * <p>
 * <p>
 * Copyright  优课学技术部  Corporation 2017 All Rights Reserved
 * <p>
 * <p>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/4/19.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class SamplePicView extends LinearLayout {

    private BaseActivity context;

    private View view;
    private View bgview,buttomview;
    private ImageView picImage;
    private TextView firstView,secondView;

    private boolean isUpPic=false;
    private int picHeight;

//    private static SamplePicView samplePicView;


    public interface TakePicListener{
        void takePicAction();
    }

    private TakePicListener takePicListener;

    private SamplePicView(BaseActivity context) {
        super(context);
        initUIView(context);
    }

    private void removeview(){
        if (view!=null){
            ViewParent viewParent = view.getParent();
            if (viewParent!=null){
                if (viewParent instanceof ViewGroup){
                    ViewGroup viewGroup= (ViewGroup) viewParent;
                    viewGroup.removeView(view);
                }
            }
            if (this.context!=null){
                context.removeViewIntoBaseContentView(view);
            }
        }
    }

    private void initUIView(final BaseActivity context){
        removeview();

        this.context=context;
        view= LayoutInflater.from(this.context).inflate(R.layout.view_sample_pic,null);
        bgview=view.findViewById(R.id.bg_view);
        bgview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpPic=false;
                hideView();
            }
        });

        buttomview=view.findViewById(R.id.buttom_view);
        buttomview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        picImage = (ImageView) view.findViewById(R.id.sample_image_view);
        firstView = (TextView) view.findViewById(R.id.first_des_view);
        secondView = (TextView) view.findViewById(R.id.second_des_view);

        TextView uploadView = (TextView) view.findViewById(R.id.upload_pic_view);
        uploadView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpPic=true;
                hideView();
            }
        });

    }

    public static SamplePicView newInstance(BaseActivity baseActivity){
//        if (samplePicView==null){
//            samplePicView=new SamplePicView(baseActivity);
//        }
//
//        return samplePicView;

        return new SamplePicView(baseActivity);
    }


    public void showView(String firstmessage,String secondmessage,int resId,TakePicListener takePicListener){

        removeview();

        this.takePicListener=takePicListener;

        picHeight=DensityUtil.dip2px(context,200);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resId);
        if (bitmap!=null){
            picHeight=picHeight+bitmap.getHeight();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        picImage.setImageDrawable(BitmapUtils.getDrawable(context,resId));
        firstView.setText(firstmessage);
        secondView.setText(secondmessage);

        addViewAnimation(buttomview,bgview);
        context.addViewIntoBaseContentView(view);
    }

    private void hideView(){

        removeViewAnimation(buttomview,bgview);
    }


    private void removeViewAnimation(View contextView,View bgView){
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, 0, picHeight);
        tAnim.setInterpolator(new AccelerateDecelerateInterpolator() );
        tAnim.setDuration(300);
        contextView.startAnimation(tAnim);

        tAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                context.removeViewIntoBaseContentView(view);
                if (isUpPic) {
                    if (takePicListener != null) {
                        takePicListener.takePicAction();
                    }
                }
//                samplePicView=null;
            }
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

    private void addViewAnimation(View contextView,View bgView) {
        TranslateAnimation tAnim = new TranslateAnimation(0, 0, picHeight, 0);
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

}
