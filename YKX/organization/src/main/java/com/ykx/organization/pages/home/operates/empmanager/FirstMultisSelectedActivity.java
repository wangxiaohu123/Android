package com.ykx.organization.pages.home.operates.empmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.baselibs.vo.TypeVO;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BaseTypeListMultiselectActivity;

import java.util.ArrayList;
import java.util.List;

/*********************************************************************************
 * Project Name  : YKX
 * Package       : com.ykx.apps
 * <p>
 * <p>
 * Copyright  优课学技术部  Corporation 2017 All Rights Reserved
 * <p>
 * <p>
 * <Pre>
 * TODO  描述文件做什么的
 * </Pre>
 *
 * @AUTHOR by wangxiaohu
 * Created by 2017/4/13.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class FirstMultisSelectedActivity extends BaseTypeListMultiselectActivity {

    private LinearLayout topView;

    private TextView titleView;
    private GridView selectedView;
    private SelectedAdapter selectedAdapter;

    private List<TypeVO> typeLists=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
    }

    private void initUI(){
        topView= (LinearLayout) findViewById(R.id.first_top_view);
        selectedView= (GridView) findViewById(R.id.selected_type_view);
        titleView= (TextView) findViewById(R.id.selected_title_view);

        selectedAdapter=new SelectedAdapter(this,null);
        selectedView.setAdapter(selectedAdapter);
    }

    @Override
    protected void setRightView(LinearLayout rightContentView) {

            TextView rightview=new TextView(this);
            rightview.setGravity(Gravity.CENTER);
            rightview.setText(getResources().getString(com.ykx.baselibs.R.string.sys_save));
            rightview.setTextSize(15);
            rightview.setTextColor(getResources().getColor(com.ykx.baselibs.R.color.theme_main_background_color));
            rightContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    TypeVO.TypeVOs typeVOs=TypeVO.getTypevos();
                    typeVOs.setDatas(typeLists);
                    intent.putExtra("typeVOS",typeVOs);

                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

            rightContentView.addView(rightview,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        TypeVO.TypeVOs typeVOs= (TypeVO.TypeVOs) intent.getSerializableExtra("typeVO");
        if (typeVOs!=null){
            typeLists.addAll(typeVOs.getDatas());
            selectedAdapter.refresh(typeLists);
            int size=typeLists.size();
            titleView.setText(getResString(R.string.sys_multis_title)+"  "+size+"/"+size);
        }
        if (typeLists.size()>0){
            topView.setVisibility(View.VISIBLE);
        }else{
            topView.setVisibility(View.GONE);
        }


    }

    protected void selectedItemClick(TypeVO typeVO){
        Intent intent=new Intent(this,MultisTypeListMultiselectActivity.class);
        intent.putExtra("typeVO",typeVO);
        intent.putExtra("action","com.ykx.organization.pages.home.operates.empmanager.FirstMultisSelectedActivity");
        intent.putExtra("isMultiselectedFlag",Boolean.valueOf(true));
        startActivity(intent);
    }

    class SelectedAdapter extends BaseAdapter{


        private LayoutInflater layoutInflater;
        private List<TypeVO> typeVOs;

        public SelectedAdapter(Context context,List<TypeVO> typeVOs){
            this.layoutInflater=LayoutInflater.from(context);
            if (typeVOs==null){
                typeVOs=new ArrayList<>();
            }
            this.typeVOs=typeVOs;

        }

        public void refresh(List<TypeVO> typeVOs){
            if (typeVOs==null){
                typeVOs=new ArrayList<>();
            }
            this.typeVOs=typeVOs;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return typeVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return typeVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{
            TextView nameView;
            ImageView closeView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_selected_type_item,null);
                viewHolder.nameView= (TextView) convertView.findViewById(R.id.selected_item_name_view);
                viewHolder.closeView= (ImageView) convertView.findViewById(R.id.selected_item_close_view);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            final TypeVO typeVO=typeVOs.get(position);
            viewHolder.nameView.setText(typeVO.getName());
            viewHolder.closeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    typeVOs.remove(typeVO);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

}
