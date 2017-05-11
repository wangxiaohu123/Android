package com.ykx.baselibs.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.ykx.baselibs.commons.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
 * Created by 2017/3/13.
 * <p>
 * <p>
 * ********************************************************************************
 */

public abstract class GetPicActivity extends BaseActivity {

    private int REQUEST_CODE_CAMERA=10001;//调用相机tag

    private String takePicPath="";

    protected void toGetPicAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //path为保存图片的路径，执行完拍照以后能保存到指定的路径下
        takePicPath= Constant.PIC_PATH+getFileName();
        File file = new File(takePicPath);
        Uri imageUri = Uri.fromFile(file );
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private String getFileName(){
        Random random = new Random();
        int s = random.nextInt(1000)%1000;
        long timestap= System.currentTimeMillis();
        String filename=timestap+"_"+s+".png";
        return filename;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            //直接取之前保存的路径
            takePhoneReturnFile(takePicPath);
        }
    }


    protected abstract void takePhoneReturnFile(String file);

    /**
     * 获取内置SD卡路径
     * @return
     */
    protected String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取外置SD卡路径
     * @return  应该就一条记录或空
     */
    protected List<String> getExtSDCardPath(){

        List<String> lResult = new ArrayList<String>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard"))
                {
                    String [] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()){
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }

}
