package com.ykx.organization.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.ykx.baselibs.utils.DateUtil;
import com.ykx.organization.R;
import com.ykx.organization.libs.views.TimePickerManager;

import java.util.Date;

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
 * Created by 2017/4/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

public abstract class DateAndTimeVIew extends LinearLayout {

    private View contentView;

    private Context context;

    private TimePickerManager timePickerManager;

    public DateAndTimeVIew(Context context) {
        super(context);
        initUI(context);
    }

    public DateAndTimeVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context){
        this.context=context;
        contentView= LayoutInflater.from(context).inflate(R.layout.view_wheel_item,null);
        LinearLayout dateAndTimeView= (LinearLayout) fineViewById(R.id.date_time_view);

        showDataView(dateAndTimeView);

        addView(contentView,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }

    public abstract TimePickerView.Type getType();

    public String initTime(){
        return DateUtil.format(new Date(),DateUtil.DATE_FORMAT);
    }


    public void showDataView(LinearLayout pViewGroup){
//        TimePickerView.Type type=TimePickerView.Type.YEAR_MONTH_DAY;
//        if (index>1){
//            type=TimePickerView.Type.HOURS_MINS;
//        }
        timePickerManager=new TimePickerManager();
        ViewGroup viewGroup= timePickerManager.showTimePickerViewWithStartData(initTime(),context,getType());
        pViewGroup.addView(viewGroup,new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }

    public void setTime(String time){
        timePickerManager.setTime(time);
    }

    public String getTime(){
        return timePickerManager.getTime();
    }

    public View fineViewById(int id){
        return contentView.findViewById(id);
    }

}
