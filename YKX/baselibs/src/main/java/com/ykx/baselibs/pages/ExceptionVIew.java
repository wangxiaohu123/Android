package com.ykx.baselibs.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.baselibs.R;
import com.ykx.baselibs.utils.BitmapUtils;

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
 * Created by 2017/3/26.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class ExceptionVIew extends LinearLayout {

    private ImageView exceptionImageView;
    private TextView exceptionDesView;
    private LinearLayout otherView,contentView;
    private Context context;

    public ExceptionVIew(Context context) {
        super(context);
        initUI(context);
    }

    public ExceptionVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.view_exception,null);

        exceptionImageView= (ImageView) view.findViewById(R.id.exception_imageView);
        exceptionDesView= (TextView) view.findViewById(R.id.exception_textView);
        otherView= (LinearLayout) view.findViewById(R.id.exception_other_view);
        contentView= (LinearLayout) view.findViewById(R.id.content_view);

        addView(view,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }

    private void setDatas(int imageResId,String excptionDes,View oView){
        exceptionImageView.setImageDrawable(BitmapUtils.getDrawable(this.context,imageResId));
        exceptionDesView.setText(excptionDes);
        if (oView!=null){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            otherView.addView(oView,layoutParams);
        }
    }

    public void setbgCorlor(int color){
        contentView.setBackgroundColor(color);
    }

    public static ExceptionVIew loadView(Context context,ViewGroup viewGroup,int imageResId,String excptionDes,View oView){
        if (viewGroup!=null){
            ExceptionVIew exceptionVIew=new ExceptionVIew(context);
            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            exceptionVIew.setDatas(imageResId,excptionDes,oView);
            viewGroup.addView(exceptionVIew,layoutParams);
            return exceptionVIew;
        }
        return null;
    }

}
