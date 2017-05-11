package com.ykx.baselibs.utils;

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
 * Created by 2017/4/24.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class TextUtils {

    public static boolean textIsNull(String message){
        if ((message!=null)&&(message.length()>0)&&(!"null".equals(message))){
            return true;
        }else{
            return false;
        }
    }

    public static String getText(String message){
        if ((message!=null)&&(message.length()>0)&&(!"null".equals(message))){
            return message;
        }else{
            return "暂无";
        }
    }
}
