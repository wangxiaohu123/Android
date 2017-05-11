package com.ykx.organization.pages.home.operates.curriculum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.CurriculumVO;
import com.ykx.organization.storage.vo.TimeVO;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 课程时间
 */

public class CurriculumTimeActivity extends BaseActivity {

    private ListView timeListView;
    private LinearLayout popView;

    private TimeAdapter timeAdapter;

    private CurriculumVO curriculumVO;

    private List<TimeVO> timeVOs=new ArrayList<>();

    private boolean isbackflag=false;

    private CurriculumTimeView campusview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        curriculumVO= (CurriculumVO) getIntent().getSerializableExtra("curriculumVO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_time);


        initUI();

        loadData();
    }

    private void initUI(){
        timeListView= (ListView) findViewById(R.id.campus_time_listview);
        timeAdapter=new TimeAdapter(this,new ArrayList<TimeVO>());
        timeListView.setAdapter(timeAdapter);
        popView= (LinearLayout) findViewById(R.id.popu_view);

        timeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final TimeVO timeVO = (TimeVO) parent.getItemAtPosition(position);

                final MaterialDialog mMaterialDialog = new MaterialDialog(CurriculumTimeActivity.this);
                mMaterialDialog.setTitle(CurriculumTimeActivity.this.getResources().getString(R.string.sys_dialog_delete_title));
                mMaterialDialog.setMessage(CurriculumTimeActivity.this.getResources().getString(R.string.sys_dialog_delete_content));
                mMaterialDialog.setPositiveButton(CurriculumTimeActivity.this.getResources().getString(R.string.default_exit_window_yes), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new OperateServers().deleteCurriculumTime(String.valueOf(curriculumVO.getId()), String.valueOf(timeVO.getId()), new HttpCallBack<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    isbackflag=true;
                                    timeVOs.remove(timeVO);
                                    timeAdapter.getTimeVOs().remove(timeVO);
                                    timeAdapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onFail(String msg) {
                                }
                            });
                            mMaterialDialog.dismiss();
                        }
                    });
                mMaterialDialog.setNegativeButton(CurriculumTimeActivity.this.getResources().getString(R.string.default_exit_window_cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
                mMaterialDialog.show();

                return false;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                campusview=new CurriculumTimeView(CurriculumTimeActivity.this);
                campusview.setCallBackListener(new CurriculumTimeView.CallBackListener() {
                    @Override
                    public void callBackRemove(View view,boolean iscannel,TimeVO timeVO) {
//                popView.removeView(view);
                        popView.setVisibility(View.GONE);

                        if ((!iscannel)&&(timeVO!=null)){
                            timeVOs.add(timeVO);

                            timeAdapter.getTimeVOs().add(0,timeVO);
                            timeAdapter.notifyDataSetChanged();
                        }
                    }
                });
                campusview.addViewInContentview(popView);
            }
        },500);

        LinearLayout leftView = (LinearLayout) actionBar.getCustomView().findViewById(com.ykx.baselibs.R.id.action_bar_left_view);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBackAction();
            }
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
            public void onClick(final View v) {
                if (v.isEnabled()) {
                    if (timeVOs.size() > 0) {
                        showLoadingView();
                        v.setEnabled(false);
                        new OperateServers().curriculumTimeSet(String.valueOf(curriculumVO.getId()), timeVOs, new HttpCallBack<List<TimeVO>>() {
                            @Override
                            public void onSuccess(List<TimeVO> data) {
//                                v.setEnabled(true);
////                                timeAdapter.clearLocalData(timeVOs);
////
////                                timeAdapter.getTimeVOs().addAll(0,data);
////                                timeAdapter.notifyDataSetChanged();
//                                timeAdapter.refresh(data);
//                                timeVOs.clear();

                                hindLoadingView();
                                isbackflag=false;
                                Intent intent=new Intent();

                                CurriculumVO curriculumVO=new CurriculumVO();
                                curriculumVO.setTimerules(data);

                                intent.putExtra("data",curriculumVO);
                                setResult(RESULT_OK,intent);
                                finish();
                            }

                            @Override
                            public void onFail(String msg) {
                                v.setEnabled(true);

                                hindLoadingView();
                            }
                        });
                    }
                }
            }
        });

        rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

//    @Override
//    protected void callBack() {
//        super.callBack();
//    }


    private void checkBackAction(){
        if (timeVOs.size() > 0) {
            final MaterialDialog mMaterialDialog = new MaterialDialog(CurriculumTimeActivity.this);
            mMaterialDialog.setTitle(CurriculumTimeActivity.this.getResources().getString(R.string.default_exit_window_title));
            mMaterialDialog.setMessage(CurriculumTimeActivity.this.getResources().getString(R.string.sys_save_and_exit_toast));
            mMaterialDialog.setPositiveButton(CurriculumTimeActivity.this.getResources().getString(R.string.default_exit_window_yes), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    resetTimeVO();
                }
            });
            mMaterialDialog.setNegativeButton(CurriculumTimeActivity.this.getResources().getString(R.string.default_exit_window_cancel), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });
            mMaterialDialog.show();
        }else{
            resetTimeVO();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            checkBackAction();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void resetTimeVO(){
        if (isbackflag) {
            Intent intent=new Intent();
            intent.setAction("refresh_time_list_action");

            CurriculumVO curriculumVO=new CurriculumVO();
            curriculumVO.setTimerules(timeAdapter.getTimeVOs());
            intent.putExtra("data",curriculumVO);

            sendBroadcast(intent);

//            Intent intent=new Intent();
//            CurriculumVO curriculumVO=new CurriculumVO();
//            curriculumVO.setTimerules(timeAdapter.getTimeVOs());
//            intent.putExtra("data",curriculumVO);
//            setResult(999,intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addCampusTimeAction(View view){
        // TODO: 2017/3/20  添加时间日前
        if (campusview!=null) {
            campusview.show();
        }
    }



    private void loadData(){

        if (curriculumVO!=null) {
            new OperateServers().getCurriculumSetTimeList(String.valueOf(curriculumVO.getId()), new HttpCallBack<List<TimeVO>>() {
                @Override
                public void onSuccess(List<TimeVO> data) {

                    timeAdapter.refresh(data);
                }

                @Override
                public void onFail(String msg) {

                }
            });
        }

    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.curriculum_activity_add_campus_time_title);
    }


    class TimeAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<TimeVO> timeVOs;
        public TimeAdapter(Context context, List<TimeVO> timeVOs){
            this.layoutInflater=LayoutInflater.from(context);
            if (timeVOs==null){
                timeVOs=new ArrayList<>();
            }
            this.timeVOs=timeVOs;
        }

        public void refresh(List<TimeVO> timeVOs){
            if (timeVOs==null){
                timeVOs=new ArrayList<>();
            }
            this.timeVOs=timeVOs;

            notifyDataSetChanged();
        }

        public void clearLocalData(List<TimeVO> datas){
            for (TimeVO timeVO:datas){
                this.timeVOs.remove(timeVO);
            }

        }

        public List<TimeVO> getTimeVOs() {
            return timeVOs;
        }

        public void setTimeVOs(List<TimeVO> timeVOs) {
            this.timeVOs = timeVOs;
        }

        @Override
        public int getCount() {
            return timeVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return timeVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{

            TextView timeView;
            TextView typeView;

            TextView dateView;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.activity_curriculum_time_item,null);
                viewHolder.timeView= (TextView) convertView.findViewById(R.id.time_textview);
                viewHolder.typeView= (TextView) convertView.findViewById(R.id.type_textview);
                viewHolder.dateView= (TextView) convertView.findViewById(R.id.date_textview);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            TimeVO timeVO=timeVOs.get(position);

            viewHolder.timeView.setText(timeVO.getStart_time()+"-"+timeVO.getEnd_time());
            viewHolder.dateView.setText(timeVO.getStart_date()+" 至 "+timeVO.getEnd_date());


            String[] repeatAts = timeVO.getRepeat_at().split(",");
            String title;
            if (CurriculumTimeView.MONTH.equals(timeVO.getRepeat_mode())){
                title=getDays(repeatAts);
            }else{
                title=getTimes(repeatAts);
            }
            viewHolder.typeView.setText(title);


            return convertView;
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
    }
}
