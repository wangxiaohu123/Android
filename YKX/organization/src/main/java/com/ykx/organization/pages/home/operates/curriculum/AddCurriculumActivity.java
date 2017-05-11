package com.ykx.organization.pages.home.operates.curriculum;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.CurriculumVO;
import com.ykx.organization.storage.vo.TeacherVO;
import com.ykx.organization.storage.vo.TimeVO;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class AddCurriculumActivity extends BaseActivity {

    private final int CAMPUS_INFO_TAG=1001;//班课详情
    private final int CAMPUS_TIME_SETTING_TAG=1002;//班课时间设置
    private final int CURRICULUM_SELECTED_TEACHERS=1003;//选择老师

    private CampusVO campusVO;

    private CurriculumVO editCurriculumVO;

    private View timeView,teacherView,onlineView;
    private LinearLayout infoContentView,timeContentView,teacherListView;
    private TextView priceview;
    private ImageView payimageview;

    private TextView bkinfonameview,bktypenameview,bkdesview,defaultbkinfoview,defaulttimeview,defaultTeacherView;

    private List<TeacherVO> teachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        campusVO= (CampusVO) getIntent().getSerializableExtra("campusVO");
        editCurriculumVO= (CurriculumVO) getIntent().getSerializableExtra("curriculumVO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curriculum);

        initUI();
        regiest();
        resetUI();

        reloadtCurriculumVO();
    }

    private void reloadtCurriculumVO(){
        if (editCurriculumVO!=null){
            new OperateServers().getCurriculum(String.valueOf(editCurriculumVO.getId()), new HttpCallBack<CurriculumVO>() {
                @Override
                public void onSuccess(CurriculumVO data) {
                    if (data!=null) {
                        editCurriculumVO = data;
                        showView();
                    }
                }

                @Override
                public void onFail(String msg) {

                }
            });

        }
    }

    private void resetUI(){

        setUnAbleNull(R.id.add_curriculum_bkxx);
//        setUnAbleNull(R.id.add_curriculum_sksj);
//        setUnAbleNull(R.id.add_curriculum_skls);
    }

    private void regiest(){
        IntentFilter intentFilter=new IntentFilter();//refresh_time_list_action
        intentFilter.addAction("refresh_time_list_action");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private void unRegiest(){
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CurriculumVO curriculumVO = (CurriculumVO) intent.getSerializableExtra("data");
            if (curriculumVO!=null) {
                editCurriculumVO.setTimerules(curriculumVO.getTimerules());
                resetTimeList(editCurriculumVO);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegiest();
    }

    @Override
    protected String titleMessage() {
        if (editCurriculumVO==null) {
            return getResources().getString(R.string.curriculum_activity_add_title);
        }else{
            return getResources().getString(R.string.curriculum_activity_editview_title);
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
                finish();
            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    private void initUI(){
        timeView=findViewById(R.id.bk_time_content_view);
        teacherView=findViewById(R.id.bk_teacher_content_view);
        onlineView=findViewById(R.id.bk_online_content_view);
        priceview= (TextView) findViewById(R.id.bk_price_view);
        payimageview= (ImageView) findViewById(R.id.online_pay_imagevew);
        teacherListView= (LinearLayout) findViewById(R.id.teacher_list_view);

        defaultbkinfoview= (TextView) findViewById(R.id.default_null_textview);
        bkinfonameview= (TextView) findViewById(R.id.bk_name_textview);
        bktypenameview= (TextView) findViewById(R.id.bk_type_name_textview);
        bkdesview= (TextView) findViewById(R.id.bk_des_textview);


        infoContentView= (LinearLayout) findViewById(R.id.campus_info_content_view);
        timeContentView= (LinearLayout) findViewById(R.id.time_content_view);
        defaulttimeview= (TextView) findViewById(R.id.default_time_textview);
        defaultTeacherView= (TextView) findViewById(R.id.teacher_list_default_view);

        if (campusVO==null){
            // TODO: 2017/3/23
        }



        showView();
    }

    private void showView(){
        if (editCurriculumVO!=null){
            timeView.setVisibility(View.VISIBLE);
            teacherView.setVisibility(View.VISIBLE);
            onlineView.setVisibility(View.VISIBLE);

            priceview.setText(String.valueOf(editCurriculumVO.getPrice()));
            if (editCurriculumVO.getOnline()==1){
                payimageview.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_switch_on));
            }else{
                payimageview.setImageDrawable(BitmapUtils.getDrawable(this,R.drawable.svg_switch_off));
            }

            bkinfonameview.setText(getResources().getString(R.string.curriculum_activity_add_info_bkmc)+editCurriculumVO.getName());
            bktypenameview.setText(getResources().getString(R.string.curriculum_activity_add_info_kmlb)+editCurriculumVO.getCate_text());
            bkdesview.setText(getResources().getString(R.string.curriculum_activity_add_info_bkjj)+editCurriculumVO.getSummary());
            defaultbkinfoview.setVisibility(View.GONE);

            infoContentView.setVisibility(View.VISIBLE);
            teachers=editCurriculumVO.getTeachers();
        }else{
            infoContentView.setVisibility(View.GONE);
        }
        resetTimeList(editCurriculumVO);
        refreshSelectedTeacher();
    }

    public void showTimeListAction(View view){
        setCurriculumTimeAction(view);
    }

    private void resetTimeList(CurriculumVO editCurriculumVO){
        if (editCurriculumVO!=null) {
            List<TimeVO> timeVOs=editCurriculumVO.getTimerules();
            if ((timeVOs!=null)&&(timeVOs.size()>0)){
                defaulttimeview.setVisibility(View.GONE);
                timeContentView.setVisibility(View.VISIBLE);
                TimeVO timeVO=timeVOs.get(0);

                TextView timeView= (TextView) timeContentView.findViewById(R.id.time_textview);
                TextView typeView= (TextView) timeContentView.findViewById(R.id.type_textview);
                TextView dateView= (TextView) timeContentView.findViewById(R.id.date_textview);

                timeView.setText(timeVO.getStart_time()+"-"+timeVO.getEnd_time());
                dateView.setText(timeVO.getStart_date()+" 至 "+timeVO.getEnd_date());

                String[] repeatAts = timeVO.getRepeat_at().split(",");
                String title;
                if (CurriculumTimeView.MONTH.equals(timeVO.getRepeat_mode())){
                    title=getDays(repeatAts);
                }else{
                    title=getTimes(repeatAts);
                }
                typeView.setText(title);
            }else{
                timeContentView.setVisibility(View.GONE);
                defaulttimeview.setVisibility(View.VISIBLE);
            }

            //// TODO: 2017/3/29
//            timeContentView.removeAllViews();
//            List<TimeVO> timeVOs=editCurriculumVO.getTimerules();
//            if (timeVOs!=null){
//                ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,25));
//                for (TimeVO timeVO:timeVOs){
//                    TextView textView=new TextView(this);
//                    textView.setTextColor(getResources().getColor(R.color.default_second_text_color));
//                    textView.setTextSize(12);
//                    textView.setGravity(Gravity.CENTER_VERTICAL);
//                    textView.setText(timeVO.getStart_time()+"-"+timeVO.getEnd_time());
//
//                    timeContentView.addView(textView,layoutParams);
//                }
//            }
        }else{
            timeContentView.setVisibility(View.GONE);
            defaulttimeview.setVisibility(View.VISIBLE);
        }

    }

    public String getDays(String[] repeatAts){
        StringBuffer stringBuffer=new StringBuffer("");
        String hz="号";
        for (String name : repeatAts){
            if (stringBuffer.length()>0){
                stringBuffer.append(",");
            }
            stringBuffer.append(name).append(hz);
        }

        return stringBuffer.toString();
    }

    public String getTimes(String[] repeatAts){
        StringBuffer stringBuffer=new StringBuffer("");
        String[] times=new String[]{"每天","周一","周二","周三","周四","周五","周六","周日"};
        for (String index : repeatAts){
            int num=Integer.valueOf(index);

            String name;
            if (num<times.length){
                name=times[num];
            }else{
                name="暂无";
            }
            if (stringBuffer.length()>0){
                stringBuffer.append(",");
            }
            stringBuffer.append(name);
        }
        return stringBuffer.toString();
    }

    public void setCurriculumInfoAction(View view){

        Intent intent=new Intent(this,CurriculumIfoActivity.class);
        intent.putExtra("campusVO",campusVO);
        intent.putExtra("editCurriculumVO",editCurriculumVO);


        startActivityForResult(intent,CAMPUS_INFO_TAG);
    }


    public void setCurriculumTimeAction(View view){

        Intent intent=new Intent(this,CurriculumTimeActivity.class);

        intent.putExtra("curriculumVO",editCurriculumVO);

        startActivityForResult(intent,CAMPUS_TIME_SETTING_TAG);

    }


    public void setCurriculumTeacherAction(View view){

        Intent intent=new Intent(this,CurriculumTeacherListActivity.class);

        TeacherVO.TeacherVOs teachervos=TeacherVO.getTeacherVOs();
        teachervos.setDatas(teachers);
        intent.putExtra("teachers",teachervos);
        if (editCurriculumVO!=null) {
            intent.putExtra("courseId", String.valueOf(editCurriculumVO.getId()));
        }

        startActivityForResult(intent,CURRICULUM_SELECTED_TEACHERS);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==CAMPUS_INFO_TAG){
                editCurriculumVO = (CurriculumVO) data.getSerializableExtra("data");
                showView();
                sendBroadcast(new Intent("refresh_curriculum_list_action"));
            }else if(requestCode==CAMPUS_TIME_SETTING_TAG){
                CurriculumVO curriculumVO = (CurriculumVO) data.getSerializableExtra("data");
                if (curriculumVO!=null) {
                    editCurriculumVO.setTimerules(curriculumVO.getTimerules());
                    showView();
                }
            }else if(requestCode==CURRICULUM_SELECTED_TEACHERS){
                TeacherVO.TeacherVOs teachervo = (TeacherVO.TeacherVOs) data.getSerializableExtra("teachers");
                if (teachervo!=null){
                    teachers=teachervo.getDatas();
                    refreshSelectedTeacher();
                }
            }
        }
    }

    private void refreshSelectedTeacher(){
        if ((teachers!=null)&&(teachers.size()>0)){
            teacherListView.setVisibility(View.VISIBLE);
            defaultTeacherView.setVisibility(View.GONE);
            teacherListView.removeAllViews();
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LayoutInflater layoutInflater=LayoutInflater.from(this);
            for (TeacherVO teacherVO:teachers){
                View itemview=layoutInflater.inflate(R.layout.view_teacher_item,null);
                ImageView headerview= (ImageView) itemview.findViewById(R.id.teacher_header_photo_image);
                TextView nameView= (TextView) itemview.findViewById(R.id.teacher_name);
                ImageView stateView= (ImageView) itemview.findViewById(R.id.state_imageView);
                nameView.setText(teacherVO.getName());
                BaseApplication.application.getDisplayImageOptions(teacherVO.getAvatar_url(),headerview);
                if (teacherVO.getState()){
                    stateView.setVisibility(View.VISIBLE);
                }else{
                    stateView.setVisibility(View.GONE);
                }
                teacherListView.addView(itemview,layoutParams);
            }
        }else{
            teacherListView.setVisibility(View.GONE);
            defaultTeacherView.setVisibility(View.VISIBLE);
        }
    }

    public void setCampusOnLineAction(final View iv){

        iv.setEnabled(false);
        if (editCurriculumVO!=null) {

            new OperateServers().curriculumSuportOnlinePay(String.valueOf(editCurriculumVO.getId()), new HttpCallBack<String>() {
                @Override
                public void onSuccess(String data) {
                    if (iv instanceof ImageView) {
                        ImageView view = (ImageView) iv;
                        if (view.getTag() == null) {
                            view.setImageDrawable(BitmapUtils.getDrawable(AddCurriculumActivity.this, R.drawable.svg_switch_off));
                            view.setTag("");
                        } else {
                            view.setImageDrawable(BitmapUtils.getDrawable(AddCurriculumActivity.this, R.drawable.svg_switch_on));
                            view.setTag(null);
                        }
                    }

                    iv.setEnabled(true);
                }

                @Override
                public void onFail(String msg) {

                    iv.setEnabled(true);
                }
            });
        }

    }

}
