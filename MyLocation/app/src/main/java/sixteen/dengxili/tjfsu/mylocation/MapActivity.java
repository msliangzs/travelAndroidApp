package sixteen.dengxili.tjfsu.mylocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.location.e.a.l;
import static com.baidu.location.e.a.u;

public class MapActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_view);
        mapView=(MapView)findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();

        baiduMap.setMyLocationEnabled(true);
        //positionText=(TextView)findViewById(R.id.positon_text_view);
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this,Manifest.permission.READ_PHONE_STATE)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }else {
            requestLocation();
        }

    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    protected  void onResume(){
        super.onResume();
        mapView.onResume();
    }
    protected  void onPause(){
        super.onPause();
        mapView.onPause();
    }
    protected void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
    public void onRequestPermissionsResult(int requestCode,String [] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result:grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }

    }
    public class MyLocationListener implements BDLocationListener {
        public void onReceiveLocation( BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() ==
                    BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }

    }



}
