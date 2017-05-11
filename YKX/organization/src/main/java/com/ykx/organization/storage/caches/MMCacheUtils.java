package com.ykx.organization.storage.caches;

import com.ykx.organization.storage.vo.LoginReturnInfo;
import com.ykx.organization.storage.vo.UserInfoVO;

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
 * Created by 2017/3/17.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class MMCacheUtils {

    private static LoginReturnInfo loginReturnInfo;

    private static UserInfoVO userInfoVO;

    public static void setLoginReturnInfo(LoginReturnInfo lri){
        if (lri!=null){

        }


        loginReturnInfo=lri;
    }

    public static LoginReturnInfo getLoginReturnInfo() {
        return loginReturnInfo;
    }

    public static UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public static void setUserInfoVO(UserInfoVO userInfoVO) {
        if (userInfoVO!=null){

        }


        MMCacheUtils.userInfoVO = userInfoVO;
    }
}
