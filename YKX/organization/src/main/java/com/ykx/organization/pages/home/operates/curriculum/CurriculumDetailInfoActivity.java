package com.ykx.organization.pages.home.operates.curriculum;

import android.os.Bundle;

import com.ykx.baselibs.commons.Constant;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BaseWebActivity;
import com.ykx.organization.storage.vo.CurriculumVO;

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
 * Created by 2017/4/20.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class CurriculumDetailInfoActivity extends BaseWebActivity {

    private CurriculumVO curriculumVO;
    private final StringBuffer stringBuffer=new StringBuffer(Constant.WEB_APP_URL+"b-courseMsg?course_id=");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        curriculumVO= (CurriculumVO) getIntent().getSerializableExtra("curriculumVO");
        super.onCreate(savedInstanceState);
    }


    @Override
    protected String getLoadUrl() {
        if (curriculumVO!=null){
            return stringBuffer.append(curriculumVO.getId()).toString();
        }
        return super.getLoadUrl();
    }

    @Override
    protected String titleMessage() {
        if (curriculumVO!=null){
            return curriculumVO.getName();
        }
        return getResString(R.string.activity_webview_title_curriculum);
    }

}
