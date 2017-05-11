package com.ykx.organization.pages.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.adapters.AppAdapter;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.bases.RoleFragment;
import com.ykx.organization.pages.home.teachings.tm.CurriculumAllListActivity;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.AppInfoVO;
import com.ykx.organization.storage.vo.LoginReturnInfo;
import com.ykx.organization.storage.vo.RoleModule;

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
 * Created by 2017/3/15.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class TeachingFragment extends RoleFragment {


    private GridView baseAppViews;
    private AppAdapter appAdapter;

    @Override
    protected int getContentViewResource() {

        return R.layout.fragment_home_teaching;
    }


    @Override
    protected void initUI() {

        baseAppViews=find(R.id.base_app_gridview,null);
        appAdapter=new AppAdapter(null,getActivity());
        baseAppViews.setAdapter(appAdapter);

        baseAppViews.setOnItemClickListener(onItemClick);

        checkApps(MMCacheUtils.getUserInfoVO().getPower());
    }

    @Override
    public void refreshWithRole(List<RoleModule> roleModel) {
        super.refreshWithRole(roleModel);

        if (createViewFlag) {
            checkApps(roleModel);
        }
    }

    private void checkApps(List<RoleModule> roleModel){
        List<AppInfoVO> appInfoVOs=new ArrayList<>();

        appInfoVOs.add(new AppInfoVO(1,getResString(R.string.jx_fragment_base_item_bkgl),R.drawable.svg_home_teaching_cell1_bkgl_selected,RoleConstants.isEnable(roleModel,RoleConstants.teaching, RoleConstants.teaching_course,null)));
        appInfoVOs.add(new AppInfoVO(2,getResString(R.string.jx_fragment_base_item_kjjy),R.drawable.svg_home_teaching_cell1_kjjy_selected,true));
        appInfoVOs.add(new AppInfoVO(3,getResString(R.string.jx_fragment_base_item_xykq),R.drawable.svg_home_teaching_cell1_xskq_selected,true));
        appInfoVOs.add(new AppInfoVO(4,getResString(R.string.jx_fragment_base_item_lskq),R.drawable.svg_home_teaching_cell1_lskq_selected,true));
        appInfoVOs.add(new AppInfoVO(5,getResString(R.string.jx_fragment_base_item_jxtj),R.drawable.svg_home_teaching_cell1_yyzc_selected,true));

        appAdapter.refresh(appInfoVOs,baseAppViews);

    }


    private AdapterView.OnItemClickListener onItemClick=new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppInfoVO appInfoVO = (AppInfoVO) parent.getItemAtPosition(position);
            if (appInfoVO!=null){
                if (appInfoVO.getId()==1){
                    toCurriculum();
                }
            }
        }
    };

    private void toCurriculum(){
        if (RoleConstants.isEnable(roleModel,RoleConstants.teaching,RoleConstants.teaching_course,RoleConstants.role_view)){
            LoginReturnInfo loginReturnInfo = MMCacheUtils.getLoginReturnInfo();
            if (loginReturnInfo!=null){
                BaseActivity activity=activity();
                Intent intent=new Intent(activity,CurriculumAllListActivity.class);
                activity.startActivity(intent);
            }
        }else{
            showToast(R.string.sys_role_fail_toast);
        }
    }

    @Override
    protected void onViewDidLoad() {

    }

    @Override
    public void onClick(View v) {

    }
}
