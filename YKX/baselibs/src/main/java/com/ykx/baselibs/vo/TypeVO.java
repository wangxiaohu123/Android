package com.ykx.baselibs.vo;

import android.widget.ImageView;

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
 * Created by 2017/3/16.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class TypeVO implements Serializable {

    private int id;
    private String name;
    private int code;

    private boolean checkFlag;

    public TypeVO() {
    }

    public TypeVO(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public TypeVO(int id, String name, int code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public static TypeVOs getTypevos(){
        return new TypeVOs();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public static class TypeVOs implements Serializable{
        private List<TypeVO> datas;

        public List<TypeVO> getDatas() {
            return datas;
        }

        public void setDatas(List<TypeVO> datas) {
            this.datas = datas;
        }
    }

}
