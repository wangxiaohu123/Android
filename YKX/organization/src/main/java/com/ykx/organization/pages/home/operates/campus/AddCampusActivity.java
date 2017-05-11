package com.ykx.organization.pages.home.operates.campus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.ykx.baselibs.https.HttpCallBack;
import com.ykx.baselibs.https.QNFileUpAndDownManager;
import com.ykx.baselibs.libs.xmls.PreManager;
import com.ykx.baselibs.views.SubmitStateView;
import com.ykx.baselibs.vo.FileVO;
import com.ykx.organization.R;
import com.ykx.organization.pages.bases.BaseUploadEditPicsActivity;
import com.ykx.organization.pages.bases.MapContainer;
import com.ykx.organization.pages.bases.MyMapView;
import com.ykx.organization.servers.OperateServers;
import com.ykx.organization.storage.vo.AddressVO;
import com.ykx.organization.storage.vo.CampusVO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddCampusActivity extends BaseUploadEditPicsActivity implements LocationSource,AMapLocationListener {

    private final int ADDRESS_REQUEST_CODE_TAG=1000;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private Marker marker;

    private GeocodeSearch geocoderSearch;

    private MyMapView mapView;
    private AMap aMap;

    private EditText xqNameView;
    private TextView addressView,xqPhoneView;
    private SubmitStateView submitStateView;

    private LatLonPoint latLonPoint;

    private LatLonPoint myLocation;

    private String adcode;

    private CampusVO campusVO;

    private boolean isUserLocation=true;

    private boolean addCampus=false;

    private boolean firstAddCampus=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        campusVO= (CampusVO) getIntent().getSerializableExtra("campusVO");
        addCampus=getIntent().getBooleanExtra("addCampus",false);
        firstAddCampus=getIntent().getBooleanExtra("firstAddCampus",true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campus);
        initUI(savedInstanceState);
        if (campusVO==null) {
            createView(null, null);
        }else{
            loadCampusInfo();
        }
        resetUI();
    }


    private void loadCampusInfo(){
        if (campusVO!=null) {
            new OperateServers().detailCampusDatas(String.valueOf(campusVO.getId()), new HttpCallBack<CampusVO>() {
                @Override
                public void onSuccess(CampusVO data) {
                    campusVO=data;
                    if (campusVO!=null){
                        resetInfo();
                        createView(campusVO.getPhoto(), campusVO.getPhoto_url());
                    }
                }

                @Override
                public void onFail(String msg) {

                }
            });
        }

    }


    private void resetUI(){

        setUnAbleNull(R.id.add_campus_xqmc);
        setUnAbleNull(R.id.add_campus_xqdz);
        setUnAbleNull(R.id.add_campus_xqdh);
        setUnAbleNull(R.id.add_campus_xqzp);
    }

    private void initUI(Bundle savedInstanceState){
        ScrollView basetSV= (ScrollView) findViewById(R.id.activity_add_curriculum);
        xqNameView= (EditText) findViewById(R.id.xq_name_textview);
        addressView= (TextView) findViewById(R.id.curriculum_type_view);
        xqPhoneView= (TextView) findViewById(R.id.xq_phone);
        mapView= (MyMapView) findViewById(R.id.amap_mapview);
        mapView.onCreate(savedInstanceState);
        submitStateView= (SubmitStateView) findViewById(R.id.submit_view);

        View firstview=findViewById(R.id.campus_title_first_view);
        TextView secondview= (TextView) findViewById(R.id.campus_title_second_view);
        if (firstAddCampus){
            secondview.setVisibility(View.GONE);
            firstview.setVisibility(View.VISIBLE);
        }else{
            String message=String.format(getResources().getString(R.string.curriculum_activity_nav_title),PreManager.getInstance().getData(PreManager.DEFAULT_BRANDNAME,""));
            secondview.setText(message);
            secondview.setVisibility(View.VISIBLE);
            firstview.setVisibility(View.GONE);
        }

        MapContainer mapcontainer = (MapContainer) findViewById(R.id.map_container);
        mapcontainer.setScrollView(basetSV);


        initMap();

        resetInfo();

    }

    private void resetInfo(){
        if (campusVO!=null){
            isUserLocation=false;
            xqNameView.setText(campusVO.getName());
            xqPhoneView.setText(campusVO.getContact());
            addressView.setText(campusVO.getAddress());
            adcode=campusVO.getAdcode();
            try {
                latLonPoint = new LatLonPoint(Double.valueOf(campusVO.getLat()), Double.valueOf(campusVO.getLng()));

                LatLng location = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
                addMarker(location);
            }catch (Exception e){

            }
        }else{
            isUserLocation=true;
        }
    }


    @Override
    protected int getGridViewContentId() {

        return R.id.campus_contents_pics;
    }

    public void useLocationAction(View view){
        isUserLocation=true;
        setNewLocation();
    }

    private void setNewLocation(){
        if (myLocation!=null) {
            latLonPoint = new LatLonPoint(myLocation.getLatitude(), myLocation.getLongitude());
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
            LatLng location = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
            addMarker(location);
        }
    }

    private void initMap(){

        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示

//        MyLocationStyle myLocationStyle=new MyLocationStyle();
//        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
//                R.drawable.svg_location);
//        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
//        myLocationStyle.myLocationIcon(des);
//        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener(){
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                if (isUserLocation) {
                    if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                        if (result != null && result.getRegeocodeAddress() != null
                                && result.getRegeocodeAddress().getFormatAddress() != null) {
                            String addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";
                            adcode = result.getRegeocodeAddress().getAdCode();
                            addressView.setText(addressName);
                        } else {
                            addressView.setText("位置信息暂无");
                        }
                    } else {
//                    toastMessage(""+rCode);
                    }
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        mapView.setCallBackListener(new MyMapView.CallBackListener() {
            @Override
            public void callUp() {
                isUserLocation=true;
                CameraPosition cameraPosition = aMap.getCameraPosition();
                LatLng latLng=cameraPosition.target;
                myLocation = new LatLonPoint(latLng.latitude, latLng.longitude);
                RegeocodeQuery query = new RegeocodeQuery(myLocation, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            }

            @Override
            public void callDown() {

            }

            @Override
            public void move() {
                CameraPosition cameraPosition = aMap.getCameraPosition();
                LatLng latLng=cameraPosition.target;
                marker.setPosition(latLng);
            }
        });
    }

    public void selectedAddressAction(View view){

        Intent intent=new Intent(this,CampusAddressActivity.class);

        if (myLocation!=null){
            String localname="当前位置";
            String address=addressView.getText().toString();
            AddressVO addressVO=new AddressVO(localname,address,myLocation.getLatitude(),myLocation.getLongitude());
            addressVO.setSearchflag(false);

            intent.putExtra("location",addressVO);
        }


        startActivityForResult(intent,ADDRESS_REQUEST_CODE_TAG);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==ADDRESS_REQUEST_CODE_TAG){
                AddressVO addressVO= (AddressVO) data.getSerializableExtra("addressVO");
                // TODO: 2017/3/23
                if (addressVO!=null) {
                    adcode = addressVO.getAdCode();
                    addressView.setText(addressVO.getAddress());

                    latLonPoint = new LatLonPoint(addressVO.getLat(), addressVO.getLng());
                    LatLng location = new LatLng(addressVO.getLat(), addressVO.getLng());
                    addMarker(location);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();

        submitStateView.setTextAndState(SubmitStateView.STATE.NORMAL,getResString(R.string.campus_activity_submit),null);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected String titleMessage() {
        if (campusVO==null) {
            return getResources().getString(R.string.curriculum_activity_title);
        }else{
            return getResources().getString(R.string.curriculum_activity_edit_title);
        }
    }


    public void saveAction(View view){

        final String name=xqNameView.getText().toString();
        final String address=addressView.getText().toString();
        final String contact=xqPhoneView.getText().toString();
        String latold="";
        String lngold="";
        if (latLonPoint!=null){
            latold=String.valueOf(latLonPoint.getLatitude());
            lngold=String.valueOf(latLonPoint.getLongitude());
        }
        final String lat=latold;
        final String lng=lngold;

        if (name.length()<=0){
            toastMessage(getResources().getString(R.string.campus_activity_add_xqmc_hint));
            return;
        }

        if ((address.length()<=0)||(latLonPoint==null)||(adcode==null)){
            toastMessage(getResources().getString(R.string.campus_activity_add_xqdz_toast));
            return;
        }

        if (contact.length()<=0){
            toastMessage(getResources().getString(R.string.campus_activity_add_xqdh_hint));
            return;
        }

        if (contact.length()>12){
            toastMessage(getResources().getString(R.string.campus_activity_add_xqdh_num_toast));
            return;
        }

        if (fileIsNull()){
            toastMessage(getResources().getString(R.string.campus_activity_add_xqdh_pics_toast));
            return;
        }

        submitStateView.setTextAndState(SubmitStateView.STATE.LOADING,getResString(R.string.sys_submit_toast),null);

        List<FileVO> files = getFiles();
        if (files.size()>0){
            QNFileUpAndDownManager qnFileUpAndDownManager= new QNFileUpAndDownManager();
            qnFileUpAndDownManager.init();
            qnFileUpAndDownManager.upfiles(files, new QNFileUpAndDownManager.FileCallBack() {
                @Override
                public void callback(boolean uploadtag, LinkedHashMap<String, String> uploadfiles) {
                    if (uploadtag) {
                        StringBuffer stringBuffer=new StringBuffer("");
                        for (Map.Entry<String,String> entry : uploadfiles.entrySet()){
                            if (stringBuffer.length()<=0){
                                stringBuffer.append(entry.getValue());
                            }else{
                                stringBuffer.append(",").append(entry.getValue());
                            }
                        }
                        submitData(stringBuffer.toString(),name,adcode,lat,lng,address,contact);
                    }
                }});
        }else{
            submitData("",name,adcode,lat,lng,address,contact);
        }
    }

    private void submitData(String photo,String name,String adcode,String lat,String lng,String address,String contact){

        String photos=getoldPics().append(photo).toString();

        if (campusVO==null) {
            new OperateServers().addCampus(photos,name, adcode, lat, lng, address, contact, new HttpCallBack<CampusVO>() {
                @Override
                public void onSuccess(CampusVO data) {

                    if (addCampus){
                        Intent intent = new Intent();
                        intent.putExtra("CampusVO", data);
                        setResult(RESULT_OK,intent);
                    }else{
                        Intent intent = new Intent(AddCampusActivity.this, CampusListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("CampusVO", data);
                        startActivity(intent);
                    }
                    finish();
                    submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.campus_activity_submit)+getResString(R.string.sys_toast_success));
                }

                @Override
                public void onFail(String msg) {
                    submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.campus_activity_submit),getResString(R.string.campus_activity_submit)+getResString(R.string.sys_toast_fail));

                }
            });
        }else{
            new OperateServers().editCampus(photos,String.valueOf(campusVO.getId()),name, adcode, lat, lng, address, contact, new HttpCallBack<CampusVO>() {
                @Override
                public void onSuccess(CampusVO data) {
                    Intent intent = new Intent(AddCampusActivity.this, CampusListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("CampusVO", data);
                    startActivity(intent);
                    finish();
                    submitStateView.setTextAndState(SubmitStateView.STATE.SUCCESS,null,getResString(R.string.campus_activity_submit)+getResString(R.string.sys_toast_success));
                }

                @Override
                public void onFail(String msg) {

                    submitStateView.setTextAndState(SubmitStateView.STATE.FAIL,getResString(R.string.campus_activity_submit),getResString(R.string.campus_activity_submit)+getResString(R.string.sys_toast_fail));
                }
            });
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
//                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));

                myLocation = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());

                if (isUserLocation){
                    setNewLocation();
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    private void addMarker(LatLng latlng) {
        if (marker != null) {
            marker.remove();
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.my_location);//svg_location
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        marker = aMap.addMarker(options);
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));
//        marker.setTitle(LOCATION_MARKER_FLAG);
    }
}
