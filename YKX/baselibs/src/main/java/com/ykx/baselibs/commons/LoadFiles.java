package com.ykx.baselibs.commons;

import android.util.Log;

import com.ykx.baselibs.R;
import com.ykx.baselibs.app.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
 * Created by 2017/4/14.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class LoadFiles {

    public static String loadServerUrl(){
        StringBuffer sb = new StringBuffer("");
        try {
            InputStream inputStream = BaseApplication.application.getResources().openRawResource(R.raw.url);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
//                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();

    }

}
