package com.ykx.organization.pages.home.operates.curriculum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.TeacherVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程老师列表
 */
public class CurriculumTeacherListActivity extends BaseActivity {


    private ListView teacherListView;
    private TeachersAdapter teachersAdapter;

    private TeacherVO.TeacherVOs teacherVOs;

    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        courseId=getIntent().getStringExtra("courseId");
        teacherVOs= (TeacherVO.TeacherVOs) getIntent().getSerializableExtra("teachers");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_teacher_list);



        initUI();
        loadDatas();
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

                final List<TeacherVO> teachers = teachersAdapter.getCheckEmps();

//                if ((teachers!=null)&&(teachers.size()>0)) {
                    showLoadingView();
                    new OperateServers().saveCurriculumTeacherWithCourseId(courseId, teacherIds(teachers), new HttpCallBack<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            hindLoadingView();
                            result(teachers);
                        }

                        @Override
                        public void onFail(String msg) {
                            hindLoadingView();
                            toastMessage(getResString(R.string.sys_save_fail_toast));
                        }
                    });
//                }else{
//                    result(teachers);
//                }
            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    private List<String> teacherIds(List<TeacherVO> teachers){
        List<String> ids=new ArrayList<>();
        if (teachers!=null) {
            for (TeacherVO teacher : teachers) {
                ids.add(String.valueOf(teacher.getId()));
            }
        }
        return ids;
    }

    private void result(List<TeacherVO> teachers ){
        TeacherVO.TeacherVOs empVOs = TeacherVO.getTeacherVOs();
        empVOs.setDatas(teachers);
        Intent intent=new Intent();
        intent.putExtra("teachers",empVOs);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void loadDatas() {
        new OperateServers().getTeachers(new HttpCallBack<List<TeacherVO>>() {
            @Override
            public void onSuccess(List<TeacherVO> teacherVOKS) {

                //已经选择的教师
                if (teacherVOs!=null){
                    List<TeacherVO> selected = teacherVOs.getDatas();
                    if (selected!=null) {
                        for (TeacherVO teacherVO : selected) {
                            for (TeacherVO teacherVOall : teacherVOKS) {
                                if (teacherVO.getName().equals(teacherVOall.getName())) {
                                    teacherVOall.setCheck(true);
                                    break;
                                }
                            }
                        }
                    }
                }

                teachersAdapter.refresh(teacherVOKS);
            }

            @Override
            public void onFail(String msg) {

            }
        });
//        List<TeacherVO> teacherVOKS=new ArrayList<>();
//        teacherVOKS.add(new TeacherVO(1,"小张老师","",true,"0"));
//        teacherVOKS.add(new TeacherVO(2,"小李老师","",false));
//        teacherVOKS.add(new TeacherVO(3,"小王老师","",true,"0"));

    }

    private void initUI(){
        teacherListView= (ListView) findViewById(R.id.teacher_listview);

        teachersAdapter=new TeachersAdapter(this,null);
        teacherListView.setAdapter(teachersAdapter);

        teacherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeacherVO empVO = (TeacherVO) parent.getItemAtPosition(position);
                if (empVO!=null){
                    empVO.setCheck(!empVO.isCheck());
                    teachersAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.curriculum_activity_add_campus_teacher_title);
    }


    class TeachersAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<TeacherVO> empvos;
        private Context context;

        public TeachersAdapter(Context context,List<TeacherVO> empvos){
            layoutInflater=LayoutInflater.from(context);
            if (empvos==null){
                empvos=new ArrayList<>();
            }
            this.empvos=empvos;
            this.context=context;

        }

        public void refresh(List<TeacherVO> empvos){
            if (empvos==null){
                empvos=new ArrayList<>();
            }
            this.empvos=empvos;
            notifyDataSetChanged();
        }

        public List<TeacherVO> getCheckEmps(){
            List<TeacherVO>  checks=new ArrayList<>();
            if (empvos!=null){
                for (TeacherVO empVO:empvos){
                    if (empVO.isCheck()){
                        checks.add(empVO);
                    }
                }
            }

            return checks;
        }

        @Override
        public int getCount() {
            return empvos.size();
        }

        @Override
        public Object getItem(int position) {
            return empvos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            ImageView photoView;
            ImageView genderView;
            TextView nameView;

            ImageView checkImageView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_operates_teacher_item,null);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.name_textview);
                viewHolder.photoView= (ImageView) convertView.findViewById(R.id.photo_view);
                viewHolder.genderView= (ImageView) convertView.findViewById(R.id.gander_view);
                viewHolder.checkImageView= (ImageView) convertView.findViewById(R.id.check_imageview);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            final TeacherVO empvo=empvos.get(position);

            viewHolder.nameView.setText(empvo.getName());
            BaseApplication.application.getDisplayImageOptions(empvo.getAvatar_url(),viewHolder.photoView);
            if ("1".equals(empvo.getSex())){
                viewHolder.genderView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_male));
            }else{
                viewHolder.genderView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_female));
            }

            if (empvo.isCheck()){
                viewHolder.checkImageView.setVisibility(View.VISIBLE);
            }else{
                viewHolder.checkImageView.setVisibility(View.GONE);

            }

            return convertView;
        }
    }
}
