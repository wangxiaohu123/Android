package com.ykx.organization.pages.home.operates.empmanager;

import android.os.Bundle;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.MultiselectListActivity;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.PositionVO;

import java.util.ArrayList;
import java.util.List;

public class RoleMultisSelectedActivity extends MultiselectListActivity {

    private TypeVO.TypeVOs typeVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        typeVO= (TypeVO.TypeVOs) getIntent().getSerializableExtra("typeVO");
        super.onCreate(savedInstanceState);

        loadData();
    }

    private void loadData() {

        new OperateServers().getPerfList(new HttpCallBack<List<PositionVO>>() {
            @Override
            public void onSuccess(List<PositionVO> data) {

                ArrayList<TypeVO> datas=new ArrayList<>();
                if (data!=null){
                    for (PositionVO positionVO:data){
                        datas.add(new TypeVO(positionVO.getName(),positionVO.getId()));
                    }
                }
                if (typeVO!=null) {
                    resetNewTypeVO(datas,typeVO.getDatas());
                }

                resetData(datas);
            }

            @Override
            public void onFail(String msg) {

            }
        });

//        ArrayList<TypeVO> datas=new ArrayList<>();
//
//        datas.add(new TypeVO("管理员",1));
//        datas.add(new TypeVO("校长",2));
//        datas.add(new TypeVO("老师",3));
//        datas.add(new TypeVO("咨询师",4));
//        datas.add(new TypeVO("其他",5));

    }


    @Override
    protected String titleMessage() {

        return getResources().getString(R.string.emp_manager_activity_zw_selected);
    }


    @Override
    protected boolean isMultiselected() {
        return true;
    }

    @Override
    protected boolean isEndFlag(TypeVO typeVO){
        return true;
    }

    @Override
    protected boolean isOneActivity() {
        return true;
    }
}

