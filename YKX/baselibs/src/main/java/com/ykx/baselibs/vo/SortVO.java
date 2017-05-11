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
 * Created by 2017/3/15.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class SortVO implements Serializable {

    private String titleName;
    private List<AreaCode> areaCodes;

    public List<AreaCode> getAreaCodes() {
        return areaCodes;
    }

    public void setAreaCodes(List<AreaCode> areaCodes) {
        this.areaCodes = areaCodes;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
