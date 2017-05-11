package com.ykx.organization.storage.vo;

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
 * Created by 2017/3/24.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class OraganizationVO implements Serializable {

    private String phone;
    private String token;
    private String status;
    private String name;
    private String logo;
    private String type;
    private String adcode;
    private String address;
    private String lat;
    private String lng;
    private String contact;
    private String summary;
    private String photo;
    private String campus_nu;
    private String course_nu;
    private String logo_url;
    private List<String> photo_url;
    private String[] type_name;
    private String campus_count;
    private String course_count;
    private String campus_limit;
    private String course_limit;

    public String getName() {
        return name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCampus_nu() {
        return campus_nu;
    }

    public void setCampus_nu(String campus_nu) {
        this.campus_nu = campus_nu;
    }

    public String getCourse_nu() {
        return course_nu;
    }

    public void setCourse_nu(String course_nu) {
        this.course_nu = course_nu;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public List<String> getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(List<String> photo_url) {
        this.photo_url = photo_url;
    }

    public String[] getType_name() {
        return type_name;
    }

    public void setType_name(String[] type_name) {
        this.type_name = type_name;
    }

    public String getCampus_count() {
        return campus_count;
    }

    public void setCampus_count(String campus_count) {
        this.campus_count = campus_count;
    }

    public String getCourse_count() {
        return course_count;
    }

    public void setCourse_count(String course_count) {
        this.course_count = course_count;
    }

    public String getCampus_limit() {
        return campus_limit;
    }

    public void setCampus_limit(String campus_limit) {
        this.campus_limit = campus_limit;
    }

    public String getCourse_limit() {
        return course_limit;
    }

    public void setCourse_limit(String course_limit) {
        this.course_limit = course_limit;
    }
}
