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
 * Created by 2017/4/18.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class ApplicantVO implements Serializable {

    private String url;
    private List<ApplicantInfo> applicants;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ApplicantInfo> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<ApplicantInfo> applicants) {
        this.applicants = applicants;
    }

    public class ApplicantInfo implements Serializable {

        private Integer id;
        private String phone;
        private String name;
        private String remark;
        private int status;//0,未处理,1.已通过，2，未通过
        private String reason;

        private String photoUrl;

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            if ((remark!=null)&&(remark.length()>0)){
                return remark;
            }
            return "暂无";
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

}
