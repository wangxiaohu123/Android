package com.ykx.organization.pages.home.operates.organization;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.MultiselectListActivity;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.TypesVO;

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
 * Created by 2017/3/16.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class TypeListActivity extends MultiselectListActivity {

    private List<TypeVO> selectedTypeVOs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }


    @Override
    protected String titleMessage() {

        return getResources().getString(R.string.sys_type_selected_title);
    }


    private void loadData(){
        new OperateServers().getOrganizationType("", new HttpCallBack<List<TypeVO>>() {
            @Override
            public void onSuccess(List<TypeVO> datas) {

                resetData(datas);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    protected void setRightView(LinearLayout rightContentView) {

        TextView rightview=new TextView(this);
        rightview.setGravity(Gravity.CENTER);
        rightview.setText(getResources().getString(R.string.curriculum_activity_add_title_save));
        rightview.setTextSize(15);
        rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
        rightContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

                TypesVO typesVO=new TypesVO();
                typesVO.setTypeVOs(selectedTypeVOs);

                intent.putExtra("typeVO",typesVO);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    protected void selectedItemClick(TypeVO typeVO){
        typeVO.setCheckFlag(!typeVO.isCheckFlag());
        refreshList(typeVO);
        if (typeVO.isCheckFlag()){
            selectedTypeVOs.add(typeVO);
        }else{
            TypeVO selectedTypeVO=null;
            for (TypeVO typeVO1:selectedTypeVOs){
                if (typeVO1.getName().equals(typeVO.getName())){
                    selectedTypeVO=typeVO1;
                    break;
                }
            }
            if (selectedTypeVO!=null) {
                selectedTypeVOs.remove(selectedTypeVO);
            }


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
