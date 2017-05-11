package com.ykx.organization.pages.home.operates.wallet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.adapters.WalletHistoryAdapter;
import com.ykx.organization.storage.vo.WalletOrderInfoVO;

import java.util.ArrayList;
import java.util.List;

public class UBActivity extends BaseActivity {

    private ListView orderListView;
    private WalletHistoryAdapter walletHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ub);

        initUI();
        loadData();
    }

    protected Drawable getPageBackgroudDrawable() {
        return  getSysDrawable(R.color.default_line_color);
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.activity_operate_wallet_ub_title);
    }

    private void initUI(){
        orderListView= (ListView) findViewById(R.id.ub_histroy_listview);
        walletHistoryAdapter=new WalletHistoryAdapter(this,null);
        orderListView.setAdapter(walletHistoryAdapter);
        orderListView.setFocusable(false);
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WalletOrderInfoVO walletOrderInfoVO = (WalletOrderInfoVO) parent.getItemAtPosition(position);
                Intent intent=new Intent(UBActivity.this,UBDetailActivity.class);
                intent.putExtra("walletOrderInfoVO",walletOrderInfoVO);

                startActivity(intent);
            }
        });
    }

    private void loadData(){
        List<WalletOrderInfoVO> walletOrderInfoVOs=new ArrayList<>();
        walletOrderInfoVOs.add(new WalletOrderInfoVO(1,1,"充值","2017-05-10",15));
        walletOrderInfoVOs.add(new WalletOrderInfoVO(2,0,"消费","2017-05-10",-25));
        walletOrderInfoVOs.add(new WalletOrderInfoVO(3,1,"充值","2017-05-10",45));
        walletOrderInfoVOs.add(new WalletOrderInfoVO(4,0,"消费","2017-05-10",-35));
        walletOrderInfoVOs.add(new WalletOrderInfoVO(5,1,"充值","2017-05-10",65));

        walletHistoryAdapter.refresh(walletOrderInfoVOs);
        orderListView.getLayoutParams().height= DensityUtil.dip2px(this,67*walletOrderInfoVOs.size());
    }

    public void toUBCZAction(View view){
        Intent intent=new Intent(UBActivity.this,UBRechargeActivity.class);
        startActivity(intent);
    }

    public void toUBSMAction(View view){
        Intent intent=new Intent(UBActivity.this,CommonProblemActivity.class);
        startActivity(intent);
    }

    public void toOrderHistoryAction(View view){
        Intent intent=new Intent(UBActivity.this,UBHistroyListActivity.class);
        startActivity(intent);
    }

}
