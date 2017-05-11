package com.ykx.organization.pages.home.teachings.tm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.pages.bases.BaseCampusSelectedListActivity;
import com.ykx.organization.pages.home.operates.campus.AddCampusActivity;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.CampusCoursVO;
import com.ykx.organization.storage.vo.CampusVO;


public class CampusSelectedListActivity extends BaseCampusSelectedListActivity {


    private int return_flag=201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    private void initUI(){
        View buttomview=findViewById(R.id.buttom_view);
        boolean isshow= RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_campus,RoleConstants.role_add);
        if (isshow) {
            buttomview.setVisibility(View.VISIBLE);
            View onclickview = findViewById(R.id.add_campus_view);
            onclickview.setOnClickListener(listener);
        }else{
            buttomview.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new OperateServers().getCampusCoursCount(new HttpCallBack<CampusCoursVO>() {
                @Override
                public void onSuccess(CampusCoursVO data) {
                    if (data!=null){
                        if (data.getCampus_count()>=data.getCampus_max()){
                            toastMessage(getResString(R.string.campus_activity_add_max_campus_toast));
                            return;
                        }
                        Intent intent=new Intent(CampusSelectedListActivity.this,AddCampusActivity.class);
                        intent.putExtra("addCampus",true);
                        boolean isfirst=true;
                        if ((campusAdapter!=null)&&(campusAdapter.getCampusVOs().size()>0)){
                            isfirst=false;
                        }
                        intent.putExtra("firstAddCampus",isfirst);
                        startActivityForResult(intent,return_flag);
                    }else{}
                }
                @Override
                public void onFail(String msg) {}
            });
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK) {
            if (requestCode == return_flag) {
                CampusVO campusVO = (CampusVO) data.getSerializableExtra("CampusVO");
                if (campusVO!=null){

                    if (campusAdapter!=null){
                        campusAdapter.getCampusVOs().add(campusVO);
                        campusAdapter.notifyDataSetChanged();
                    }

                }
            }
        }
    }
}
