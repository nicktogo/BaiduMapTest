package com.example.baidumaptest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


public class MainActivity extends Activity {
	
	private MapView mapView;
	
	private BaiduMap baiduMap;
	
	private LocationClient locationClient;
	
	private boolean isFirstTimeToLocat = true;
	
	private BDLocationListener bdlocationListener = new BDLocationListener(){
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			
			
			
			MyLocationData locationData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					.direction(100)
					.latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			
			baiduMap.setMyLocationData(locationData);
			
			
			if(isFirstTimeToLocat){
				isFirstTimeToLocat = false;
				LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
				MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(latlng, 16);
				baiduMap.animateMapStatus(update);
			}
			
					
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        
        mapView = (MapView) findViewById(R.id.map_view);
        
        baiduMap = mapView.getMap();
        
        baiduMap.setMyLocationEnabled(true);
        
        locationClient = new LocationClient(getApplicationContext());
        
        locationClient.registerLocationListener(bdlocationListener);
        
        //set locat args
        
        
        
        LocationClientOption option = new LocationClientOption();
        
        option.setOpenGps(true);
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setScanSpan(5*1000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);//手机机头方向
        
        locationClient.setLocOption(option);
        
        locationClient.start();
        
        Log.d("MainActivity","" + locationClient.requestLocation());
        
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	mapView.onResume();
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	mapView.onPause();
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
