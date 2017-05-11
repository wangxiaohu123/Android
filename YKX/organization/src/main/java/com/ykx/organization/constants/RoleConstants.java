package com.ykx.organization.constants;

import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.RoleModule;

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
 * Created by 2017/4/27.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class RoleConstants {


    public static final String role_add="add";
    public static final String role_view="view";
    public static final String role_edit="edit";
    public static final String role_drop="drop";

    public static final String panel="panel"; //工作台
    public static final String panel_certification="certification"; //登记
    public static final String panel_marketing="marketing"; //营销活动
    public static final String panel_news="news"; //今日必读
    public static final String panel_summary="summary"; //经营概况
    public static final String panel_teaching="teaching"; //校区，班课，老师统计


    public static final String connection="connection"; //联络


    public static final String teaching="teaching";//教学
    public static final String teaching_course="course";//班课管理


    public static final String operation="operation"; //运营
    public static final String operation_campus="campus"; //校区管理
    public static final String operation_staff="staff"; //员工管理
    public static final String operation_brand="brand"; //品牌管理
    public static final String operation_real="real"; //实名登记


    public static boolean isEnable(List<RoleModule> roleModules,String firstMOdule,String secondModule, String role){

        if (roleModules==null){
            roleModules= MMCacheUtils.getUserInfoVO().getPower();
        }

        if ((firstMOdule!=null)&&(secondModule!=null)) {
            if (roleModules != null) {
                for (RoleModule roleModule : roleModules) {
                    if (firstMOdule.equals(roleModule.getAlias())){
                        List<RoleModule> roleSecondModules = roleModule.getModules();
                        if (roleSecondModules!=null){
                            for (RoleModule roleSecondModule : roleSecondModules) {
                                if (secondModule.equals(roleSecondModule.getAlias())) {
                                    if (role!=null) {
                                        List<RoleModule.ActionVO> actionVOList = roleSecondModule.getAction();
                                        if (actionVOList != null) {
                                            for (RoleModule.ActionVO actionVO : actionVOList) {
                                                if (role.equals(actionVO.getAlias())) {
                                                    return actionVO.isChecked();
                                                }
                                            }
                                        } else {
                                            return false;
                                        }
                                    }else{
                                        return roleSecondModule.isShow();
                                    }
                                }
                            }
                        }else{
                            return false;
                        }
                    }
                }
            }else{
                return false;
            }
        }
        return false;
    }


}
