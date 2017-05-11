package com.ykx.organization.pages.bases;

import com.ykx.baselibs.pages.RootFragment;
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

public abstract class RoleFragment extends RootFragment {


    protected List<RoleModule> roleModel;

    public void refreshWithRole(List<RoleModule> roleModel){

        this.roleModel=roleModel;
    }

}
