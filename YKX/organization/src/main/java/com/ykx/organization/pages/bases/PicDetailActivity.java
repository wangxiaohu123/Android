package com.ykx.organization.pages.bases;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.DeviceUtils;
import com.ykx.organization.R;

public class PicDetailActivity extends BaseActivity {

    private ImageView imageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        initUI();
    }

    private void initUI(){
        imageView= (ImageView) findViewById(R.id.image_view);

        int width = DeviceUtils.getDeviceWith(this);
        Size size=resetSize();
        int height=(int)(width*(size.height*1.0/size.width));

        imageView.setLayoutParams(new LinearLayout.LayoutParams(width,height));


        if (BaseUploadEditPicsActivity.detailPicVO!=null){
            if (BaseUploadEditPicsActivity.detailPicVO.bitmap!=null){
                imageView.setImageBitmap(BaseUploadEditPicsActivity.detailPicVO.bitmap);
            }else{
                BaseApplication.application.getDisplayImageOptions(BaseUploadEditPicsActivity.detailPicVO.picurl,imageView);
            }
        }
    }

    protected Size resetSize(){

        Size size = new Size(4,3);

        return size;
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.sys_pic_edit_title);
    }

    public void deletePicAction(View view){
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

}
