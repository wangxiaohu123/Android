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
 * Created by 2017/4/12.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class TeacherVO implements Serializable {

    private Integer id;
    private String name;

    private String agency_id;
    private String avatar;
    private String sex;
    private String phone;
    private String status;
    private String summary;
    private String resume;
    private String honor;
    private String created_at;
    private String updated_at;
    private String avatar_url;
//    private String pivot;


//    private String photoUrl;
    private boolean state;
//    private String gender;

    private boolean isCheck;

    public TeacherVO() {
    }

//    public TeacherVO(Integer id, String name, String photoUrl, Boolean state) {
//        this.id = id;
//        this.name = name;
//        this.photoUrl = photoUrl;
//        this.state = state;
//    }
//
//    public TeacherVO(Integer id, String name, String photoUrl, Boolean state,String gender) {
//        this.id = id;
//        this.name = name;
//        this.photoUrl = photoUrl;
//        this.state = state;
//        this.gender=gender;
//    }

    public static TeacherVOs getTeacherVOs(){
        return new TeacherVOs();
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }

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

//    public String getPhotoUrl() {
//        return photoUrl;
//    }
//
//    public void setPhotoUrl(String photoUrl) {
//        this.photoUrl = photoUrl;
//    }
//
    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public String getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(String agency_id) {
        this.agency_id = agency_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public static class TeacherVOs implements Serializable{
        private List<TeacherVO> datas;

        public List<TeacherVO> getDatas() {
            return datas;
        }

        public void setDatas(List<TeacherVO> datas) {
            this.datas = datas;
        }
    }

}
