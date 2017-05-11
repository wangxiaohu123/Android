//package com.ykx.baselibs.libs.wx;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import com.taobao.weex.IWXRenderListener;
//import com.taobao.weex.WXSDKEngine;
//import com.taobao.weex.WXSDKInstance;
//import com.taobao.weex.common.WXException;
//import com.taobao.weex.common.WXRenderStrategy;
//import com.ykx.baselibs.R;
//
//import java.util.HashMap;
//import java.util.Map;
//
///*********************************************************************************
// * Project Name  : YKX
// * Package       : com.ykx.apps
// * <p>
// * <p>
// * Copyright  优课学技术部  Corporation 2017 All Rights Reserved
// * <p>
// * <p>
// * <Pre>
// * TODO  描述文件做什么的
// * </Pre>
// *
// * @AUTHOR by wangxiaohu
// * Created by 2017/3/15.
// * <p>
// * <p>
// * ********************************************************************************
// */
//
//public class WXTestActivity extends AppCompatActivity implements IWXRenderListener {
//
//    private static String TEST_URL = "http://dotwe.org/raw/dist/6fe11640e8d25f2f98176e9643c08687.bundle.js";
//    private WXSDKInstance mWXSDKInstance;
//    private FrameLayout mContainer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxtest);
//
//        mContainer = (FrameLayout) findViewById(R.id.container);
//
//        mWXSDKInstance = new WXSDKInstance(this);
//        mWXSDKInstance.registerRenderListener(this);
//        /**
//         * pageName:自定义，一个标示符号。
//         * url:远程bundle JS的下载地址
//         * options:初始化时传入WEEX的参数，比如 bundle JS地址
//         * flag:渲染策略。WXRenderStrategy.APPEND_ASYNC:异步策略先返回外层View，其他View渲染完成后调用onRenderSuccess。WXRenderStrategy.APPEND_ONCE 所有控件渲染完后后一次性返回。
//         */
//
//
//        try {
//            WXSDKEngine.registerModule("myModule", MyModule.class);
//        } catch (WXException e) {
//            e.printStackTrace();
//        }
//
//        Map<String, Object> options = new HashMap<>();
//        options.put(WXSDKInstance.BUNDLE_URL, TEST_URL);
//
//        mWXSDKInstance.renderByUrl("WXSample",TEST_URL,options,null,WXRenderStrategy.APPEND_ONCE);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (mWXSDKInstance != null) {
//            mWXSDKInstance.onActivityStart();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mWXSDKInstance != null) {
//            mWXSDKInstance.onActivityResume();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mWXSDKInstance != null) {
//            mWXSDKInstance.onActivityPause();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mWXSDKInstance != null) {
//            mWXSDKInstance.onActivityStop();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mWXSDKInstance != null) {
//            mWXSDKInstance.onActivityDestroy();
//        }
//    }
//
//    @Override
//    public void onViewCreated(WXSDKInstance instance, View view) {
//        Log.d("xx","onViewCreated");
//        if (view.getParent() != null) {
//            ((ViewGroup) view.getParent()).removeView(view);
//        }
//        mContainer.addView(view);
//    }
//
//    @Override
//    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
//        Log.d("xx","onRenderSuccess");
//    }
//
//    @Override
//    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
//        Log.d("xx","onRefreshSuccess");
//
//    }
//
//    @Override
//    public void onException(WXSDKInstance instance, String errCode, String msg) {
//        Log.d("xx","onException");
//
//    }
//}
