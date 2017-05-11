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
 * Created by 2017/3/28.
 * <p>
 * <p>
 * ********************************************************************************
 */
//"total": 3,
//        "per_page": "100",
//        "current_page": 1,
//        "last_page": 1,
//        "next_page_url": null,
//        "prev_page_url": null,
//        "from": 1,
//        "to": 3,
public class CampusInfo implements Serializable {

    private Integer per_page;
    private Integer current_page;
    private Integer last_page;
    private Integer from;
    private Integer to;

    private List<CampusVO> data;

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public List<CampusVO> getData() {
        if (data==null){
            data=new ArrayList<>();
        }
        return data;
    }

    public void setData(List<CampusVO> data) {
        this.data = data;
    }
}
