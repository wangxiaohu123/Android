package com.ykx.organization.storage.vo;

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
 * Created by 2017/3/20.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class PopTimeVO implements Serializable {

    private String name;
    private String value;

    private boolean checkFalg;

    public PopTimeVO() {
    }

    public PopTimeVO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public PopTimeVO(String name, String value, boolean checkFalg) {
        this.name = name;
        this.value = value;
        this.checkFalg = checkFalg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCheckFalg() {
        return checkFalg;
    }

    public void setCheckFalg(boolean checkFalg) {
        this.checkFalg = checkFalg;
    }
}
