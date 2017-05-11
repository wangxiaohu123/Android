package com.ykx.baselibs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/*********************************************************************************
 * Project Name  : CQ_HQ
 * Package       : com.cqhq.gis.utils
 * <p/>
 * Copyright  禄康源电子商务部  Corporation 2016 All Rights Reserved
 * <p/>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 16/11/22 上午8:45.
 * <p/>
 * ********************************************************************************
 */
public class DeviceUtils {

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isCallPhone(Activity activity) {
        TelephonyManager telephony = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        }else {
            return false;
        }
    }

    public static int getDeviceWith(Activity context){

        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

//        System.out.println("heigth : " + dm.heightPixels);
//        System.out.println("width : " + dm.widthPixels);

        return dm.widthPixels;
    }


}
