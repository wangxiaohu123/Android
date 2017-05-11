package com.ykx.organization.storage.vo;

import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.utils.TextUtils;
import com.ykx.organization.storage.caches.MMCacheUtils;

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
 * Created by 2017/3/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

//"phone": "159****8645",
//        "token": "18a7dd75dc1365d3b33cc4fda2b4a5b18ade655b",
//        "status": 1,
//        "cert": 0,
//        "name": "测试机构名称",
//        "logo": "Foi7f5geiMw4lSPXAF-4a7dLToRM",
//        "type": "170000",
//        "adcode": 0,
//        "address": "",
//        "lat": "0.0000000",
//        "lng": "0.0000000",
//        "contact": "",
//        "summary": null,
//        "photo": null

public class LoginReturnInfo  implements Serializable {

    private String phone;
    private String token;
    private List<AgenciesVO> agencies;



    private String status;

    private String cert;
    private String name;//登记机构名称
    private String logo;
    private String logo_url;
    private String type;
    private String adcode;
    private String address;
    private String lat;
    private String lng;
    private String contact;
    private String summary;
    private String photo;
//
//    private int campus_count;
//    private int course_count;
//    private int campus_limit;
//    private int course_limit;

    public static AgenciesVO getSelectedAgenciesVO(){

        String brandid= PreManager.getInstance().getData(PreManager.DEFAULT_BRANDID,"");
        LoginReturnInfo loginReturnInfo = MMCacheUtils.getLoginReturnInfo();
        if (loginReturnInfo!=null) {
            List<AgenciesVO> agenciesVOList = loginReturnInfo.getAgencies();
            for (AgenciesVO agenciesVO : agenciesVOList) {
                if (String.valueOf(agenciesVO.getAgency_id()).equals(brandid)) {

                    return agenciesVO;
                }
            }
        }
        return null;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AgenciesVO> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<AgenciesVO> agencies) {
        this.agencies = agencies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getName() {
        return TextUtils.getText(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

//    public int getCampus_count() {
//        return campus_count;
//    }
//
//    public void setCampus_count(int campus_count) {
//        this.campus_count = campus_count;
//    }
//
//    public int getCourse_count() {
//        return course_count;
//    }
//
//    public void setCourse_count(int course_count) {
//        this.course_count = course_count;
//    }
//
//    public int getCampus_limit() {
//        return campus_limit;
//    }
//
//    public void setCampus_limit(int campus_limit) {
//        this.campus_limit = campus_limit;
//    }
//
//    public int getCourse_limit() {
//        return course_limit;
//    }
//
//    public void setCourse_limit(int course_limit) {
//        this.course_limit = course_limit;
//    }
}
