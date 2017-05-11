package com.ykx.baselibs.pages;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.ykx.baselibs.app.BaseApplication;


/**
 * Created by ChenChao on 2016/2/29.
 */
public abstract class RootFragment extends BaseFragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {


    protected View contentView;


    protected boolean createViewFlag=false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView==null) {//TODO 不用重复加载  如特殊情况需要每次加载的做特殊处理
            contentView = inflater.inflate(getContentViewResource(), container, false);
            contentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            initUI();
        }
        createViewFlag=true;
        return contentView;
    }

    protected abstract int getContentViewResource();

    protected abstract void initUI();

    protected abstract void onViewDidLoad();

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= 16)
            contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        else
            contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

        onViewDidLoad();
    }

    protected <T extends View> T find(int id, View.OnClickListener listener) {
        if (contentView != null) {
            View v = contentView.findViewById(id);
            if (listener != null) {
                v.setOnClickListener(listener);
            }
            return (T) v;
        }
        return null;
    }

    protected String getResString(int resId){

        return BaseApplication.application.getResources().getString(resId);
    }

    protected void showToast(int resId){

        Toast.makeText(BaseApplication.application,getResString(resId),Toast.LENGTH_SHORT).show();
    }



    protected Drawable findDrawable(int id) {
        return activity().findDrawable(id);
    }

    protected BaseActivity activity() {
        return (BaseActivity) getActivity();
    }

}
