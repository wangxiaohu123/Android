package com.ykx.organization.libs.views;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.TimePickerView;
import com.ykx.baselibs.utils.DateUtil;

import java.text.ParseException;
import java.util.Calendar;
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
 * Created by 2017/3/20.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class TimePickerManager {

    private TimePickerView pvTime;

    private TimePickerListener timePickerListener;

    public TimePickerListener getTimePickerListener() {
        return timePickerListener;
    }

    public void setTimePickerListener(TimePickerListener timePickerListener) {
        this.timePickerListener = timePickerListener;
    }

    public interface TimePickerListener{
        void onTimeSelect(Date date, View v);
    }

    public ViewGroup showTimePickerView(Context context,TimePickerView.Type tpvtype){

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2016,0,23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2027,11,28);
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (timePickerListener!=null){
                    timePickerListener.onTimeSelect(date,v);
                }
            }
        }).setLabel("年", "月", "日", "时", "分", "秒") //设置空字符串以隐藏单位提示   hide label
                .isDialog(false)
                .setOutSideCancelable(true)// default is true
                .setDividerColor(Color.BLACK)
                .setContentSize(14)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .setType(tpvtype)
                .build(dur);
        pvTime.show();


        return pvTime.contentContainer;
    }

    private int dur=0;
    public void setDur(int dur){
        this.dur=dur;
    }

    public ViewGroup showTimePickerViewWithStartData(String startData,Context context,TimePickerView.Type tpvtype){

        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 1, 1);

        if (startData!=null) {
            try {
                Date date = DateUtil.stringToDate(startData, DateUtil.DATE_FORMAT);
                selectedDate.setTime(date);
            }catch (Exception e){
            }
        }else {
        }

        Calendar endDate = Calendar.getInstance();
        endDate.set(2117,1,1);
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (timePickerListener!=null){
                    timePickerListener.onTimeSelect(date,v);
                }
            }
        }).setLabel("年", "月", "日", "时", "分", "秒") //设置空字符串以隐藏单位提示   hide label
                .isDialog(false)
                .setOutSideCancelable(true)// default is true
                .setDividerColor(Color.BLACK)
                .setContentSize(14)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .setType(tpvtype)
                .build(dur);
        pvTime.show();


        return pvTime.contentContainer;
    }

    public void setTime(String dates){
        if (dates==null){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            Date date=DateUtil.stringToDate(dates,DateUtil.DATE_FORMAT);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pvTime.setDate(calendar);
    }

    public void setTime(String dates,String dataFormart){
        if (dates==null){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            Date date=DateUtil.stringToDate(dates,dataFormart);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pvTime.setDate(calendar);
    }

    public boolean isNext(String startTime,String endTime){
        long start = DateUtil.stringToDate(startTime,DateUtil.DATE_FORMAT).getTime();
        long end = DateUtil.stringToDate(endTime,DateUtil.DATE_FORMAT).getTime();
        if (end-start>0){
            return true;
        }
        return false;
    }

    public String getTime(){
        Date date = pvTime.getTime();
        if (date!=null){
            return DateUtil.format(date,DateUtil.DATE_FORMAT);
        }
        return "";
    }


    public String getYMD(){
        Date date = pvTime.getTime();
        if (date!=null){
            return DateUtil.format(date,DateUtil.DATE_FORMAT_TO_DAY);
        }
        return "";
    }

//    public String getDT(){
//
//
//        return pvTime.getDateTime();
//
//    }

    public void closeTimePickerView(){
        if (pvTime!=null){
            pvTime.dismiss();
        }

    }

}
