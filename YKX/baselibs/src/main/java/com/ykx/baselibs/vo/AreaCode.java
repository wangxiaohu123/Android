package com.ykx.baselibs.vo;

import java.io.Serializable;
import java.util.List;

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
 * Created by 2017/3/14.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class AreaCode implements Serializable {

    private Integer adcode;
    private String name;
    private List<AreaCode> areas;

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaCode> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaCode> areas) {
        this.areas = areas;
    }
}
