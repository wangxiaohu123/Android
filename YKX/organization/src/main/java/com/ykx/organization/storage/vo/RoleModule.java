package com.ykx.organization.storage.vo;

import com.ykx.baselibs.utils.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by 2017/4/24.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class RoleModule implements Serializable {

    private String alias;
    private String name;
    private String ico;
    private String belong;
    private boolean show;
    private List<ActionVO> action;

    private List<RoleModule> modules;

    public static RoleModuleList getRoleModuleList(){
        return new RoleModuleList();
    }

    public List<RoleModule> getModules() {
        return modules;
    }

    public void setModules(List<RoleModule> modules) {
        this.modules = modules;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<ActionVO> getAction() {
        return action;
    }

    public void setAction(List<ActionVO> action) {
        this.action = action;
    }

    public class ActionVO implements Serializable{

        private String alias;
        private String name;
        private boolean checked;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    public static class RoleModuleList implements Serializable{
        private List<RoleModule> datas;

        public List<RoleModule> getDatas() {
            return datas;
        }

        public void setDatas(List<RoleModule> datas) {
            this.datas = datas;
        }
    }

    public static List<Map<String,Object>> getMaps(List<RoleModule> datas){
        List<Map<String,Object>> maps=new ArrayList<>();
        if (datas!=null){
            for (RoleModule roleModule:datas){
                Map<String,Object> roleModuleMaps=new HashMap<>();
                roleModuleMaps.put("alias",roleModule.getAlias());
                roleModuleMaps.put("belong",roleModule.getBelong());
                roleModuleMaps.put("ico",roleModule.getIco());
                roleModuleMaps.put("name",roleModule.getName());
                roleModuleMaps.put("show",roleModule.isShow());


                List<Map<String,Object>> moduleMaps=new ArrayList<>();

                List<RoleModule> roleModules = roleModule.getModules();
                for (RoleModule roleModule1:roleModules){
                    Map<String,Object> roleModule1Maps=new HashMap<>();
                    roleModule1Maps.put("alias",roleModule1.getAlias());
                    roleModule1Maps.put("belong",roleModule1.getBelong());
                    roleModule1Maps.put("ico",roleModule1.getIco());
                    roleModule1Maps.put("name",roleModule1.getName());
                    roleModule1Maps.put("show",roleModule1.isShow());

                    List<Map<String,Object>> moduleactionMaps=new ArrayList<>();

                    List<ActionVO> actionVOs = roleModule1.getAction();
                    for (ActionVO actionVO:actionVOs) {
                        Map<String, Object> actionMaps = new HashMap<>();
                        actionMaps.put("alias",actionVO.getAlias());
                        actionMaps.put("checked",actionVO.isChecked());
                        actionMaps.put("name",actionVO.getName());

                        moduleactionMaps.add(actionMaps);
                    }

                    roleModule1Maps.put("action",moduleactionMaps);

                    moduleMaps.add(roleModule1Maps);
                }
                roleModuleMaps.put("modules",moduleMaps);

                maps.add(roleModuleMaps);
            }
        }
        return maps;
    }
}
