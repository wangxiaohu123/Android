//package com.ykx.baselibs.libs.wx;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//
//import com.taobao.weex.IWXRenderListener;
//import com.taobao.weex.WXSDKEngine;
//import com.taobao.weex.WXSDKInstance;
//import com.taobao.weex.common.WXException;
//import com.taobao.weex.common.WXRenderStrategy;
//import com.taobao.weex.utils.WXFileUtils;
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
//public class LocalhostActivity extends AppCompatActivity implements IWXRenderListener {
//
//    WXSDKInstance mWXSDKInstance;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_local);
//
//        mWXSDKInstance = new WXSDKInstance(this);
//        mWXSDKInstance.registerRenderListener(this);
//
//
//        try {
//            WXSDKEngine.registerComponent("richtext",RichText.class);
//        } catch (WXException e) {
//            e.printStackTrace();
//        }
//        /**
//         * WXSample 可以替换成自定义的字符串，针对埋点有效。
//         * template 是.we transform 后的 js文件。
//         * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
//         * jsonInitData 可以为空。
//         */
//        Map<String,Object> options=new HashMap<>();
//        options.put(WXSDKInstance.BUNDLE_URL,"file://setp.js");
//        mWXSDKInstance.render("WXSample", WXFileUtils.loadAsset("setp.js", this), null, null, WXRenderStrategy.APPEND_ASYNC);
//    }
//
//    @Override
//    public void onViewCreated(WXSDKInstance instance, View view) {
//        setContentView(view);
//    }
//
//    @Override
//    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
//
//    }
//
//    @Override
//    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
//
//    }
//
//    @Override
//    public void onException(WXSDKInstance instance, String errCode, String msg) {
//
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityResume();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityPause();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityStop();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityDestroy();
//        }
//    }
//}
//
