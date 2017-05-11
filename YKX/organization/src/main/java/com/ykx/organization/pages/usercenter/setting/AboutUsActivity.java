package com.ykx.organization.pages.usercenter.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;

public class AboutUsActivity extends BaseActivity {

    private TextView item1view,item2view,item3view,item4view,item5view,item6view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initUI();
    }

    private void initUI(){
        item1view= (TextView) findViewById(R.id.item1_view);
        item2view= (TextView) findViewById(R.id.item2_view);
        item3view= (TextView) findViewById(R.id.item3_view);
        item4view= (TextView) findViewById(R.id.item4_view);
        item5view= (TextView) findViewById(R.id.item5_view);
        item6view= (TextView) findViewById(R.id.item6_view);

        item1view.setText(createSpannableStringBuilder(getResString(R.string.activity_about_us_item1),44,4));
        item2view.setText(createSpannableStringBuilder(getResString(R.string.activity_about_us_item2),50,11));
        item3view.setText(createSpannableStringBuilder(getResString(R.string.activity_about_us_item3),17,20));
        item4view.setText(createSpannableStringBuilder(getResString(R.string.activity_about_us_item4),0,0));
        item5view.setText(createSpannableStringBuilder(getResString(R.string.activity_about_us_item5),0,0));
        item6view.setText(createSpannableStringBuilder(getResString(R.string.activity_about_us_item6),0,0));
    }


    private SpannableStringBuilder createSpannableStringBuilder(String message,int index,int num){
        SpannableStringBuilder builder = new SpannableStringBuilder("缩进"+message);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.theme_main_background_color));
        builder.setSpan(redSpan, (index+2), (index+2+num), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return builder;
    }


    @Override
    protected String titleMessage() {
        return getResString(R.string.user_center_about_us_title);
    }
}
