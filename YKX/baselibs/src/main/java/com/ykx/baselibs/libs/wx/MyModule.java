//package com.ykx.baselibs.libs.wx;
//
//import android.widget.Toast;
//
//import com.taobao.weex.annotation.JSMethod;
//import com.taobao.weex.bridge.JSCallback;
//import com.taobao.weex.common.WXModule;
//import com.taobao.weex.common.WXModuleAnno;
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
//public class MyModule extends WXModule {
//
////    @WXModuleAnno(runOnUIThread = true)
//
//    @JSMethod(uiThread = true)
//    public void printLog(String msg) {
//        Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_SHORT).show();
//    }
//
//
//    @JSMethod
//    public void getLocation(String msg,JSCallback callback){
//        //获取定位代码.....
//        Map<String,String> data=new HashMap<>();
//        data.put("x","x");
//        data.put("y","y");
//        //通知一次
//        callback.invoke(data);
//        //持续通知
////        callback.invokeAndKeepAlive(data);
//
//        //invoke方法和invokeAndKeepAlive两个方法二选一
//    }
//
//}
