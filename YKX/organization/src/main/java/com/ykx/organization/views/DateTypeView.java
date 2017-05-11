package com.ykx.organization.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.organization.R;
import com.ykx.organization.storage.vo.PopTimeVO;
import com.ykx.organization.storage.vo.PositionVO;

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
 * Created by 2017/4/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class DateTypeView extends LinearLayout {

    private View contentView;

    private Context context;


    private View weekLineView,monthLineView;
    private TextView weekTextView,monthTextView;

    private List<PopTimeVO> weekPopTimeVOs;
    private List<PopTimeVO> monthPopTimeVOs;

    private GridView gridView;
    private DataAdapter dataAdapter;

    private int selectedTypeFalg=0;//0->按周，1->按月


    public int getSelectedTypeFalg(){
        return selectedTypeFalg;
    }

    public DataAdapter getDataAdapter(){
        return dataAdapter;
    }

    public DateTypeView(Context context) {
        super(context);
        initUI(context);
    }

    public DateTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context){
        this.context=context;
        contentView= LayoutInflater.from(context).inflate(R.layout.view_time_type_item,null);

        weekLineView=contentView.findViewById(R.id.week_buttom_line_view);
        monthLineView=contentView.findViewById(R.id.month_buttom_line_view);
        weekTextView= (TextView) contentView.findViewById(R.id.type_week_view);
        monthTextView= (TextView) contentView.findViewById(R.id.type_month_textview);

        contentView.findViewById(R.id.type_month_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTypeFalg==0){
                    loadMonthData();
                    weekLineView.setVisibility(View.GONE);
                    monthLineView.setVisibility(View.VISIBLE);
                    monthTextView.setTextColor(getResources().getColor(R.color.theme_main_background_color));
                }
            }
        });


        contentView.findViewById(R.id.type_week_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTypeFalg!=0){
                    loadWeekData();
                    weekTextView.setTextColor(getResources().getColor(R.color.theme_main_background_color));
                    weekLineView.setVisibility(View.VISIBLE);
                    monthLineView.setVisibility(View.GONE);
                }
            }
        });

        gridView= (GridView) contentView.findViewById(R.id.data_grideview);
        dataAdapter=new DataAdapter(context,new ArrayList<PopTimeVO>());
        gridView.setAdapter(dataAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (selectedTypeFalg==0){

                    PopTimeVO popTimeVO = weekPopTimeVOs.get(position);
                    if (popTimeVO.isCheckFalg()){
                        popTimeVO.setCheckFalg(false);
                    }else{
                        if ("0".equals(popTimeVO.getValue())){
                            for (PopTimeVO positionVO:weekPopTimeVOs){
                                positionVO.setCheckFalg(false);
                            }
                        }else{
                            weekPopTimeVOs.get(weekPopTimeVOs.size()-1).setCheckFalg(false);
                        }
                        popTimeVO.setCheckFalg(true);
                    }
                    loadWeekData();

                }else{
                    PopTimeVO popTimeVO = monthPopTimeVOs.get(position);
                    if (popTimeVO.isCheckFalg()){
                        popTimeVO.setCheckFalg(false);
                    }else{
                        popTimeVO.setCheckFalg(true);
                    }
                    loadMonthData();
                }

            }
        });
        loadTimeData();
        loadWeekData();


        addView(contentView,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }


    private void loadTimeData(){
        weekPopTimeVOs=new ArrayList<>();

        weekPopTimeVOs.add(new PopTimeVO("周一","1",false));
        weekPopTimeVOs.add(new PopTimeVO("周二","2",false));
        weekPopTimeVOs.add(new PopTimeVO("周三","3",false));
        weekPopTimeVOs.add(new PopTimeVO("周四","4",false));
        weekPopTimeVOs.add(new PopTimeVO("周五","5",false));
        weekPopTimeVOs.add(new PopTimeVO("周六","6",false));
        weekPopTimeVOs.add(new PopTimeVO("周日","7",false));
        weekPopTimeVOs.add(new PopTimeVO("每天","0",false));

        monthPopTimeVOs=new ArrayList<>();
        for (int i=1;i<=31;i++){
            monthPopTimeVOs.add(new PopTimeVO(String.valueOf(i),String.valueOf(i),false));
        }

    }


    private void loadWeekData(){
        selectedTypeFalg=0;
        gridView.setNumColumns(3);
        dataAdapter.setPopTimeVOs(weekPopTimeVOs);
    }

    private void loadMonthData(){
        selectedTypeFalg=1;
        gridView.setNumColumns(5);

        dataAdapter.setPopTimeVOs(monthPopTimeVOs);
    }


    public View fineViewById(int id){
        return contentView.findViewById(id);
    }


    public class DataAdapter extends BaseAdapter {


        private LayoutInflater layoutInflater;
        private List<PopTimeVO> popTimeVOs;
        public DataAdapter(Context context,List<PopTimeVO> popTimeVOs){
            this.layoutInflater=LayoutInflater.from(context);
            if (popTimeVOs==null){
                popTimeVOs=new ArrayList<>();
            }
            this.popTimeVOs=popTimeVOs;
        }


        public void setPopTimeVOs(List<PopTimeVO> popTimeVOs) {
            this.popTimeVOs = popTimeVOs;


            notifyDataSetChanged();
        }

        public String getSelectedData(){
            StringBuffer stringBuffer=new StringBuffer("");
            for (PopTimeVO popTimeVO:popTimeVOs){
                if (popTimeVO.isCheckFalg()){
                    if (stringBuffer.length()<=0){
                        stringBuffer.append(popTimeVO.getValue());
                    }else{
                        stringBuffer.append(",").append(popTimeVO.getValue());
                    }
                }
            }
            return stringBuffer.toString();
        }

        @Override
        public int getCount() {
            return popTimeVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return popTimeVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder{

            ImageView checkView;
            TextView desView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=layoutInflater.inflate(R.layout.view_curriculum_time_item,null);
                viewHolder.desView= (TextView) convertView.findViewById(R.id.campus_time_textview);
                viewHolder.checkView= (ImageView) convertView.findViewById(R.id.campus_time_imagview);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            PopTimeVO popTimeVO=popTimeVOs.get(position);
            viewHolder.desView.setText(popTimeVO.getName());
            if (popTimeVO.isCheckFalg()){

                viewHolder.desView.setTextColor(getResources().getColor(R.color.theme_main_background_color));
                viewHolder.checkView.setVisibility(View.VISIBLE);
            }else{

                viewHolder.desView.setTextColor(getResources().getColor(R.color.default_second_text_color));
                viewHolder.checkView.setVisibility(View.INVISIBLE);
            }


            return convertView;
        }
    }

}
