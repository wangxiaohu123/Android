package com.ykx.baselibs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.baselibs.R;
import com.ykx.baselibs.pages.BaseActivity;

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
 * Created by 2017/3/30.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class SubmitStateView extends LinearLayout{

    private BaseActivity context;

    private View enableView;//遮盖所有View的事件

    public enum STATE{
        NORMAL,
        LOADING,
        ENABLE,
        SUCCESS,
        FAIL
    }

    private View contentView;
    private ImageView progressBar;
    private TextView textView;

    public SubmitStateView(Context context) {
        super(context);
        initView(context);
    }

    public SubmitStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        if (context instanceof BaseActivity) {
            this.context = (BaseActivity)context;
        }
        contentView = LayoutInflater.from(context).inflate(R.layout.view_submit_state,null);
        progressBar= (ImageView) contentView.findViewById(R.id.submit_progressbar);
        textView= (TextView) contentView.findViewById(R.id.submit_textview);
        addView(contentView,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        enableView=new View(context);
        enableView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
//        enableView.setBackgroundColor(Color.BLACK);
//        enableView.setAlpha(0.4f);

    }

    private void startLoading(){
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        progressBar.setAnimation(operatingAnim);
    }

    public void setTextAndState(STATE state,String title,String contentMessage){

        if ((title!=null)&&(title.length()>0)) {
            textView.setText(title);
        }
        if (state==STATE.LOADING){
            contentView.setBackgroundResource(R.drawable.corner_view_loading);
            progressBar.setVisibility(VISIBLE);
            if (this.context!=null) {
                context.addViewIntoBaseContentView(enableView);
            }
            startLoading();
            progressBar.setVisibility(VISIBLE);
            setEnabled(false);
        }else if(state==STATE.NORMAL){
            contentView.setBackgroundResource(R.drawable.textview_selector);
            progressBar.clearAnimation();
            progressBar.setVisibility(GONE);
            if (this.context!=null) {
                context.removeViewIntoBaseContentView(enableView);
            }
            setEnabled(true);
        }else if(state==STATE.ENABLE){
            contentView.setBackgroundResource(R.drawable.corner_view_unable);
            progressBar.clearAnimation();
            progressBar.setVisibility(GONE);
            if (this.context!=null) {
                context.removeViewIntoBaseContentView(enableView);
            }
            setEnabled(true);
        }else{
            progressBar.clearAnimation();
            progressBar.setVisibility(GONE);
            setEnabled(true);
//            contentView.setBackgroundResource(R.drawable.corner_view);
//            progressBar.setVisibility(GONE);
            if (this.context!=null) {
                context.removeViewIntoBaseContentView(enableView);
                if (contentMessage!=null) {
                    if (state == STATE.SUCCESS) {
                        context.showDefaultToast(contentMessage, R.drawable.svg_success);
                    } else if (state == STATE.FAIL) {
                        contentView.setBackgroundResource(R.drawable.textview_selector);
                        context.showDefaultToast(contentMessage, R.drawable.svg_fail);
                    }
                }
            }
        }


    }
}
