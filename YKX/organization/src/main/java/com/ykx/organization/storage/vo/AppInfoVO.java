package com.ykx.organization.storage.vo;

import com.ykx.organization.constants.RoleConstants;

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
 * Created by 2017/4/27.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class AppInfoVO implements Serializable {

    private int id;
    private String name;
    private int picId;
    private boolean isShow=true;


    public AppInfoVO(int id, String name, int picId, boolean isShow) {
        this.id = id;
        this.name = name;
        this.picId = picId;
        this.isShow = isShow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

}
