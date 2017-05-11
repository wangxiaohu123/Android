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
 * TODO  班课对象
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/17.
 * <p>
 * <p>
 * ********************************************************************************
 */
public class CurriculumVO implements Serializable {


    private Integer id;
    private String campus_id;
    private String name;
    private int cate;
    private String summary;
    private String photo;
    private int person;
    private String amount;
    private int style;
    private int times;
    private String cate_name;
    private String cate_text;
    private List<String> photo_url;

    private String start_date;
    private String end_date;

    private int online=1;//是否支持在线
    private float price;//课程价格

    private String status;//0,下架。1，正常
    private String reason;//下架原因

    private CampusVO campus;//校区信息
    private String campus_name;

    private List<TimeVO> timerules;

    private List<TeacherVO> teachers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
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

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_text() {
        return cate_text;
    }

    public void setCate_text(String cate_text) {
        this.cate_text = cate_text;
    }

    public List<String> getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(List<String> photo_url) {
        this.photo_url = photo_url;
    }

    public List<TeacherVO> getTeachers() {

        if ((teachers!=null)&&(teachers.size()>0)) {

            return teachers;
        }else{//// TODO: 2017/4/12 测试数据
            teachers=new ArrayList<>();
//            teachers.add(new TeacherVO(1,"小张老师","",true));

            return teachers;
        }
    }

    public void setTeachers(List<TeacherVO> teachers) {
        this.teachers = teachers;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<TimeVO> getTimerules() {
        return timerules;
    }

    public void setTimerules(List<TimeVO> timerules) {
        this.timerules = timerules;
    }

    public CampusVO getCampus() {
        return campus;
    }

    public void setCampus(CampusVO campus) {
        this.campus = campus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCampus_name() {
        return campus_name;
    }

    public void setCampus_name(String campus_name) {
        this.campus_name = campus_name;
    }
}
