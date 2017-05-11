package com.ykx.organization.servers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ykx.baselibs.vo.AreaCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
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
 * Created by 2017/3/14.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class LocalDataServer {

    public interface ReturnCallBack<T>{
        void callBack(T object);
    }

    public class RetrunData implements Serializable{
        private List<AreaCode> areaCodeList;

        public List<AreaCode> getAreaCodeList() {
            return areaCodeList;
        }

        public void setAreaCodeList(List<AreaCode> areaCodeList) {
            this.areaCodeList = areaCodeList;
        }
    }

    //读SD中的文件
    private String readFileSdcardFile(Context context,String filename) throws IOException {
        String res="";
        try{
            InputStream fin = context.getClass().getResourceAsStream("/assets/"+filename);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


    public void getAreaCodes(final Context context,final ReturnCallBack<RetrunData> returnCallBack){

        final Handler handler =  new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what==10001) {
                    if (returnCallBack != null) {
                        RetrunData retrunData=(RetrunData)msg.getData().getSerializable("areas");
                        returnCallBack.callBack(retrunData);
                    }
                }


            }
        };

        new Thread(){
            @Override
            public void run() {
                super.run();
                Type type = new TypeToken<List<AreaCode>>(){}.getType();
                try {
                    String areacodedata=readFileSdcardFile(context, "areas.json");
                    List<AreaCode> areaCodeList = new Gson().fromJson(areacodedata,type);

                    RetrunData retrunData=new RetrunData();
                    retrunData.setAreaCodeList(areaCodeList);


                    Message message=new Message();
                    message.what=10001;
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("areas",retrunData);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1001);
                }


            }
        }.start();

    }



}
