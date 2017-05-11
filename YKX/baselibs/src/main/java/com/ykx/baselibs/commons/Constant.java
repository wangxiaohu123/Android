package com.ykx.baselibs.commons;


/**
 * <p>
 * Description：基本常量
 * </p>
 *
 * @author wangxiaohu
 */
public class Constant {

//    public static final String BASE_URL = "http://172.20.200.60/";
    public static String BASE_URL = "https://api.develop.ykx100.com/";

    public static String WEB_APP_URL = "https://m.youkexue.com/";

    //读取txt文件
    static {
        String configUrl = LoadFiles.loadServerUrl();
        if ((configUrl!=null)&&(configUrl.length()>0)){
            BASE_URL=configUrl;
        }
    }


    /**
     * 本地存储文件地址
     */
    public static final String PATH_APP = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/ykx";
    public static final String PATH_DB = PATH_APP + "/cache_data";
    public static final String AUDIO_PATH = PATH_APP + "/audio/";
    public static final String VIDEO_PATH = PATH_APP + "/video/";
    public static final String PIC_PATH = PATH_APP + "/pic/";
    public static final String PIC_CACHE_PATH = PIC_PATH+"ImageLoaderCache/";
    public static final String PNG_CACHE_PATH = PIC_PATH+"pngs/";
    public static final String OTHERS_PATH = PATH_APP + "/others/";
    public static final String LOG_PATH = PATH_APP + "/log/";


}
