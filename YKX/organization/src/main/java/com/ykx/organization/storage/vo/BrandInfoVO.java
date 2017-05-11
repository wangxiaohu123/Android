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
 * Created by 2017/4/6.
 * <p>
 * <p>
 * ********************************************************************************
 */

//"id": 22,
//        "phone": "15982848645",
//        "token": "16a077744f4b9e7b9466ffb595aaacf09fb9adbc",
//        "status": 1,
//        "cert": 0,
//        "branding": -1,
//        "name": "感觉你好",
//        "logo": "Fn80YcLDgpptosi1gRRhj3OJXKBM",
//        "type": null,
//        "adcode": null,
//        "address": null,
//        "lat": null,
//        "lng": null,
//        "contact": null,
//        "founded": null,
//        "summary": null,
//        "photo": null,
//        "campus_count": 1,
//        "course_count": 1,
//        "score": "5.00",
//        "hits": 0,
//        "logo_url": "https://omhg5r8cx.qnssl.com/Fn80YcLDgpptosi1gRRhj3OJXKBM?e=1491450333&token=Isc3J6r9gwG6b1zgTgycGeFYyF99kEPFZXs14U7G:sVMVJKAYJq4wUChSzRrXlrcvgZU=",
//        "campus_limit": 3,
//        "course_limit": 10

public class BrandInfoVO implements Serializable {

    private Brand brand;
    private Branding branding;

    public Brand getBrand() {
        if (brand==null){
            brand=new Brand();
        }
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Branding getBranding() {
        if (branding==null){
            branding=new Branding();
        }
        return branding;
    }

    public void setBranding(Branding branding) {
        this.branding = branding;
    }

    public class Brand implements Serializable{

        private Integer id;
        private String phone;
        private String token;
        private Integer status;
        private Integer cert;
        private Integer branding;
        private String founded;
        private String score;
        private Integer hits;
        private Integer campus_count;
        private Integer course_count;
        private Integer campus_limit;
        private Integer course_limit;

        private String name;
        private String logo;
        private String logo_url;
        private String teaching;
        private String team;
        private String honour;
        private String environment;
        private String operation;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCert() {
            return cert;
        }

        public void setCert(Integer cert) {
            this.cert = cert;
        }

        public Integer getBranding() {
            return branding;
        }

        public void setBranding(Integer branding) {
            this.branding = branding;
        }

        public String getFounded() {
            return founded;
        }

        public void setFounded(String founded) {
            this.founded = founded;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public Integer getHits() {
            return hits;
        }

        public void setHits(Integer hits) {
            this.hits = hits;
        }

        public Integer getCampus_count() {
            return campus_count;
        }

        public void setCampus_count(Integer campus_count) {
            this.campus_count = campus_count;
        }

        public Integer getCourse_count() {
            return course_count;
        }

        public void setCourse_count(Integer course_count) {
            this.course_count = course_count;
        }

        public Integer getCampus_limit() {
            return campus_limit;
        }

        public void setCampus_limit(Integer campus_limit) {
            this.campus_limit = campus_limit;
        }

        public Integer getCourse_limit() {
            return course_limit;
        }

        public void setCourse_limit(Integer course_limit) {
            this.course_limit = course_limit;
        }

        public String getName() {
            if ((name!=null)&&(name.length()>0)) {
                return name;
            }else{
                return "暂无";
            }
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

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getTeaching() {
            return teaching;
        }

        public void setTeaching(String teaching) {
            this.teaching = teaching;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getHonour() {
            return honour;
        }

        public void setHonour(String honour) {
            this.honour = honour;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }
    }
    public class Branding implements Serializable{

        private Integer status; //-1 未审核,0 审核中,1 通过,2 未通过
        private String name;
        private String logo_url;
        private String reason;
        private Boolean is_apply;

        private Integer id;
        private Integer agency_id;
        private String logo;
        private String trademark;
        private String number;
        private String certificate;
        private String attorney;
        private String linkman;
        private String phone;
        private String tel;
        private String certificate_url;
        private String attorney_url;

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

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Boolean getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(Boolean is_apply) {
            this.is_apply = is_apply;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAgency_id() {
            return agency_id;
        }

        public void setAgency_id(Integer agency_id) {
            this.agency_id = agency_id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTrademark() {
            return trademark;
        }

        public void setTrademark(String trademark) {
            this.trademark = trademark;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getAttorney() {
            return attorney;
        }

        public void setAttorney(String attorney) {
            this.attorney = attorney;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCertificate_url() {
            return certificate_url;
        }

        public void setCertificate_url(String certificate_url) {
            this.certificate_url = certificate_url;
        }

        public String getAttorney_url() {
            return attorney_url;
        }

        public void setAttorney_url(String attorney_url) {
            this.attorney_url = attorney_url;
        }
    }
}
