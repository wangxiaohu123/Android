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
 * Created by 2017/4/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

//{"ret":200,"data":{"campus_count":0,"course_count":0,"campus_max":"4","course_max":"6"},"msg":""}

public class CampusCoursVO implements Serializable{

    private Integer campus_count;
    private Integer course_count;
    private Integer campus_max;
    private Integer course_max;

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

    public Integer getCampus_max() {
        return campus_max;
    }

    public void setCampus_max(Integer campus_max) {
        this.campus_max = campus_max;
    }

    public Integer getCourse_max() {
        return course_max;
    }

    public void setCourse_max(Integer course_max) {
        this.course_max = course_max;
    }
}
