/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hyphenate.easeui.R;

public class EaseBaiduMapActivity extends EaseBaseActivity implements LocationSource,AMapLocationListener {

	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;


	private GeocodeSearch geocoderSearch;

	private MapView mapView;
	private AMap aMap;
	private Marker marker;

	private LatLonPoint myLocation;

	private Button sendButton = null;

	private boolean isUserLocation=true;

	private String addressName;

	public static EaseBaiduMapActivity instance = null;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
//        SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.ease_activity_baidumap);
		mapView= (MapView) findViewById(R.id.amap_mapview);
		mapView.onCreate(savedInstanceState);
		sendButton = (Button) findViewById(R.id.btn_location_send);
		Intent intent = getIntent();
		double latitude = intent.getDoubleExtra("latitude", 0);
		initMapView();
		if (latitude == 0) {
		} else {
			isUserLocation=false;
			double longtitude = intent.getDoubleExtra("longitude", 0);
			String address = intent.getStringExtra("address");
			showMap(latitude, longtitude, address);
		}
	}

	private void initMapView(){

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
							addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";
						} else {
						}
					} else {
					}
				}
			}

			@Override
			public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

			}
		});
	}

	private void showMap(double latitude, double longtitude, String address) {
		sendButton.setVisibility(View.GONE);
		myLocation = new LatLonPoint(latitude, longtitude);
		setNewLocation();
	}


	public void back(View v) {
		finish();
	}

	public void sendLocation(View view) {
		Intent intent = this.getIntent();
		if (myLocation!=null) {
			intent.putExtra("latitude", myLocation.getLatitude());
			intent.putExtra("longitude", myLocation.getLongitude());
		}else{
			finish();
		}
		intent.putExtra("address", addressName);
		this.setResult(RESULT_OK, intent);
		finish();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
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
		if (isUserLocation) {
			if (mListener != null && amapLocation != null) {
				if (amapLocation != null
						&& amapLocation.getErrorCode() == 0) {
//                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
					myLocation = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
					setNewLocation();

				} else {
					String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
					Log.e("AmapErr", errText);
				}
			}
		}
	}

	private void setNewLocation(){
		if (myLocation!=null) {
			LatLonPoint latLonPoint = new LatLonPoint(myLocation.getLatitude(), myLocation.getLongitude());
			RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
			geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
			LatLng location = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
			addMarker(location);
		}
	}


	private void addMarker(LatLng latlng) {
		if (marker != null) {
			marker.remove();
		}
		Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ease_icon_marka);//svg_location
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
