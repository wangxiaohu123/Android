package com.ykx.organization.storage.vo;

import com.ykx.baselibs.utils.TextUtils;

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
 * Created by 2017/4/26.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class UserInfoVO implements Serializable {

    private String avatar;
    private String name;
    private String phone;
    private List<RoleModule> power;

    private Easemob easemob;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        if (TextUtils.textIsNull(name)){
            return name;
        }
        return "品牌创建者";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<RoleModule> getPower() {
        return power;
    }

    public void setPower(List<RoleModule> power) {
        this.power = power;
    }

    public Easemob getEasemob() {
        return easemob;
    }

    public void setEasemob(Easemob easemob) {
        this.easemob = easemob;
    }

    public class Easemob{
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
