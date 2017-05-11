package com.ykx.organization.storage.vo;

import java.io.Serializable;
import java.util.ArrayList;
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
 * TODO  校区对象
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/17.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class CampusVO implements Serializable {

    private Integer id;
    private String name;
    private String address;


    private String adcode;
    private String lat;
    private String lng;
    private String contact;
    private String agency_id;
    private String updated_at;
    private String created_at;
    private String area_text;

    private String status;//1.已实名，0.未实名

    private boolean isSelected=false;

    private String photo;
    private List<String> photo_url;

    private List<CourseVO> courses;

    private List<String> curriculumInfos;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<String> getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(List<String> photo_url) {
        this.photo_url = photo_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        if ((address!=null)&&(address.length()>0)){
            return address;
        }else{
            return "暂无";
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
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

    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getArea_text() {
        return area_text;
    }

    public void setArea_text(String area_text) {
        this.area_text = area_text;
    }

    public List<String> getCurriculumInfos() {
        curriculumInfos=new ArrayList<>();
        if ((courses!=null)&&(courses.size()>0)){
            for (CourseVO courseVO:courses){
                StringBuffer stringBuffer=new StringBuffer("· ");
                stringBuffer.append(courseVO.getName()).append("(").append(courseVO.getCount()).append(" 门班课)");
                curriculumInfos.add(stringBuffer.toString());
            }
        }else{
            curriculumInfos.add("· 未设置班课");
        }

        return curriculumInfos;
    }

    public void setCurriculumInfos(List<String> curriculumInfos) {
        this.curriculumInfos = curriculumInfos;
    }

    public List<CourseVO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseVO> courses) {
        this.courses = courses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static CampusList newCampusList(){
        return new CampusList();
    }

    public static class CampusList implements Serializable{



        private List<CampusVO> datas;

        public List<CampusVO> getDatas() {
            return datas;
        }

        public void setDatas(List<CampusVO> datas) {
            this.datas = datas;
        }
    }
}
