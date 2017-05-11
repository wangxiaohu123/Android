package com.ykx.organization.pages.home.operates.empmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.organization.R;
import com.ykx.organization.constants.RoleConstants;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.caches.MMCacheUtils;
import com.ykx.organization.storage.vo.EmpVO;

import java.util.ArrayList;
import java.util.List;

public class EmpManagerMainActivity extends BaseActivity {

    private ListView empListView;

    private EmpsAdapter empsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_manager_main);

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();
    }

    private void loadDatas() {
        new OperateServers().getEmpsList(new HttpCallBack<List<EmpVO>>() {
            @Override
            public void onSuccess(List<EmpVO> data) {
               empsAdapter.refresh(data);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initUI(){
        empListView= (ListView) findViewById(R.id.emps_listview);

        empsAdapter=new EmpsAdapter(this,null);
        empListView.setAdapter(empsAdapter);


        boolean isadd = RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_staff,RoleConstants.role_add);
        View view=findViewById(R.id.buttom_view);
        if (!isadd){
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.emp_manager_activity_main_title);
    }


    public void addEmpAction(View view){

        Intent intent=new Intent(this,AddEmpActivity.class);

        startActivity(intent);

    }

    public void invitedEmpAction(View view){

        Intent intent=new Intent(this,InvitedEmpActivity.class);

        startActivity(intent);
    }

    class EmpsAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<EmpVO> empvos;
        private Context context;


        private boolean editEnable=true;
        private boolean dropEnable=true;

        public EmpsAdapter(Context context,List<EmpVO> empvos){
            layoutInflater=LayoutInflater.from(context);
            if (empvos==null){
                empvos=new ArrayList<>();
            }
            this.empvos=empvos;
            this.context=context;
            getrole();
        }

        private void getrole(){
            editEnable= RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_campus,RoleConstants.role_edit);
            dropEnable=RoleConstants.isEnable(MMCacheUtils.getUserInfoVO().getPower(),RoleConstants.operation,RoleConstants.operation_campus,RoleConstants.role_drop);
        }

        public void refresh(List<EmpVO> empvos){
            if (empvos==null){
                empvos=new ArrayList<>();
            }
            this.empvos=empvos;
            notifyDataSetChanged();
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
            TextView jobsView;
            TextView phoneView;

            ImageView editView;
            View lineView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_operates_emp_manager_emp,null);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.name_textview);
                viewHolder.jobsView= (TextView) convertView.findViewById(R.id.jobs_textview);
                viewHolder.phoneView= (TextView) convertView.findViewById(R.id.phone_textview);
                viewHolder.photoView= (ImageView) convertView.findViewById(R.id.photo_view);
                viewHolder.genderView= (ImageView) convertView.findViewById(R.id.gander_view);
                viewHolder.editView= (ImageView) convertView.findViewById(R.id.edit_view);
                viewHolder.lineView=convertView.findViewById(R.id.line_view);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            final EmpVO empvo=empvos.get(position);
            viewHolder.nameView.setText(empvo.getName());
            viewHolder.jobsView.setText(empvo.getJobs());
            viewHolder.phoneView.setText(empvo.getPhone());
            BaseApplication.application.getDisplayImageOptions(empvo.getAvatar_url(),viewHolder.photoView);
            if ("1".equals(empvo.getSex())){
                viewHolder.genderView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_male));
            }else{
                viewHolder.genderView.setImageDrawable(BitmapUtils.getDrawable(context,R.drawable.svg_female));
            }

            if ((empvos.size()-1)==position){
                viewHolder.lineView.setVisibility(View.VISIBLE);
            }else{
                viewHolder.lineView.setVisibility(View.GONE);
            }

            if ((!editEnable)&&(!dropEnable)){
                viewHolder.editView.setVisibility(View.GONE);
            }

            viewHolder.editView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View view = LayoutInflater.from(context).inflate(R.layout.view_popup_delete_edit,null);
                    final BaseActivity baseActivity= (BaseActivity) context;
                    final PopupWindow popupWindow = baseActivity.showPopupWindow(v,view,R.color.theme_transparent_style);

                    View deleteview = view.findViewById(R.id.delete_view);
                    if (!dropEnable){
                        deleteview.setVisibility(View.GONE);
                    }
                    deleteview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            popupWindow.dismiss();
                            baseActivity.showDeleteDialog(new BaseActivity.SelectedListener() {
                                @Override
                                public void callBack(boolean flag) {
                                    if (flag) {
                                        new OperateServers().deleteEmpWithEmpId(String.valueOf(empvo.getId()), new HttpCallBack<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                empvos.remove(empvo);
                                                refresh(empvos);
                                            }

                                            @Override
                                            public void onFail(String msg) {
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });


                    View editview=view.findViewById(R.id.edit_view);
                    if (!editEnable){
                        editview.setVisibility(View.GONE);
                    }
                    editview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            Intent intent=new Intent(context,AddEmpActivity.class);
                            intent.putExtra("empVO",empvo);

                            context.startActivity(intent);
                        }
                    });

                }
            });


            return convertView;
        }
    }

}
