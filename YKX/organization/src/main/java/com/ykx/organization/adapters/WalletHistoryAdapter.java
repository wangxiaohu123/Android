package com.ykx.organization.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ykx.organization.R;
import com.ykx.organization.storage.vo.WalletOrderInfoVO;

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
 * Created by 2017/5/10.
 * <p>
 * <p>
 * ********************************************************************************
 */

public class WalletHistoryAdapter extends BaseAdapter {


    private LayoutInflater layoutInflater;
    private List<WalletOrderInfoVO> walletOrderInfoVOs;

    public WalletHistoryAdapter(Context context,List<WalletOrderInfoVO> walletOrderInfoVOs){
        this.layoutInflater=LayoutInflater.from(context);
        if (walletOrderInfoVOs==null){
            walletOrderInfoVOs=new ArrayList<>();
        }
        this.walletOrderInfoVOs=walletOrderInfoVOs;

    }

    public void refresh(List<WalletOrderInfoVO> walletOrderInfoVOs){
        if (walletOrderInfoVOs==null){
            walletOrderInfoVOs=new ArrayList<>();
        }
        this.walletOrderInfoVOs=walletOrderInfoVOs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return walletOrderInfoVOs.size();
    }

    @Override
    public Object getItem(int position) {
        return walletOrderInfoVOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder{
        TextView titleView;
        TextView timeView;
        TextView countView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.activity_wallet_history_item,null);
            viewHolder.titleView= (TextView) convertView.findViewById(R.id.xf_title_view);
            viewHolder.timeView= (TextView) convertView.findViewById(R.id.xf_time_view);
            viewHolder.countView= (TextView) convertView.findViewById(R.id.xf_num_view);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        WalletOrderInfoVO walletOrderInfoVO=walletOrderInfoVOs.get(position);
        viewHolder.titleView.setText(walletOrderInfoVO.getTitle());
        viewHolder.timeView.setText(walletOrderInfoVO.getTime());
        viewHolder.countView.setText(String.valueOf(walletOrderInfoVO.getCount()+"个"));

        return convertView;
    }
}
