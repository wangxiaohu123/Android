package com.ykx.organization.pages.home.operates.empmanager;

import android.os.Bundle;

import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.pages.bases.BaseCampusSelectedListActivity;
import com.ykx.organization.storage.vo.CampusVO;

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
 * Created by 2017/4/12.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class MultiCampusSelectedListActivity extends BaseCampusSelectedListActivity {

    private CampusVO.CampusList campusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        campusList= (CampusVO.CampusList) getIntent().getSerializableExtra("CampusList");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<CampusVO> resetCampusVO(List<CampusVO> allTypeVO) {
        if (campusList!=null) {
            List<CampusVO> selectedTypeVO = campusList.getDatas();
            if ((allTypeVO != null) && (selectedTypeVO != null)) {
                for (CampusVO types : selectedTypeVO) {
                    for (CampusVO typea : allTypeVO) {
                        if (typea.getName().equals(types.getName())) {
                            typea.setSelected(true);
                            break;
                        }
                    }
                }
            }
        }
        return allTypeVO;
    }

    @Override
    protected boolean isMultiselectFlag() {
        return true;
    }
}
