package com.ykx.organization.pages.home.operates.wallet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.storage.vo.WalletOrderInfoVO;

public class UBDetailActivity extends BaseActivity {

    private WalletOrderInfoVO walletOrderInfoVO;

    private View zffsView,yjhView,xfxView;

    private TextView lshview,lxview,slview,zffsview,jyhview,xfxview,ubsview,sjview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        walletOrderInfoVO= (WalletOrderInfoVO) getIntent().getSerializableExtra("walletOrderInfoVO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubdetail);

        initUI();
    }


    protected Drawable getPageBackgroudDrawable() {
        return  getSysDrawable(R.color.default_line_color);
    }


    @Override
    protected String titleMessage() {
        return getResString(R.string.activity_operate_wallet_ub_one_detail_title);
    }

    private void initUI(){

        zffsView=findViewById(R.id.zffs_contect_view);
        yjhView=findViewById(R.id.jyh_contect_view);
        xfxView=findViewById(R.id.xfx_contect_view);

        lshview= (TextView) findViewById(R.id.wallet_xfx_view);
        lxview= (TextView) findViewById(R.id.wallet_xfx_view);
        slview= (TextView) findViewById(R.id.wallet_xfx_view);
        zffsview= (TextView) findViewById(R.id.wallet_xfx_view);
        jyhview= (TextView) findViewById(R.id.wallet_xfx_view);
        xfxview= (TextView) findViewById(R.id.wallet_xfx_view);
        ubsview= (TextView) findViewById(R.id.wallet_xfx_view);
        sjview= (TextView) findViewById(R.id.wallet_xfx_view);

        if (walletOrderInfoVO!=null){
            if (walletOrderInfoVO.getType()==1){
                zffsView.setVisibility(View.VISIBLE);
                yjhView.setVisibility(View.VISIBLE);
                xfxView.setVisibility(View.GONE);
            }else{
                zffsView.setVisibility(View.GONE);
                yjhView.setVisibility(View.GONE);
                xfxView.setVisibility(View.VISIBLE);
            }

        }
    }
}
