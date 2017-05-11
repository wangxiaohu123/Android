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
 * TODO  实名认证信息
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/4/11.
 * <p>
 * <p>
 * ********************************************************************************
 */

//"status": -1,
//        "name": "",
//        "charter_url": "",
//        "reason": "",
//        "is_apply": true

public class SMAutoInfoVO implements Serializable {

    private Integer status;//-1 未审核,0 审核中,1 通过,2 未通过
    private String name;
    private String charter_url;
    private String reason;
    private boolean is_apply;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharter_url() {
        return charter_url;
    }

    public void setCharter_url(String charter_url) {
        this.charter_url = charter_url;
    }

    public String getReason() {
        if ((reason!=null)&&(reason.length()>0)) {
            return reason;
        }else{
            return "暂无";
        }
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean is_apply() {
        return is_apply;
    }

    public void setIs_apply(boolean is_apply) {
        this.is_apply = is_apply;
    }
}
