package com.ykx.organization.pages.home.operates.curriculum;

import android.content.Intent;
import android.os.Bundle;

import com.ykx.baselibs.pages.MultiselectListActivity;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;

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
 * TODO  收费方式  收费方式 1 按次 2 按时 3 基础报名费
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/16.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class SFTypeListActivity extends MultiselectListActivity {

    public final static List<TypeVO> datas=new ArrayList<>();

    static {
        datas.add(new TypeVO("按次",1));
        datas.add(new TypeVO("按时",2));
        datas.add(new TypeVO("基础报名费",3));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }


    @Override
    protected String titleMessage() {

        return getResources().getString(R.string.curriculum_activity_sf_type_list_title);
    }


    private void loadData(){
        resetData(datas);
    }

    protected void selectedItemClick(TypeVO typeVO){
        Intent intent=new Intent();
        intent.putExtra("type",typeVO);
        setResult(RESULT_OK,intent);
        finish();
    }

}
