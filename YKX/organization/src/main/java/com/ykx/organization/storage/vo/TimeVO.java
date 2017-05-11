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
 * Created by 2017/3/20.
 * <p>
 * <p>
 * ********************************************************************************
 */

//"repeat_mode": "month",
//        "repeat_at": "1,2,3,4,5",
//        "start_time": "08:00:00",
//        "end_time": "12:00:00",
//        "start_date": "2017-03-01",
//        "end_date": "2017-03-31"

public class TimeVO implements Serializable {

    private int id;
    private String repeat_mode;
    private String repeat_at;
    private String start_time;
    private String end_time;
    private String start_date;
    private String end_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepeat_mode() {
        return repeat_mode;
    }

    public void setRepeat_mode(String repeat_mode) {
        this.repeat_mode = repeat_mode;
    }

    public String getRepeat_at() {
        return repeat_at;
    }

    public void setRepeat_at(String repeat_at) {
        this.repeat_at = repeat_at;
    }

    public String getStart_time() {
        if (start_time!=null){
            if (start_time.length()>5){

                return start_time.substring(0,5);
            }
        }
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        if (end_time!=null){
            if (end_time.length()>5){

                return end_time.substring(0,5);
            }
        }
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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
}
