package com.ykx.organization.pages.home.operates.wallet;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.baselibs.utils.DensityUtil;
import com.ykx.organization.R;
import com.ykx.organization.libs.pays.alipay.AlipayManager;
import com.ykx.organization.libs.pays.wx.WXPayManager;
import com.ykx.organization.storage.vo.CZTypeInfoVO;

import java.util.ArrayList;
import java.util.List;

public class UBRechargeActivity extends BaseActivity {


    private GridView gridView;
    private SelectedTypeAdapter selectedTypeAdapter;

    private ImageView zfbCheckView,wxCheckView,yeCheckView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubrecharge);

        initUI();
        loadData();
    }

    @Override
    protected String titleMessage() {
        return getResString(R.string.activity_operate_wallet_ub_recharge_title);
    }

    private void initUI(){
        zfbCheckView= (ImageView) findViewById(R.id.zfb_check_view);
        wxCheckView= (ImageView) findViewById(R.id.wx_check_view);
        yeCheckView= (ImageView) findViewById(R.id.ye_check_view);
        gridView= (GridView) findViewById(R.id.cz_style_view);
        selectedTypeAdapter=new SelectedTypeAdapter(this,null);
        gridView.setAdapter(selectedTypeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeAdapter.resetSelect(position);
            }
        });
    }

    private void loadData(){
        List<CZTypeInfoVO> czTypeInfoVOs=new ArrayList<>();
        czTypeInfoVOs.add(new CZTypeInfoVO(1,100,10.00,null));
        czTypeInfoVOs.add(new CZTypeInfoVO(2,200,20.00,null));
        czTypeInfoVOs.add(new CZTypeInfoVO(3,300,30.00,null));
        czTypeInfoVOs.add(new CZTypeInfoVO(4,500,40.00,""));
        czTypeInfoVOs.add(new CZTypeInfoVO(5,1500,50.00,null));
        czTypeInfoVOs.add(new CZTypeInfoVO(6,3000,60.00,null));

        int hnum=czTypeInfoVOs.size()/2+((czTypeInfoVOs.size()%2!=0)?1:0)-1;
        selectedTypeAdapter.refresh(czTypeInfoVOs);
        gridView.getLayoutParams().height= DensityUtil.dip2px(this,26+hnum*13+53*(hnum+1));
    }



    public void zfbAction(View view){
        zfbCheckView.setVisibility(View.VISIBLE);
        wxCheckView.setVisibility(View.INVISIBLE);
        yeCheckView.setVisibility(View.INVISIBLE);

    }

    public void wxAction(View view){
        zfbCheckView.setVisibility(View.INVISIBLE);
        wxCheckView.setVisibility(View.VISIBLE);
        yeCheckView.setVisibility(View.INVISIBLE);
    }

    public void yeAction(View view){
//        zfbCheckView.setVisibility(View.INVISIBLE);
//        wxCheckView.setVisibility(View.INVISIBLE);
//        yeCheckView.setVisibility(View.VISIBLE);
    }

    public void doCZAction(View view){
        CZTypeInfoVO czTypeInfoVO=selectedTypeAdapter.getCZTypeInfoVO();
        if (czTypeInfoVO!=null){

            if (zfbCheckView.getVisibility()==View.VISIBLE){
                new AlipayManager(this).payV2();
            }else if(wxCheckView.getVisibility()==View.VISIBLE){
                new WXPayManager(this).pay();
            }else if(yeCheckView.getVisibility()==View.VISIBLE){

            }
        }
    }


    public class SelectedTypeAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<CZTypeInfoVO> czTypeInfoVOs;
        private int selectedIndex;

        public SelectedTypeAdapter(Context context,List<CZTypeInfoVO> czTypeInfoVOs){

            this.layoutInflater=LayoutInflater.from(context);
            if (czTypeInfoVOs==null){
                czTypeInfoVOs=new ArrayList<>();
            }
            this.czTypeInfoVOs=czTypeInfoVOs;
            selectedIndex=0;
        }

        public void refresh(List<CZTypeInfoVO> czTypeInfoVOs){
            if (czTypeInfoVOs==null){
                czTypeInfoVOs=new ArrayList<>();
            }
            this.czTypeInfoVOs=czTypeInfoVOs;
            selectedIndex=0;
            notifyDataSetChanged();
        }

        public void resetSelect(int index){
            selectedIndex=index;
            notifyDataSetChanged();
        }

        public CZTypeInfoVO getCZTypeInfoVO(){
            if (selectedIndex<czTypeInfoVOs.size()) {
                return czTypeInfoVOs.get(selectedIndex);
            }
            return  null;
        }

        @Override
        public int getCount() {
            return czTypeInfoVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return czTypeInfoVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHold{
            TextView ubNumView;
            TextView ubPriceView;
            ImageView yhTagImage;
            View bgView;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHold viewHold;
            if (convertView==null){
                viewHold=new ViewHold();
                convertView=layoutInflater.inflate(R.layout.activity_wallet_selected_type_item,null);
                viewHold.bgView= convertView.findViewById(R.id.item_content_view);
                viewHold.ubNumView= (TextView) convertView.findViewById(R.id.ub_num_view);
                viewHold.ubPriceView= (TextView) convertView.findViewById(R.id.ub_price_view);
                viewHold.yhTagImage= (ImageView) convertView.findViewById(R.id.ub_yh_view);

                convertView.setTag(viewHold);
            }else{
                viewHold= (ViewHold) convertView.getTag();
            }
            Resources resource =getBaseContext().getResources();
            if (position==selectedIndex){
                viewHold.bgView.setBackgroundResource(R.drawable.corner_view_wallet_dh_type_selected_bg);
                ColorStateList white = resource.getColorStateList(R.color.white);
                viewHold.ubNumView.setTextColor(white);
                viewHold.ubPriceView.setTextColor(white);
            }else{
                viewHold.bgView.setBackgroundResource(R.drawable.corner_view_wallet_dh_type_unselected_bg);
                viewHold.ubNumView.setTextColor(resource.getColorStateList(R.color.default_first_text_color));
                viewHold.ubPriceView.setTextColor(resource.getColorStateList(R.color.default_second_text_color));
            }

            CZTypeInfoVO czTypeInfoVO=czTypeInfoVOs.get(position);
            viewHold.ubNumView.setText(String.valueOf(czTypeInfoVO.getUbNum()+" U币"));
            viewHold.ubPriceView.setText(String.valueOf("¥"+czTypeInfoVO.getPrices()));
            if (czTypeInfoVO.getYhl()!=null){
                viewHold.yhTagImage.setVisibility(View.VISIBLE);
            }else{
                viewHold.yhTagImage.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }
    }

}
