package com.ykx.organization.pages.home.operates.curriculum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.adapters.CurriculumAdapter;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.home.teachings.tm.CurriculumAllListActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.CampusCoursVO;
import com.ykx.organization.storage.vo.CampusVO;
import com.ykx.organization.storage.vo.CurriculumVO;

import java.util.ArrayList;
import java.util.List;

public class CurriculumListActivity extends BaseActivity {

    private CampusVO campusVO;
    private CurriculumAdapter campusAdapter;

    private LinearLayout contentview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        campusVO= (CampusVO) getIntent().getSerializableExtra("campusVO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_list);

        initUI();
        regiest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void regiest(){
        IntentFilter intentFilter=new IntentFilter();//refresh_time_list_action
        intentFilter.addAction("refresh_curriculum_list_action");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadData();
        }
    };

    private void unRegiest(){
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegiest();
    }

    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.curriculum_activity_listview_title);
    }

    private void initUI(){

        contentview= (LinearLayout) findViewById(R.id.content_view);
        ListView listView= (ListView) findViewById(R.id.campus_listview);

        campusAdapter=new CurriculumAdapter(this,new ArrayList<CurriculumVO>(),campusVO);
        listView.setAdapter(campusAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CurriculumVO curriculumVO = (CurriculumVO) campusAdapter.getItem(position);
//                Intent intent=new Intent(CurriculumListActivity.this,AddCurriculumActivity.class);
//                intent.putExtra("curriculumVO",curriculumVO);
//                intent.putExtra("campusVO",campusVO);
//
//                startActivity(intent);

                Intent intent=new Intent(CurriculumListActivity.this,CurriculumDetailInfoActivity.class);
                intent.putExtra("curriculumVO",curriculumVO);

                startActivity(intent);

            }
        });

        boolean isadd = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.teaching,RoleConstants.teaching_course,RoleConstants.role_add);
        View view=findViewById(R.id.buttom_view);
        if (!isadd){
            view.setVisibility(View.GONE);
        }

    }

    private void loadData(){

        if (campusVO!=null) {

            new OperateServers().getCurriculumWithCampusId(String.valueOf(campusVO.getId()), new HttpCallBack<List<CurriculumVO>>() {
                @Override
                public void onSuccess(List<CurriculumVO> campusVOs) {
                    campusAdapter.setCampusVOs(campusVOs,contentview);
                }

                @Override
                public void onFail(String msg) {

                }
            });
        }
    }


    public void addCampusAction(View view){

        new OperateServers().getCourseCount(new HttpCallBack<CampusCoursVO>() {
            @Override
            public void onSuccess(CampusCoursVO data) {

                if (data!=null){
                    if (data.getCourse_count()>=data.getCourse_max()){
                        toastMessage(getResString(R.string.curriculum_activity_add_max_curriculum_toast));
                        return;
                    }

                    Intent intent=new Intent(CurriculumListActivity.this,AddCurriculumActivity.class);
                    intent.putExtra("campusVO",campusVO);

                    startActivity(intent);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }


}
