package com.ykx.organization.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykx.baselibs.utils.BitmapUtils;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.storage.vo.AppInfoVO;

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
 * Created by 2017/4/27.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class AppAdapter extends BaseAdapter {


    public interface CallBackListener{
        void callBack(List<AppInfoVO> infoVOs);
    }

    private List<AppInfoVO> appInfoVOs;
    private LayoutInflater layoutInflater;

    private Context context;

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public AppAdapter(List<AppInfoVO> appInfoVOs, Context context){
        this.layoutInflater=LayoutInflater.from(context);
        if (appInfoVOs==null){
            appInfoVOs=new ArrayList<>();
        }
        this.appInfoVOs=appInfoVOs;
        this.context=context;
    }

    public void refresh(List<AppInfoVO> appInfoVOs, GridView gridView){
        if (appInfoVOs==null){
            appInfoVOs=new ArrayList<>();
        }
        this.appInfoVOs=getCheckInfo(appInfoVOs);
        int hang=(this.appInfoVOs.size()/3)+((this.appInfoVOs.size()%3!=0)?1:0);
        gridView.getLayoutParams().height=hang* DensityUtil.dip2px(context,124);
        notifyDataSetChanged();

        if (callBackListener!=null){
            callBackListener.callBack(this.appInfoVOs);
        }
    }

    private List<AppInfoVO> getCheckInfo(List<AppInfoVO> appInfoVOs){
        List<AppInfoVO> nAppInfoVOs=new ArrayList<>();
        if (appInfoVOs!=null){
            for (AppInfoVO appInfoVO:appInfoVOs){
                if (appInfoVO.isShow()){
                    nAppInfoVOs.add(appInfoVO);
                }
            }
        }
        return nAppInfoVOs;
    }

    @Override
    public int getCount() {
        return appInfoVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfoVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{

        ImageView appPic;
        TextView appName;
        View rightLineView;
        View buttomLineView;

    }

    private int getLastPosit(){
        if (appInfoVOs!=null){
            int count=(this.appInfoVOs.size()/3)+((this.appInfoVOs.size()%3!=0)?1:0);

            return (count-1)*3;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.view_home_app_item,null);

            viewHolder.appPic= (ImageView) convertView.findViewById(R.id.app_logo);
            viewHolder.appName= (TextView) convertView.findViewById(R.id.app_name);
            viewHolder.rightLineView=convertView.findViewById(R.id.right_lieve_view);
            viewHolder.buttomLineView=convertView.findViewById(R.id.buttom_lieve_view);


            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        AppInfoVO appInfoVO = appInfoVOs.get(position);

        viewHolder.appPic.setImageDrawable(BitmapUtils.getDrawable(context,appInfoVO.getPicId()));
        viewHolder.appName.setText(appInfoVO.getName());
        if (position%3==2){
            viewHolder.rightLineView.setVisibility(View.GONE);
        }else{
            viewHolder.rightLineView.setVisibility(View.VISIBLE);
        }

        if ((position+1)>getLastPosit()){
            viewHolder.buttomLineView.setVisibility(View.GONE);
        }else{
            viewHolder.buttomLineView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
