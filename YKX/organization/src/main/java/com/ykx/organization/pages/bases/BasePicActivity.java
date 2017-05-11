package com.ykx.organization.pages.bases;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.baoyz.actionsheet.ActionSheet;
import com.yalantis.ucrop.UCrop;
import com.ykx.baselibs.commons.Constant;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
 * Created by 2017/3/31.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class BasePicActivity extends BaseActivity {

    public class Size{
        int width;
        int height;

        public Size() {
        }

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    private Map<String,Bitmap> bitmaps=new HashMap<>();

    private final int REQUEST_CAMERE = 101;
    private final int REQUEST_PICS = 102;


    private Uri fileUri = null;

    private Size defaultSize;

    private boolean isedit=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_basepic);
    }


    protected void showPicDialog(boolean isedit){
        this.isedit=isedit;
        showPicDialog(new Size(1,1));
    }

    protected void showPicDialog(Size size){
        defaultSize=size;
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("照相", "相册选择")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener(){
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                         if (index==0){
                             takePic();
                         }else if(index==1){
                             spikPictures();
                         }
                    }
                }).show();
    }


    private void takePic(){
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Constant.PNG_CACHE_PATH;
        File path1 = new File(path);
        if(!path1.exists()){
            path1.mkdirs();
        }
        File file = new File(path1,System.currentTimeMillis()+".png");
        fileUri = Uri.fromFile(file);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent1, REQUEST_CAMERE);
    }

    private void spikPictures() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, REQUEST_PICS);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, REQUEST_PICS);
    }

    protected void initUCropActivity(){
        if (!isedit){
            returnPic(fileUri.getPath());
            return;
        }
        Size size=resetSize();
        UCrop.of(fileUri, fileUri)
                .withAspectRatio(size.width, size.height)
                .withMaxResultSize(size.width*100, size.height*100)
                .start(this);
    }

    protected void initUCropTakePicActivity(String path){
        if (!isedit){
            returnPic(path);
            return;
        }
        Uri fileUri = Uri.fromFile(new File(path));
        String pathn = Constant.PNG_CACHE_PATH;
        File path1 = new File(pathn);
        if(!path1.exists()){
            path1.mkdirs();
        }
        File file = new File(path1,System.currentTimeMillis()+".png");
        Uri fileUrin = Uri.fromFile(file);
        Size size=resetSize();
        UCrop.of(fileUri, fileUrin)
                .withAspectRatio(size.width, size.height)
                .withMaxResultSize(size.width*300, size.height*300)
                .start(this);
    }

    private void returnPic(final String path){
//        final Bitmap bitmap=BitmapFactory.decodeFile(path);

        showLoadingView();
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... params) {
                Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
                Tiny.getInstance().source(path).asBitmap().withOptions(options).compress(new BitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap) {
                        //return the compressed bitmap object
                        resetBitmap(bitmap);
                        hindLoadingView();

                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute();

//        new AsyncTask<String,String,String>(){
//            private Bitmap newBitmap;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                showLoadingView();
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                newBitmap = BitmapUtils.getZoomImage(bitmap,150);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                resetBitmap(newBitmap);
//                hindLoadingView();
//            }
//        }.execute();
    }

//    public final int getByteCount(Bitmap bitmap) {
//        // int result permits bitmaps up to 46,340 x 46,340
//        if (bitmap==null){
//            return 0;
//        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        Log.e("xx","length=" + baos.toByteArray().length);
//
//        return baos.toByteArray().length;
//    }

    protected Size resetSize(){
        if (defaultSize==null) {
            defaultSize=new Size();
            defaultSize.width = 4;
            defaultSize.height = 3;
        }
        return defaultSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            if(requestCode==REQUEST_CAMERE){
                initUCropActivity();
            }else if(requestCode==REQUEST_PICS){
                getPIC(data);
            }else if(requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                String filepath=resultUri.getPath();
                Bitmap bitmap=BitmapFactory.decodeFile(filepath);
                bitmaps.put(filepath,bitmap);
                resetBitmap(bitmap);
                showFilePath(filepath);
            }else if(requestCode == UCrop.RESULT_ERROR) {
//                final Throwable cropError = UCrop.getError(data);
                resetBitmap(null);
            }
        }
    }

    protected void showFilePath(String filepath) {
    }

    private void getPIC(Intent data){
        try {
            Uri originalUri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
//        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            String path="";
            if(cursor!=null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }else{
                path=data.getData().getPath();
            }

//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String path = cursor.getString(column_index);

            initUCropTakePicActivity(path);
        }catch (Exception e){

        }
    }

    protected void resetBitmap(Bitmap bitmap){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Map.Entry<String,Bitmap> entry:bitmaps.entrySet()){
            Bitmap bitmap=entry.getValue();
            if (bitmap!=null){
                if (!bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }
        }
        bitmaps.clear();
    }
}
