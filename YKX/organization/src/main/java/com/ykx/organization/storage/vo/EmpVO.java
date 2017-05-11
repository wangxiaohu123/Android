package com.ykx.organization.storage.vo;

import com.ykx.baselibs.vo.TypeVO;

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
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/4/12.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class EmpVO implements Serializable {

    private Integer id;
    private String agency_id;
    private String name;
    private String avatar;
    private String sex;//0女1男
    private String phone;
    private String status;
    private String summary;
    private String resume;
    private String honor;

    private List<CampusVO> campuses;
    private List<ItemVO> positions;
    private List<TypeVO> categories;

    private String avatar_url;

    private List<RoleModule> power;


//    private String phone;
//
//    private String photoUrl;
//    private String gender;
//    private String jobs;

    private int state;//0.未处理,1.以接受,2.未接受

    private boolean isCheck;

    public EmpVO() {
    }
//    public EmpVO(Integer id, String photoUrl, String name, String gender, String jobs, String phone) {
//        this.id = id;
//        this.photoUrl = photoUrl;
//        this.name = name;
//        this.gender = gender;
//        this.jobs = jobs;
//        this.phone = phone;
//    }
//    public EmpVO(Integer id, String photoUrl, String name, String gender, String jobs, String phone,int state) {
//        this.id = id;
//        this.photoUrl = photoUrl;
//        this.name = name;
//        this.gender = gender;
//        this.jobs = jobs;
//        this.phone = phone;
//        this.state=state;
//    }
//
//    public EmpVO(Integer id, String photoUrl, String name, String gender, String jobs, String phone,int state,boolean isCheck) {
//        this.id = id;
//        this.photoUrl = photoUrl;
//        this.name = name;
//        this.gender = gender;
//        this.jobs = jobs;
//        this.phone = phone;
//        this.state=state;
//        this.isCheck=isCheck;
//    }

    public static EmpVOs getEmpVOs(){
        return new EmpVOs();
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String getPhotoUrl() {
//        return photoUrl;
//    }
//
//    public void setPhotoUrl(String photoUrl) {
//        this.photoUrl = photoUrl;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
    public String getJobs() {
        StringBuffer jobs=new StringBuffer("");
        if (this.positions!=null){
            for (ItemVO itemVO:this.positions){
                if (jobs.length()<=0){
                    jobs.append(itemVO.getName());
                }else{
                    jobs.append(",").append(itemVO.getName());
                }
            }
        }
        String njobs=jobs.toString();

        if ((njobs!=null)&&(njobs.length()>0)){
            return njobs;
        }
        return "暂无";
    }
//
//    public void setJobs(String jobs) {
//        this.jobs = jobs;
//    }


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

    public List<CampusVO> getCampuses() {
        return campuses;
    }

    public void setCampuses(List<CampusVO> campuses) {
        this.campuses = campuses;
    }

    public List<ItemVO> getPositions() {
        return positions;
    }

    public void setPositions(List<ItemVO> positions) {
        this.positions = positions;
    }

    public List<TypeVO> getCategories() {
        return categories;
    }

    public void setCategories(List<TypeVO> categories) {
        this.categories = categories;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<RoleModule> getPower() {
        if ((power!=null)&&(power.size()>0)){
            for (RoleModule roleModule:power){
                if (roleModule==null){
                    return new ArrayList<>();
                }
            }
        }
        return power;
    }

    public void setPower(List<RoleModule> power) {
        this.power = power;
    }

    public static class EmpVOs implements Serializable{
        private List<EmpVO> datas;

        public List<EmpVO> getDatas() {
            return datas;
        }

        public void setDatas(List<EmpVO> datas) {
            this.datas = datas;
        }
    }
}
