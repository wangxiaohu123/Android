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
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/3/16.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class NumListActivity extends MultiselectListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }


    @Override
    protected String titleMessage() {

        return getResources().getString(R.string.sys_num_selected_title);
    }


    private void loadData(){
        List<TypeVO> datas=new ArrayList<>();
        for (int i=1;i<=100;i++){
            datas.add(new TypeVO(String.valueOf(i),i));
        }
        resetData(datas);
    }

    protected void selectedItemClick(TypeVO typeVO){
        Intent intent=new Intent();
        intent.putExtra("num",typeVO.getCode());
        setResult(RESULT_OK,intent);
        finish();
    }

}
