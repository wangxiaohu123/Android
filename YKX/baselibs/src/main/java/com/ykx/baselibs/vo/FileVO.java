package com.ykx.baselibs.vo;

import android.graphics.Bitmap;

import java.io.Serializable;

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
 * Created by 2017/3/13.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class FileVO implements Serializable {

    private Bitmap bitmap;
    private String key;

    public FileVO() {
    }

    public FileVO(Bitmap bitmap, String key) {
        this.bitmap = bitmap;
        this.key = key;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
