package com.ykx.organization.pages.bases;

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

import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.CampusVO;

import java.util.ArrayList;
import java.util.List;

public class BaseCampusSelectedListActivity extends BaseActivity {

    protected CampusAdapter campusAdapter;

    private boolean isMultiselectFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMultiselectFlag=isMultiselectFlag();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_selected_list);

        initUI();
        loadData();
    }


    @Override
    protected void setRightView(LinearLayout rightContentView) {

        if (isMultiselectFlag) {
            TextView rightview = new TextView(this);
            rightview.setGravity(Gravity.CENTER);
            rightview.setText(getResources().getString(R.string.curriculum_activity_add_title_save));
            rightview.setTextSize(15);
            rightview.setTextColor(getResources().getColor(R.color.theme_main_background_color));
            rightContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();

                    CampusVO.CampusList campusVO = CampusVO.newCampusList();
                    campusVO.setDatas(campusAdapter.getSelectedCampusVOs());

                    intent.putExtra("campusVOs",campusVO);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            rightContentView.addView(rightview, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }

    protected boolean isMultiselectFlag(){
        return false;
    }

    private void initUI(){
        ListView listView = (ListView) findViewById(R.id.curriculum_listview);

        campusAdapter=new CampusAdapter(this,new ArrayList<CampusVO>());
        listView.setAdapter(campusAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CampusVO campusVO = (CampusVO) campusAdapter.getItem(position);
                if (isMultiselectFlag){
                    campusVO.setSelected(!campusVO.isSelected());
                    campusAdapter.notifyDataSetChanged();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("campusVO", campusVO);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadData(){

        new OperateServers().getAllCampusDatas(new HttpCallBack<List<CampusVO>>() {
            @Override
            public void onSuccess(List<CampusVO>  data) {
                campusAdapter.setCampusVOs(resetCampusVO(data));
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    protected List<CampusVO> resetCampusVO(List<CampusVO>  data){

        return data;
    }


    @Override
    protected String titleMessage() {
        return getResources().getString(R.string.curriculum_activity_list_title);
    }


    public class CampusAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<CampusVO> campusVOs;

        public CampusAdapter(Context context,List<CampusVO> campusVOs){
            this.layoutInflater=LayoutInflater.from(context);
            if (campusVOs==null){
                campusVOs=new ArrayList<>();
            }
            this.campusVOs=campusVOs;
        }

        public List<CampusVO> getCampusVOs() {
            if (campusVOs==null){
                campusVOs=new ArrayList<>();
            }
            return campusVOs;
        }

        public List<CampusVO> getSelectedCampusVOs() {
            if (campusVOs!=null){
                ArrayList returndatas=new ArrayList<>();
                for (CampusVO campusVO:campusVOs){
                    if (campusVO.isSelected()){
                        returndatas.add(campusVO);
                    }
                }
                return returndatas;
            }

            return new ArrayList<>();
        }

        public void setCampusVOs(List<CampusVO> campusVOs) {
            this.campusVOs = campusVOs;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return campusVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return campusVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{

            TextView nameView;
            TextView addressView;

            ImageView imageView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.activity_campus_teaching_list_item,null);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.xq_name_textview);
                viewHolder.addressView= (TextView) convertView.findViewById(R.id.xq_address_textview);
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.selected_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            final CampusVO campusVO=campusVOs.get(position);
            viewHolder.nameView.setText(campusVO.getName());
            viewHolder.addressView.setText(campusVO.getAddress());
            if (campusVO.isSelected()){
                viewHolder.imageView.setVisibility(View.VISIBLE);
            }else{
                viewHolder.imageView.setVisibility(View.GONE);
            }

            return convertView;
        }
    }

}
