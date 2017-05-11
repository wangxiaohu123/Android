package com.ykx.baselibs.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ykx.baselibs.R;

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
 * Created by 2017/3/7.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class CustomEditText extends LinearLayout {

    private Context context;

    private ImageView leftImageView;
    private ImageView rightImageView;
    private EditText editText;
    private View line;

    private boolean isShowCloseFlag=false;

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public CustomEditText(Context context) {
        super(context);
        this.context=context;
        initUI();
    }

    public CustomEditText(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context=context;
        initUI();
    }

    private void initUI(){
        View view= LayoutInflater.from(this.context).inflate(R.layout.view_custom_edittext,null);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        addView(view,layoutParams);

        leftImageView = (ImageView) view.findViewById(R.id.custom_edittext_left_imageView);
        rightImageView = (ImageView) view.findViewById(R.id.custom_edittext_right_imageView);

        rightImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText = (EditText) view.findViewById(R.id.custom_edittext_editText);
        line = view.findViewById(R.id.custom_edittext_line);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 if (!isShowCloseFlag){
                     if (editText.hasFocus()) {
                         if (editText.getText().toString().length() > 0) {
                             rightImageView.setVisibility(View.VISIBLE);
                         } else {
                             rightImageView.setVisibility(View.GONE);
                         }
                     }
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    line.setBackgroundResource(R.color.theme_main_background_color);
                    if (!isShowCloseFlag) {
                        if (editText.getText().toString().length()>0) {
                            rightImageView.setVisibility(View.VISIBLE);
                        }else{
                            rightImageView.setVisibility(View.GONE);
                        }
                    }
                }else{
                    line.setBackgroundResource(R.color.default_line_color);
                    if (!isShowCloseFlag) {
                        rightImageView.setVisibility(View.GONE);
                    }
                }
            }
        });

//        rightImageView.setCompoundDrawables(FontIconDrawable.inflate(this.context, R.xml.icon_close),null,null,null);
    }

    public void setLeftImageView(Drawable resid){
        leftImageView.setVisibility(View.VISIBLE);
        leftImageView.setImageDrawable(resid);

    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public void setRightImageView(Drawable resid){
        if (resid==null){
            rightImageView.setVisibility(View.GONE);
            isShowCloseFlag=true;
        }else{
            isShowCloseFlag=false;
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageDrawable(resid);
        }
//        rightImageView.setVisibility(View.VISIBLE);
//        rightImageView.setCompoundDrawables(resid,null,null,null);
    }


    public void setHintText(String hintText){
        editText.setHint(hintText);
    }

}
