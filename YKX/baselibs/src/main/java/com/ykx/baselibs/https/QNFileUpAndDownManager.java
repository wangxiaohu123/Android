package com.ykx.baselibs.https;

import android.graphics.Bitmap;
import android.util.Log;

import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.ykx.baselibs.commons.Constant;
import com.ykx.baselibs.vo.FileVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
 * Created by 2017/3/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class QNFileUpAndDownManager {

    public enum TokenType{
        PUBLIC,PRIVATE
    }

    // 初始化、执行上传
    private volatile boolean isCancelled = false;

    private UploadManager uploadManager;

    private TokenType tokenType=TokenType.PUBLIC;


    public interface FileCallBack{
        void callback(boolean uploadtag,LinkedHashMap<String,String> uploadfiles);
    }

    public void init(TokenType tokenType){
        this.tokenType=tokenType;
        createInit();
    }


    public void init(){
        createInit();
    }


    private void createInit(){

        String dirPath = Constant.OTHERS_PATH;

        //默认使用key的url_safe_base64编码字符串作为断点记录文件的文件名
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator(){
            public String gen(String key, File file){
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
            }
        };
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone2) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    private byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void upfiles(File file){
        if ((file!=null)&&(file.exists())) {

            byte[] data = getBytes(file);//<File对象、或 文件路径、或 字节数组>
            String key = null;//<指定七牛服务上的文件名，或 null>;
            String token = BaseHttp.getQnToken();//<从服务端SDK获取>;
            if (tokenType==TokenType.PRIVATE){
                token=BaseHttp.getQnPrivateToken();
            }

            uploadManager.put(data, key, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {
                                Log.i("qiniu", "Upload Success");
                            } else {
                                Log.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }
                    },
                    new UploadOptions(null, null, false, new UpProgressHandler() {
                        public void progress(String key, double percent) {
                            Log.i("qiniu", key + ": " + percent);
                        }
                    }, new UpCancellationSignal() {
                        public boolean isCancelled() {
                            return isCancelled;
                        }
                    }
                    )
            );
        }else{
            // TODO: 2017/3/10 文件异常
        }
    }


    public byte[] changeBitmapToBytes(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public void upfiles(final Bitmap file,String filename,UpCompletionHandler upCompletionHandler){
        if ((file!=null)) {

            byte[] data = changeBitmapToBytes(file);//<File对象、或 文件路径、或 字节数组>
            String key = filename;//"";//<指定七牛服务上的文件名，或 null>;
            String token = BaseHttp.getQnToken();//<从服务端SDK获取>;
            if (tokenType==TokenType.PRIVATE){
                token=BaseHttp.getQnPrivateToken();
            }

            uploadManager.put(data, key, token,upCompletionHandler,
                    new UploadOptions(null, null, false, new UpProgressHandler() {
                        public void progress(String key, double percent) {
                            Log.i("qiniu", key + ": " + percent);
                        }
                    }, new UpCancellationSignal() {
                        public boolean isCancelled() {
                            return isCancelled;
                        }
                    }
                    )
            );
        }else{
            // TODO: 2017/3/10 文件异常
        }
    }

    private String getFileName(){
        Random random = new Random();
        int s = random.nextInt(1000);
        long timestap= System.currentTimeMillis();
        String filename=timestap+"_"+s+".png";
        return filename;
    }

    public void upfiles(final List<FileVO> bitmaps,final FileCallBack filcallback){
        
        final LinkedHashMap<String,String> uploadfiles=new LinkedHashMap<>();

        if (bitmaps!=null) {
           for (final FileVO fileVO:bitmaps){

//               String filename=getFileName();
               upfiles(fileVO.getBitmap(), null, new UpCompletionHandler() {
                   @Override
                   public void complete(String key, ResponseInfo info, JSONObject response) {
                       if (info.isOK()){
                           try {
                               uploadfiles.put(fileVO.getKey(), (String) info.response.get("key"));//(String) info.response.get("key")
                               if (bitmaps.size()==uploadfiles.size()){
                                   // TODO: 2017/3/13 upload all files over
                                   if (filcallback!=null){
                                       filcallback.callback(true,uploadfiles);
                                   }
                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                               filcallback.callback(false,uploadfiles);
                           }

                       }else{
                           // TODO: 2017/3/13 updoad files fail
                           filcallback.callback(false,uploadfiles);
                       }
                   }
               });
           }
        }

    }

    public void downFile(){

    }

    // 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
    private void cancell() {
        isCancelled = true;
    }

}
