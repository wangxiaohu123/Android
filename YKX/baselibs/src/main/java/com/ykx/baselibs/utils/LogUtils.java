package com.ykx.baselibs.utils;

import android.util.Log;

/*********************************************************************************
 * Project Name  : Health
 * Package       : com.sofn.agriculture.baselibs.utils
 * <p/>
 * <p/>
 * Copyright  禄康源电子商务部  Corporation 2016 All Rights Reserved
 * <p/>
 * <p/>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 16/1/19 上午11:24.
 * <p/>
 * <p/>
 * ********************************************************************************
 */
public class LogUtils {

    private static final String TAG="Health_TAG";
    private static final boolean isShowLog=true;

    public static void showLog(String message){

        if (isShowLog){

            Log.d(TAG,message);
        }

    }



}
