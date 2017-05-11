package com.ykx.organization.pages.bases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.utils.DeviceUtils;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.organization.R;

import java.io.Serializable;
import java.util.ArrayList;
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
 * Created by 2017/3/31.
 * <p>
 * <p>
 * ********************************************************************************
 */

public abstract class BaseUploadEditPicsActivity extends BasePicActivity {

//    private final int REQUEST_SFZFM=101;

    private final int PIC_DETAIL_FLAG=107;

    public static PicVO detailPicVO;

    private GridView picGridView;

    private PicAdapter picAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    protected void createView(String photos,List<String> photourl) {
        picGridView=createGridView();
        ViewGroup viewGroup= (ViewGroup) findViewById(getGridViewContentId());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewGroup.addView(picGridView,layoutParams);

        List<PicVO> bitmaps=new ArrayList<>();
        picAdapter=new PicAdapter(this,bitmaps);
        picGridView.setAdapter(picAdapter);
        picAdapter.resetPICs(photos,photourl);
        picAdapter.refreshAdatper(picGridView);
    }

    private GridView createGridView(){
        GridView gridViev=new GridView(this);
        gridViev.setNumColumns(3);
        gridViev.setHorizontalScrollBarEnabled(false);

        return gridViev;
    }



    protected void resetBitmap(Bitmap bitmap){
        refresh(BitmapUtils.getRoundedCornerBitmap(bitmap,0,4,3,true));
    }
    /**
     * 返回应用时回调方法
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_SFZFM) {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
//                refresh(BitmapUtils.getRoundedCornerBitmap(bitmap,DensityUtil.dip2px(this,2),4,3,true));
//            }
            if (requestCode==PIC_DETAIL_FLAG){
                if (detailPicVO!=null) {
                    picAdapter.getBitmaps().remove(detailPicVO);
                    picAdapter.refreshAdatper(picGridView);
                }
            }
        }
    }

    protected boolean fileIsNull(){

        List<PicVO> bitmaps = picAdapter.getBitmaps();
        if ((bitmaps!=null)&&(bitmaps.size()>1)){
            return false;
        }
        return true;
    }

    protected List<FileVO> getFiles(){
        List<FileVO> files=new ArrayList<>();
        List<PicVO> bitmaps = picAdapter.getBitmaps();
        if ((bitmaps!=null)&&(bitmaps.size()>1)){
            for (int i=0;i<bitmaps.size();i++){
                Bitmap bitmap=bitmaps.get(i).bitmap;
                if (bitmap!=null) {
                    files.add(new FileVO(bitmap, String.valueOf(i)));
                }
            }
        }
        return files;
    }

    protected StringBuffer getoldPics(){

        StringBuffer stringBuffer=new StringBuffer("");

        List<PicVO> bitmaps = picAdapter.getBitmaps();
        for (PicVO picVO:bitmaps){
            if (picVO.qnkey!=null) {
                if (picVO.picurl.contains(picVO.qnkey)) {
                    stringBuffer.append(picVO.qnkey).append(",");
                }
            }
        }

        return stringBuffer;
    }



    private void refresh(Bitmap bitmap){

        List<PicVO> picVOs=picAdapter.getBitmaps();
        picVOs.add(picAdapter.getCount()-1,new PicVO(String.valueOf(System.currentTimeMillis()),bitmap));
        picAdapter.refreshAdatper(picGridView);

    }


    protected abstract int getGridViewContentId();


    class PicVO implements Serializable {
        String picurl;
        String qnkey;
        Bitmap bitmap;

        boolean addFlag;

        public PicVO() {
        }

        public PicVO(String picurl, Bitmap bitmap) {
            this.picurl = picurl;
            this.bitmap = bitmap;
        }
        public PicVO(String picurl, Bitmap bitmap,boolean addFlag) {
            this.picurl = picurl;
            this.bitmap = bitmap;
            this.addFlag=addFlag;
        }
    }

    class PicAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<PicVO> bitmaps;
        private Context context;



        public PicAdapter(Context context, List<PicVO> bitmaps){
            this.layoutInflater= LayoutInflater.from(context);
            if (bitmaps==null){
                bitmaps=new ArrayList<>();
            }
            this.bitmaps=bitmaps;
            this.context=context;
        }

        public void resetPICs(String photos,List<String> photourl){
            if (photos != null) {
                String[] photokeys = photos.split(",");
                for (String photokey : photokeys) {
                    PicVO picVO = new PicVO();
                    picVO.qnkey = photokey;
                    if ((photourl != null) && (photourl.size() > 0)) {
                        for (String pturl : photourl) {
                            if (pturl.contains(photokey)) {
                                picVO.picurl = pturl;
                                break;
                            }
                        }
                    }
                    bitmaps.add(picVO);
                }
            }
            if (bitmaps.size()<6){
                bitmaps.add(new PicVO("",null,true));
            }

            notifyDataSetChanged();

        }

        public List<PicVO> getBitmaps() {
            return bitmaps;
        }

        private int getwidth(){
            int width = DeviceUtils.getDeviceWith(BaseUploadEditPicsActivity.this);

            int jg= DensityUtil.dip2px(BaseUploadEditPicsActivity.this,5);
            picGridView.setVerticalSpacing(jg);
            picGridView.setHorizontalSpacing(jg);

            int num = 3;
            float widthitem=(width-jg*(num-1)-2*DensityUtil.dip2px(BaseUploadEditPicsActivity.this,20))/num;

            return (int)widthitem;
        }

        public void refreshAdatper(GridView gridView) {

            if (this.bitmaps.size()>6){
                this.bitmaps.remove(6);
            }else{
                if (this.bitmaps.size()<6){
                    boolean isIncludeAdd=false;
                    for (PicVO picVO:this.bitmaps){
                        if (picVO.addFlag){
                            isIncludeAdd=true;
                        }
                    }
                    if (!isIncludeAdd){
                        bitmaps.add(new PicVO("",null,true));
                    }
                }
            }

            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            int col=(this.bitmaps.size()/3)+((this.bitmaps.size()%3==0)?0:1);
            params.height = col* ((int)(getwidth()*0.75)+DensityUtil.dip2px(BaseUploadEditPicsActivity.this,5));

            gridView.setLayoutParams(params);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return bitmaps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            ImageView imageView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.activity_campus_info_gridview_item,null);
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.pic_imageview);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            int width = getwidth();
            ViewGroup.LayoutParams layoutParams=viewHolder.imageView.getLayoutParams();
            layoutParams.width=width;
            layoutParams.height=(int)(width*0.75);

            final PicVO picVO = bitmaps.get(position);
            if (picVO.bitmap!=null){
                final Bitmap bitmap=picVO.bitmap;
                viewHolder.imageView.setImageBitmap(bitmap);

                viewHolder.imageView.setAdjustViewBounds(true) ; //自动调整边距
                viewHolder.imageView.setPadding(0,0,0,0);

                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(BaseUploadEditPicsActivity.this,PicDetailActivity.class);
                        detailPicVO=picVO;
                        startActivityForResult(intent,PIC_DETAIL_FLAG);
//                        bitmaps.remove(picVO);
//                        refreshAdatper(picGridView);

                    }
                });
            }else{
                if (picVO.picurl.contains("http")){
                    BaseApplication.application.getDisplayImageOptions(picVO.picurl,viewHolder.imageView);
                    viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(BaseUploadEditPicsActivity.this,PicDetailActivity.class);
                            detailPicVO=picVO;
                            startActivityForResult(intent,PIC_DETAIL_FLAG);

//                            bitmaps.remove(picVO);
//                            refreshAdatper(picGridView);

                        }
                    });
                }else{
                    viewHolder.imageView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_home_operate_curriculum_pic_add));
                    int tb= DensityUtil.dip2px(context,15);
                    int lr= DensityUtil.dip2px(context,30);
                    viewHolder.imageView.setPadding(lr,tb,lr,tb);

                    viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ((bitmaps!=null)&&(bitmaps.size()<7)) {

                                showPicDialog(new Size(4,3));
//                                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent1, REQUEST_SFZFM);
                            }else{
                                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkzp_max_toast));
                            }
                        }
                    });
                }
            }

            return convertView;
        }
    }

}
