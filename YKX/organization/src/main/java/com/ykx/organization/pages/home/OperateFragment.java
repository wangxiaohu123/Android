package com.ykx.organization.pages.home;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ykx.organization.R;
import com.ykx.organization.adapters.AppAdapter;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.authentication.AuthenticationResultActivity;
import com.ykx.organization.pages.bases.RoleFragment;
import com.ykx.organization.pages.home.operates.brandmanager.BrandManagerMainActivity;
import com.ykx.organization.pages.home.operates.campus.CampusListActivity;
import com.ykx.organization.pages.home.operates.empmanager.EmpManagerMainActivity;
import com.ykx.organization.pages.home.operates.wallet.WalletMainActivity;
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
 * TODO  运营主界面
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/15.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class OperateFragment extends RoleFragment {


    private GridView baseAppViews;
    private AppAdapter appAdapter;

    private View ykxContextView;
    private GridView ykxAppViews;
    private AppAdapter ykxAppAdapter;


    @Override
    protected int getContentViewResource() {

        return R.layout.fragment_home_operate;
    }

    @Override
    protected void initUI() {

        baseAppViews=find(R.id.base_app_gridview,null);
        appAdapter=new AppAdapter(null,getActivity());
        baseAppViews.setAdapter(appAdapter);

        baseAppViews.setOnItemClickListener(onItemClick);

        ykxContextView=find(R.id.ykx_context_app_view,null);
        ykxAppViews=find(R.id.ykx_app_gridview,null);
        ykxAppAdapter=new AppAdapter(null,getActivity());
        ykxAppAdapter.setCallBackListener(new AppAdapter.CallBackListener() {
            @Override
            public void callBack(List<AppInfoVO> infoVOs) {
                if ((infoVOs!=null)&&(infoVOs.size()>0)){
                    ykxContextView.setVisibility(View.VISIBLE);
                }else{
                    ykxContextView.setVisibility(View.GONE);
                }
            }
        });
        ykxAppViews.setAdapter(ykxAppAdapter);

        ykxAppViews.setOnItemClickListener(ykxItemClick);

        checkApps(MMCacheUtils.getUserInfoVO().getPower());

        find(R.id.wallet_view,toWalletAction);
    }

    @Override
    public void refreshWithRole(List<RoleModule> roleModel) {
        super.refreshWithRole(roleModel);
        if (createViewFlag) {
            checkApps(roleModel);
        }
    }

    private View.OnClickListener toWalletAction=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Activity activity=activity();
            Intent intent=new Intent(activity,WalletMainActivity.class);
            activity.startActivity(intent);
        }
    };

    private void checkApps(List<RoleModule> roleModel){
        List<AppInfoVO> appInfoVOs=new ArrayList<>();

        appInfoVOs.add(new AppInfoVO(isShow(RoleConstants.operation_campus,1),getResString(R.string.operate_fragment_group_item1_cell_xqbk),R.drawable.svg_home_operate_cell1_jgxq,isEnable(roleModel,RoleConstants.operation_campus)));
        appInfoVOs.add(new AppInfoVO(2,getResString(R.string.operate_fragment_group_item1_cell_ddgl),R.drawable.svg_home_operate_cell1_ddgl,true));
        appInfoVOs.add(new AppInfoVO(3,getResString(R.string.operate_fragment_group_item1_cell_xygl),R.drawable.svg_home_operate_cell1_xygl,true));
        appInfoVOs.add(new AppInfoVO(isShow(RoleConstants.operation_staff,4),getResString(R.string.operate_fragment_group_item1_cell_yggl),R.drawable.svg_home_operate_cell1_yggl,isEnable(roleModel,RoleConstants.operation_staff)));
        appInfoVOs.add(new AppInfoVO(5,getResString(R.string.operate_fragment_group_item1_cell_pjgl),R.drawable.svg_home_operate_cell1_pjgl,true));
        appInfoVOs.add(new AppInfoVO(6,getResString(R.string.operate_fragment_group_item1_cell_yxzc),R.drawable.svg_home_operate_cell1_yyzc,true));
        appInfoVOs.add(new AppInfoVO(7,getResString(R.string.operate_fragment_group_item1_cell_yytj),R.drawable.svg_home_operate_cell1_yytj,true));

        appAdapter.refresh(appInfoVOs,baseAppViews);

        List<AppInfoVO> ykxAppInfoVOs=new ArrayList<>();

        ykxAppInfoVOs.add(new AppInfoVO(isShow(RoleConstants.operation_brand,1),getResString(R.string.operate_fragment_group_item2_ykx_vip_ppgl),R.mipmap.brand_rz_pp,isEnable(roleModel,RoleConstants.operation_brand)));
        ykxAppInfoVOs.add(new AppInfoVO(isShow(RoleConstants.operation_real,2),getResString(R.string.operate_fragment_group_item2_ykx_vip_smdj),R.drawable.svg_smrz_selected,isEnable(roleModel,RoleConstants.operation_real)));

        ykxAppAdapter.refresh(ykxAppInfoVOs,ykxAppViews);

    }

    private boolean isEnable(List<RoleModule> roleModel,String appFlag){
       return RoleConstants.isEnable(roleModel,RoleConstants.operation,appFlag,null);
    }

    private int isShow(String secondRoleName,int defaultIndex){
        boolean isshow =  RoleConstants.isEnable(roleModel,RoleConstants.operation,secondRoleName,RoleConstants.role_view);
        if (!isshow){
            return -1;
        }
        return defaultIndex;
    }


    private AdapterView.OnItemClickListener onItemClick=new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppInfoVO appInfoVO = (AppInfoVO) parent.getItemAtPosition(position);
            if (appInfoVO!=null){
                if (appInfoVO.getId()==1){
                    toJGXQAction();
                }else if(appInfoVO.getId()==4){
                    toEmpManager();
                }else if (appInfoVO.getId()==-1){
                    showToast(R.string.sys_role_fail_toast);
                }
            }
        }
    };

    private AdapterView.OnItemClickListener ykxItemClick=new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppInfoVO appInfoVO = (AppInfoVO) parent.getItemAtPosition(position);
            if (appInfoVO!=null){
                if (appInfoVO.getId()==1){
                    toPPGLAction();
                }else if(appInfoVO.getId()==2){
                    toSMRZAction();
                }else if (appInfoVO.getId()==-1){
                    showToast(R.string.sys_role_fail_toast);
                }
            }
        }
    };

    private void toJGXQAction(){
        LoginReturnInfo loginReturnInfo = MMCacheUtils.getLoginReturnInfo();
        if (loginReturnInfo!=null){
            Activity activity=activity();
//            String organizationname = loginReturnInfo.getName();
//            if ((organizationname!=null)&&(organizationname.length()>0)){
                Intent intent=new Intent(activity,CampusListActivity.class);

                activity.startActivity(intent);
//            }else{
//                Intent intent=new Intent(activity,AddOrganizationActivity.class);
//
//                activity.startActivity(intent);
//            }
        }
    }

    private void toSMRZAction(){
//        AuthenticationResultActivity
//        Intent intent=new Intent(getActivity(),AuthenticationActivity.class);
        Intent intent=new Intent(getActivity(),AuthenticationResultActivity.class);
        getActivity().startActivity(intent);
    }

    private void toPPGLAction(){
        Intent intent=new Intent(getActivity(),BrandManagerMainActivity.class);
        getActivity().startActivity(intent);
    }

    private void toEmpManager(){
        Intent intent=new Intent(getActivity(),EmpManagerMainActivity.class);
        getActivity().startActivity(intent);
    }



    @Override
    protected void onViewDidLoad() {

    }

    @Override
    public void onClick(View v) {

    }
}