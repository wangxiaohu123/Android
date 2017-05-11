package com.ykx.organization.pages.home.operates.curriculum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.QNFileUpAndDownManager;
import com.ykx.baselibs.utils.DateUtil;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.baselibs.views.SelectedButtomView;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.libs.views.TimePickerManager;
import com.ykx.organization.pages.bases.BaseUploadEditPicsActivity;
import com.ykx.organization.pages.home.teachings.tm.CampusSelectedListActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.CurriculumVO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 班课信息
 */

public class CurriculumIfoActivity extends BaseUploadEditPicsActivity {

    private CampusVO campusVO;
    private CurriculumVO editCurriculumVO;

    private int REQUEST_XZXQ=1000;//校区选择
    private int REQUEST_ZSRS=1002;//返回人数
    private int REQUEST_BKMS=1003;//返回班课描述信息
//    private int REQUEST_SFFS=1004;//收费方式
    private int REQUEST_SFTIMES=1005;//次数选择

    private TextView bkssxqView,bklmView,zsrsView,startTimeView,endTimeView,sftypeView,sftimeView,bkjjView;
    private EditText bkNameView,priceView;
    private View bkssxqContentView,timeContentView,dateContentView,buttomView;

    private LinearLayout dataView;

    private TypeVO typeVO;//班课类目

    private TypeVO sffsvo;//收费方式

    private int dataIndex=0;//0->开始时间，1-》结束时间

    private TimePickerManager timePickerManager;

    private  String summary;//班课简介

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        campusVO= (CampusVO) getIntent().getSerializableExtra("campusVO");
        editCurriculumVO= (CurriculumVO) getIntent().getSerializableExtra("editCurriculumVO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_info);

        initUI();
        if (editCurriculumVO==null){
            createView(null,null);
        }else{
            createView(editCurriculumVO.getPhoto(),editCurriculumVO.getPhoto_url());
        }

        resetUI();
    }

    private void resetUI(){

        setUnAbleNull(R.id.add_curriculum_info_ssxq);
        setUnAbleNull(R.id.bk_name_textview);
        setUnAbleNull(R.id.add_curriculum_info_lm);
//        setUnAbleNull(R.id.add_curriculum_info_bkjj);
        setUnAbleNull(R.id.add_curriculum_info_bkzp);

        setUnAbleNull(R.id.add_curriculum_info_zsrs);
        setUnAbleNull(R.id.add_curriculum_info_sffs);
        setUnAbleNull(R.id.add_curriculum_info_bkcs);
        setUnAbleNull(R.id.name_textview);
    }

    private void initUI(){
        dateContentView=findViewById(R.id.date_content_view);
        buttomView=findViewById(R.id.date_content_buttom_view);
        dataView= (LinearLayout) findViewById(R.id.date_view);

        bkssxqContentView=findViewById(R.id.bk_campus_content_view);
        bkssxqView= (TextView) findViewById(R.id.bk_campus_message_view);

        bklmView= (TextView) findViewById(R.id.bklm_view);
        bkNameView= (EditText) findViewById(R.id.bk_input_name_edittext);

        sftypeView= (TextView) findViewById(R.id.sffs_view);
        timeContentView = findViewById(R.id.sffs_times_content_view);
        sftimeView= (TextView) findViewById(R.id.sffs_times_view);
        zsrsView= (TextView) findViewById(R.id.zsrs_view);
        bkjjView= (TextView) findViewById(R.id.bkjj_view);

        startTimeView= (TextView) findViewById(R.id.bk_start_time);
        endTimeView= (TextView) findViewById(R.id.bk_end_time);

        priceView= (EditText) findViewById(R.id.input_name_edittext);

        summary="";
//        startTimeView.setText("2017-04-01");
//        endTimeView.setText("2017-09-01");

        if (editCurriculumVO!=null){
            typeVO=new TypeVO(editCurriculumVO.getCate_name(),editCurriculumVO.getCate());

            bkNameView.setText(editCurriculumVO.getName());
            bklmView.setText(editCurriculumVO.getCate_name());

            sffsvo=SFTypeListActivity.datas.get(editCurriculumVO.getStyle()-1);
            sftypeView.setText(sffsvo.getName());

            summary=editCurriculumVO.getSummary();
            if ((summary!=null)&&(summary.length()>0)){
                bkjjView.setText(getResString(R.string.csys_base_pic_and_message_info));
            }else{
                summary="";
            }
//            bkjjView.setText(editCurriculumVO.getSummary());
            zsrsView.setText(String.valueOf(editCurriculumVO.getPerson()));

            sftimeView.setText(String.valueOf(editCurriculumVO.getTimes()));

            try {
                Double cny = Double.parseDouble(editCurriculumVO.getAmount());//转换成Double
                DecimalFormat df = new DecimalFormat("0.00");//格式化
                priceView.setText(df.format(cny));
            }catch (Exception e){

            }


            startTimeView.setText(editCurriculumVO.getStart_date());
            endTimeView.setText(editCurriculumVO.getEnd_date());

            if (editCurriculumVO.getStyle()==1){
                timeContentView.setVisibility(View.VISIBLE);
            }else{
                timeContentView.setVisibility(View.GONE);
            }
        }

        if (campusVO==null){
            bkssxqContentView.setVisibility(View.VISIBLE);
            if (editCurriculumVO!=null) {
                campusVO=editCurriculumVO.getCampus();
                if (campusVO!=null) {
                    bkssxqView.setText(campusVO.getName());
                }else{
                    getCampusInfo(editCurriculumVO.getCampus_id());
                }
            }
        }else{
            bkssxqContentView.setVisibility(View.GONE);
        }

    }


    @Override
    protected int getGridViewContentId() {
        return R.id.campus_grideview_pics;
    }

    private void getCampusInfo(String campusId){
        new OperateServers().detailCampusDatas(campusId, new HttpCallBack<CampusVO>() {
            @Override
            public void onSuccess(CampusVO data) {
                campusVO=data;
                if (campusVO!=null){
                    bkssxqView.setText(campusVO.getName());
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    public void selectCampusAction(View view){
        Intent intent=new Intent(this, CampusSelectedListActivity.class);

        startActivityForResult(intent,REQUEST_XZXQ);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        typeVO = (TypeVO) intent.getSerializableExtra("typeVO");
        if (typeVO!=null){
            bklmView.setText(typeVO.getName());
        }


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

                submitAction();

            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    private void submitAction(){

        if (bkssxqContentView.getVisibility()==View.VISIBLE){
            if (campusVO==null){
                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkxq_toast));
                return;
            }

        }

        if (bkNameView.getText().toString().length()<=0){
            toastMessage(getResources().getString(R.string.curriculum_activity_add_bkmc_toast));
            return;
        }

        if (typeVO==null){
            toastMessage(getResources().getString(R.string.curriculum_activity_add_bklm_toast));
            return;
        }

        String bkjj=summary;//bkjjView.getText().toString();
//        if (bkjjView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_go_input))){
////            toastMessage(getResources().getString(R.string.curriculum_activity_add_bkjj_toast));
////            return;
//            bkjj="";
//        }


        if (fileIsNull()){
            toastMessage(getResources().getString(R.string.curriculum_activity_add_bkzp_toast));
            return;
        }


        if (zsrsView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_selectd))){
            toastMessage(getResources().getString(R.string.curriculum_activity_add_bkzsrs_toast));
            return;
        }

        if (sffsvo==null){
            toastMessage(getResources().getString(R.string.curriculum_activity_add_bksffs_toast));
            return;
        }

        final StringBuffer sftime=new StringBuffer("");
        if (sffsvo.getCode()==1){
            if (sftimeView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_selectd))){
                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkbccs_toast));
                return;
//                sftime.append("0");
            }else {
                sftime.append(sftimeView.getText().toString());
            }

            if (startTimeView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_start_time))){
                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkkssj_toast));
                return;
            }
        }else{
            sftime.append("0");
        }


        if (priceView.getText().toString().length()<=0){
            toastMessage(getResources().getString(R.string.curriculum_activity_add_bkjg_toast));
            return;

        }

        if (sffsvo.getCode()==2){
            if (startTimeView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_start_time))){
                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkkssj_toast));
                return;
            }
            if (endTimeView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_end_time))){
                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkjssj_toast));
                return;
            }
        }

        if (sffsvo.getCode()==3){
            if (startTimeView.getText().toString().equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_start_time))){
                toastMessage(getResources().getString(R.string.curriculum_activity_add_bkkssj_toast));
                return;
            }
        }



        final String campusid=String.valueOf(campusVO.getId());
        final String name=bkNameView.getText().toString();
        final String cate=String.valueOf(typeVO.getCode());
        final String summary=bkjj;
        final String person=zsrsView.getText().toString();

        int am =0;
        try {
            am = (int)(Double.valueOf(priceView.getText().toString()).doubleValue());
        }catch (Exception e){

        }
        final String amount=String.valueOf(am);
        final String start=startTimeView.getText().toString();
        final String end=endTimeView.getText().toString();

        final String price="20";//计算出来
        final String sfType=String.valueOf(sffsvo.getCode());


        showLoadingView();
        List<FileVO> files=getFiles();
        if (files.size()>0){
            QNFileUpAndDownManager qnFileUpAndDownManager= new QNFileUpAndDownManager();
            qnFileUpAndDownManager.init();
            qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
                @Override
                public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {
                    if (uploadtag) {
                        StringBuffer stringBuffer=new StringBuffer("");
                        for (Map.Entry<String,String> entry : uploadfiles.entrySet()){
                            if (stringBuffer.length()<=0){
                                stringBuffer.append(entry.getValue());
                            }else{
                                stringBuffer.append(",").append(entry.getValue());
                            }
                        }
                        submitData(sftime.toString(),sfType,campusid,name,cate,summary,stringBuffer.toString(),person,amount,start,end,price);
                    }
                }});
        }else{
            submitData(sftime.toString(),sfType,campusid,name,cate,summary,"",person,amount,start,end,price);
        }

    }

    private void submitData(String times,String style,String campusid,String name,String cate,String summary,String photo,String person,String amount,String start,String end,String price){

        String allphotos=getoldPics().append(photo).toString();

        OperateServers operateServers=new OperateServers();
        if (editCurriculumVO==null){//add
            operateServers.addCurriculumInfo(times,style,campusid,name,cate,summary,allphotos,person,amount,start,end,price,new HttpCallBack<CurriculumVO>(){
                @Override
                public void onSuccess(CurriculumVO data) {

                    hindLoadingView();
                    Intent intent=new Intent();
                    intent.putExtra("data",data);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                @Override
                public void onFail(String msg) {
                    hindLoadingView();
                }
            });
        }else{//update
            operateServers.editCurriculumInfo(String.valueOf(editCurriculumVO.getId()),times,style,campusid,name,cate,summary,allphotos,person,amount,start,end,price,new HttpCallBack<CurriculumVO>(){
                @Override
                public void onSuccess(CurriculumVO data) {

                    hindLoadingView();
                    Intent intent=new Intent();
                    intent.putExtra("data",data);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                @Override
                public void onFail(String msg) {
                    hindLoadingView();
                }
            });

        }
    }


    public void selectedCurriculumTypeAction(View view){

        Intent intent=new Intent(this,TypeListMultiselectActivity.class);
        intent.putExtra("action","com.ykx.organization.pages.home.operates.curriculum.curriculumInfoActivity");

        startActivity(intent);
    }


    public void selectedCurriculumInfoAction(View view){

//        String bkjj=bkjjView.getText().toString();
        Intent intent=new Intent(this,CurriculumInfoDesActivity.class);
        intent.putExtra("message",summary);

        startActivityForResult(intent,REQUEST_BKMS);

    }


    public void selectedStudentNumAction(View view){

        Intent intent=new Intent(this,NumListActivity.class);

        startActivityForResult(intent,REQUEST_ZSRS);
    }


    public void startCurriculumTimeAction(View view){
        dataIndex=0;
        dateContentView.setVisibility(View.VISIBLE);
        showDataView(this,dataView);
        addViewAnimation(buttomView,dateContentView);
    }


    public void endCurriculumTimeAction(View view){
        dataIndex=1;
        dateContentView.setVisibility(View.VISIBLE);
        showDataView(this,dataView);
        addViewAnimation(buttomView,dateContentView);
    }

    public void selectedSYTypeCampusAction(View view){

        ArrayList<TypeVO> datas=new ArrayList<>();

        datas.add(new TypeVO("按次",1));
        datas.add(new TypeVO("按时",2));
        datas.add(new TypeVO("基础报名费",3));

        int index=0;
        if (sffsvo!=null){
            for (int i=0;i<datas.size();i++){
                if (datas.get(i).getName().equals(sffsvo.getName())){
                    index=i;
                    break;
                }
            }
        }

        showOption(index,datas,new  SelectedButtomView.SelectedButtomViewListener(){
            @Override
            public void callBack(boolean isTrue, TypeVO typevo) {
                if (isTrue){
                    sffsvo = typevo;
                    if (sffsvo!=null) {
                        sftypeView.setText(sffsvo.getName());
                        if (sffsvo.getCode()==1){
                            timeContentView.setVisibility(View.VISIBLE);
                        }else{
                            timeContentView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

//        Intent intent=new Intent(this,SFTypeListActivity.class);
//
//        startActivityForResult(intent,REQUEST_SFFS);
    }

    public void selectedSYTimesCampusAction(View view){

        Intent intent=new Intent(this,NumListActivity.class);

        startActivityForResult(intent,REQUEST_SFTIMES);
    }

    public void cannelDataAction(View view){

        removeViewAnimation(buttomView,dateContentView);
        dateContentView.setVisibility(View.GONE);
    }

    public void doViewAction(View view){

    }

    public void sureDataAction(View view){

        removeViewAnimation(buttomView,dateContentView);
        dateContentView.setVisibility(View.GONE);
        if (dataIndex==0){
            startTimeView.setText(timePickerManager.getYMD());
        }else if(dataIndex==1){
            Date start=DateUtil.stringToDate(startTimeView.getText().toString(),DateUtil.DATE_FORMAT_TO_DAY);
            Date end=DateUtil.stringToDate(timePickerManager.getYMD(),DateUtil.DATE_FORMAT_TO_DAY);
            if ((DateUtil.getMillis(end)-DateUtil.getMillis(start))>0){
                endTimeView.setText(timePickerManager.getYMD());
            }else{
                toastMessage(getResString(R.string.curriculum_activity_add_campus_info_bk_end_dy_start));
            }
        }
    }


    private void showDataView(Context context,ViewGroup pViewGroup){

        pViewGroup.removeAllViews();
        timePickerManager=new TimePickerManager();
        timePickerManager.setDur(DensityUtil.dip2px(this,10));
        ViewGroup viewGroup= timePickerManager.showTimePickerView(context,TimePickerView.Type.YEAR_MONTH_DAY);
        pViewGroup.addView(viewGroup,new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        if (dataIndex==0){
            String starttime=startTimeView.getText().toString();
            if (!starttime.equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_start_time))){
                timePickerManager.setTime(starttime, DateUtil.DATE_FORMAT_TO_DAY);
            }
        }else if(dataIndex==1){
            String enttime=endTimeView.getText().toString();
            if (!enttime.equals(getResources().getString(R.string.curriculum_activity_add_campus_info_bk_end_time))){
                timePickerManager.setTime(enttime, DateUtil.DATE_FORMAT_TO_DAY);
            }
        }
    }


    /**
     * 返回应用时回调方法
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode==REQUEST_ZSRS){
                int num= data.getIntExtra("num",0);
                zsrsView.setText(String.valueOf(num));
//            }else if(requestCode==REQUEST_SFFS){
//                sffsvo = (TypeVO) data.getSerializableExtra("type");
//                if (sffsvo!=null) {
//                    sftypeView.setText(sffsvo.getName());
//                    if (sffsvo.getCode()==1){
//                        timeContentView.setVisibility(View.VISIBLE);
//                    }else{
//                        timeContentView.setVisibility(View.GONE);
//                    }
//                }
            }else if (requestCode==REQUEST_SFTIMES){
                int num= data.getIntExtra("num",0);
                sftimeView.setText(String.valueOf(num));
            }else if (requestCode==REQUEST_BKMS){
                String message=data.getStringExtra("message");
                if ((message!=null)&&(message.length()>0)){
                    bkjjView.setText(getResString(R.string.csys_base_pic_and_message_info));
                    summary=message;
                }
//                bkjjView.setText(message);
            }else if (requestCode==REQUEST_XZXQ){
                campusVO= (CampusVO) data.getSerializableExtra("campusVO");
                if (campusVO!=null) {
                    bkssxqView.setText(campusVO.getName());
                }
            }
        }
    }


    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.curriculum_activity_add_campus_info_title);
    }


//    @Override
//    protected boolean isCheckBackKey() {
//        return (editCurriculumVO==null)?true:false;
//    }
}
