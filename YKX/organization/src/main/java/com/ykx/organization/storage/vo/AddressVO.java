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
 * Created by 2017/3/23.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class AddressVO implements Serializable {

    private String namne;
    private String address;
    private double lat;
    private double lng;

    private String adCode;

    private boolean searchflag=true;

    public AddressVO() {
    }

    public AddressVO(String namne, String address, double lat, double lng) {
        this.namne = namne;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getNamne() {
        return namne;
    }

    public void setNamne(String namne) {
        this.namne = namne;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isSearchflag() {
        return searchflag;
    }

    public void setSearchflag(boolean searchflag) {
        this.searchflag = searchflag;
    }
}
