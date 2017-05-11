package com.ykx.organization.pages.home.operates.campus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;
import com.ykx.organization.storage.vo.AddressVO;

import java.util.ArrayList;
import java.util.List;

public class CampusAddressActivity extends BaseActivity {

    private EditText searchAddressView;
    private ListView addressListView;

    private AddressAdapter addressAdapter;

    private AddressVO locationaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationaddress= (AddressVO) getIntent().getSerializableExtra("location");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_address);

        initUI();
    }

    private void initUI(){

        addressListView= (ListView) findViewById(R.id.address_listview);
        searchAddressView= (EditText) findViewById(R.id.search_editview);

        View nullview=findViewById(R.id.null_view);
        int statusBarHeight1 = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        nullview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,statusBarHeight1));


        List<AddressVO> addressVOs=new ArrayList<>();
        if (locationaddress!=null) {
            addressVOs.add(locationaddress);
        }
        addressAdapter=new AddressAdapter(addressVOs,this);
        addressListView.setAdapter(addressAdapter);

        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddressVO addressVO= (AddressVO) parent.getItemAtPosition(position);
                Intent intent=new Intent();
                intent.putExtra("addressVO",addressVO);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

        searchAddressView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPOIData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchPOIData(String address){

        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(address, "成都市");
        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(this, inputquery);
        inputTips.setInputtipsListener(inputtipsListener);
        inputTips.requestInputtipsAsyn();


//        PoiSearch.Query query = new PoiSearch.Query(address, "","成都市");
//        query.setPageSize(50);// 设置每页最多返回多少条poiitem
//        query.setPageNum(1);//设置查询页码
//
//        PoiSearch poiSearch=new PoiSearch(CampusAddressActivity.this,query);
//        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
//            @Override
//            public void onPoiSearched(PoiResult poiResult, int i) {
//                Log.e("xx","data="+new Gson().toJson(poiResult));
//                ArrayList<PoiItem> poiItems = poiResult.getPois();
//
//                refreshList(poiItems);
//
//
//            }
//
//            @Override
//            public void onPoiItemSearched(PoiItem poiItem, int i) {
//
//            }
//        });
//        poiSearch.searchPOIAsyn();

    }

    private Inputtips.InputtipsListener inputtipsListener=new Inputtips.InputtipsListener(){
        @Override
        public void onGetInputtips(List<Tip> list, int i) {
            refreshList(list);
        }
    };


    private void refreshList(List<Tip> poiItems){
        List<AddressVO> addressVOs=new ArrayList<>();
        if (locationaddress!=null) {
            addressVOs.add(locationaddress);
        }
        for (Tip poiItem:poiItems){
            LatLonPoint latLonPoint=poiItem.getPoint();//.getLatLonPoint();
            StringBuffer address=new StringBuffer("");
//            address.append(poiItem.getProvinceName()).append(poiItem.getCityName()).append(poiItem.getAdName()).append(poiItem.getSnippet());
            address.append(poiItem.getAddress());

            Log.e("xx","name="+poiItem.getName()+",address="+poiItem.getAddress());


            AddressVO addressVO=new AddressVO(poiItem.getName(),address.toString(),latLonPoint.getLatitude(),latLonPoint.getLongitude());
            addressVO.setAdCode(poiItem.getAdcode());
            addressVOs.add(addressVO);
        }
        addressAdapter.setAddressVOs(addressVOs);
    }

//    private void searchData(String address){
//        CloudSearch mCloudSearch = new CloudSearch(this);// 初始化查询类
//        mCloudSearch.setOnCloudSearchListener(new CloudSearch.OnCloudSearchListener() {
//            @Override
//            public void onCloudSearched(CloudResult cloudResult, int i) {
//
//                ArrayList<CloudItem> cloudItems=cloudResult.getClouds();
//
//                refreshList(cloudItems);
//            }
//
//            @Override
//            public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {
//
//            }
//        });// 设置回调函数
//        CloudSearch.SearchBound bound = new CloudSearch.SearchBound("全国");// 输入city “全国”，为本表全部搜索。
//        try {
//            CloudSearch.Query mQuery = new CloudSearch.Query("", address, bound);
//            mCloudSearch.searchCloudAsyn(mQuery);
//        } catch (AMapException e) {
//            e.printStackTrace();
//        }
//
//    }

//    private void search(String address){
//        GeocodeSearch geocoderSearch = new GeocodeSearch(CampusAddressActivity.this);
//        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//            }
//
//            @Override
//            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//                List<GeocodeAddress> geocodeAddresses = geocodeResult.getGeocodeAddressList();
//                refreshList(geocodeAddresses);
//
//            }
//        });
//        GeocodeQuery query = new GeocodeQuery(address, "");
//        geocoderSearch.getFromLocationNameAsyn(query);
//
//    }

    private void refreshList(ArrayList<PoiItem> poiItems){
        List<AddressVO> addressVOs=new ArrayList<>();
        if (locationaddress!=null) {
            addressVOs.add(locationaddress);
        }
        for (PoiItem poiItem:poiItems){
            LatLonPoint latLonPoint=poiItem.getLatLonPoint();
            StringBuffer address=new StringBuffer("");
            address.append(poiItem.getProvinceName()).append(poiItem.getCityName()).append(poiItem.getAdName()).append(poiItem.getSnippet());



            AddressVO addressVO=new AddressVO(poiItem.getTitle(),address.toString(),latLonPoint.getLatitude(),latLonPoint.getLongitude());
            addressVO.setAdCode(poiItem.getAdCode());
            addressVOs.add(addressVO);
        }
        addressAdapter.setAddressVOs(addressVOs);
    }

    @Override
    protected boolean isHideActionBar() {
        return true;
    }

    public void cannelAction(View view){

        finish();
    }

    class AddressAdapter extends BaseAdapter{

        private LayoutInflater layoutInflater;
        private List<AddressVO> addressVOs;

        private AddressAdapter(List<AddressVO> addressVOs, Context context){
            if (addressVOs==null){
                addressVOs=new ArrayList<>();
            }
            this.addressVOs=addressVOs;

            this.layoutInflater=LayoutInflater.from(context);

        }

        public List<AddressVO> getAddressVOs() {
            return addressVOs;
        }

        public void setAddressVOs(List<AddressVO> addressVOs) {
            this.addressVOs = addressVOs;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return addressVOs.size();
        }

        @Override
        public Object getItem(int position) {
            return addressVOs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewhHolder{
            TextView nameView;
            TextView addressView;
            ImageView flagView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewhHolder viewhHolder;
            if (convertView==null){
                viewhHolder=new ViewhHolder();
                convertView=layoutInflater.inflate(R.layout.activity_campus_address_item,null);
                viewhHolder.nameView= (TextView) convertView.findViewById(R.id.name_textView);
                viewhHolder.addressView= (TextView) convertView.findViewById(R.id.address_textView);
                viewhHolder.flagView= (ImageView) convertView.findViewById(R.id.flag_view);

                convertView.setTag(viewhHolder);
            }else{
                viewhHolder= (ViewhHolder) convertView.getTag();
            }

            AddressVO addressVO = addressVOs.get(position);
            viewhHolder.nameView.setText(addressVO.getNamne());
            viewhHolder.addressView.setText(addressVO.getAddress());

            if (!addressVO.isSearchflag()){
                viewhHolder.flagView.setVisibility(View.VISIBLE);
            }else{
                viewhHolder.flagView.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }
    }
}
