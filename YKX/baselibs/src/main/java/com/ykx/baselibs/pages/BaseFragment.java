package com.ykx.baselibs.pages;

import android.app.Fragment;

import java.lang.reflect.Field;

/*********************************************************************************
 * Project Name  : Health
 * Package       : com.sofn.health.base.pages.home
 * <p/>
 * <p/>
 * Copyright  禄康源电子商务部  Corporation 2016 All Rights Reserved
 * <p/>
 * <p/>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 16/1/27 上午9:01.
 * <p/>
 * <p/>
 * ********************************************************************************
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            //throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshFragment(){

    }
}
