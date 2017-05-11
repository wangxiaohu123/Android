package com.ykx.organization.storage.vo;

import com.ykx.baselibs.utils.TextUtils;

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
 * Created by 2017/4/18.
 * <p>
 * <p>
 * ********************************************************************************
 */
//"agency_id": 1,
//        "brand_name": "",
//        "brand_logo": "",
//        "position
public class AgenciesVO implements Serializable {

    private Integer agency_id;
    private String brand_name;
    private String brand_logo;
    private String[] position;

    private boolean cert;
    private boolean branding;

    public Integer getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(Integer agency_id) {
        this.agency_id = agency_id;
    }

    public String getBrand_name() {

        if (TextUtils.textIsNull(brand_name)){
            return brand_name;
        }
        return "我的品牌";
//        return TextUtils.getText(brand_name);
//        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String[] getPosition() {
        return position;
    }

    public void setPosition(String[] position) {
        this.position = position;
    }

    public boolean isCert() {
        return cert;
    }

    public void setCert(boolean cert) {
        this.cert = cert;
    }

    public boolean isBranding() {
        return branding;
    }

    public void setBranding(boolean branding) {
        this.branding = branding;
    }
}
