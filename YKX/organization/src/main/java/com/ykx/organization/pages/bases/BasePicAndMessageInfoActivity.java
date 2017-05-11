package com.ykx.organization.pages.bases;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;

import com.ykx.organization.R;

import java.io.File;
import java.io.IOException;


public class BasePicAndMessageInfoActivity extends BasePicActivity{

//    private final String editUrl="http://172.20.200.8:3000/";//  file:///android_asset/wx.html

    private final String editUrl="file:///android_asset/fwb/index.html";

    private String title;
    private String message;

    private WebView webView;

    public static final int INPUT_FILE_REQUEST_CODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 2;
    private ValueCallback<Uri[]> mFilePathCallback;

    private String mCameraPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title=getIntent().getStringExtra("title");
        message=getIntent().getStringExtra("message");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_pic_and_message_info);

        initUI();
    }

    private void initUI(){

        webView=(WebView) findViewById(R.id.pam_webview);
        webView.loadUrl(editUrl);
        webView.addJavascriptInterface(new JsToJava(), "android");
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);

        webView.setWebChromeClient(mWebChromeClient);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals(editUrl)){
                    loadData();
                }
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
        });
    }

    @Override
    protected void setRightView(LinearLayout rightContentView) {

        TextView rightview=new TextView(this);
        rightview.setGravity(Gravity.CENTER);
        rightview.setText(getResources().getString(R.string.curriculum_activity_add_title_save));
        rightview.setTextSize(15);
        rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
        rightContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:summernoteGet()");
            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }


    private class JsToJava{

        @android.webkit.JavascriptInterface
        public void submitData(final String str) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(BasePicAndMessageInfoActivity.this, "" + str, Toast.LENGTH_SHORT).show();
                    message=str;
                    Intent intent=new Intent();
                    intent.putExtra("message",message);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }

        @android.webkit.JavascriptInterface
        public void showPicSelectedAction(){
            showPicDialog(new Size(5,5));
        }
    }

    private void loadData(){//summernoteSet
//        String message="来自提价的html元素数据";//message
//        String message="<p>2</p><p>31</p><p>23</p><p>123</p><p>1</p><p>23</p><p>12</p><p>31</p><p>23</p><p>12</p><p>3</p><p><img style=\"width: 100%;\" src=\"http://onz4ffdfq.bkt.clouddn.com/FuOwx-OLZ7iXcFZCJI4qiNuZuALo\"><br></p>";
        webView.loadUrl("javascript:summernoteSet('" + message+ "')");
//        webView.loadUrl("javascript:summernoteSet()");
    }

    //在sdcard卡创建缩略图
    //createImageFileInSdcard
    @SuppressLint("SdCardPath")
    private File createImageFile() {
        File file=new File(Environment.getExternalStorageDirectory()+"/","tmp.png");
        mCameraPhotoPath=file.getAbsolutePath();
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                WebChromeClient.FileChooserParams fileChooserParams) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }


            mFilePathCallback = filePathCallback;

            /**
             标准意图，被发送到相机应用程序捕获一个图像，并返回它。通过一个额外的extra_output控制这个图像将被写入。如果extra_output是不存在的，
             那么一个小尺寸的图像作为位图对象返回。如果extra_output是存在的，那么全尺寸的图像将被写入extra_output URI值。
             */
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    //设置MediaStore.EXTRA_OUTPUT路径,相机拍照写入的全路径
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    Log.e("WebViewSetting", "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    System.out.println(mCameraPhotoPath);
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");
            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
                System.out.println(takePictureIntent);
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片文件选择");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;
        }



        //The undocumented magic method override
        //Eclipse will swear at you if you try to put @Override here
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            BasePicAndMessageInfoActivity.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);

        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            BasePicAndMessageInfoActivity.this.startActivityForResult(
                    Intent.createChooser(i, "Image Chooser"),
                    FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            BasePicAndMessageInfoActivity.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), BasePicAndMessageInfoActivity.FILECHOOSER_RESULTCODE);

        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result != null) {
//                String imagePath = ImageFilePath.getPath(this, result);
                String imagePath=result.getPath();
                if (!TextUtils.isEmpty(imagePath)) {
                    result = Uri.parse("file:///" + imagePath);
                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
            // 5.0的回调
            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
//                        Logger.d("camera_photo_path", mCameraPhotoPath);
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
//                    Logger.d("camera_dataString", dataString);
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }
//
//    @android.webkit.JavascriptInterface
//    public void submitData(final String str) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(BasePicAndMessageInfoActivity.this, "js调用了Native函数传递参数：" + str, Toast.LENGTH_SHORT).show();
//                message=str;
//                Intent intent=new Intent();
//                intent.putExtra("message",message);
//                setResult(RESULT_OK,intent);
//                finish();
//            }
//        });
//
//    }
//
//    @android.webkit.JavascriptInterface
//    public void showPicSelectedAction(){
//        showPicDialog(new Size(5,5));
//    }

    protected void showFilePath(String filepath) {
        webView.loadUrl("javascript:setImage('"+filepath+"')");
    }

    @Override
    protected String titleMessage() {
        if (title!=null){
            return title;
        }
        return getResString(R.string.sys_base_pic_and_message_edit);
    }

    @Override
    protected void callBack() {
        super.callBack();
        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
