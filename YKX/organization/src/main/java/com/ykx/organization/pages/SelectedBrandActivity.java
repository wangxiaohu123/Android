package com.ykx.organization.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ykx.baselibs.app.BaseApplication;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.servers.UserServers;
import com.ykx.organization.storage.vo.AgenciesVO;
import com.ykx.organization.storage.vo.LoginReturnInfo;

import java.util.ArrayList;
import java.util.List;


public class SelectedBrandActivity extends BaseActivity {

    private TextView noticesView;
    private ListView brandListView;
    private BrandAdapter brandAdapter;
    private LoginReturnInfo userinfo;
    private String phone,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        phone=getIntent().getStringExtra("phone");
        password=getIntent().getStringExtra("password");
        userinfo= (LoginReturnInfo) getIntent().getSerializableExtra("data");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_brand);

        initUI();
        loadData();
    }

    private void initUI(){
        noticesView= (TextView) findViewById(R.id.notice_view);
        brandListView= (ListView) findViewById(R.id.brand_list_view);
        brandAdapter=new BrandAdapter(this,null);

        brandListView.setAdapter(brandAdapter);

        brandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                final AgenciesVO brandVO = (AgenciesVO) parent.getItemAtPosition(position);

                new UserServers().handleAgence(String.valueOf(brandVO.getAgency_id()), new HttpCallBack<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        PreManager.getInstance().setLoginInfo(phone,password,String.valueOf(brandVO.getAgency_id()),brandVO.getBrand_name());
                        Intent intent=new Intent(SelectedBrandActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFail(String msg) {
                        toastMessage(getResString(R.string.brandmanager_activity_base_info_selected_fail_toast));
                    }
                });

            }
        });
        setNum(0);

    }

    private void loadData(){
        if (userinfo!=null) {
            List<AgenciesVO> brandVOs = userinfo.getAgencies();

//            brandVOs.add(new BrandVO(1, "新东方", "", "校长"));
//            brandVOs.add(new BrandVO(1, "新东方", "", "校长"));
//            brandVOs.add(new BrandVO(1, "新东方", "", "校长"));

            brandAdapter.refresh(brandVOs);
            setNum(brandVOs.size());
        }
    }

    private void setNum(int num){
        String zsrss=String.format(getResources().getString(R.string.selected_brand_activity_notice),String.valueOf(num));
        noticesView.setText(zsrss);
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.selected_brand_activity_title);
    }

    class BrandAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<AgenciesVO> brandVOs;

        public BrandAdapter(Context context,List<AgenciesVO> brandVOs){
            layoutInflater=LayoutInflater.from(context);
            if (brandVOs==null){
                brandVOs=new ArrayList<>();
            }
            this.brandVOs=brandVOs;

        }

        public void refresh(List<AgenciesVO> brandVOs){
            if (brandVOs==null){
                brandVOs=new ArrayList<>();
            }
            this.brandVOs=brandVOs;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return brandVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return brandVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            ImageView logoView;
            TextView nameView;
            TextView roleNameView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_selected_brand_item,null);
                viewHolder.logoView= (ImageView) convertView.findViewById(R.id.logo_view);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.name_textview);
                viewHolder.roleNameView= (TextView) convertView.findViewById(R.id.role_name_textview);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            final AgenciesVO brandVO=brandVOs.get(position);

            viewHolder.nameView.setText(brandVO.getBrand_name());
            viewHolder.roleNameView.setText(changeArrayToString(brandVO.getPosition()));
            BaseApplication.application.getDisplayImageOptions(brandVO.getBrand_logo(),viewHolder.logoView);

            return convertView;
        }


        private String changeArrayToString(String[] roles){
            if ((roles!=null)&&(roles.length>0)){
                StringBuffer stringBuffer=new StringBuffer("");
                for (String role:roles){
                    if (stringBuffer.toString().length()<=0){
                        stringBuffer.append(role);
                    }else{
                        stringBuffer.append(",").append(role);
                    }
                }

                return stringBuffer.toString();
            }

            return "暂无";

        }
    }


}
