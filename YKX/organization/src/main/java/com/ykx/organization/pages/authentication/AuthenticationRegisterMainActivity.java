package com.ykx.organization.pages.authentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykx.baselibs.commons.Constant;
import com.ykx.baselibs.libs.sortlistview.SortModel;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.vo.SortVO;
import com.ykx.organization.R;
import com.ykx.organization.libs.views.SamplePicView;
import com.ykx.organization.pages.bases.BasePicActivity;
import com.ykx.organization.pages.bases.SortListActivity;
import com.ykx.organization.servers.LocalDataServer;

public class AuthenticationRegisterMainActivity extends BasePicActivity {


    private int picSelectedIndex=0;
    private int REQUEST_BXXKZ=1001;//办学许可证tag
    private int REQUEST_SFZZM=2001;//身份证正面tag
    private int REQUEST_SFZFM=2002;//身份证反面tag

    private ImageView xkzImageView;
    private ImageView zmImageView;
    private ImageView fmImageView;

    private ImageView xkzIV,zmIV,fmIV;

    public static Bitmap xkzBitmap;
    public static Bitmap sfzzmBitmap;
    public static Bitmap sfzfmBitmap;

    private int tag=0;

    private EditText inputName;
    private TextView addressView;

    private TextView nameTextView;
    private TextView xkzTextView;
    private TextView frontTextView;
    private TextView backTextView;
    private TextView adcodeTextView;

    private SortModel sortModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tag=getIntent().getIntExtra("register_type",0);//1->办学许可登记,2->营业执照登记
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_register_main);

        initUI();

        resetUI();
    }

    private void resetUI(){

        setUnAbleNull(R.id.name_textview);
        setUnAbleNull(R.id.xkz_textview);
        setUnAbleNull(R.id.persion_front_textview);
        setUnAbleNull(R.id.persion_back_textview);
        setUnAbleNull(R.id.adcode_textview);
    }

    private void initUI(){
        xkzImageView= (ImageView) findViewById(R.id.showpicimageview);
        zmImageView=((ImageView)findViewById(R.id.take_phone_zm_imageview));
        fmImageView=((ImageView)findViewById(R.id.take_phone_fm_imageview));

        xkzIV= (ImageView) findViewById(R.id.xkz_imageview);
        zmIV= (ImageView) findViewById(R.id.zm_imageview);
        fmIV= (ImageView) findViewById(R.id.fm_imageview);

        inputName= (EditText) findViewById(R.id.input_name_edittext);
        addressView= (TextView) findViewById(R.id.address_edittext);

        nameTextView= (TextView) findViewById(R.id.name_textview);
        xkzTextView= (TextView) findViewById(R.id.xkz_textview);
        frontTextView= (TextView) findViewById(R.id.persion_front_textview);
        backTextView= (TextView) findViewById(R.id.persion_back_textview);
        adcodeTextView= (TextView) findViewById(R.id.adcode_textview);

        if (tag==1){
            nameTextView.setText(getResources().getString(R.string.authentication_register_main_bxxk_xxmc));
            inputName.setHint(getResources().getString(R.string.authentication_register_main_bxxk_xxmc_hint));
            xkzTextView.setText(getResources().getString(R.string.authentication_register_main_bxxk_bxxkz));
            frontTextView.setText(getResources().getString(R.string.authentication_register_main_bxxk_fzrsfz_zm));
            backTextView.setText(getResources().getString(R.string.authentication_register_main_bxxk_fzrsfz_fm));
            adcodeTextView.setText(getResources().getString(R.string.authentication_register_main_bxxk_szcs));
            addressView.setHint(getResources().getString(R.string.authentication_register_main_bxxk_szcs_hint));
        }else if(tag==2){
            nameTextView.setText(getResources().getString(R.string.authentication_register_main_yyzz_xxmc));
            inputName.setHint(getResources().getString(R.string.authentication_register_main_yyzz_xxmc_hint));
            xkzTextView.setText(getResources().getString(R.string.authentication_register_main_yyzz_bxxkz));
            frontTextView.setText(getResources().getString(R.string.authentication_register_main_yyzz_fzrsfz_zm));
            backTextView.setText(getResources().getString(R.string.authentication_register_main_yyzz_fzrsfz_fm));
            adcodeTextView.setText(getResources().getString(R.string.authentication_register_main_yyzz_szcs));
            addressView.setHint(getResources().getString(R.string.authentication_register_main_yyzz_szcs_hint));
        }
    }

    @Override
    protected String titleMessage() {
        if (tag==1){
            return getResources().getString(R.string.authentication_register_main_bxxk_title);
        }else if(tag==2){
            return getResources().getString(R.string.authentication_register_main_yyzz_title);

        }
        return super.titleMessage();
    }

    private void showBXXKZAction(){
        int resid;
        String firstmessage="";
        if (tag==1){
            resid=R.mipmap.sample_bxxkz;
            firstmessage=getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_bxxkzj);
        }else{
            resid=R.mipmap.sample_yyzz;
            firstmessage=getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_yyzz);
        }
        SamplePicView.newInstance(this).showView(firstmessage, getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_second), resid, new SamplePicView.TakePicListener() {
            @Override
            public void takePicAction() {
                showPicDialog(false);
                picSelectedIndex=REQUEST_BXXKZ;
            }
        });
    }

    private void showSFZZMction(){
        int resid=R.mipmap.sample_sfzzm;
        SamplePicView.newInstance(this).showView(getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_sfzzm), getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_second), resid, new SamplePicView.TakePicListener() {
            @Override
            public void takePicAction() {
                showPicDialog(false);
                picSelectedIndex=REQUEST_SFZZM;
            }
        });
    }

    private void showSFZFMction(){

        int resid=R.mipmap.sample_sfzfm;
        SamplePicView.newInstance(this).showView(getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_sfzbm), getResString(R.string.authentication_register_main_bxxk_sample_bxxkz_second), resid, new SamplePicView.TakePicListener() {
            @Override
            public void takePicAction() {
                showPicDialog(false);
                picSelectedIndex=REQUEST_SFZFM;
            }
        });
    }



    private String getMessage(int messageId1,int messageId2){
        if (tag==1){
           return getResources().getString(messageId1);
        }else if(tag==2){
            return getResources().getString(messageId2);
        }
        return getResources().getString(messageId1);
    }

    public void nextAction(View view){

        String inputname=inputName.getText().toString();
        if (inputname.length()<=0){
            toastMessage(getMessage(R.string.authentication_register_main_xxmc_toast,R.string.authentication_register_main_qymc_toast));
            return;
        }

        if (xkzBitmap==null){
            toastMessage(getMessage(R.string.authentication_register_main_xxbxxk_toast,R.string.authentication_register_main_qyyyzffb_toast));
            return;
        }
        if (sfzzmBitmap==null){
            toastMessage(getMessage(R.string.authentication_register_main_xxfzrsfzzm_toast,R.string.authentication_register_main_qyfrsfzzm_toast));
            return;
        }
        if (sfzfmBitmap==null){
            toastMessage(getMessage(R.string.authentication_register_main_xxfzrsfzfm_toast,R.string.authentication_register_main_qyfrsfzfm_toast));
            return;
        }

        int addressCode=-1;

        if (sortModel!=null){
            addressCode=sortModel.getCode();
        }else{
            toastMessage(getResources().getString(R.string.authentication_register_main_city_toast));
            return;
        }

        Intent intent=new Intent(this,AuthenticationRegisterNextActivity.class);
        intent.putExtra("register_type",tag);
//
//        intent.putExtra("xkzBitmap",xkzBitmap);
//        intent.putExtra("sfzzmBitmap",sfzzmBitmap);
//        intent.putExtra("sfzfmBitmap",sfzfmBitmap);
        intent.putExtra("inputname",inputname);
        intent.putExtra("address",String.valueOf(addressCode));


        startActivity(intent);

    }

    public void takenBZXKZAction(View view){
        showBXXKZAction();
//        showPicDialog(new Size(4,3));
//        picSelectedIndex=REQUEST_BXXKZ;

//        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent1, REQUEST_BXXKZ);
    }

    public void takenZMAction(View view){
        showSFZZMction();
//        showPicDialog(new Size(4,3));
//        picSelectedIndex=REQUEST_SFZZM;

//        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent1, REQUEST_SFZZM);
    }


    public void takenFMAction(View view){
        showSFZFMction();
//        showPicDialog(new Size(4,3));
//        picSelectedIndex=REQUEST_SFZFM;

//        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent1, REQUEST_SFZFM);
    }


    protected void resetBitmap(Bitmap bitmap){
        BitmapUtils.writeBitmapToFile(bitmap, Constant.OTHERS_PATH,"test");


        if (picSelectedIndex==REQUEST_BXXKZ){
            xkzImageView.setVisibility(View.GONE);
            xkzIV.setImageBitmap(bitmap);
            xkzIV.setVisibility(View.VISIBLE);
            xkzBitmap=bitmap;
        }else if (picSelectedIndex==REQUEST_SFZZM){
            zmImageView.setVisibility(View.GONE);
            zmIV.setImageBitmap(bitmap);
            zmIV.setVisibility(View.VISIBLE);
            sfzzmBitmap=bitmap;
        }else if (picSelectedIndex==REQUEST_SFZFM){
            fmImageView.setVisibility(View.GONE);
            fmIV.setImageBitmap(bitmap);
            fmIV.setVisibility(View.VISIBLE);
            sfzfmBitmap=bitmap;
        }
    }


//    /**
//     * 返回应用时回调方法
//     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_BXXKZ) {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
//                xkzIV.setImageBitmap(bitmap);
//                xkzIV.setVisibility(View.VISIBLE);
//                xkzBitmap=bitmap;
//            }else if (requestCode==REQUEST_SFZZM){
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
//                zmIV.setImageBitmap(bitmap);
//                zmIV.setVisibility(View.VISIBLE);
//                sfzzmBitmap=bitmap;
//            }else if (requestCode==REQUEST_SFZFM){
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
//                fmIV.setImageBitmap(bitmap);
//                fmIV.setVisibility(View.VISIBLE);
//                sfzfmBitmap=bitmap;
//            }
//        }
//    }


    public void showProviceAction(final View view){
        view.setEnabled(false);
        new LocalDataServer().getAreaCodes(this, new LocalDataServer.ReturnCallBack<LocalDataServer.RetrunData>() {
            @Override
            public void callBack(LocalDataServer.RetrunData object) {

                view.setEnabled(true);
                Intent intent=new Intent(AuthenticationRegisterMainActivity.this, SortListActivity.class);
                intent.putExtra("actionname","com.ykx.organization.page.authentication.proviceactivity");
                SortVO sortVO=new SortVO();
                sortVO.setAreaCodes(object.getAreaCodeList());
                sortVO.setTitleName("省列表");

                intent.putExtra("datas",sortVO);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        sortModel= (SortModel) intent.getSerializableExtra("sortModel");
        if (sortModel!=null){
            addressView.setText(sortModel.getName());
        }

    }
}
