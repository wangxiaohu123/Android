package com.ykx.organization.storage.vo;

import android.util.ArrayMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
 * Created by 2017/4/17.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class EmpRoleVO implements Serializable {

    private Map<RoleTitle,List<RoleContentVO>> roles;

    public Map<RoleTitle, List<RoleContentVO>> getRoles() {
        return roles;
    }

    public void setRoles(Map<RoleTitle, List<RoleContentVO>> roles) {
        this.roles = roles;
    }


    public void creatdata(){
        if (roles==null){
            roles=new LinkedHashMap<>();
        }

        for (int i=0;i<4;i++){
            RoleTitle roleTitle=new RoleTitle();
            roleTitle.setTitle("大模块"+i);
            List<RoleContentVO> roleContentVOs=new ArrayList<>();
            for (int j=0;j<5;j++){
                List<KeyValue> keyValues=new ArrayList<>();
                for (int z=0;z<4;z++){
                    keyValues.add(new KeyValue(i,"权限"+(z+1),((z%2)==0)));
                }
                RoleContentVO roleContentVO=new RoleContentVO("小模块权限"+(j+1),((j%2)==0),keyValues);
                roleContentVOs.add(roleContentVO);
            }
            roles.put(roleTitle,roleContentVOs);
        }

    }

    public class RoleTitle implements Serializable {

        private String title;

        public RoleTitle() {
        }

        public RoleTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


    public class RoleContentVO implements Serializable {

        public RoleContentVO() {
        }

        public RoleContentVO(String roleModleName, boolean edit, List<KeyValue> keyValues) {
            this.roleModleName = roleModleName;
            this.edit = edit;
            this.keyValues = keyValues;
        }

        private String roleModleName;
        private boolean edit;
        private List<KeyValue> keyValues;


        public String getRoleModleName() {
            return roleModleName;
        }

        public void setRoleModleName(String roleModleName) {
            this.roleModleName = roleModleName;
        }

        public boolean isEdit() {
            return edit;
        }

        public void setEdit(boolean edit) {
            this.edit = edit;
        }

        public List<KeyValue> getKeyValues() {
            return keyValues;
        }

        public void setKeyValues(List<KeyValue> keyValues) {
            this.keyValues = keyValues;
        }
    }

}
