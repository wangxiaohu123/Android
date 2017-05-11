package com.ykx.organization.pages.bases;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

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
 * Created by 2017/3/28.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class MyMapView extends MapView {

    public interface CallBackListener{
        void callUp();
        void callDown();
        void move();
    }

    private CallBackListener callBackListener;

    public MyMapView(Context context) {
        super(context);
        initMapView();
    }


    public MyMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initMapView();
    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    private void initMapView(){
        getMap().setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (callBackListener!=null){
                        callBackListener.callUp();
                    }
                }else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    if (callBackListener!=null){
                        callBackListener.callDown();
                    }
                }else if(motionEvent.getAction()==MotionEvent.ACTION_MOVE){
                    if (callBackListener!=null){
                        callBackListener.move();
                    }
                }
            }
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction()==MotionEvent.ACTION_UP){
//            if (callBackListener!=null){
//                callBackListener.callUp();
//            }
//        }
//        return super.onTouchEvent(event);
//    }


}
