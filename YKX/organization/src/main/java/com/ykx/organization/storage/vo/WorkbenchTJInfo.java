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
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/4/26.
 * <p>
 * <p>
 * ********************************************************************************
 */


//"certification": {
//        "cert": false,
//        "branding": false
//        },
//        "summary": {
//        "todayHit": "5",
//        "diffHit": 100,
//        "todayCounsel": 0,
//        "diffCounsel": 100,
//        "todayOrder": 0,
//        "diffOrder": 0,
//        "todayAmount": 0,
//        "diffAmount": 0
//        },
//        "teaching": {
//        "campus_count": 0,
//        "course_count": 0,
//        "teacher_count": 0
//        },
//        "news": []
//        },

public class WorkbenchTJInfo implements Serializable {

    private Certification certification;
    private Summary summary;
    private Teaching teaching;
    private List<News> news;

    public WorkbenchTJInfo(){
//        certification=new Certification();
//        certification.setBranding(true);
//        certification.setCert(false);
//
//        summary=new Summary();
//        summary.setTodayHit("100");
//        summary.setDiffHit(10);
//        summary.setTodayCounsel(50);
//        summary.setDiffCounsel(20);
//        summary.setTodayOrder(45);
//        summary.setDiffOrder(25);
//        summary.setTodayAmount(120000);
//        summary.setDiffAmount(35);
//
//        teaching=new Teaching();
//        teaching.campus_count=10;
//        teaching.course_count=20;
//        teaching.teacher_count=30;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Teaching getTeaching() {
        return teaching;
    }

    public void setTeaching(Teaching teaching) {
        this.teaching = teaching;
    }

    public List<News> getNews() {
        if (news!=null&&(news.size()>0)){
        }else{
            news.add(new News("[重要消息]优课学机构版V1.0正式发布!"));
            news.add(new News("[违规]违反经营信誉值扣分通知。"));
            news.add(new News("引爆免费私域流量秘籍!"));
        }
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public class Summary implements Serializable{


        private String todayHit;
        private int diffHit;
        private int todayCounsel;
        private int diffCounsel;
        private int todayOrder;
        private int diffOrder;
        private int todayAmount;
        private int diffAmount;

        public String getTodayHit() {
            return todayHit;
        }

        public void setTodayHit(String todayHit) {
            this.todayHit = todayHit;
        }

        public int getDiffHit() {
            return diffHit;
        }

        public void setDiffHit(int diffHit) {
            this.diffHit = diffHit;
        }

        public int getTodayCounsel() {
            return todayCounsel;
        }

        public void setTodayCounsel(int todayCounsel) {
            this.todayCounsel = todayCounsel;
        }

        public int getDiffCounsel() {
            return diffCounsel;
        }

        public void setDiffCounsel(int diffCounsel) {
            this.diffCounsel = diffCounsel;
        }

        public int getTodayOrder() {
            return todayOrder;
        }

        public void setTodayOrder(int todayOrder) {
            this.todayOrder = todayOrder;
        }

        public int getDiffOrder() {
            return diffOrder;
        }

        public void setDiffOrder(int diffOrder) {
            this.diffOrder = diffOrder;
        }

        public int getTodayAmount() {
            return todayAmount;
        }

        public void setTodayAmount(int todayAmount) {
            this.todayAmount = todayAmount;
        }

        public int getDiffAmount() {
            return diffAmount;
        }

        public void setDiffAmount(int diffAmount) {
            this.diffAmount = diffAmount;
        }
    }


    public class Certification implements Serializable{

        private boolean cert;
        private boolean branding;

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


    public class Teaching implements Serializable{
        private int campus_count;
        private int course_count;
        private int teacher_count;

        public int getCampus_count() {
            return campus_count;
        }

        public void setCampus_count(int campus_count) {
            this.campus_count = campus_count;
        }

        public int getCourse_count() {
            return course_count;
        }

        public void setCourse_count(int course_count) {
            this.course_count = course_count;
        }

        public int getTeacher_count() {
            return teacher_count;
        }

        public void setTeacher_count(int teacher_count) {
            this.teacher_count = teacher_count;
        }
    }

    public class News implements Serializable{

        private String title;

        public News() {
        }

        public News(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
