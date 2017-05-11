//package com.ykx.baselibs.libs.wx;
//
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.method.LinkMovementMethod;
//import android.text.style.URLSpan;
//import android.widget.TextView;
//
//import com.taobao.weex.WXSDKInstance;
//import com.taobao.weex.dom.WXDomObject;
//import com.taobao.weex.ui.component.WXComponent;
//import com.taobao.weex.ui.component.WXComponentProp;
//import com.taobao.weex.ui.component.WXVContainer;
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
//public class RichText extends WXComponent {
//
//    private TextView mHost;
//
//    public RichText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
//        super(instance, dom, parent);
//    }
//
//    @Override
//    protected void initView() {
//        mHost=new TextView(getContext());
//        mHost.setMovementMethod(LinkMovementMethod.getInstance());
//    }
//
//    @WXComponentProp(name = "tel")
//    public void setTelLink(String tel){
//        SpannableString spannable=new SpannableString(tel);
//        spannable.setSpan(new URLSpan("tel:"+tel),0,tel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mHost.setText(spannable);
//    }
//}