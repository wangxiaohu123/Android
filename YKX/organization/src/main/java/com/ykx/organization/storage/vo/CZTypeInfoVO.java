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
 * Created by 2017/5/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class CZTypeInfoVO implements Serializable {

    private Integer id;
    private Integer ubNum;
    private Double prices;
    private String yhl;

    public CZTypeInfoVO() {
    }

    public CZTypeInfoVO(Integer id, Integer ubNum, Double prices, String yhl) {
        this.id = id;
        this.ubNum = ubNum;
        this.prices = prices;
        this.yhl = yhl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUbNum() {
        return ubNum;
    }

    public void setUbNum(Integer ubNum) {
        this.ubNum = ubNum;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }

    public String getYhl() {
        return yhl;
    }

    public void setYhl(String yhl) {
        this.yhl = yhl;
    }
}
