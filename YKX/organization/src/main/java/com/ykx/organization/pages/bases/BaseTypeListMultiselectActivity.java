package com.ykx.organization.pages.bases;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.MultiselectListActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.pages.home.operates.curriculum.TypeListMultiselectActivity;
import com.ykx.organization.servers.OperateServers;

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
 * Created by 2017/4/13.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class BaseTypeListMultiselectActivity  extends MultiselectListActivity {

    private TypeVO typeVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        typeVO= (TypeVO) getIntent().getSerializableExtra("typeVO");
        super.onCreate(savedInstanceState);

        loadData();
    }

    @Override
    protected String titleMessage() {

        if (typeVO==null){
            return getResources().getString(R.string.sys_type_selected_title);
        }else{
            return typeVO.getName();
        }
    }

    @Override
    protected Drawable getNextDrawable() {
        if (typeVO!=null) {
            if ((typeVO.getCode() % 10000) != 0) {
                return null;
            }
            return BitmapUtils.getDrawable(this, R.drawable.svg_detail);
        }else{
            return BitmapUtils.getDrawable(this, R.drawable.svg_detail);
        }
    }

    /**
     * 加载不同的分类数据
     */
    protected void loadData(){
        String typeId="";
        if (typeVO!=null){
            typeId=String.valueOf(typeVO.getCode());
        }
        new OperateServers().getOrganizationType(typeId, new HttpCallBack<List<TypeVO>>() {
            @Override
            public void onSuccess(List<TypeVO> datas) {

                resetData(datas);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    protected boolean isEndFlag(TypeVO typeVO){
        boolean isendflag=false;//是否为最后一级
        if (typeVO!=null) {
            if ((typeVO.getCode() % 100) != 0) {
                isendflag = true;
            }
        }
        return isendflag;
    }

    protected void selectedItemClick(TypeVO typeVO){

        boolean isendflag=false;//是否为最后一级
        if ((typeVO.getCode()%100)!=0){
            isendflag=true;
        }

        if (isendflag){
            Intent intent=new Intent();
            intent.putExtra("typeVO",typeVO);
            intent.setAction(getIntent().getStringExtra("action"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this,TypeListMultiselectActivity.class);
            intent.putExtra("typeVO",typeVO);
            intent.putExtra("action",getIntent().getStringExtra("action"));
            intent.putExtra("isMultiselectedFlag",Boolean.valueOf(isMultiselectedFlag));
            startActivity(intent);
        }
    }

//    @Override
//    protected List<TypeVO> getTypeVOs() {
//
//        List<TypeVO> typeVOs=new ArrayList<>();
//
//        typeVOs.add(new TypeVO("机构类目1","10001"));
//        typeVOs.add(new TypeVO("机构类目2","10002"));
//        typeVOs.add(new TypeVO("机构类目3","10003"));
//        typeVOs.add(new TypeVO("机构类目4","10004"));
//        typeVOs.add(new TypeVO("机构类目5","10005"));
//
//        return typeVOs;
//    }
}